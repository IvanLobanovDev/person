package telran.java51.person.model;

import java.io.Serializable;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
// То же что @Embeded - интегрирует данные из одного класса в другой
@Embeddable
public class Address implements Serializable{

	private static final long serialVersionUID = -335865397457421859L;
	String city;
	String street;
	Integer building;

}
