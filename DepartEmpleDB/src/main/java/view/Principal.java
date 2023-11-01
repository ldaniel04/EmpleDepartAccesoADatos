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
				if(subopcion == 1) {
					crearEmple(gestor);	
				}
				else {
					crearDepart(gestor);
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
				// delete();
				break;
			case 0:
				cerrarGestor(gestor);
				break;
			default:
				System.out.println("Has introducido un valor erroneo, vuele a intentarlo");
				break;
			}
			}catch (SQLException e) {
				System.out.println("Problema");
			}

		} while (key != 0);
	}

	private static void cerrarGestor(Gestor gestor) {
		gestor.cerrarGestor();
	}

	public static void crearEmple(Gestor gestor) throws SQLException {
		Integer idEmple, idDepart;
		String nombreEmple, nombreDepart;
		Double salario;

		System.out.println("Id ?: ");
		idEmple = IO.readInt();
		System.out.println("Nombre: ?");
		nombreEmple = IO.readString();
		System.out.println("Salario: ?");
		salario = IO.readDouble();
		System.out.println("Id de su departamento: ?");
		idDepart = IO.readInt();
		System.out.println("Nombre de su departamento: ?");
		nombreDepart = IO.readString();

		Empleado emple = new Empleado(idEmple, nombreEmple, salario);
		Departamento depart = new Departamento(idDepart, nombreDepart);
		// Agrega un departamento a un empleado
		depart.agregarDepartamento(emple);
		// Agrega un jefe a un departamento
		emple.agregarJefe(depart);

		
			gestor.add(emple);
		

	}
	
	public static void crearDepart(Gestor gestor) throws SQLException {
		
		Integer idEmple, idDepart;
		String nombreEmple, nombreDepart;
		Double salario;

		
		System.out.println("Id de su departamento: ?");
		idDepart = IO.readInt();
		System.out.println("Nombre de su departamento: ?");
		nombreDepart = IO.readString();
		System.out.println("Id ?: ");
		idEmple = IO.readInt();
		System.out.println("Nombre: ?");
		nombreEmple = IO.readString();
		System.out.println("Salario: ?");
		salario = IO.readDouble();
		

		Empleado emple = new Empleado(idEmple, nombreEmple, salario);
		Departamento depart = new Departamento(idDepart, nombreDepart);
		// Agrega un departamento a un empleado
		depart.agregarDepartamento(emple);
		// Agrega un jefe a un departamento
		emple.agregarJefe(depart);

		
			gestor.add(depart);
		
		
		
	}
	
	private static void mostrarTodo(Gestor gestor) throws SQLException {
		
		List<Empleado> listaEmple;
		List<Departamento> listaDepart;
		
		listaEmple = gestor.mostrarEmpleados();
		listaDepart = gestor.mostrarDepartamentos();
		
		System.out.println("MOSTRANDO EMPLEADOS\n");
		
		for (Empleado empleados : listaEmple) {
			System.out.println(empleados);
		}
		
		System.out.println("\nMOSTRANDO DEPARTAMENTOS\n");
		
		for (Departamento departamentos : listaDepart) {
			System.out.println(departamentos);
		}
		
		
	}

}
