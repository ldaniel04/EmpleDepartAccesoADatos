package view;

import java.sql.SQLException;
import java.util.List;

import dao.Gestor;
import io.IO;
import model.Departamento;
import model.Empleado;

public class Principal {

	/**
	 * @param args
	 * @author José Antonio Fernández-Montes García, Jorge Balmisa Rosillo, Luis
	 *         Daniel Cedeño Murillo
	 */
	public static void main(String[] args) {

		Gestor gestor = new Gestor();

		menuPpal(gestor);

	}

	private static void menuPpal(Gestor gestor) {
		int key = 0, subopcion = 0;

		do {
			try {
				String contenido = """
						Gestor de Empleados y Departamentos
						Introduzca:
						1 - Añadir algun empleado o departamento
						2 - Para mostrar los empleados o departamentos
						3 - Para actualizar algun empleado o departamento
						4 - Para eleminar algun empleado o departamento
						5 - Asigna un departamento a un empleado
						6 - Asigna un jefe a un departamento
						 """;
				System.out.println(contenido);
				switch (key = IO.readInt()) {
				case 1:
					// add();
//				System.out.println("1 Empleado\n2 Departamento");
//				if (key == 1) {
//					// Objeto.addEmpleado();
//				} else {
//					// Objeto.addDepart();
//				}

					System.out.println("1 Empleado\n2 Departamento");
					subopcion = IO.readInt();
					if (subopcion == 1) {
//					crearEmple(gestor);	
						crearEmpleSinJefe(gestor);
					} else {
//					crearDepart(gestor);
						crearDepartSinJefe(gestor);
					}

					break;
				case 2:
					// show();

					mostrarTodo(gestor);
					break;
				case 3:
					System.out.println("1 Empleado\n2 Departamento");
					subopcion = IO.readInt();
					if (subopcion == 1) {
						updateEmpleado(gestor);
					} else if (subopcion == 2) {
						updateDepartamento(gestor);
					} else {
						System.err.println("No ha escogido una opción válida, retornando al menú de opciones...");
						return;
					}
					break;
				case 4:
					System.out.println("1 Empleado\n2 Departamento");
					subopcion = IO.readInt();
					if (subopcion == 1) {
						eliminarEmpleado(gestor);
					} else if (subopcion == 2) {
						eliminarDepartamento(gestor);
					} else {
						System.err.println("No ha escogido una opción válida, retornando al menú de opciones...");
						return;
					}
					break;

				case 5:
					agregarEmpleadoADepartamento(gestor);
					break;
					
				case 6:
					agregarJefeADepartamento(gestor);
					break;
				case 0:
					cerrarGestor(gestor);
					break;
				default:
					System.out.println("Has introducido un valor erroneo, vuelva a intentarlo");
					break;
				}
			} catch (SQLException e) {
				System.out.println("Problema");
				e.printStackTrace();
			}

		} while (key != 0);
	}

	private static void cerrarGestor(Gestor gestor) {
		gestor.cerrarGestor();
	}

	public static void crearEmpleSinJefe(Gestor gestor) {
		Empleado emple;
		String nombre;
		Double salario;
		

		
		System.out.println("Nombre?: ");
		nombre = IO.readString();
		if (nombre == "") {
			return;
		}
		System.out.println("Salario?: ");
		salario = IO.readDouble();

		emple = new Empleado(nombre, salario);

		gestor.addEmpleadoSinDepartamento(emple);

	}

	public static void crearDepartSinJefe(Gestor gestor) {

		Departamento depart;
		String nombre;

		System.out.println("Nombre?: ");
		nombre = IO.readString();
		
		if (nombre == "") {
			return;
		}

		depart = new Departamento(nombre);

		gestor.addDepartamentoSinJefe(depart);

	}

	private static void mostrarTodo(Gestor gestor) throws SQLException {

		List<Empleado> listaEmple;
		List<Departamento> listaDepart;

		listaEmple = gestor.mostrarEmpleados();
		listaDepart = gestor.mostrarDepartamentos();

		if (listaEmple.isEmpty()) {
			System.out.println("No hay empleados");
		} else {
			System.out.println("MOSTRANDO EMPLEADOS\n");

			for (Empleado empleados : listaEmple) {
				if (empleados.getDepartamento() == null) {
					System.out.println(empleados);
				} else {
					System.out.println(empleados.mostrarInfoEmpleado2());
				}
			}
		}

		if (listaDepart.isEmpty()) {
			System.out.println("No hay departamentos");

		} else {
			System.out.println("\nMOSTRANDO DEPARTAMENTOS\n");

			for (Departamento departamentos : listaDepart) {
				if(departamentos.getJefe() == null) {
					System.out.println(departamentos);
				}
				else {
					System.out.println(departamentos.mostrarInfoDepartamento2());
				}
				
			}
		}

	}

	public static void mostrarEmpleados(Gestor gestor) throws SQLException {
		List<Empleado> listaEmple;

		listaEmple = gestor.mostrarEmpleados();

		if (listaEmple.isEmpty()) {
			System.out.println("No hay empleados");
		} else {

			for (Empleado empleados : listaEmple) {
				if (empleados.getDepartamento() == null) {
					System.out.println(empleados);
				} else {
					System.out.println(empleados.mostrarInfoEmpleado2());
				}
			}
		}

	}

	public static void mostrarDepartamentos(Gestor gestor) throws SQLException {
		List<Departamento> listaDepart;

		listaDepart = gestor.mostrarDepartamentos();

		if (listaDepart.isEmpty()) {
			System.out.println("No hay empleados");
		} else {

			for (Departamento departamentos : listaDepart) {
				if (departamentos.getJefe() == null) {
					System.out.println(departamentos);
				} else {
					System.out.println(departamentos.mostrarInfoDepartamento2());
				}

			}
		}

	}
	
	public static void updateEmpleado (Gestor gestor) throws SQLException {
		Integer id;
		Empleado emple;
		Double salario;
		String nombre;
		List<Integer>listaIds;
		boolean check = false;
		
		mostrarEmpleados(gestor);
		System.out.println("Selecciona una id del empleado que quieras modificar");
		id = IO.readInt();
		
		listaIds = gestor.recuperarIdsEmpleados();
		
		for (Integer ids : listaIds) {
			if(id == ids) {
				check = true;
				break;
			}
		}
		
		if(check) {
		emple = gestor.buscarJefe(id);
		System.out.println(emple);
		
		System.out.println("¿Qué nombre le quieres poner al empleado? ");
		nombre = IO.readString();
		
		if(nombre.length() <= 2) {
			System.out.println("Nombre no cambiado");
		} else {
			emple.setNombre(nombre);
			System.out.println("Nombre cambiado");
		}
		
		System.out.println("¿Qué salario tendrá " + nombre + "?");
		salario = IO.readDouble();
		
		if (salario != null) {
			emple.setSalario(salario);
		}
		
		gestor.actualizar(emple);
		}else {
			System.err.println("Introduce una id que exista");
			return;
		}
		
	}
	
	public static void updateDepartamento (Gestor gestor) throws SQLException {
		Integer id;
		Departamento depart;
		String nombre;
		List<Integer>listaIds;
		boolean check = false;
		
		mostrarDepartamentos(gestor);
		System.out.println("Selecciona una id del empleado que quieras modificar");
		id = IO.readInt();
		
		listaIds = gestor.recuperarIdsDepartamentos();
		
		for (Integer ids : listaIds) {
			if(id == ids) {
				check = true;
				break;
			}
		}
		
		if(check) {
		depart = gestor.buscarDepartamento(id);
		
		System.out.println("¿Qué nombre le quieres poner al departamento con nombre actual " + depart.getNombre() + "? ");
		nombre = IO.readString();
		
		if(nombre.length() <= 2) {
			System.out.println("Nombre no cambiado");
		} else {
			depart.setNombre(nombre);
			System.out.println("Nombre cambiado");
		}
		gestor.actualizar(depart);
		} 
		
	}

	public static void agregarEmpleadoADepartamento(Gestor gestor) throws SQLException {
		Integer id;
		Empleado emple;
		Departamento depart;

		mostrarEmpleados(gestor);
		System.out.println("Selecciona una id de los empleados para agregarla a un departamento");
		id = IO.readInt();

		// Busca al empleado
		emple = gestor.buscarJefe(id);

		mostrarDepartamentos(gestor);
		System.out.println("Selecciona la id del departamento a agregar al siguiente empleado?: " + emple);
		id = IO.readInt();

		depart = gestor.buscarDepartamento(id);

		depart.agregarDepartamento(emple);

		System.out.println(emple.mostrarInfoEmpleado2() + "\n\n");

		gestor.actualizar(emple);

		System.out
				.println("Actualizado. Has agregado a " + emple.mostrarInfoEmpleado2() + " al departamento " + depart);

	}
	
	
	
	public static void agregarJefeADepartamento(Gestor gestor) throws SQLException{
		Integer id;
		Empleado jefe;
		Departamento depart;
		
		mostrarDepartamentos(gestor);
		System.out.println("Selecciona una id de los departamentos para agregarle un jefe");
		id = IO.readInt();
		//Busca el dpto seleccionado
		depart = gestor.buscarDepartamento(id);
		
		mostrarEmpleados(gestor);
		System.out.println("Selecciona la id de un empleado para agregarlo como jefe del departamento seleccionado anteriormente: ");
		id = IO.readInt();
		
		//Recuperamos el futuro jefe seleccionado
		jefe = gestor.buscarJefe(id);

		//Le agrego al departamento
		jefe.agregarJefe(depart);
		
		System.out.println(depart.mostrarInfoDepartamento2());
		
		gestor.actualizar(depart);
		
		System.out.println("Actualizado. Has agregado a " + depart.mostrarInfoDepartamento2() + " el jefe " + jefe);
		
		
		
		
	}
	
	private static void eliminarEmpleado(Gestor gestor) throws SQLException {
		Integer id;
		
		mostrarEmpleados(gestor);
		System.out.println("Introduce id a eliminar");
		
		id = IO.readInt();
		
		gestor.deleteEmpleados(id);
		
	}
	
	private static void eliminarDepartamento(Gestor gestor) throws SQLException {
		Integer id;
		
		mostrarDepartamentos(gestor);
		System.out.println("Introduce id a eliminar");
		
		id = IO.readInt();
		
		gestor.deleteDepartamentos(id);
		
	}
	
	

//	private static void mostrarIdsEmpleados(Gestor gestor) {
//		
//	}
//
//	
//	private static void mostrarIdsDepartamentos(Gestor gestor) {
//		
//		
//	}
}
