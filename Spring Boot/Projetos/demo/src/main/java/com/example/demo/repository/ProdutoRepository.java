package com.example.demo.repository;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.example.demo.model.Produto;

public interface ProdutoRepository extends PagingAndSortingRepository<Produto, String> {

	@Query("SELECT P FROM Produto P WHERE P.idProduto = :ID_PRODUTO ORDER BY ID_PRODUTO ASC")
	public Produto findById(@Param("ID_PRODUTO") long idProduto);
	
	@Query("SELECT P FROM Produto P WHERE UPPER(P.nome) = UPPER(:NOME) ORDER BY ID_PRODUTO ASC")
	public Produto findByNome(@Param("NOME") String nome);

	@Query("SELECT P FROM Produto P ORDER BY ID_PRODUTO ASC")
	public ArrayList<Produto> findAll();
}