package com.example.demo.model;

import javax.persistence.*;

@Entity
@Table(name = "TB_PEDIDO_ITEM")
public class PedidoItem {

	@EmbeddedId
	private PedidoItemPK pedidoItemPK;
	
	@Column(name = "QUANTIDADE", nullable = false)
	private Long quantidade;
	
	public PedidoItem() {}
	
	public PedidoItem(PedidoItemPK pedidoItemPK, Long quantidade) {
		setPedidoItemPK(pedidoItemPK);
		setQuantidade(quantidade);
	}
	
	public void setPedidoItemPK(PedidoItemPK pedidoItemPK) {
		this.pedidoItemPK = pedidoItemPK;
	}
	
	public void setQuantidade(Long quantidade) {
		this.quantidade = quantidade;
	}
	
	public PedidoItemPK getPedidoItemPK() {
		return pedidoItemPK;
	}
	
	public Long getQuantidade() {
		return quantidade;
	}
}