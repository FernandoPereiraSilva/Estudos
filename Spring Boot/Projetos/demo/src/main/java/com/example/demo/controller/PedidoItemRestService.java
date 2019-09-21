package com.example.demo.controller;

import java.util.ArrayList;

import javax.websocket.server.PathParam;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.PedidoItem;
import com.example.demo.model.PedidoItemPK;
import com.example.demo.repository.PedidoItemRepository;

@RestController
@RequestMapping("/service/pedidoItem")
public class PedidoItemRestService {

	@Autowired
	private PedidoItemRepository pedidoItemRepository;

	private static final Logger LOGGER = LogManager.getLogger(PedidoItemRestService.class.getName());

	@RequestMapping(value = "/cadastrar", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public PedidoItem createPedidoItem(@RequestBody PedidoItem pedidoItem) {
		try {
			LOGGER.info("Service: service/pedidoItem/cadastrar. Method: POST (createPedidoItem) - Iniciando criação do item do pedido");
			if (pedidoItem.getPedidoItemPK() != null && pedidoItem.getPedidoItemPK().getPedido() != null && pedidoItem.getPedidoItemPK().getPedido().getIdPedido() != null && pedidoItem.getPedidoItemPK().getProduto() != null && pedidoItem.getPedidoItemPK().getProduto().getIdProduto() != null && pedidoItem.getQuantidade() != null) {
				pedidoItem = pedidoItemRepository.save(pedidoItem);
				LOGGER.info("Service: service/pedidoItem/cadastrar. Method: POST (createPedidoItem) - Item do pedido criado");
				return pedidoItem;
			} else {
				LOGGER.info("Service: service/pedidoItem/cadastrar. Method: POST (createPedidoItem) - Campos necessários não preenchidos");
			}
		} catch (Exception e) {
			LOGGER.error("Service: service/pedidoItem/cadastrar. Method: POST (createPedidoItem) - Erro ao criar o item do pedido", e);
			e.printStackTrace();
		}

		return null;
	}

	@RequestMapping(value = "/consultar", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ArrayList<PedidoItem> getPedidoItem(@PathParam("idPedido") Long idPedido) {
		try {
			LOGGER.info("Service: service/pedidoItem/consultar. Method: GET (getPedidoItem) - Iniciando listagem de todos os itens do pedido");
			return pedidoItemRepository.findByPedido(idPedido);
		} catch (Exception e) {
			LOGGER.error("Service: service/pedidoItem/consultar. Method: GET (getPedidoItem) - Erro ao buscar os itens", e);
			e.printStackTrace();
			return null;
		}		
	}

	@RequestMapping(value = "/deletar", method = RequestMethod.DELETE, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ArrayList<PedidoItem> deletePedidoItem(@RequestBody PedidoItemPK pedidoItemPK) {
		try {
			LOGGER.info("Service: service/pedidoItem/deletar. Method: DELETE (deletePedidoItem) - Iniciando a exclusão do item do pedido");
			if (pedidoItemPK.getPedido() != null && pedidoItemPK.getPedido().getIdPedido() != 0 && pedidoItemPK.getProduto() != null && pedidoItemPK.getProduto().getIdProduto() != 0) {
				LOGGER.info("Service: service/pedidoItem/deletar. Method: DELETE (deletePedidoItem) - Excluindo o item do pedido");
				pedidoItemRepository.deleteById(pedidoItemPK.getPedido().getIdPedido(), pedidoItemPK.getProduto().getIdProduto());
			} else {
				LOGGER.info("Service: service/pedidoItem/deletar. Method: DELETE (deletePedidoItem) - Campos necessários não preenchidos");
			}
		} catch (Exception e) {
			LOGGER.error("Service: service/pedidoItem/deletar. Method: DELETE (deletePedidoItem) - Erro ao excluir o item do pedido", e);
			e.printStackTrace();
		}
		
		return null;
	}
}