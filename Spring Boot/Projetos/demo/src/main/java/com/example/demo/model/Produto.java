package com.example.demo.model;

import javax.persistence.*;

@Entity
@Table(name = "TB_PRODUTO")
public class Produto {

	@Id
	@Column(name = "ID_PRODUTO")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idProduto;
	
	@Column(name = "NOME", nullable = false, length = 200)
	private String nome;
	
	@Column(name = "PRECO", nullable = true)
	private Double preco;
	
	public Produto() {}
	
	public Produto(long idProduto, String nome, Double preco) {
		setIdProduto(idProduto);
		setNome(nome);
		setPreco(preco);
	}
	
	public void setIdProduto(Long idProduto) {
		this.idProduto = idProduto;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public void setPreco(Double preco) {
		this.preco = preco;
	}
	
	public Long getIdProduto() {
		return idProduto;
	}
	
	public String getNome() {
		return nome;
	}
	
	public Double getPreco() {
		return preco;
	}
}