package model;

import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Depart {
	private Integer id; 
	private String nombre; 
	private Empleado jefe; 

}
