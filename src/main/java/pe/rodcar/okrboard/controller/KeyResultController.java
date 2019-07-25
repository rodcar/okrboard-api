package pe.rodcar.okrboard.controller;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import pe.rodcar.okrboard.entities.KeyResult;
import pe.rodcar.okrboard.service.KeyResultService;

@RestController
@RequestMapping("/keyresults")
@Api(value= "REST of key results")
public class KeyResultController {

	@Autowired
	private KeyResultService keyResultService;
	
	@ApiOperation("List of all the key results")
	@GetMapping(produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<KeyResult>> fetchKeyResult() {
		try {
			List<KeyResult> keyResults = new ArrayList<>();
			keyResults = keyResultService.findAll();
			return new ResponseEntity<>(keyResults, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@ApiOperation("Save a key result")
	@PostMapping(consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> saveKeyResult(@Valid @RequestBody KeyResult keyResult) {
		try {
			KeyResult keyResultPersisted = keyResultService.save(keyResult);
			URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(keyResultPersisted.getId()).toUri();
			return ResponseEntity.created(location).build();
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
