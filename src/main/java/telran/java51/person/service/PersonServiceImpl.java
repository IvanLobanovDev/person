package telran.java51.person.service;

import java.time.LocalDate;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import telran.java51.person.dao.PersonRepository;
import telran.java51.person.dto.AddressDto;
import telran.java51.person.dto.ChildDto;
import telran.java51.person.dto.CityPopulationDto;
import telran.java51.person.dto.EmployeeDto;
import telran.java51.person.dto.PersonDto;
import telran.java51.person.dto.exceptions.PersonNotFoundException;
import telran.java51.person.model.Address;
import telran.java51.person.model.Child;
import telran.java51.person.model.Employee;
import telran.java51.person.model.Person;

@Service
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService, CommandLineRunner {

	final ModelMapper modelMapper;
	final PersonRepository personRepository;

	@Override
	@Transactional
	public Boolean addPerson(PersonDto personDto) {
		if (personRepository.existsById(personDto.getId())) {
			return false;
		}
//		switch (personDto.getClass().getSimpleName()) {
//		case "ChildDto": personRepository.save(modelMapper.map(personDto, Child.class));
//			break;
//		case "EmployeeDto": personRepository.save(modelMapper.map(personDto, Employee.class));
//		break;
//		default:
//			personRepository.save(modelMapper.map(personDto, Person.class));
//			break;
//		}
		if (personDto instanceof ChildDto) {
			personRepository.save(modelMapper.map(personDto, Child.class));
			return true;
		}
		if (personDto instanceof EmployeeDto) {
			personRepository.save(modelMapper.map(personDto, Employee.class));
			return true;
		}
		if (personDto instanceof PersonDto) {
			personRepository.save(modelMapper.map(personDto, Person.class));
			return true;
		}
		return false;
	}

	@Override
	public PersonDto findPersonById(Integer id) {
		Person person = personRepository.findById(id).orElseThrow(PersonNotFoundException::new);
		return returnInstanceOfPersonDto(person);
	}

	@Override
	@Transactional
	public PersonDto updateName(Integer id, String name) {
		Person person = personRepository.findById(id).orElseThrow(PersonNotFoundException::new);
		if (name == null) {
			return null;
		} else {
			person.setName(name);
			return returnInstanceOfPersonDto(person);
		}
	}

	@Override
	@Transactional
	public PersonDto updateAddress(Integer id, AddressDto addressDto) {
		Person person = personRepository.findById(id).orElseThrow(PersonNotFoundException::new);
		if (addressDto == null) {
			return null;
		} else {
			person.setAddress(modelMapper.map(addressDto, Address.class));
			return returnInstanceOfPersonDto(person);
		}
	}

	@Override
	@Transactional
	public PersonDto deletePerson(Integer id) {
		Person person = personRepository.findById(id).orElseThrow(PersonNotFoundException::new);
		personRepository.deleteById(id);
		return returnInstanceOfPersonDto(person);
	}

	@Override
	@Transactional(readOnly = true)
	public Iterable<PersonDto> findPersonByCity(String city) {
		return personRepository.findAllPersonsByAddressCityIgnoreCase(city).map(p -> returnInstanceOfPersonDto(p))
				.toList();
	}

	@Override
	@Transactional(readOnly = true)
	public Iterable<PersonDto> findPersonByAges(Integer minAge, Integer maxAge) {
		LocalDate dateFrom = LocalDate.now().minusYears(maxAge);
		LocalDate dateTo = LocalDate.now().minusYears(minAge);
		return personRepository.findAllPersonsByBirthDateBetween(dateFrom, dateTo)
				.map(p -> returnInstanceOfPersonDto(p)).collect(Collectors.toList());
	}

	@Override
	@Transactional(readOnly = true)
	public Iterable<PersonDto> findPersonByName(String name) {
		return personRepository.findAllPersonsByNameIgnoreCase(name).map(p -> returnInstanceOfPersonDto(p)).toList();
	}

	@Override
	public Iterable<CityPopulationDto> getCitiesPopulation() {
		return personRepository.getCitiesPopulation();
	}

//	создаем сколько-то сущностей сразу
	@Transactional
	@Override
	public void run(String... args) throws Exception {
		if (personRepository.count() == 0) {
			Person person = new Person(1000, "John", LocalDate.of(1985, 3, 11),
					new Address("Tel Aviv", "Ben Gvirol", 87));
			Child child = new Child(2000, "Mosche", LocalDate.of(2018, 7, 5), new Address("Ashkelon", "Bar Kohva", 21),
					"Shalom");
			Employee employee = new Employee(3000, "Sara", LocalDate.of(1995, 11, 23),
					new Address("Rehovot", "Herzl", 7), "Motorola", 20_000);
			personRepository.save(person);
			personRepository.save(child);
			personRepository.save(employee);
		}

	}

	@Override
	@Transactional(readOnly = true)
	public Iterable<ChildDto> findAllChildren() {
		return personRepository.findAllPersonsByKindergartenIsNotNull().map(c -> modelMapper.map(c, ChildDto.class))
				.toList();
	}

	@Override
	@Transactional(readOnly = true)
	public Iterable<EmployeeDto> findEmployeesBySalary(Integer from, Integer to) {
		return personRepository.findAllPersonsByCompanyIsNotNullAndSalaryBetween(from, to)
				.map(e -> modelMapper.map(e, EmployeeDto.class)).toList();
	}

	private PersonDto returnInstanceOfPersonDto(Person person) {
		if (person instanceof Child child) {
			return modelMapper.map(child, ChildDto.class);
		}
		if (person instanceof Employee employee) {
			return modelMapper.map(employee, EmployeeDto.class);
		}
		return modelMapper.map(person, PersonDto.class);
	}

}

//Реализовать API PersonInheritance
//Поправить addPerson, чтобы он корректно добавлял
