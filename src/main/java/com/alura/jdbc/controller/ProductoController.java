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

import com.alura.jdbc.dao.ProductoDAO;
import com.alura.jdbc.factory.ConeccionFactory;
import com.alura.jdbc.modelo.Producto;
import com.mysql.cj.PerConnectionLRUFactory;
import com.mysql.cj.xdevapi.PreparableStatement;

import java.sql.Statement;


public class ProductoController {

	private ProductoDAO productoDAO;
	
	public ProductoController() {
		this.productoDAO = new ProductoDAO(new ConeccionFactory().recuperaConexion());

	}

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


	public List<Producto> listar(){
		return productoDAO.listar();
		

		
	}

	public void guardar(Producto producto)  {
		
		productoDAO.guardar(producto);
		
	  
	    
		    	    
	}





}
