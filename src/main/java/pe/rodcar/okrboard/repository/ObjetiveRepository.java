package pe.rodcar.okrboard.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pe.rodcar.okrboard.entities.Objetive;

@Repository
public interface ObjetiveRepository extends JpaRepository<Objetive, Long>{
 	
	Optional<Objetive>  findById(Long id);
	
}
