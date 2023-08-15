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
		        try {
		            PreparedStatement statement;
		                statement = con.prepareStatement(
		                        "INSERT INTO PRODUCTO "
		                        + "(nombre, descripcion, cantidad , categoria_id)"
		                        + " VALUES (?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
		    
		            try (statement) {
		                statement.setString(1, producto.getNombre());
		                statement.setString(2, producto.getDescripcion());
		                statement.setInt(3, producto.getCantidad());
		                statement.setInt(4, producto.getCategoriaId());
		    
		                statement.execute();
		    
		                final ResultSet resultSet = statement.getGeneratedKeys();
		    
		                try (resultSet) {
		                    while (resultSet.next()) {
		                        producto.setId(resultSet.getInt(1));
		                        
		                        System.out.println(String.format("Fue insertado el producto: %s", producto));
		                    }
		                }
		            }
		        } catch (SQLException e) {
		            throw new RuntimeException(e);
		        }
		    }


		 public List<Producto> listar() {
		        List<Producto> resultado = new ArrayList<>();

		        try {
		            final PreparedStatement statement = con
		                    .prepareStatement("SELECT ID, nombre, descripcion, cantidad FROM producto");
		    
		            try (statement) {
		                statement.execute();
		    
		                final ResultSet resultSet = statement.getResultSet();
		    
		                try (resultSet) {
		                    while (resultSet.next()) {
		                        resultado.add(new Producto(
		                                resultSet.getInt("ID"),
		                                resultSet.getString("nombre"),
		                                resultSet.getString("descripcion"),
		                                resultSet.getInt("cantidad")));
		                    }
		                }
		            }
		        } catch (SQLException e) {
		            throw new RuntimeException(e);
		        }

		        return resultado;
		    }
		 
		 public int eliminar(Integer id) {
		        try {
		            final PreparedStatement statement = con.prepareStatement("DELETE FROM PRODUCTO WHERE ID = ?");

		            try (statement) {
		                statement.setInt(1, id);
		                statement.execute();

		                int updateCount = statement.getUpdateCount();

		                return updateCount;
		            }
		        } catch (SQLException e) {
		            throw new RuntimeException(e);
		        }
		    }
		 
		 public int modificar(String nombre, String descripcion, Integer cantidad, Integer id) {
		        try {
		            final PreparedStatement statement = con.prepareStatement(
		                    "UPDATE PRODUCTO SET "
		                    + " nombre = ?, "
		                    + " descripcion = ?,"
		                    + " cantidad = ?"
		                    + " WHERE id = ?");

		            try (statement) {
		                statement.setString(1, nombre);
		                statement.setString(2, descripcion);
		                statement.setInt(3, cantidad);
		                statement.setInt(4, id);
		                statement.execute();

		                int updateCount = statement.getUpdateCount();

		                return updateCount;
		            }
		        } catch (SQLException e) {
		            throw new RuntimeException(e);
		        }
		    }



		
}

