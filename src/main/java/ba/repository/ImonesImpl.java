package ba.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ba.models.Imones;



public interface ImonesImpl extends JpaRepository<Imones, Integer>{
	  List<Imones> findAllByLookupId(int lookup_id);
	  List<Imones> findAllByJarCode(long jar_code);
	  
	  @Query(value = "SELECT * FROM imones WHERE lower(name) LIKE '?1'",  nativeQuery = true)
	  List<Imones> findAllByName1(String name);
	  List<Imones> findByNameContainingIgnoreCase(String name);
	  @Query(value = "SELECT * FROM imones WHERE avg_wage > ?1",  nativeQuery = true)
	  List<Imones> findAllWageFrom(float avg_wage);
	  
}
