package view;

import dao.Bdd;
import io.IO;

public class Principal {
	
	/**
	 * @param args
	 * @author José Antonio Fernández-Montes García, Jorge Balmisa Rosillo, Luis Daniel Cedeño Murillo
	 */
	public static void main(String[] args) {
//		CREATE
//		READ 
//		UPDATE
//		DELETE




		
		menuPpal();
		
		
	}

	private static void menuPpal() {
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
			//add();
			System.out.println("1 Empleado\n2 Departamento");
			if (key == 1) {
				//Objeto.addEmpleado();
			}else {
				//Objeto.addDepart();
			}
			break;
		case 2:
			//show();
			break;
		case 3:
			//update();
			break;
		case 4:
			//delete();
			break;
		case 0:
			break;
		default:
			System.out.println("Has introducido un valor erroneo, vuele a intentarlo");
			break;
		}
		
		}while(key!=0);
	}
}
