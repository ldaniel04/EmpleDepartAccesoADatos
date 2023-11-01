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
	
	public Departamento(String nombre) {
		this(null, nombre, null);
	}
	
	public Departamento(String nombre, Empleado jefe) {
		this(null, nombre, jefe);
	}
	
	/**
	 * Asigna un departamento a un empleado
	 * @param emple
	 */
	public void  agregarDepartamento(Empleado emple) {
		emple.setDepartamento(this);
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return id + "|" + nombre + "|" + jefe.getId(); 
	}
}


