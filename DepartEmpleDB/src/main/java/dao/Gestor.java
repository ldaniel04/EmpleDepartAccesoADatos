package dao;

import java.sql.Connection;
import java.sql.ResultSet;

import model.Departamento;
import model.Empleado;

public class Gestor {

	private Connection conexion = null;

	public Gestor() {
		conexion = Bdd.getConnection();
		crearTablas();

	}

	public boolean add(Empleado emple) {

	}

	public boolean add(Departamento depart) {

	}

	private Empleado leerEmple(ResultSet resultados) {

	}

	private Departamento leerDepart(ResultSet resultados) {

	}

	public boolean actualizar(Empleado emple) {

	}

	public boolean actualizar(Departamento depart) {

	}

	public void cerrarGestor() {
		Bdd.close();
	}

	private void crearTablas() {

		String sentenciaEmpleados = null;
		String sentenciaDepartamentos = null;

		if (Bdd.typeDB.equals("sqlite")) {

			sentenciaEmpleados = """
					CREATE TABLE IF NOT EXISTS empleados (
					id INTEGER PRIMARY KEY AUTOINCREMENT,
					nombre STRING NOT NULL,
					salario REAL DEFAULT 0.0,
					departamento INTEGER NOT NULL
					)

					""";
			sentenciaDepartamentos = """
					CREATE TABLE IF NOT EXISTS departamentos (
					id INTEGER PRIMARY KEY,
					nombre STRING NOT NULL,
					jefe INTEGER NOT NULL
					)
					""";

		}

		if (Bdd.typeDB.equals("mariadb")) {

			sentenciaEmpleados = """
					CREATE TABLE IF NOT EXISTS empleados (
					id INT AUTO_INCREMENT PRIMARY KEY,
					nombre VARCHAR(255) NOT NULL,
					salario DECIMAL(12,2) DEFAULT 0.0,
					departamento INT NOT NULL
					)

					""";
			sentenciaDepartamentos = """
					CREATE TABLE IF NOT EXISTS departamentos (
					id INT PRIMARY KEY,
					nombre VARCHAR(255) NOT NULL,
					jefe INT NOT NULL
					)
					""";

		}

		try {
			conexion.createStatement().executeUpdate(sentenciaEmpleados);
			conexion.createStatement().executeUpdate(sentenciaDepartamentos);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

}
