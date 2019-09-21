package com.example.demo.controller;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.PedidoStatus;
import com.example.demo.repository.PedidoStatusRepository;

@RestController
@RequestMapping("/service/pedidoStatus")
public class PedidoStatusRestService {

	@Autowired
	private PedidoStatusRepository pedidoStatusRepository;

	private static final Logger LOGGER = LogManager.getLogger(PedidoStatusRestService.class.getName());

	@RequestMapping(value = "/consultar", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ArrayList<PedidoStatus> getPedidoStatus() {
		try {
			LOGGER.info("Service: service/pedidoStatus/consultar. Method: GET (getPedidoStatus) - Iniciando listagem de todos os status");
			return pedidoStatusRepository.findAll();
		} catch (Exception e) {
			LOGGER.error("Service: service/pedidoStatus/consultar. Method: GET (getPedidoStatus) - Erro ao buscar os status", e);
			e.printStackTrace();
			return null;
		}		
	}
}