package com.example.demo.model;

import java.io.Serializable;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Embeddable
@Cacheable(false)
public class PedidoItemPK implements Serializable {

	private static final long serialVersionUID = 1L;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_PEDIDO")
	@JsonBackReference
	private Pedido pedido;
	
	@ManyToOne
	@JoinColumn(name = "ID_PRODUTO")
	private Produto produto;
	
	public PedidoItemPK() {}
	
	public PedidoItemPK(Pedido pedido, Produto produto) {
		setPedido(pedido);
		setProduto(produto);
	}
	
	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}
	
	public void setProduto(Produto produto) {
		this.produto = produto;
	}
	
	public Pedido getPedido() {
		return pedido;
	}
	
	public Produto getProduto() {
		return produto;
	}
}