package model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Departamento {
	private Integer id; 
	private String nombre; 
	private Empleado jefe; 

	public Departamento(Integer id, String nombre) {
		setId(id);
		setNombre(nombre);
	}
	
	/**
	 * Asigna un departamento a un empleado
	 * @param emple
	 */
	public void  agregarDepartamento(Empleado emple) {
		emple.setDepartamento(this);
	}
	
	
}
