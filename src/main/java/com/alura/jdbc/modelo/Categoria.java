package com.alura.jdbc.modelo;

import java.util.ArrayList;
import java.util.List;

public class Categoria {

	private Integer id;
	private String nombre;
	
	private List<Producto> productos;
	
	
	public Categoria(int id, String nombre) {
		this.id= id;
		this.nombre = nombre;
		
	}
	public Integer getId() {
		
		return this.id;
		
		
	}
	
	public String toString() {
		return this.nombre;
		
		
	}
	public void agregar(Producto producto) {
		if (this.productos == null ) {
			this.productos = new ArrayList<Producto>();
			
			
		}
		
		this.productos.add(producto);
		
	}
	public List<Producto> getproductos() {
		// TODO Auto-generated method stub
		return this.productos;
	}

	

	

}
