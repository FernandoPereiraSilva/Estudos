package com.example.demo.repository;

import java.util.ArrayList;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.example.demo.model.PedidoItem;

public interface PedidoItemRepository extends PagingAndSortingRepository<PedidoItem, String> {

	@Query("SELECT PI FROM PedidoItem PI WHERE PI.pedidoItemPK.pedido.idPedido = :ID_PEDIDO ORDER BY ID_PEDIDO ASC")
	public ArrayList<PedidoItem> findByPedido(@Param("ID_PEDIDO") Long idPedido);
	
	@Modifying
	@Transactional
	@Query("DELETE PedidoItem PI WHERE PI.pedidoItemPK.pedido.idPedido = :ID_PEDIDO AND PI.pedidoItemPK.produto.idProduto = :ID_PRODUTO")
	public void deleteById(@Param("ID_PEDIDO") Long idPedido, @Param("ID_PRODUTO") Long idProduto);
}