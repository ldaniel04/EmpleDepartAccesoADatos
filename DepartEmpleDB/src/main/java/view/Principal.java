package view;

import java.sql.SQLException;

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
		int key;

		do {
			String contenido = """
					Gestor de Empleados y Departamentos
					Introduzca:
					1 - Anyadir algun empleado o departamento
					2 - Para mostrar los empleados o departamentos
					3 - Para actualizar algun empleado o departamento
					4 - Para eleminar algun empleado o departamento
					 """;
			switch (key = IO.readInt()) {
			case 1:
				// add();
				System.out.println("1 Empleado\n2 Departamento");
				if (key == 1) {
					// Objeto.addEmpleado();
				} else {
					// Objeto.addDepart();
				}
				break;
			case 2:
				// show();
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

		} while (key != 0);
	}

	private static void cerrarGestor(Gestor gestor) {
		gestor.cerrarGestor();
	}

	public static void crearAmbos(Gestor gestor) {
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

		try {
			gestor.add(emple);
		} catch (SQLException e) {
			System.out.println("Problema al añadir al empleado");
			return;
		}

	}

}