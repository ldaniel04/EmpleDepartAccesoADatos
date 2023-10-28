package model;

import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Departamento {
	private Integer id; 
	private String nombre; 
	private Empleado jefe; 

}
