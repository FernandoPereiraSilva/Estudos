package com.example.demo.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "TB_PEDIDO")
public class Pedido {

	@Id
	@Column(name = "ID_PEDIDO")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idPedido;
	
	@Column(name = "DT_PEDIDO", nullable = false)
	private Date dtPedido;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ID_CLIENTE")
	private Cliente cliente;
	
	@OneToMany(mappedBy = "pedidoItemPK.pedido", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<PedidoItem> listPedidoItem;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ID_STATUS")
	private PedidoStatus status;
	
	public Pedido() {}
	
	public Pedido(long idPedido, Date dtPedido, Cliente cliente, List<PedidoItem> listPedidoItem, PedidoStatus status) {
		setIdPedido(idPedido);
		setDtPedido(dtPedido);
		setListPedidoItem(listPedidoItem);
		setCliente(cliente);
		setStatus(status);
	}
	
	public void setIdPedido(Long idPedido) {
		this.idPedido = idPedido;
	}
	
	public void setDtPedido(Date dtPedido) {
		this.dtPedido = dtPedido;
	}
	
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	
	public void setListPedidoItem(List<PedidoItem> listPedidoItem) {
		this.listPedidoItem = listPedidoItem;
	}
	
	public void setStatus(PedidoStatus status) {
		this.status = status;
	}
	
	public Long getIdPedido() {
		return idPedido;
	}
	
	public Date getDtPedido() {
		return dtPedido;
	}
	
	public Cliente getCliente() {
		return cliente;
	}
	
	public List<PedidoItem> getListPedidoItem() {
		return listPedidoItem;
	}
	
	public PedidoStatus getStatus() {
		return status;
	}
}