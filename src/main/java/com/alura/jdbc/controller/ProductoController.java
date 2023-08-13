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
	    final Connection con = factory.recuperaConexion();
	    
	    try(con){   
	   final PreparedStatement statement = con.prepareStatement("UPDATE producto SET "
	            + "nombre = ?, descripcion = ?, cantidad = ? "
	            + "WHERE ID = ?");
	    try(statement){
	    	
	    
	    statement.setString(1, nombre);
	    statement.setString(2, descripcion);
	    statement.setInt(3, cantidad);  
	    statement.setInt(4, ID);  
	    
	    int updateCount = statement.executeUpdate();
	    con.close();
	    
	    return updateCount;
	    	}
	    
	    }
	}


	public int eliminar(Integer ID) throws SQLException {
	    ConeccionFactory factory = new ConeccionFactory();
	    final Connection con = factory.recuperaConexion();
	    try(con){
		    final PreparedStatement statement = con.prepareStatement("DELETE FROM PRODUCTO WHERE ID = ?");
		    try (statement){
			    statement.setInt(1, ID);
			    int updateCount = statement.executeUpdate(); 
			    con.close();
			    return updateCount;
		    }
	    }
	}


	public List<Map<String, String>> listar() throws SQLException{
		ConeccionFactory factory = new ConeccionFactory();
		final Connection con= factory.recuperaConexion();
		
		try(con){
			
				final PreparedStatement  statement = con.prepareStatement("SELECT ID, nombre, descripcion, cantidad FROM producto");
				try(statement){
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
				return resultado;

				}
		}
	}

	public void guardar(Map<String, String> producto) throws SQLException {
	    String nombre = producto.get("nombre");
	    String descripcion = producto.get("descripcion");
	    Integer cantidad = Integer.valueOf(producto.get("cantidad"));
	    Integer maximoCantidad = 50;
	    
	    ConeccionFactory factory = new ConeccionFactory();
	    final Connection con = factory.recuperaConexion();
	    try(con){
	    	
	    
	    con.setAutoCommit(false);
	    
	    final PreparedStatement statement = con.prepareStatement("INSERT INTO producto "
	            + "(nombre, descripcion, cantidad)"
	            + " VALUES(?,?,?)",
	            Statement.RETURN_GENERATED_KEYS);
	    try(statement){
	
	    do {
	        int cantidadParaGuardas = Math.min(cantidad, maximoCantidad);
	        ejecutaRegistro(nombre, descripcion, cantidadParaGuardas, statement);
	        
	        cantidad -= maximoCantidad;
	        
	        
	    } while (cantidad>0);
	    
	    con.commit();
	    System.out.println("Commit");
	   
	}catch (Exception e) 
	    {
		con.rollback();	
	    System.out.println("Commit");

			}
	    		}
		    	    
	}



	private void ejecutaRegistro(String nombre, String descripcion, Integer cantidad, PreparedStatement statement)
			throws SQLException {
		if (cantidad<50) {
			throw new RuntimeException("Ocurrio un Error");
			
		}
		statement.setString(1, nombre);
    	statement.setString(2, descripcion);
    	statement.setInt(3, cantidad);
    	statement.execute();
    	
    	
    	final ResultSet resultSet = statement.getGeneratedKeys();
    	try(resultSet){
    	while(resultSet.next()) {
    		System.out.println(String.format("Fue Insertado el producto de ID %d",
    	    		resultSet.getInt(1)));

    							}
    				}
    	
    	resultSet.close();
	}

}
