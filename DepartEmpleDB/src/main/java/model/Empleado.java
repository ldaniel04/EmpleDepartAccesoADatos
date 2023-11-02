package model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
public class Empleado {
	private Integer id;
	private String nombre;
	private Double salario;
	private Departamento departamento;

//	public Empleado(Integer id) {
//		this();
//	}

	public Empleado(Integer id, String nombre, Double salario) {
		this.id = id;
		this.nombre = nombre;
		this.salario = salario;
	}

	public Empleado(String nombre, Double salario) {
		this(null, nombre, salario, null);

	}

	public Empleado(String nombre, Double salario, Departamento depart) {
		this(null, nombre, salario, depart);
	}

	/**
	 * Asigna un jefe a un departamento
	 * 
	 * @param depart
	 */
	public void agregarJefe(Departamento depart) {
		depart.setJefe(this);
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return id + "|" + nombre + "|" + salario + "|" + departamento;
	}

	public String mostrarInfoEmpleado2() {
		if (this.departamento != null) {
			return id + "|" + nombre + "|" + salario + "|" + departamento.getId();
		} else {
			return this.toString();
		}
	}

}