package com.alura.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.management.RuntimeErrorException;

import com.alura.jdbc.modelo.Categoria;

public class CategoriaDAO {
	
	private Connection  con ;
	

	public CategoriaDAO(Connection con) {
		this.con = con ;
		
		
		
		// TODO Auto-generated constructor stub
	}

	public List<Categoria> listar() {
		List<Categoria>resulado = new ArrayList<>();
		
		try {
			final PreparedStatement statement = con.prepareStatement(
					"SELECT id , nombre from categoria");
			
			try (statement){

			final ResultSet resultSet = statement.executeQuery();
			try (resultSet) {
				 while (resultSet.next()) {
					var categoria = new Categoria(resultSet.getInt("id"),
							 resultSet.getString("nombre"));
					
					resulado.add(categoria);
					
					 
				 }
				
			};
			}
			
			 
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e);
			
		}
		
		return resulado;
	}
	
	

}
