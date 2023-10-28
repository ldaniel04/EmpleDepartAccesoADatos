package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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

	public boolean add(Empleado emple) throws SQLException {
		String sentenceSQL = """
				INSERT INTO empleados (id, nombre, salario, departamento)
				VALUES (?,?,?,?)
				""";
		ps = conexion.prepareStatement(sentenceSQL);
		ps.setInt(1, emple.getId());
		ps.setString(2, emple.getNombre());
		ps.setDouble(3, emple.getSalario());
		ps.setInt(4, emple.getDepartamento().getId());

		comprobacion = ps.executeUpdate() > 0;

		return comprobacion;
	}

	public boolean add(Departamento depart) throws SQLException {

		String sentenceSQL = """
				INSERT INTO departamentos (id, nombre, jefe)
				VALUES (?,?,?)
				""";
		ps = conexion.prepareStatement(sentenceSQL);
		ps.setInt(1, depart.getId());
		ps.setString(2, depart.getNombre());
		ps.setInt(3, depart.getJefe().getId());

		comprobacion = ps.executeUpdate() > 0;

		return comprobacion;
	}

	private Empleado leerEmple(ResultSet resultados) { //REVISARLOS DE NUEVO

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

			departamento = departamentoPorId(id,nombre,salario,departId);

			return new Empleado(id, nombre, salario, departamento);
			// Guardamos el departamento donde está el empleado con un método leerdepart_2,
			// que devolvería un objeto departamento
			// y para crear ese objeto departamento le pasamos los campos que ya tenemos de
			// empleado ?
			// SELECT * FROM DEPARTAMENTO WHERE ID = id

		} catch (SQLException e) {
		}
		return null;
	}

	private Departamento leerDepart(ResultSet resultados) {//REVISARLOS DE NUEVO

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

			jefe = empleadoPorId(idJefe);

			return new Departamento(id, nombre, jefe);

		} catch (SQLException e) {
		}
		return null;
	}
	
	private Departamento departamentoPorId(Integer idEmpleado, String nombre,  Double salario, Integer idDepartamento) throws SQLException {//REVISARLOS DE NUEVO

		String nombreDepartamento;
		Integer idDpto;
		Departamento dp;
		
		String consultaSQL = """
				SELECT nombre, jefe 
				FROM departamentos 
				WHERE id = ?
				""";
		ps = conexion.prepareStatement(consultaSQL);
		ps.setInt(1, idDepartamento);
		
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			idDpto = rs.getInt("id");
			nombreDepartamento = rs.getString("nombre");
		}
		dp = new Departamento(idDpto, nombreDepartamento, new Empleado(idEmpleado, nombre, salario,idDpto));
//		comprobacion = 
	}

	private Empleado empleadoPorId(int idEmpleado) {//REVISARLOS DE NUEVO

	}

	public boolean actualizar(Empleado emple) throws SQLException {

		String sentenciaSQL = """
				UPDATE empleados 
				SET nombre = ?, salario = ?, departamento = ?
				WHERE id = ?
				""";
		ps = conexion.prepareStatement(sentenciaSQL);
		ps.setString(1, emple.getNombre());
		ps.setDouble(2, emple.getSalario());
		ps.setInt(3, emple.getDepartamento().getId());
		
		comprobacion = ps.executeUpdate()>0;
		
		return comprobacion;
	}

	public boolean actualizar(Departamento depart) throws SQLException {

		String sentenciaSQL = """
				UPDATE departamentos 
				SET nombre = ?, jefe = ?
				WHERE id = ?
				""";
		ps = conexion.prepareStatement(sentenciaSQL);
		ps.setString(1, depart.getNombre());
		ps.setInt(2, depart.getJefe().getId());
		
		comprobacion = ps.executeUpdate()>0;
		
		return comprobacion;
	}

	public List<Empleado> mostrarEmpleados() {//TOCAR CANDO METODOS PARA LEER LISTOS

	}

	public List<Departamento> mostrarDepartamentos() {//TOCAR CANDO METODOS PARA LEER LISTOS

	}

	public boolean deleteJefe() throws SQLException {
		
		String setenciaSQL = """
				UPDATE departamento
				SET jefe = null
				WHERE jefe = ?
				""";
		
		String subSentenciaSQL ="""
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
