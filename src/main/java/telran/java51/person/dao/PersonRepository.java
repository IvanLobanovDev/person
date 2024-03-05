package telran.java51.person.dao;

import java.time.LocalDate;
import java.util.stream.Stream;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import telran.java51.person.model.Person;

public interface PersonRepository extends CrudRepository<Person, Integer> {
	
	Stream<Person> findAllPersonsByBirthDateBetween(LocalDate dateFrom, LocalDate dateTo);
	
	Stream<Person> findAllPersonsByNameIgnoreCase(String name);
	
	@Query(value = "SELECT * FROM person p WHERE UPPER(p.city) = UPPER(?1)", 
			  nativeQuery = true)
	Stream<Person> findAllPersonsByCityIgnoreCase(String city);

}
