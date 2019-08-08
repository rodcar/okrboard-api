package pe.rodcar.okrboard.service;

import java.util.Optional;

import pe.rodcar.okrboard.entities.User;

public interface UserService extends CrudService<User>{
	Optional<User> findById(Long id) throws Exception;
	//Optional<User> findByUsername(String username);
	boolean existsById(Long id);
}
