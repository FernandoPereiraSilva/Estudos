package com.example.demo.repository;

import java.util.ArrayList;
import java.util.Date;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.example.demo.model.Pedido;

public interface PedidoRepository extends PagingAndSortingRepository<Pedido, String> {

	@Query("SELECT P FROM Pedido P WHERE P.cliente.idCliente = :ID_CLIENTE ORDER BY ID_PEDIDO ASC")
	public ArrayList<Pedido> findByCliente(@Param("ID_CLIENTE") long idCliente);

	@Query(
		"\n SELECT P FROM Pedido P" + 
		"\n   INNER JOIN P.cliente C" + 
		"\n WHERE (" + 
		"\n   :NOME IS NULL" + 
		"\n   OR UPPER(C.nome) LIKE UPPER('%' || :NOME || '%')" + 
		"\n ) AND (" + 
		"\n   :DT_PEDIDO IS NULL" + 
		"\n   OR TRUNC(P.dtPedido) = TRUNC(:DT_PEDIDO)" + 
		"\n ) AND (" + 
		"\n   :ID_STATUS IS NULL" + 
		"\n   OR P.status.idStatus = :ID_STATUS" + 
		"\n ) AND (" + 
		"\n   :ID_PEDIDO IS NULL" + 
		"\n   OR P.idPedido = :ID_PEDIDO" + 
		"\n )"
	)
	public ArrayList<Pedido> find(@Param("NOME") String nome, @Param("DT_PEDIDO") Date dtPedido, @Param("ID_STATUS") Long idStatus, @Param("ID_PEDIDO") Long idPedido);
	
	@Query("SELECT P FROM Pedido P ORDER BY ID_PEDIDO ASC")
	public ArrayList<Pedido> findAll();
}