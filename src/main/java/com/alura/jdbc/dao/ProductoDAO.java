package com.alura.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alura.jdbc.factory.ConeccionFactory;
import com.alura.jdbc.modelo.Producto;

public class ProductoDAO {
	
	final private Connection con;
		public ProductoDAO(Connection con) {
			this.con = con;
			
		}
		
		public void guardar(Producto producto) {
		    try(con) {

		        final PreparedStatement statement = con.prepareStatement("INSERT INTO producto "
		                + "(nombre, descripcion, cantidad)"
		                + " VALUES(?,?,?)",
		                Statement.RETURN_GENERATED_KEYS);
		        try (statement) {
		            ejecutaRegistro(producto, statement);
		        } 
		    } catch (SQLException e) {
		        throw new RuntimeException(e);
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
	    	

		}

		public List<Producto> listar() {
		    List<Producto> resultado = new ArrayList<>();

		    ConeccionFactory factory = new ConeccionFactory();
		    
		    try (Connection con = factory.recuperaConexion()) {
		        try (PreparedStatement statement = con.prepareStatement("SELECT ID, nombre, descripcion, cantidad FROM producto")) {
		            statement.execute();
		            try (ResultSet resultSet = statement.getResultSet()) {
		                while (resultSet.next()) {
		                    Producto fila = new Producto(
		                            resultSet.getInt("ID"),
		                            resultSet.getString("nombre"),
		                            resultSet.getString("descripcion"),
		                            resultSet.getInt("cantidad")
		                    );
		                    resultado.add(fila);
		                }
		            }
		        } catch (SQLException e) {
		            throw new RuntimeException(e);
		        }
		    } catch (SQLException e) {
		        throw new RuntimeException(e);
		    }

		    return resultado;
		}

		
}

