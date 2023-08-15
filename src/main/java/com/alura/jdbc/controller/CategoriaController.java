package com.alura.jdbc.controller;

import java.util.ArrayList;
import java.util.List;

import com.alura.jdbc.dao.CategoriaDAO;
import com.alura.jdbc.factory.ConeccionFactory;
import com.alura.jdbc.modelo.Categoria;

public class CategoriaController {
	
	private CategoriaDAO categoriDAO;
	
	public CategoriaController() {
		var factory = new ConeccionFactory();
		this.categoriDAO = new CategoriaDAO (factory.recuperaConexion());
		
		
		
	}

	public List<Categoria> listar() {
		// TODO
		return  categoriDAO.listar();
		
	}

    public List<Categoria> cargaReporte() {
    	return this.categoriDAO.listarConProductos();
    	
    }

}