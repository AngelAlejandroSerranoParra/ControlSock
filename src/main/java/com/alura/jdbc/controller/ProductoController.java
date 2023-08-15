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
import com.alura.jdbc.modelo.Producto;
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

	public void guardar(Producto producto) throws SQLException {
	  
	    
	    ConeccionFactory factory = new ConeccionFactory();
	    final Connection con = factory.recuperaConexion();
	    
	    try(con){
	    	 
	    con.setAutoCommit(false);
	    
	    final PreparedStatement statement = con.prepareStatement("INSERT INTO producto "
	            + "(nombre, descripcion, cantidad)"
	            + " VALUES(?,?,?)",
	            Statement.RETURN_GENERATED_KEYS);
	    try(statement){

	        ejecutaRegistro(producto , statement);
	        
	        con.commit();
	   
	   
	}catch (Exception e) 
	    {
		e.printStackTrace();
	    System.out.println("Commit");
	    con.rollback();

			}
	    		}
		    	    
	}



	private void ejecutaRegistro(Producto producto, PreparedStatement statement)
			throws SQLException {
		
		statement.setString(1, producto.getNombre());
    	statement.setString(2, producto.getDescripcion());
    	statement.setInt(3, producto.getCantidad());
    	statement.execute();
    	

    	final ResultSet resultSet = statement.getGeneratedKeys();
    	try(resultSet){
    	while(resultSet.next()) {
    		producto.setId(resultSet.getInt(1));
    		System.out.println(String.format("Fue Insertado el producto de ID %s",producto));

    							}
    				}
    	
    	resultSet.close();
	}

}
