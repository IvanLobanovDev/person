package telran.java51.person.service;

import java.time.LocalDate;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import telran.java51.person.dao.PersonRepository;
import telran.java51.person.dto.AddressDto;
import telran.java51.person.dto.CityPopulationDto;
import telran.java51.person.dto.PersonDto;
import telran.java51.person.dto.exceptions.PersonNotFoundException;
import telran.java51.person.model.Address;
import telran.java51.person.model.Person;

@Service
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService {

	final ModelMapper modelMapper;
	final PersonRepository personRepository;

//	Обычно операции, которые что-то меняют в БД делаются трнзакционными
	
	@Override
	@Transactional
	public Boolean addPerson(PersonDto personDto) {
		if (personRepository.existsById(personDto.getId())) {
			return false;
		}
		personRepository.save(modelMapper.map(personDto, Person.class));
		return true;
	}

	@Override
	public PersonDto findPersonById(Integer id) {
		Person person = personRepository.findById(id).orElseThrow(PersonNotFoundException::new);
		return modelMapper.map(person, PersonDto.class);
	}

//	Когда метод делается транзакционным, при апдейтах сейвить ничего не нужно. Это делает commit в конце нашего транзакционного метода

	@Override
	@Transactional
	public PersonDto updateName(Integer id, String name) {
		Person person = personRepository.findById(id).orElseThrow(PersonNotFoundException::new);
		if (name == null) {
			return null;
		} else {
			person.setName(name);
//			personRepository.save(person);
			return modelMapper.map(person, PersonDto.class);
		}
	}

//	Когда метод делается транзакционным, при апдейтах сейвить ничего не нужно. Это делает commit в конце нашего транзакционного метода

	@Override
	@Transactional
	public PersonDto updateAddress(Integer id, AddressDto addressDto) {
		Person person = personRepository.findById(id).orElseThrow(PersonNotFoundException::new);
		if (addressDto == null) {
			return null;
		} else {
			person.setAddress(modelMapper.map(addressDto, Address.class));
//			personRepository.save(person);
			return modelMapper.map(person, PersonDto.class);
		}
	}

	@Override
	@Transactional
	public PersonDto deletePerson(Integer id) {
		Person person = personRepository.findById(id).orElseThrow(PersonNotFoundException::new);
		personRepository.deleteById(id);
		return modelMapper.map(person, PersonDto.class);
	}
	
//	Здесь аннотацию нужно ставить потому что возвращается Stream, а Stream это не данные и соединение разрывается
//	Поэтому нужно возвращать либо коллекции, либо ставить аннотацию @Transactional(readOnly = true), чтобы не убивать параллельную работу
	
//	Транзакционность не свойственна MongoDB, если нам нужна транзакционность, выбираем реляционные базы данных
	@Override
	@Transactional(readOnly = true)
	public Iterable<PersonDto> findPersonByCity(String city) {
		return personRepository.findAllPersonsByAddressCityIgnoreCase(city).map(p -> modelMapper.map(p, PersonDto.class))
				.toList();
	}

	@Override
	@Transactional(readOnly = true)
	public Iterable<PersonDto> findPersonByAges(Integer minAge, Integer maxAge) {
		LocalDate dateFrom = LocalDate.now().minusYears(maxAge);
		LocalDate dateTo = LocalDate.now().minusYears(minAge);
		return personRepository.findAllPersonsByBirthDateBetween(dateFrom, dateTo)
				.map(p -> modelMapper.map(p, PersonDto.class)).collect(Collectors.toList());
	}

	@Override
	@Transactional(readOnly = true)
	public Iterable<PersonDto> findPersonByName(String name) {
		return personRepository.findAllPersonsByNameIgnoreCase(name).map(p -> modelMapper.map(p, PersonDto.class))
				.toList();
	}

	@Override
	public Iterable<CityPopulationDto> getCitiesPopulation() {
		return personRepository.getCitiesPopulation();
	}

}

// Реализовать все эндпоинты
// Подключить к докеру через MySQL
