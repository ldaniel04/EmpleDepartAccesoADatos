package model;

import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Empleado {
	private Integer id; 
	private String nombre; 
	private Double salario;
	private Departamento departamento;

	
	public Empleado(Integer id) {
		this();
	}
}
