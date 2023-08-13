package com.alura.jdbc.factory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class ConeccionFactory {
	
	private DataSource dataSource;
	
	public ConeccionFactory() {
		var pooledDataSource = new ComboPooledDataSource();
		pooledDataSource.setJdbcUrl("jdbc:mysql://localhost/controL_de_stock?useTimeZone=true&serverTimeZone=UTC");
		pooledDataSource.setUser("root");
		pooledDataSource.setPassword("12345");
		pooledDataSource.setMaxPoolSize(10);
		
		
		this.dataSource = pooledDataSource;
		

		
	}
	
	public Connection recuperaConexion() throws SQLException {
		return this.dataSource.getConnection();
		
	}

}
