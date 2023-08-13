package com.alura.jdbc.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alura.jdbc.factory.ConeccionFactory;
import com.mysql.cj.xdevapi.PreparableStatement;

import java.sql.Statement;


public class ProductoController {

	public int modificar(String nombre, String descripcion, Integer cantidad, Integer ID) throws SQLException {
	    ConeccionFactory factory = new ConeccionFactory();
	    Connection con = factory.recuperaConexion();
	    
	    PreparedStatement statement = con.prepareStatement("UPDATE producto SET "
	            + "nombre = ?, descripcion = ?, cantidad = ? "
	            + "WHERE ID = ?");
	    
	    statement.setString(1, nombre);
	    statement.setString(2, descripcion);
	    statement.setInt(3, cantidad);  
	    statement.setInt(4, ID);  
	    
	    int updateCount = statement.executeUpdate();
	    con.close();
	    
	    return updateCount;
	}


	public int eliminar(Integer ID) throws SQLException {
	    ConeccionFactory factory = new ConeccionFactory();
	    Connection con = factory.recuperaConexion();

	    PreparedStatement statement = con.prepareStatement("DELETE FROM PRODUCTO WHERE ID = ?");
	    statement.setInt(1, ID);

	    int updateCount = statement.executeUpdate(); 

	    con.close();

	    return updateCount;
	}


	public List<Map<String, String>> listar() throws SQLException{
		
		Connection con= new ConeccionFactory().recuperaConexion();
		
		
		PreparedStatement  statement = con.prepareStatement("SELECT ID, nombre, descripcion, cantidad FROM producto");
		
		statement.execute();
		
		ResultSet resultSet = statement.getResultSet();
		
		List<Map<String, String>> resultado = new ArrayList<>();

	
		while( resultSet.next()) {		
			Map<String, String>fila = new HashMap<>();
			fila.put("ID", String.valueOf(resultSet.getInt("ID")));
			fila.put("nombre", resultSet.getString("nombre"));
			fila.put("descripcion", resultSet.getString("descripcion"));
			fila.put("cantidad", String.valueOf(resultSet.getInt("cantidad")));
			
			resultado.add(fila);

		}
		con.close();	
		return resultado;
	}

	public void guardar(Map<String, String> producto) throws SQLException {
	    String nombre = producto.get("nombre");
	    String descripcion = producto.get("descripcion");
	    Integer cantidad = Integer.valueOf(producto.get("cantidad"));
	    Integer maximoCantidad = 50;
	    
	    ConeccionFactory factory = new ConeccionFactory();
	    Connection con = factory.recuperaConexion();
	    con.setAutoCommit(false);
	    
	    PreparedStatement statement = con.prepareStatement("INSERT INTO producto "
	            + "(nombre, descripcion, cantidad)"
	            + " VALUES(?,?,?)",
	            Statement.RETURN_GENERATED_KEYS);
	    
	    do {
	        int cantidadParaGuardas = Math.min(cantidad, maximoCantidad);
	        ejecutaRegistro(nombre, descripcion, cantidadParaGuardas, statement);
	        cantidad -= maximoCantidad;
	        
	        
	    } while (cantidad>0);
	    
	    
	    con.close();
	}



	private void ejecutaRegistro(String nombre, String descripcion, Integer cantidad, PreparedStatement statement)
			throws SQLException {
		
		statement.setString(1, nombre);
    	statement.setString(2, descripcion);
    	statement.setInt(3, cantidad);
    
    	statement.execute();
    	
    	
    	ResultSet resultSet = statement.getGeneratedKeys();
    	
    	while(resultSet.next()) {
    		System.out.println(String.format("Fue Insertado el producto de ID %d",
    	    		resultSet.getInt(1)));

    	}
	}

}
