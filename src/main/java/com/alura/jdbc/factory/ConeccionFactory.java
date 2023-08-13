package com.alura.jdbc.factory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConeccionFactory {
	public Connection recuperaConexion() throws SQLException {
		return DriverManager.getConnection(
				"jdbc:mysql://localhost/controL_de_stock?useTimeZone=true&serverTimeZone=UTC",
				"root",
				"12345");
	}

}
