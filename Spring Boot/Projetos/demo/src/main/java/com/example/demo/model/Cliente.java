package com.example.demo.model;

import javax.persistence.*;

@Entity
@Table(name = "TB_CLIENTE")
public class Cliente {

	@Id
	@Column(name = "ID_CLIENTE")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idCliente;
	
	@Column(name = "NOME", nullable = false, length = 200)
	private String nome;
	
	@Column(name = "ENDERECO", nullable = true, length = 200)
	private String endereco;
	
	public Cliente() {}
	
	public Cliente(long idCliente, String nome, String endereco) {
		setIdCliente(idCliente);
		setNome(nome);
		setEndereco(endereco);
	}
	
	public void setIdCliente(Long idCliente) {
		this.idCliente = idCliente;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}
	
	public Long getIdCliente() {
		return idCliente;
	}
	
	public String getNome() {
		return nome;
	}
	
	public String getEndereco() {
		return endereco;
	}
}