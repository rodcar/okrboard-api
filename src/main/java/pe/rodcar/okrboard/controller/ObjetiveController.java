package pe.rodcar.okrboard.controller;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
import pe.rodcar.okrboard.entities.Objetive;
import pe.rodcar.okrboard.service.ObjetiveService;

@RestController
@RequestMapping("/objetives")
@Api(value = "REST of objetives management")
public class ObjetiveController {

	@Autowired
	private ObjetiveService objetiveService;
	
	@ApiOperation("List of all objetives")
	@GetMapping(produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Objetive>> fetchObjetives() {
		try {
			List<Objetive> objetives = new ArrayList<>();
			objetives = objetiveService.findAll();
			return new ResponseEntity<List<Objetive>>(objetives, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@ApiOperation("Save an objetive")
	@PostMapping(consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> saveObjetive(@Valid @RequestBody Objetive objetive) {
		try {
			Objetive o = new Objetive();
			o = objetiveService.save(objetive);
			URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(o.getId()).toUri();
			return ResponseEntity.created(location).build();
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@ApiOperation("Update an objetive")
	@PutMapping(value= "/{id}", consumes= MediaType.APPLICATION_JSON_VALUE, produces= MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> updateObjetive(@PathVariable("id") Long id, @RequestBody Objetive objetive) {
		try {
			objetive.setId(id);
			objetiveService.update(objetive);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@ApiOperation("Delete an objetive")
	@DeleteMapping(value = "/{id}", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> deleteObjetive(@PathVariable("id") Long id) {
		try {
			Optional<Objetive> objetive = objetiveService.findById(id);
			
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
	@GetMapping(value= "/{id}", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Objetive> fetchObjetive(@PathVariable("id") Long id) {
		try {
			Optional<Objetive> objetive = objetiveService.findById(id);
			
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
