package com.example.demo;

import java.util.ArrayList;

import com.example.demo.model.Cliente;
import com.example.demo.model.Pedido;

public class Storage {

	private static ArrayList<Cliente> clientes;
	private static ArrayList<Pedido> pedidos;
	
	public static ArrayList<Cliente> getClientes() {
		if (Storage.clientes == null) {
			clientes = new ArrayList<Cliente>();
//			clientes.add(new Cliente(1, "Fernando"));
//			clientes.add(new Cliente(2, "Willian"));
//			clientes.add(new Cliente(3, "João"));
//			clientes.add(new Cliente(4, "Jefferson"));
		}

		return clientes;
	}
	
	public static ArrayList<Pedido> getPedidos() {
		if (Storage.pedidos == null) {
			pedidos = new ArrayList<Pedido>();
		}

		return pedidos;
	}
}