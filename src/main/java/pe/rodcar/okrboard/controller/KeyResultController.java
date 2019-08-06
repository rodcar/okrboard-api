package pe.rodcar.okrboard.controller;

import java.net.URI;
import java.security.Principal;
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
import pe.rodcar.okrboard.entities.KeyResult;
import pe.rodcar.okrboard.entities.Objective;
import pe.rodcar.okrboard.entities.User;
import pe.rodcar.okrboard.security.services.UserPrinciple;
import pe.rodcar.okrboard.service.KeyResultService;
import pe.rodcar.okrboard.service.ObjectiveService;
import pe.rodcar.okrboard.service.UserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/users")
@Api(value= "REST of key results")
public class KeyResultController {
	
	private static final Logger logger = LoggerFactory.getLogger(KeyResultController.class);

	@Autowired
	private KeyResultService keyResultService;
	
	@Autowired
	private ObjectiveService objectiveService;
	
	@Autowired
	private UserService userService;
	
	/*@ApiOperation("List of all the key results")
	@GetMapping(produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<KeyResult>> fetchKeyResult() {
		try {
			List<KeyResult> keyResults = new ArrayList<>();
			keyResults = keyResultService.findAll();
			return new ResponseEntity<>(keyResults, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}*/
	
	@PreAuthorize("hasRole('USER')")
	@ApiOperation(value="Save a key result", authorizations = @Authorization(value="Bearer"))
	@PostMapping(value="/{userId}/objectives/{objectiveId}/keyresults", consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> saveKeyResult(@PathVariable("userId") Long userId, @PathVariable("objectiveId") Long objectiveId, @Valid @RequestBody KeyResult keyResult, Principal principal) {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		if (authentication instanceof AnonymousAuthenticationToken) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		
		UserPrinciple userPrinciple = (UserPrinciple) authentication.getPrincipal();
		
		if (userPrinciple.getId() != userId) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		
		if (!objectiveService.existsById(objectiveId)) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		try {
			User user = new User();
			user.setId(userId);
			Objective objective = new Objective();
			objective.setId(objectiveId);
			objective.setUser(user);
			keyResult.setObjective(objective);
			KeyResult keyResultPersisted = keyResultService.save(keyResult);
			URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(keyResultPersisted.getId()).toUri();
			return ResponseEntity.created(location).build();
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@ApiOperation("Get a Key Result by id")
	@GetMapping(value="/{userId}/objectives/{objectiveId}/keyresults/{id}")
	public ResponseEntity<Object> fetchKeyResultById(@PathVariable("userId") Long userId, @PathVariable("objectiveId") Long objectiveId, @PathVariable("id") Long id) {
		if (!userService.existsById(userId) || !objectiveService.existsById(objectiveId)) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}				
		
		try {
			Optional<KeyResult> keyResult = keyResultService.findById(id);
			
			if (!keyResult.isPresent()) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			} else {
				return new ResponseEntity<>(keyResult.get(), HttpStatus.OK);
			}			
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PreAuthorize("hasRole('USER')")
	@ApiOperation(value="Updates a Key Result by id", authorizations = @Authorization(value="Bearer"))
	@PutMapping(value="/{userId}/objectives/{objectiveId}/keyresults/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<KeyResult> updateKeyResult(@PathVariable("userId") Long userId, @PathVariable("objectiveId") Long objectiveId, @PathVariable("id") Long id, @Valid @RequestBody KeyResult keyResultRequest, Principal principal) {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		if (authentication instanceof AnonymousAuthenticationToken) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		
		UserPrinciple userPrinciple = (UserPrinciple) authentication.getPrincipal();
		
		if (userPrinciple.getId() != userId) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		
		if (!objectiveService.existsById(objectiveId) || !keyResultService.existsById(id)) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		try {
			KeyResult keyResult = keyResultService.findById(id).get();
			
			logger.info("user id finded: " + keyResult.getObjective().getUser().getId() + ", user principle id: " + userPrinciple.getId());			
			if (keyResult.getObjective().getUser().getId() != userPrinciple.getId()) {
				return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
			}
			
			keyResult.setTitle(keyResultRequest.getTitle());
			keyResult.setProgress(keyResultRequest.getProgress());
			KeyResult keyResultUpdated = keyResultService.update(keyResult);
			return new ResponseEntity<>(keyResultUpdated, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}		
	}
	
	@PreAuthorize("hasRole('USER')")
	@ApiOperation(value="Deletes an Key Result by id", authorizations = @Authorization(value = "Bearer"))
	@DeleteMapping(value="/{userId}/objectives/{objectiveId}/keyresults/{id}")
	public ResponseEntity<Object> deleteKeyResult(@PathVariable("userId") Long userId, @PathVariable("objectiveId") Long objectiveId, @PathVariable("id") Long id, Principal principal) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		if (authentication instanceof AnonymousAuthenticationToken) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		
		UserPrinciple userPrinciple = (UserPrinciple) authentication.getPrincipal();
		
		if (userPrinciple.getId() != userId) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		
		if (!objectiveService.existsById(objectiveId) || !keyResultService.existsById(id)) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		
		try {
			KeyResult keyResult = keyResultService.findById(id).get();
			
			if (userPrinciple.getId() != keyResult.getObjective().getUser().getId()) {
				return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
			}
			
			keyResultService.deleteById(id);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
}
