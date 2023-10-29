package model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Empleado {
	private Integer id; 
	private String nombre; 
	private Double salario;
	private Departamento departamento;

	
//	public Empleado(Integer id) {
//		this();
//	}
	
	public Empleado(Integer id, String nombre, Double salario) {
		setId(id);
		setNombre(nombre);
		setSalario(salario);
	}
	
	/**
	 * Asigna un jefe a un departamento
	 * @param depart
	 */
	public void agregarJefe(Departamento depart) {
		depart.setJefe(this);
	}
	
}
