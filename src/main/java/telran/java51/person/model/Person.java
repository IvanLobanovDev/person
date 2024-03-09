package telran.java51.person.model;

import java.io.Serializable;
import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
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
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Person implements Serializable {
	private static final long serialVersionUID = -2812010942738675155L;
	@Id
	Integer id;
	@Setter
	String name;
	LocalDate birthDate;
	@Setter
	Address address;

}

//3 стратегии мэпинга наследников

//1 - Single strategy - все сущности записываются в одну таблицу. При этом добавляется колонка с типом данных. Минус - появляются пустоты
//2 - Joined - создаются 3 таблицы. Минус - любой запрос требует Join
//3 - Table per class - для каждой сущности отдельный класс