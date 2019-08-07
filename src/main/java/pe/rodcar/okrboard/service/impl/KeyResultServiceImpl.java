package pe.rodcar.okrboard.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.rodcar.okrboard.entities.KeyResult;
import pe.rodcar.okrboard.repository.KeyResultRepository;
import pe.rodcar.okrboard.service.KeyResultService;

@Service
public class KeyResultServiceImpl implements KeyResultService{

	@Autowired
	private KeyResultRepository keyResultRepository;
	
	@Override
	public List<KeyResult> findAll() throws Exception {
		return keyResultRepository.findAll();
	}

	@Override
	public KeyResult save(KeyResult t) throws Exception {
		return keyResultRepository.save(t);
	}

	@Override
	public KeyResult update(KeyResult t) throws Exception {
		return keyResultRepository.save(t);
	}

	@Override
	public void deleteById(Long id) throws Exception {
		keyResultRepository.deleteById(id);
	}

	@Override
	public void deleteAll() throws Exception {
		// Method not implemented
	}

	@Override
	public Optional<KeyResult> findById(Long id) throws Exception {
		return keyResultRepository.findById(id);
	}

	@Override
	public boolean existsById(Long id) { 
		return keyResultRepository.existsById(id);
	}
	
}
