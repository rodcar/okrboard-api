package pe.rodcar.okrboard.service;

import java.util.List;

import pe.rodcar.okrboard.entities.Objective;

public interface ObjectiveService extends CrudService<Objective>{

	List<Objective> findByUserId(Long id) throws Exception;
	
	boolean existsById(Long id);
}
