package telran.java51.person.dao;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import telran.java51.person.dto.CityPopulationDto;
import telran.java51.person.model.Person;

public interface PersonRepository extends CrudRepository<Person, Integer> {
	
	Stream<Person> findAllPersonsByBirthDateBetween(LocalDate dateFrom, LocalDate dateTo);
	
	Stream<Person> findAllPersonsByNameIgnoreCase(String name);
	
//	Стараться максимально не использовать Query, так как это делает код не универсальным
//	Стараться использовать JPQL вместо nativeQuery, так как если изменится структура хранения JPQL более гибкий

//	@Query("select p from Person p where lower(p.address.city) = lower(?1)")
//	@Query(value = "SELECT * FROM person p WHERE UPPER(p.city) = UPPER(?1)", 
//			  nativeQuery = true)
//	Stream<Person> findAllPersonsByCityIgnoreCase(String city);
	
//	@Query("select p from Person p where p.address.city = :cityName")
//	Stream<Person> findAllPersonsByCityIgnoreCase(@Param("cityName")String city);
	
	Stream<Person> findAllPersonsByAddressCityIgnoreCase(String city);
	
//	важно указать полное имя класса включая package
	@Query("select new telran.java51.person.dto.CityPopulationDto(p.address.city, count(p)) from Person p group by p.address.city order by count(p) desc")
	List<CityPopulationDto> getCitiesPopulation();

}
