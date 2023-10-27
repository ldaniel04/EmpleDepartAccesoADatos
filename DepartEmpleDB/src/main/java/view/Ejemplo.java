//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.SQLException;
//
//public class Ejemplo {
//
//	Connection conn;
//	static boolean comprobacion = false;
//	
//	public boolean addEmpleado(Empleado e) {
//		String sql = "";
//		
//		sql = "INSERT INTO empleado (id, nombre, salario, departamento) VALUES(?,?,?,?)";
//		
//		try {
//			PreparedStatement ps = conn.prepareStatement(sql);
//			ps.setInt(1, e.getId);
//			ps.setString(2, e.getString);
//			ps.setDouble(3, e.getDouble);
//			ps.setInt(4, e.getDepartamento);
//			
//			comprobacion = ps.executeUpdate()>0;
//			return comprobacion;
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		CREATE TABLE departamento{
//			id INTEGER PRIMARY KEY AUTOINCREMENT,
//			nombre TEXT NOT NULL,
//			jefe INTEGER
//		}

//		CREATE TABLE empleado{
//			id INTEGER PRIMARY KEY AUTOINCREMENT,
//			nombre TEXT NOT NULL,
//			salario REAL DEFAULT 0.0,
//			departamento INTEGER
//		}

//MARIA DB

//		CREATE TABLE departamento{
//			id INT PRIMARY KEY AUTOINCREMENT,
//			nombre VARCHAR (TAMAÑO) NOT NULL,
//			jefe INT
//		}

//		CREATE TABLE empleado{
//			id INT PRIMARY KEY AUTOINCREMENT,
//			nombre VARCHAR(TAMAÑO) NOT NULL,
//			salario DECIMAL DEFAULT 0.0,
//			departamento INT
//		}
//}



