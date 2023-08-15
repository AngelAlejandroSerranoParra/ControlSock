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
			var queySelect = "SELECT id , nombre from categoria";
			
			System.out.println(queySelect);
			final PreparedStatement statement = con.prepareStatement(
					queySelect);
			
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

	public List<Categoria> listarConProductos() {
		List<Categoria>resulado = new ArrayList<>();
		
		try {
			var queySelect = "SELECT c.id , c.nombre ,p.id , p.nombre , p.cantidad"
					+"from categoria c"
					+"INNER JOIN producto p ON c.id = p.categoria_id";
			
			System.out.println(queySelect);
			final PreparedStatement statement = con.prepareStatement(
					queySelect);
			
			try (statement){

			final ResultSet resultSet = statement.executeQuery();
			try (resultSet) {
				 while (resultSet.next()) {
					Integer categoriaId = resultSet.getInt("id");
					String categoriaNombre = resultSet.getString("nombre");
					var categoria = resulado
							.stream()
							.filter(cat -> cat.getId().equals(categoriaId))
							.findAny().orElseGet(() ->{
								Categoria cat = new Categoria(categoriaId,
										categoriaNombre);
								
								resulado.add(cat);
								return cat;
								
								
							});
							
							
					
					 
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
