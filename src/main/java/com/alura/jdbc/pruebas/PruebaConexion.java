package com.alura.jdbc.pruebas;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.alura.jdbc.factory.ConeccionFactory;

public class PruebaConexion {

    public static void main(String[] args) throws SQLException {
		Connection con= new ConeccionFactory().recuperaConexion();


        System.out.println("Cerrando la conexion");

        con.close();
    }

}
