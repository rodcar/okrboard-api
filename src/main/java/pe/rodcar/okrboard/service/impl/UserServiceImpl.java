package pe.rodcar.okrboard.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.rodcar.okrboard.entities.User;
import pe.rodcar.okrboard.repository.UserRepository;
import pe.rodcar.okrboard.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public List<User> findAll() throws Exception {
		return userRepository.findAll();
	}

	@Override
	public User save(User t) throws Exception {
		return userRepository.save(t);
	}

	@Override
	public User update(User t) throws Exception {
		return userRepository.save(t);
	}

	@Override
	public void deleteById(Long id) throws Exception {
		userRepository.deleteById(id);
	}

	@Override
	public void deleteAll() throws Exception {
		
	}

	@Override
	public Optional<User> findById(Long id) throws Exception {	
		return userRepository.findById(id);
	}

	@Override
	public boolean existsById(Long id) {
		return userRepository.existsById(id);
	}

}
