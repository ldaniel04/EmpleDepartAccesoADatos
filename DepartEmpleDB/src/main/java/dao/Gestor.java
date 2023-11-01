package dao;

import java.beans.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Departamento;
import model.Empleado;

public class Gestor {

	private Connection conexion = null;
	private boolean comprobacion = false;
	private PreparedStatement ps;

	public Gestor() {
		conexion = Bdd.getConnection();
		crearTablas();

	}

	public int add(Empleado emple) throws SQLException {
		int anadidos, ultimaId = 0;
		String sentenceSQL = """
				INSERT INTO empleados (nombre, salario, departamento)
				VALUES (?,?,?)
				""";
		ps = conexion.prepareStatement(sentenceSQL, java.sql.Statement.RETURN_GENERATED_KEYS);
//		ps.setInt(1, emple.getId());
		ps.setString(1, emple.getNombre());
		ps.setDouble(2, emple.getSalario());
		ps.setInt(3, emple.getDepartamento().getId());

		anadidos = ps.executeUpdate();

		if (anadidos > 0) {
			ResultSet rs = ps.getGeneratedKeys();
			rs.next();
			ultimaId = rs.getInt(1);
		}

		return ultimaId;
	}

	public int add(Departamento depart) throws SQLException {
		int anadidos, ultimaId = 0;
		String sentenceSQL = """
				INSERT INTO departamentos (nombre, jefe)
				VALUES (?,?)
				""";
		ps = conexion.prepareStatement(sentenceSQL, java.sql.Statement.RETURN_GENERATED_KEYS);
//		ps.setInt(1, depart.getId());
		ps.setString(1, depart.getNombre());
		ps.setInt(2, depart.getJefe().getId());

		anadidos = ps.executeUpdate();

		if (anadidos > 0) {
			ResultSet rs = ps.getGeneratedKeys();
			rs.next();
			ultimaId = rs.getInt(1);
		}

		return ultimaId;
	}

	public int addDepartSinJefe(Departamento depart) throws SQLException {

		int anadidos, ultimaId = 0;
		String sentenceSQL = """
				INSERT INTO departamentos (nombre)
				VALUES (?)
				""";
		ps = conexion.prepareStatement(sentenceSQL, java.sql.Statement.RETURN_GENERATED_KEYS);
//		ps.setInt(1, depart.getId());
		ps.setString(1, depart.getNombre());

		anadidos = ps.executeUpdate();

		if (anadidos > 0) {
			
			ResultSet rs = ps.executeQuery("SELECT last_insert_rowid()");
			if (rs.next()) {
				ultimaId = rs.getInt(1);
			};

		}

		return ultimaId;

	}

	private Empleado leerEmple(ResultSet resultados) { // REVISARLOS DE NUEVO

		Integer id;
		String nombre;
		Double salario;
		Departamento departamento;
		Integer departId;

		// Con el resultado de la query (ResultSet) formamos el objeto por los campos de
		// la tabla y lo devolvemos

		try {

			id = resultados.getInt("id");
			nombre = resultados.getString("nombre");
			salario = resultados.getDouble("salario");
			departId = resultados.getInt("departamento");

			departamento = buscarDepartamento(departId);

			Empleado emple = new Empleado(id, nombre, salario);

			// Agregamos el jefe al departamento recuperado
			emple.agregarJefe(departamento);

			// Agregamos al empleado el departamento
			departamento.agregarDepartamento(emple);

			return emple;

//			departamento = departamentoPorId(id,nombre,salario,departId);
//
//			return new Empleado(id, nombre, salario, departamento);
// 			Guardamos el departamento donde está el empleado con un método leerdepart_2,
// 			que devolvería un objeto departamento
// 			y para crear ese objeto departamento le pasamos los campos que ya tenemos de
//			 empleado ?
//			 SELECT * FROM DEPARTAMENTO WHERE ID = id

		} catch (SQLException e) {
		}
		return null;
	}

	private Departamento leerDepart(ResultSet resultados) {// REVISARLOS DE NUEVO

		Integer id;
		String nombre;
		Empleado jefe;
		Integer idJefe;

		// Con el resultado de la query (ResultSet) formamos el objeto por los campos de
		// la tabla y lo devolvemos

		try {

			id = resultados.getInt("id");
			nombre = resultados.getString("nombre");
			idJefe = resultados.getInt("jefe");

			// Primero buscamos el jefe que tiene el departamento
			jefe = buscarJefe(idJefe);

//			jefe = empleadoPorId(idJefe);

//			return new Departamento(id, nombre);

			// Segundo, creamos el departamento solo con los campos id y nombre
			Departamento depart = new Departamento(id, nombre);
			// Tercero, agregamos este departamento al jefe que hemos recuperado
			depart.agregarDepartamento(jefe);
			// Cuarto, agregamos el jefe a este departamento
			jefe.agregarJefe(depart);

		} catch (SQLException e) {
		}
		return null;
	}

	public Empleado buscarJefe(Integer id) throws SQLException {
		String sentencia = """
				SELECT nombre, salario FROM empleados
				WHERE id = ?
				""";

		ps = conexion.prepareStatement(sentencia);
		ps.setInt(1, id);
		ResultSet rs = ps.executeQuery();

		String nombreEmpleado = rs.getString("nombre");
		Double salario = rs.getDouble("salario");

		return new Empleado(id, nombreEmpleado, salario);

	}

	public Departamento buscarDepartamento(Integer id) throws SQLException {

		String sentencia = """

				SELECT nombre from departamentos
				WHERE id = ?
				""";

		ps = conexion.prepareStatement(sentencia);
		ps.setInt(1, id);
		ResultSet rs = ps.executeQuery();

		String nombreDepartamento = rs.getString("nombre");

		return new Departamento(id, nombreDepartamento);

	}

//	private Departamento departamentoPorId(Integer idEmpleado, String nombre,  Double salario, Integer idDepartamento) throws SQLException {//REVISARLOS DE NUEVO
//
//		String nombreDepartamento;
//		Integer idDpto;
//		Departamento dp;
//		
//		String consultaSQL = """
//				SELECT nombre, jefe 
//				FROM departamentos 
//				WHERE id = ?
//				""";
//		ps = conexion.prepareStatement(consultaSQL);
//		ps.setInt(1, idDepartamento);
//		
//		ResultSet rs = ps.executeQuery();
//		while (rs.next()) {
//			idDpto = rs.getInt("id");
//			nombreDepartamento = rs.getString("nombre");
//		}
//		dp = new Departamento(idDpto, nombreDepartamento, new Empleado(idEmpleado, nombre, salario,idDpto));
////		comprobacion = 
//	}

//	private Empleado empleadoPorId(int idEmpleado) {//REVISARLOS DE NUEVO
//
//	}

	public boolean actualizar(Empleado emple) throws SQLException {

		String sentenciaSQL = """
				UPDATE empleados
				SET nombre = ?, salario = ?, departamento = ?
				WHERE id = ?
				""";
		ps = conexion.prepareStatement(sentenciaSQL);
		ps.setString(2, emple.getNombre());
		ps.setDouble(3, emple.getSalario());
		ps.setInt(4, emple.getDepartamento().getId());
		ps.setInt(1, emple.getId());

		comprobacion = ps.executeUpdate() > 0;

		return comprobacion;
	}

	public boolean actualizar(Departamento depart) throws SQLException {

		String sentenciaSQL = """
				UPDATE departamentos
				SET nombre = ?, jefe = ?
				WHERE id = ?
				""";
		ps = conexion.prepareStatement(sentenciaSQL);
		ps.setString(2, depart.getNombre());
		ps.setInt(3, depart.getJefe().getId());
		ps.setInt(1, depart.getId());

		comprobacion = ps.executeUpdate() > 0;

		return comprobacion;
	}

	public List<Empleado> mostrarEmpleados() throws SQLException {// TOCAR CANDO METODOS PARA LEER LISTOS

		List<Empleado> lista = new ArrayList<Empleado>();
		String sentencia = """
				SELECT * FROM empleados
				""";

		ps = conexion.prepareStatement(sentencia);
		ResultSet rs = ps.executeQuery();

		while (rs.next()) {
			lista.add(leerEmple(rs));
		}

		return lista;

	}

	public List<Departamento> mostrarDepartamentos() throws SQLException {// TOCAR CANDO METODOS PARA LEER LISTOS

		List<Departamento> lista = new ArrayList<Departamento>();
		String sentencia = """
				SELECT * FROM departamentos
				""";

		ps = conexion.prepareStatement(sentencia);
		ResultSet rs = ps.executeQuery();

		while (rs.next()) {
			lista.add(leerDepart(rs));
		}

		return lista;

	}

//	public boolean deleteEmpleados(int id, String tabla) {
//		
//		String sentencia = """
//				DELETE * FROM empleados WHERE ID = ?
//				""";
//		
//		ps = conexion.prepareStatement(sentencia);
//		
//		
//	}

	public boolean deleteJefe() throws SQLException {

		String setenciaSQL = """
				UPDATE departamento
				SET jefe = null
				WHERE jefe = ?
				""";

		String subSentenciaSQL = """
				DELETE *
				FROM empleado
				WHERE ID = ?
				""";
		ps = conexion.prepareStatement(setenciaSQL);
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
					nombre TEXT NOT NULL,
					salario REAL DEFAULT 0.0,
					departamento INTEGER
					)

					""";
			sentenciaDepartamentos = """
					CREATE TABLE IF NOT EXISTS departamentos (
					id INTEGER PRIMARY KEY AUTOINCREMENT,
					nombre TEXT NOT NULL,
					jefe INTEGER
					)
					""";

		}

		if (Bdd.typeDB.equals("mariadb")) {

			sentenciaEmpleados = """
					CREATE TABLE IF NOT EXISTS empleados (
					id INT PRIMARY KEY AUTOINCREMENT,
					nombre VARCHAR(255) NOT NULL,
					salario DECIMAL(12,2) DEFAULT 0.0,
					departamento INT
					)

					""";
			sentenciaDepartamentos = """
					CREATE TABLE IF NOT EXISTS departamentos (
					id INT PRIMARY KEY AUTOINCREMENT,
					nombre VARCHAR(255) NOT NULL,
					jefe INT
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
