package com.alura.jdbc.modelo;

public class Producto {
	
	private Integer Id;
	private String nombre;
	private String descripcion;
	private Integer cantidad;
	private Integer categoriaId;
	
	public Producto(String nombre, String descripcion, Integer cantidad) {
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.cantidad = cantidad;
	}

	public Producto(int ID, String nombre, String descripcion, int cantidad) {
		this.Id = ID;
		this.nombre = nombre ;
		this.descripcion = descripcion;
		this.cantidad = cantidad;
		
		
	}

	public Producto(int id, String nombre, int cantidad) {
		this.Id = id;
		this.nombre = nombre ;
		this.cantidad = cantidad ;
		
		
	
		// TODO Auto-generated constructor stub
	}

	public String getNombre() {
		return nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public Integer getCantidad() {
		return cantidad;
	}

	public void setId(Integer Id) {
		this.Id = Id;
	}
	public Integer getid() {
		return this.Id;
	}
	
	public void setCategoriaId(Integer categoriaId) {
		this.categoriaId = categoriaId;
		
	}
	public int getCategoriaId() {
		return this.categoriaId;
	}

	
	@Override
	public String toString() {
		return String.format(
			"{Id: %s, nombre: %s, descripcion: %s, cantidad: %d}",
			this.Id,
			this.nombre,
			this.descripcion,
			this.cantidad
		);
	}

	

	
}
