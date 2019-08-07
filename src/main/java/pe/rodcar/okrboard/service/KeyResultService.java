package pe.rodcar.okrboard.service;

import pe.rodcar.okrboard.entities.KeyResult;

public interface KeyResultService extends CrudService<KeyResult>{
	
	boolean existsById(Long id);
	
}
