package com.example.demo.model;

import javax.persistence.*;

@Entity
@Table(name = "TB_PEDIDO_STATUS")
public class PedidoStatus {

	@Id
	@Column(name = "ID_STATUS")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idStatus;
	
	@Column(name = "DS_STATUS", nullable = false, length = 200)
	private String dsStatus;
	
	public PedidoStatus() {}
	
	public PedidoStatus(long idStatus, String dsStatus) {
		setIdStatus(idStatus);
		setDsStatus(dsStatus);
	}
	
	public void setIdStatus(Long idStatus) {
		this.idStatus = idStatus;
	}
	
	public void setDsStatus(String dsStatus) {
		this.dsStatus = dsStatus;
	}
	
	public Long getIdStatus() {
		return idStatus;
	}
	
	public String getDsStatus() {
		return dsStatus;
	}
}