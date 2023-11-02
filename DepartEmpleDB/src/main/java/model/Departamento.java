package model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class Departamento {
	
	
	private Integer id;
	private String nombre;
	private Empleado jefe;

	
	public Departamento(Integer id, String nombre) {
		this(id, nombre, null);
		
		
	}
	public Departamento(String nombre) {
		this(null, nombre, null);
	}
	
	public void agregarDepartamento(Empleado emple) {
		emple.setDepartamento(this);
	}
	
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return id + "     |     " + nombre + "     |      " + jefe;
	}
	
	public String mostrarInfoDepartamento2() {
		if (this.jefe != null) {
			return id + "      |   " + nombre + "        |   " + jefe.getId();
		} else {
			return this.toString();
		}
	}
}