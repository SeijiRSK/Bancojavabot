package com.bancoJavaBot.objeto;

import java.util.ArrayList;

public class ListaClientes {
	private ArrayList<Cliente> listaClientes = new ArrayList<Cliente>();

	public ArrayList<Cliente> getListaClientes() {
		return listaClientes;
	}

	public void setListaClientes(ArrayList<Cliente> listaClientes) {
		this.listaClientes = listaClientes;
	} 
}
