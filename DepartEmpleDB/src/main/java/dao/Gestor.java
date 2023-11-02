package dao;

import java.sql.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Departamento;
import model.Empleado;

/**
 * Clase encargada de comunicarse con las bases de datos
 */
public class Gestor {

	private Connection conexion = null;
	private boolean comprobacion = false; //Variable que devolvemos en los métodos que devuelven un boolean

	public Gestor() {
		conexion = Bdd.getConnection();
		crearTablas();

	}

	/**
	 * Añadimos un empleado sin departamento
	 * @param emple
	 * @return si se ha ejecutado la operacion
	 */
	public boolean addEmpleadoSinDepartamento(Empleado emple) {

		String sql = """
				INSERT into empleados(nombre, salario)
				VALUES (?,?)
				""";

		try {
			PreparedStatement ps = conexion.prepareStatement(sql);
			ps.setString(1, emple.getNombre());
			ps.setDouble(2, emple.getSalario());

			return ps.executeUpdate() > 0;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return false;

	}

	/**
	 * Añadimos un departamento sin jefe
	 * @param depart
	 * @return si se ha ejecutado la operacion
	 */
	public boolean addDepartamentoSinJefe(Departamento depart) {

		String sql = """

				INSERT INTO departamentos (nombre)
				VALUES (?)

				""";

		try {
			PreparedStatement ps = conexion.prepareStatement(sql);
			ps.setString(1, depart.getNombre());

			return ps.executeUpdate() > 0;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return false;

	}

	/**
	 * Leemos empleados por un result set recibido
	 * @param resultados de la query
	 * @return un objeto empleado
	 */
	private Empleado leerEmple(ResultSet resultados) { // REVISARLOS DE NUEVO

		Empleado emple;
		Integer id;
		String nombre;
		Double salario;
		Departamento departamento;
		Integer departId;

		

		try {

			id = resultados.getInt("id");
			nombre = resultados.getString("nombre");
			salario = resultados.getDouble("salario");
			departId = resultados.getInt("departamento");

			//Si no hay departamento creamos el objeto empleado sin él
			if (departId == 0) {
				emple = new Empleado(id, nombre, salario);
			} else {
				//Buscamos el departamento para crear el objeto
				departamento = buscarDepartamento(departId);

				emple = new Empleado(id, nombre, salario, departamento);
			}

			return emple;

		} catch (SQLException e) {
		}
		return null;
	}

	/**
	 * Leemos departamentos por un result set recibido
	 * @param resultados de la query
	 * @return un objeto Departamento
	 */
	private Departamento leerDepart(ResultSet resultados) {// REVISARLOS DE NUEVO

		Departamento depart;
		Integer id;
		String nombre;
		Empleado jefe;
		Integer idJefe;

		

		try {

			id = resultados.getInt("id");
			nombre = resultados.getString("nombre");
			idJefe = resultados.getInt("jefe");

			//Si no hay jefe creamos el objeto empleado sin él
			if (idJefe == 0) {
				depart = new Departamento(id, nombre);
			} else {
				//Buscamos el jefe para crear el objeto
				jefe = buscarJefe(idJefe);
				depart = new Departamento(id, nombre, jefe);
			}

			return depart;

		} catch (SQLException e) {
		}
		return null;
	}

	/**
	 * Busca un empleado según la id pasada
	 * @param id
	 * @return un objeto Empleado
	 * @throws SQLException
	 */
	public Empleado buscarJefe(Integer id) throws SQLException {
		String sentencia = """
				SELECT nombre, salario FROM empleados
				WHERE id = ?
				""";

		PreparedStatement ps = conexion.prepareStatement(sentencia);
		ps.setInt(1, id);
		ResultSet rs = ps.executeQuery();

		String nombreEmpleado = rs.getString("nombre");
		Double salario = rs.getDouble("salario");


		return new Empleado(id, nombreEmpleado, salario);

	}
/**
 * Busca un departamento según la id pasada
 * @param id
 * @return un objeto departamento
 * @throws SQLException
 */
	public Departamento buscarDepartamento(Integer id) throws SQLException {

		String sentencia = """

				SELECT nombre from departamentos
				WHERE id = ?
				""";

		PreparedStatement ps = conexion.prepareStatement(sentencia);
		ps.setInt(1, id);
		ResultSet rs = ps.executeQuery();

		String nombreDepartamento = rs.getString("nombre");

		return new Departamento(id, nombreDepartamento);

	}

	/**
	 * Actualiza un empleado con un empleado que se le pasa
	 * @param emple
	 * @return si ha tenido exito la operacion
	 * @throws SQLException
	 */
	public boolean actualizar(Empleado emple) throws SQLException {

		String sentenciaSQL = """
				UPDATE empleados
				SET nombre = ?, salario = ?, departamento = ?
				WHERE id = ?
				""";
		PreparedStatement ps = conexion.prepareStatement(sentenciaSQL);
		ps.setString(1, emple.getNombre());
		ps.setDouble(2, emple.getSalario());
		if (emple.getDepartamento() != null) { //Si no hay departamento no los leemos y evitamos el error
			ps.setInt(3, emple.getDepartamento().getId());
		}
		ps.setInt(4, emple.getId());

		comprobacion = ps.executeUpdate() > 0;

		return comprobacion;
	}

	/**
	 * Actualiza un departamento con un empleado que se le pasa
	 * @param depart
	 * @return si ha tenido exito la operacion
	 * @throws SQLException
	 */
	public boolean actualizar(Departamento depart) throws SQLException {

		String sentenciaSQL = """
				UPDATE departamentos
				SET nombre = ?, jefe = ?
				WHERE id = ?
				""";
		PreparedStatement ps = conexion.prepareStatement(sentenciaSQL);
		ps.setString(1, depart.getNombre());
		if(depart.getJefe() != null) {
		ps.setInt(2, depart.getJefe().getId());
		}
		ps.setInt(3, depart.getId());

		comprobacion = ps.executeUpdate() > 0;

		return comprobacion;
	}

	/**
	 * Devuelve una lista de empleados que va leyendo con el método leerEmple
	 * @return una lista de empleados
	 * @throws SQLException
	 */
	public List<Empleado> mostrarEmpleados() throws SQLException {// TOCAR CANDO METODOS PARA LEER LISTOS

		List<Empleado> lista = new ArrayList<Empleado>();
		String sentencia = """
				SELECT * FROM empleados
				""";

		PreparedStatement ps = conexion.prepareStatement(sentencia);
		ResultSet rs = ps.executeQuery();

		while (rs.next()) {
			lista.add(leerEmple(rs));
		}

		return lista;

	}

	/**
	 * Devuelve una lista de departamentos que va leyendo con el método leerDepart
	 * @return
	 * @throws SQLException
	 */
	public List<Departamento> mostrarDepartamentos() throws SQLException {// TOCAR CANDO METODOS PARA LEER LISTOS

		List<Departamento> lista = new ArrayList<Departamento>();
		String sentencia = """
				SELECT * FROM departamentos
				""";

		PreparedStatement ps = conexion.prepareStatement(sentencia);
		ResultSet rs = ps.executeQuery();

		while (rs.next()) {
			lista.add(leerDepart(rs));
		}

		return lista;

	}

	/**
	 * Borra un empleado y su referencia en el departamento si era jefe. Usa una transacción
	 * @param id
	 * @return el resultado de la operación
	 */
	public boolean deleteEmpleados(int id) {
		int eliminados, modificados;
		boolean check = false;
		String sentencia = """
				DELETE FROM empleados WHERE ID = ?
				""";

		try {
			PreparedStatement ps = conexion.prepareStatement(sentencia);

			conexion.setAutoCommit(false);

			ps.setInt(1, id);

			eliminados = ps.executeUpdate();

			sentencia = """
					UPDATE departamentos SET jefe = NULL WHERE jefe = ?
					""";

			ps = conexion.prepareStatement(sentencia);

			ps.setInt(1, id);

			modificados = ps.executeUpdate();

			conexion.commit();

			return check;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			try {
				conexion.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

		return check;

	}

	/**
	 * Borra un departamento y su referencia en el empleado si este estaba en el departamento. Usa una transacción
	 * @param id
	 * @return el resultado de la operación
	 */
	public boolean deleteDepartamentos(int id) {
		int eliminados, modificados;
		boolean check = false;
		String sentencia = """
				DELETE FROM departamentos WHERE ID = ?
				""";

		try {
			PreparedStatement ps = conexion.prepareStatement(sentencia);

			conexion.setAutoCommit(false);

			ps.setInt(1, id);

			eliminados = ps.executeUpdate();

			sentencia = """
					UPDATE empleados SET departamento = NULL WHERE departamento = ?
					""";

			ps = conexion.prepareStatement(sentencia);

			ps.setInt(1, id);

			modificados = ps.executeUpdate();

			conexion.commit();

			return check;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			try {
				conexion.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

		return check;

	}

	/**
	 * Recupera todas las Ids de empleados
	 * @return lista con las Ids
	 * @throws SQLException
	 */
	public List<Integer> recuperarIdsEmpleados() throws SQLException {
		List<Integer> listaIds = new ArrayList<Integer>();

		String sql = """
					SELECT id FROM empleados
				""";

		PreparedStatement ps = conexion.prepareStatement(sql);

		ResultSet rs = ps.executeQuery();

		while (rs.next()) {
			listaIds.add(rs.getInt(1));
		}
		return listaIds;
	}

	/**
	 * Recupera todas las Ids de departamentos
	 * @return lista con las Ids
	 * @throws SQLException
	 */
	public List<Integer> recuperarIdsDepartamentos() throws SQLException {
		List<Integer> listaIds = new ArrayList<Integer>();

		String sql = """
					SELECT id FROM departamentos
				""";

		PreparedStatement ps = conexion.prepareStatement(sql);

		ResultSet rs = ps.executeQuery();

		while (rs.next()) {
			listaIds.add(rs.getInt(1));
		}
		return listaIds;
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
					id INT AUTO_INCREMENT PRIMARY KEY,
					nombre VARCHAR(255) NOT NULL,
					salario DECIMAL(12,2) DEFAULT 0.0,
					departamento INT
					)

					""";
			sentenciaDepartamentos = """
					CREATE TABLE IF NOT EXISTS departamentos (
					id INT AUTO_INCREMENT PRIMARY KEY,
					nombre VARCHAR(255) NOT NULL,
					jefe INT
					)
					""";

		}
		try {
			conexion.createStatement().executeUpdate(sentenciaEmpleados);
			conexion.createStatement().executeUpdate(sentenciaDepartamentos);
		} catch (Exception e) {
			System.err.println("No se ha realizado conexión con la base de datos");
		}
	}
}
