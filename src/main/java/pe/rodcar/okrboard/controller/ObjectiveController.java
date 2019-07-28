package pe.rodcar.okrboard.controller;

import java.net.URI;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import pe.rodcar.okrboard.entities.Objective;
import pe.rodcar.okrboard.entities.User;
import pe.rodcar.okrboard.security.services.UserPrinciple;
import pe.rodcar.okrboard.service.ObjectiveService;
import pe.rodcar.okrboard.service.UserService;

@RestController
@RequestMapping("/users")
@Api(value = "REST of objetives management")
public class ObjectiveController {

	@Autowired
	private ObjectiveService objetiveService;
	
	@Autowired
	private UserService userService;
	
	@ApiOperation("List of all objetives of an user by id")
	@GetMapping(value="/{userId}/objectives", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Objective>> fetchObjetivesByUserId(@PathVariable("userId") Long userId) {
		if (!userService.existsById(userId)) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		try {
			List<Objective> objetives = new ArrayList<>();
			objetives = objetiveService.findByUserId(userId);
			return new ResponseEntity<List<Objective>>(objetives, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PreAuthorize("hasRole('USER')")
	@ApiOperation(value="Save an objective", authorizations = @Authorization(value = "Bearer"))
	@PostMapping(value="/{userId}/objectives", consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> saveObjetive(@PathVariable("userId") Long userId, @Valid @RequestBody Objective objective, Principal principal) {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		if (authentication instanceof AnonymousAuthenticationToken) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		
		UserPrinciple userPrinciple = (UserPrinciple) authentication.getPrincipal();
		
		if (userPrinciple.getId() != userId) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		
		try {
			User user = new User();
			user.setId(userId);
			objective.setUser(user);
			Objective o = objetiveService.save(objective);
			URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(o.getId()).toUri();
			return ResponseEntity.created(location).build();
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@ApiOperation("Update an objetive")
	@PutMapping(value= "/{userId}/objectives/{id}", consumes= MediaType.APPLICATION_JSON_VALUE, produces= MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> updateObjetive(@PathVariable("id") Long id, @RequestBody Objective objetive) {
		try {
			objetive.setId(id);
			objetiveService.update(objetive);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@ApiOperation("Delete an objetive")
	@DeleteMapping(value = "/{userId}/objectives/{id}", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> deleteObjetive(@PathVariable("id") Long id) {
		try {
			Optional<Objective> objetive = objetiveService.findById(id);
			
			if (!objetive.isPresent()) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);			
			} else {
				objetiveService.deleteById(id);
				return new ResponseEntity<>("The objetive was deleted", HttpStatus.OK);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@ApiOperation("Get an objetive by id")
	@GetMapping(value= "/{userId}/objectives/{id}", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Objective> fetchObjetive(@PathVariable("id") Long id) {
		try {
			Optional<Objective> objetive = objetiveService.findById(id);
			
			if (!objetive.isPresent()) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			} else {
				return new ResponseEntity<>(objetive.get(), HttpStatus.OK);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}		
	}
}
