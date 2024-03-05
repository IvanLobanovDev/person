package telran.java51.person.model;

import java.io.Serializable;
import java.time.LocalDate;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@EqualsAndHashCode(of = "id")
@Entity
//@Table(name = "persons")
//Рекомендуется делать сущности, хранящиеся в DB сериализованными Тк 
//1) Мы будем хранить не известные фреймворку объекты (адрес) в виде набора байтов
//2) Для RemoteCall, 
public class Person implements Serializable {
	private static final long serialVersionUID = -2812010942738675155L;
	@Id
	Integer id;
	@Setter
	String name;
	LocalDate birthDate;
	@Setter
//	Говорим, что поля address должны быть встроены в таблицу person
	@Embedded
	Address address;

}
