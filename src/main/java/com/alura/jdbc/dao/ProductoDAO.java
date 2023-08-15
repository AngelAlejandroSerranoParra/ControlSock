package com.alura.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.alura.jdbc.factory.ConeccionFactory;
import com.alura.jdbc.modelo.Producto;

public class ProductoDAO {
	
	final private Connection con;
		public ProductoDAO(Connection con) {
			this.con = con ;
			
		}
		
	public void guardarProducto(Producto producto) throws SQLException {

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
	    	

		}
		

}
