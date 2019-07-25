package pe.rodcar.okrboard.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.rodcar.okrboard.entities.Objetive;
import pe.rodcar.okrboard.repository.ObjetiveRepository;
import pe.rodcar.okrboard.service.ObjetiveService;

@Service
public class ObjetiveServiceImpl implements ObjetiveService {

	@Autowired
	private ObjetiveRepository objetiveRepository;

	@Override
	public List<Objetive> findAll() throws Exception {
		return objetiveRepository.findAll();
	}

	@Override
	public Objetive save(Objetive t) throws Exception {
		return objetiveRepository.save(t);
	}

	@Override
	public Objetive update(Objetive t) throws Exception {
		return objetiveRepository.save(t);
	}

	@Override
	public void deleteById(Long id) throws Exception {
		objetiveRepository.deleteById(id);
	}

	@Override
	public void deleteAll() throws Exception {
		// Method not implemented
	}

	@Override
	public Optional<Objetive> findById(Long id) throws Exception {
		return objetiveRepository.findById(id);
	}
	
	
}
