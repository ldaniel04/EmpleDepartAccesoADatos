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

public class Gestor {

	private Connection conexion = null;
	private boolean comprobacion = false;
//	private PreparedStatement ps;

	public Gestor() {
		conexion = Bdd.getConnection();
		crearTablas();

	}

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

	private Empleado leerEmple(ResultSet resultados) { // REVISARLOS DE NUEVO

		Empleado emple;
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

			if (departId == 0) {
				emple = new Empleado(id, nombre, salario);
			} else {
				departamento = buscarDepartamento(departId);

				emple = new Empleado(id, nombre, salario, departamento);
			}

			return emple;

		} catch (SQLException e) {
		}
		return null;
	}

	private Departamento leerDepart(ResultSet resultados) {// REVISARLOS DE NUEVO

		Departamento depart;
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

			if (idJefe == 0) {
				depart = new Departamento(id, nombre);
			} else {
				jefe = buscarJefe(idJefe);
				depart = new Departamento(id, nombre, jefe);
			}

			return depart;

		} catch (SQLException e) {
		}
		return null;
	}

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

	public boolean actualizar(Empleado emple) throws SQLException {

		String sentenciaSQL = """
				UPDATE empleados
				SET nombre = ?, salario = ?, departamento = ?
				WHERE id = ?
				""";
		PreparedStatement ps = conexion.prepareStatement(sentenciaSQL);
		ps.setString(1, emple.getNombre());
		ps.setDouble(2, emple.getSalario());
		ps.setInt(3, emple.getDepartamento().getId());
		ps.setInt(4, emple.getId());

		comprobacion = ps.executeUpdate() > 0;

		return comprobacion;
	}

	public boolean actualizar(Departamento depart) throws SQLException {

		String sentenciaSQL = """
				UPDATE departamentos
				SET nombre = ?, jefe = ?
				WHERE id = ?
				""";
		PreparedStatement ps = conexion.prepareStatement(sentenciaSQL);
		ps.setString(1, depart.getNombre());
		ps.setInt(2, depart.getJefe().getId());
		ps.setInt(3, depart.getId());

		comprobacion = ps.executeUpdate() > 0;

		return comprobacion;
	}

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
	
	
	public List<Integer> recuperarIdsEmpleados() throws SQLException{
		List<Integer> listaIds = new ArrayList<Integer>();
		
		String sql = """
					SELECT id FROM empleados
				""";
		
		PreparedStatement ps = conexion.prepareStatement(sql);
		
		ResultSet rs = ps.executeQuery();
		
		while(rs.next()) {
		listaIds.add(rs.getInt(1));
		}
		return listaIds;
	}
	
	public List<Integer> recuperarIdsDepartamentos() throws SQLException{
		List<Integer> listaIds = new ArrayList<Integer>();
		
		String sql = """
					SELECT id FROM departamentos
				""";
		
		PreparedStatement ps = conexion.prepareStatement(sql);
		
		ResultSet rs = ps.executeQuery();
		
		while(rs.next()) {
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
			System.out.println("PROBANDO ");
				conexion.createStatement().executeUpdate(sentenciaEmpleados);
				conexion.createStatement().executeUpdate(sentenciaDepartamentos);
			} catch (Exception e) {
				System.err.println("No se ha realizado conexión con la base de datos");
			}
	}
}
