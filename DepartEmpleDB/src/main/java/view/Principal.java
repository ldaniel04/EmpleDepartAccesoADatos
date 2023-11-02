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
						1 - Anyadir algun empleado o departamento
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
					// update();
					break;
				case 4:
					eliminar(gestor);
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
					System.out.println("Has introducido un valor erroneo, vuele a intentarlo");
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

		depart = new Departamento(nombre);

		gestor.addDepartamentoSinJefe(depart);

	}

//	public static void crearEmple(Gestor gestor) throws SQLException {
//		//Por como están hechos lo métodos, estos objetos nos sirven para agregar y comprobar.
//		Empleado emple;
//		Departamento depart;
//		Integer idEmple, idDepart;
//		String nombreEmple, nombreDepart;
//		Double salario;
//
//
//		//Generar emple sin id -> Comprobar si el dpto donde va a estar emple existe -> Si es su jefe, recuperar su id y ponérsela ->
//		
//		System.out.println("Nombre: ?");
//		nombreEmple = IO.readString();
//		System.out.println("Salario: ?");
//		salario = IO.readDouble();
//		System.out.println("Id de su departamento: ?");
//		idDepart = IO.readInt();
//		
//		
//		//Si el departamento existe, recupera la información del mismo y se la pasa a el objeto empleado.
//		if(gestor.buscarDepartamento(idDepart).getNombre() != null) {  //Gestor.metodo.getnombre!=null
//			depart = gestor.buscarDepartamento(idDepart);
//			
//			
//			
//			emple = new Empleado(nombreEmple, salario, depart);
//			gestor.add(emple);
//		}
//		else {
//			System.out.println("Departamento no encontrado, introduce nombre de su departamento para crearlo: ?");
//			nombreDepart = IO.readString();
//			
//			//Creo un departamento sin jefe y lo añado, guardándome su id.
//			depart = new Departamento(nombreDepart);
//			idDepart = gestor.addDepartSinJefe(depart);
//			
//			//Recupero el departamento recién creado
//			depart = gestor.buscarDepartamento(idDepart);
//			
//			//Creo el empleado con los datos y el departamento recién recuperado y me guardo su id
//			emple = new Empleado(nombreEmple, salario, depart);
//			
//			idEmple = gestor.add(emple);
//			
//			//Actualizo la información del jefe de departamento. Como es nuevo departamento, asumimos que el primer empleado en él es el jefe.
//			depart = new Departamento(idDepart, nombreDepart, emple);
//			
//			gestor.actualizar(depart);

//			//Asignamos id = 0 a emple para que el departamento se pueda escribir correctamente
//			emple = new Empleado(0, nombreEmple, salario);
//
//			depart = new Departamento(nombreDepart, emple);
//
//			depart.setJefe(emple);
//			
//			//Añadimos departamento para recuperar su id
//			gestor.add(depart);
//			
//			//Asumimos que como el departamento es nuevo, el jefe va a ser el primer empleado que entra
//			
//			//Volvemos a crear el empleado pero con la id del departamento (que ha sido asignada automáticamente) correcta
//			emple = new Empleado (nombreEmple, salario, gestor.buscarDepartamentoNombre(nombreDepart));

//		}

//		Empleado emple = new Empleado(idEmple, nombreEmple, salario);
//		emple = new Empleado(nombreEmple, salario);
//		depart = new Departamento(idDepart, nombreDepart);
//		// Agrega un departamento a un empleado
//		depart.agregarDepartamento(emple);
//		// Agrega un jefe a un departamento
//		emple.agregarJefe(depart);
//
//		
//			gestor.add(emple);

//	}

//	public static void crearDepart(Gestor gestor) throws SQLException {
//		Departamento depart;
//		Empleado emple;
//		Integer idEmple, idDepart;
//		String nombreEmple, nombreDepart;
//		Double salario;
//
//		System.out.println("Nombre de su departamento: ?");
//		nombreDepart = IO.readString();
//		System.out.println("Id de su jefe: ? ");
//		idEmple = IO.readInt();
//		
//		
//		
//		
//		if(gestor.buscarJefe(idEmple) != null) {
//			
//			emple = gestor.buscarJefe(idEmple);
//			
//			depart = new Departamento(nombreDepart, emple);
//			
//			gestor.add(depart);
//			
//		}else {
//			
//			System.out.println("Jefe no encontrado, escribe su nombre: ");
//			nombreEmple = IO.readString();
//			System.out.println("Salario: ?");
//			salario = IO.readDouble();
//			
//			//Creo un empleado sin departamento y lo añado a la tabla, guardándome su id.
//			emple = new Empleado(nombreEmple, salario);
//			idEmple = gestor.add(emple);
//			
//			//Recupero el empleado recién creado
//			emple = new Empleado(idEmple, nombreEmple, salario);
//			
//			
//			//Creo el departamento con los datos y el empleado recién recuperado y me guardo su id (De emple solo necesito el id)
//
//			depart = new Departamento(nombreDepart, emple);
//			idDepart = gestor.add(depart);
//			
//			//Actualizo la información del empleado con su departamento
//			depart = new Departamento(idDepart, nombreDepart, emple);
//			emple = new Empleado(idEmple, nombreEmple, salario, depart);
//			
//			//Actualizo con la información que cambie
//			gestor.actualizar(emple);
//			
//			
//		}
//		
//		
//		
//			
//		
//		
//		
//	}

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
	
	private static void eliminar(Gestor gestor) throws SQLException {
		Integer id;
		
		mostrarEmpleados(gestor);
		System.out.println("Introduce id a eliminar");
		
		id = IO.readInt();
		
		gestor.deleteEmpleados(id);
		
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
