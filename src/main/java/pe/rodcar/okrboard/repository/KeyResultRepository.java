package pe.rodcar.okrboard.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pe.rodcar.okrboard.entities.KeyResult;

@Repository
public interface KeyResultRepository extends JpaRepository<KeyResult, Long>{

	Optional<KeyResult> findById(Long id);
	
	boolean existsById(Long id);
	
}
