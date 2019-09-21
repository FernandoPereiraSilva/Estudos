package com.example.demo.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.websocket.server.PathParam;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Pedido;
import com.example.demo.model.PedidoStatus;
import com.example.demo.repository.PedidoRepository;

@RestController
@RequestMapping("/service/pedido")
public class PedidoRestService {

	@Autowired
	private PedidoRepository pedidoRepository;

	private static final Logger LOGGER = LogManager.getLogger(PedidoRestService.class.getName());

	@RequestMapping(value = "/cadastrar", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Pedido createPedido(@RequestBody Pedido pedido) {
		try {
			LOGGER.info("Service: service/pedido/cadastrar. Method: POST (createPedido) - Iniciando criação de pedido");
			if (pedido.getCliente() != null && pedido.getCliente().getIdCliente() != null) {
				PedidoStatus pedidoStatus = new PedidoStatus();
				pedidoStatus.setIdStatus(1L);
				pedido.setDtPedido(new Date());
				pedido.setStatus(pedidoStatus);
				pedido = pedidoRepository.save(pedido);
				LOGGER.info("Service: service/pedido/cadastrar. Method: POST (createPedido) - Pedido criado, id " + pedido.getIdPedido());
				return pedido;
			} else {
				LOGGER.info("Service: service/pedido/cadastrar. Method: POST (createPedido) - Campos necessários não preenchidos");
			}
		} catch (Exception e) {
			LOGGER.error("Service: service/pedido/cadastrar. Method: POST (createPedido) - Erro ao criar o pedido", e);
			e.printStackTrace();
		}

		return null;
	}

	@RequestMapping(value = "/consultar", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ArrayList<Pedido> getPedido(@PathParam("idPedido") Long idPedido, @PathParam("nome") String nome, @PathParam("idStatus") Long idStatus, @PathParam("dtPedido") String dtPedido) {
		try {
			Date dtFormated = null;
			
			try {
				dtFormated = new SimpleDateFormat("dd/MM/yyyy").parse(dtPedido);
			} catch (Exception e) {
				dtPedido = null;
			}
			
			ArrayList<Pedido> arrayListPedido = pedidoRepository.find(nome, dtFormated, idStatus, idPedido);
			if (arrayListPedido != null) {
				LOGGER.info("Service: service/pedido/consultar. Method: GET (getPedido) - Retornando o pedido");
				return arrayListPedido;
			}
			LOGGER.info("Service: service/pedido/consultar. Method: GET (getPedido) - Não foi encontrado nenhum pedido para os filtros informados");
		} catch (Exception e) {
			LOGGER.error("Service: service/pedido/consultar. Method: GET (getPedido) - Erro ao buscar o pedido", e);
			e.printStackTrace();
		}
		
		return null;
	}

	@RequestMapping(value = "/deletar", method = RequestMethod.DELETE, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ArrayList<Pedido> deletePedido(@RequestBody Pedido pedido) {
		try {
			LOGGER.info("Service: service/pedido/deletar. Method: DELETE (deletePedido) - Iniciando a exclusão do pedido");
			if (pedido.getIdPedido() != 0) {
				LOGGER.info("Service: service/pedido/deletar. Method: DELETE (deletePedido) - Excluindo o pedido " + pedido.getIdPedido());
				pedidoRepository.delete(pedido);
				return pedidoRepository.findAll();
			} else {
				LOGGER.info("Service: service/pedido/deletar. Method: DELETE (deletePedido) - Campos necessários não preenchidos");
			}
		} catch (Exception e) {
			LOGGER.error("Service: service/pedido/deletar. Method: DELETE (deletePedido) - Erro ao excluir o pedido", e);
			e.printStackTrace();
		}

		return null;
	}

	@RequestMapping(value = "/alterar", method = RequestMethod.PATCH, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Pedido patchPedido(@RequestBody Pedido pedido) {
		try {
			LOGGER.info("Service: service/pedido/alterar. Method: PATCH (patchPedido) - Iniciando a alteração por atributos do pedido");
			if (pedido.getIdPedido() != 0) {
				ArrayList<Pedido> arrayListPedido = getPedido(pedido.getIdPedido(), null, null, null);
				if (arrayListPedido.size() > 0) {
					Pedido previousPedido = arrayListPedido.get(0);
					
					if (pedido.getDtPedido() != null && !pedido.getDtPedido().equals(previousPedido.getDtPedido())) {
						previousPedido.setDtPedido(pedido.getDtPedido());
					}
					if (pedido.getStatus() != null && previousPedido.getStatus() != null && pedido.getStatus().getIdStatus() != null && !pedido.getStatus().getIdStatus().equals(previousPedido.getStatus().getIdStatus())) {
						previousPedido.setStatus(pedido.getStatus());
					}

					LOGGER.info("Service: service/pedido/alterar. Method: PATCH (patchPedido) - Alterando o pedido " + pedido.getIdPedido());
					previousPedido = pedidoRepository.save(previousPedido);
					return previousPedido;
				} else {
					LOGGER.info("Service: service/pedido/alterar. Method: PATCH (patchPedido) - Não foi encontrado o pedido " + pedido.getIdPedido());
					return null;
				}
			} else {
				LOGGER.info("Service: service/pedido/alterar. Method: PATCH (patchPedido) - Campos necessários não preenchidos");
			}
		} catch (Exception e) {
			LOGGER.error("Service: service/pedido/alterar. Method: PATCH (patchPedido) - Erro ao alterar o pedido", e);
			e.printStackTrace();
		}

		return null;
	}
}