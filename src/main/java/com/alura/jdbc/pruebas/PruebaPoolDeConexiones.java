package com.alura.jdbc.pruebas;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Iterator;

import com.alura.jdbc.factory.ConeccionFactory;

public class PruebaPoolDeConexiones {
	public static void main(String[] args) throws SQLException {
		 ConeccionFactory coneccionFactory = new ConeccionFactory();
		 
		 for (int i = 0; i < 20; i++) {
			 Connection conexion = coneccionFactory.recuperaConexion();
			 System.out.println("Abriendo la conexion de numero" + (i+1));
			
		}
		
	}

}
