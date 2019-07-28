package pe.rodcar.okrboard.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import pe.rodcar.okrboard.entities.Objective;

@Repository
public interface ObjectiveRepository extends JpaRepository<Objective, Long>{
 	
	Optional<Objective>  findById(Long id);
	
	@Query("select objective from Objective objective where objective.user.id=?1")
	List<Objective> findByUserId(Long id) throws Exception;
	
	boolean existsById(Long id);
}
