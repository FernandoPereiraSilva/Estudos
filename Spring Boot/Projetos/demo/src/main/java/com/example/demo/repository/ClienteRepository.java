package com.example.demo.repository;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.example.demo.model.Cliente;

public interface ClienteRepository extends PagingAndSortingRepository<Cliente, String> {

	@Query(
		"\n SELECT C FROM Cliente C" +  
		"\n WHERE (" + 
		"\n   :ID_CLIENTE IS NULL" + 
		"\n   OR C.idCliente = :ID_CLIENTE" + 
		"\n ) AND (" + 
		"\n   :NOME IS NULL" + 
		"\n   OR UPPER(C.nome) LIKE UPPER('%' || :NOME || '%')" + 
		"\n ) " +
		"\n ORDER BY ID_CLIENTE ASC"
	)
	public ArrayList<Cliente> find(@Param("ID_CLIENTE") long idCliente, @Param("NOME") String nome);
}