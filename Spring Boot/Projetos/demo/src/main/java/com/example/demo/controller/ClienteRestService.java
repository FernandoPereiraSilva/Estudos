package com.example.demo.controller;

import java.util.ArrayList;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;
import javax.websocket.server.PathParam;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Cliente;
import com.example.demo.model.Pedido;
import com.example.demo.repository.ClienteRepository;

@RestController
@RequestMapping("/service/cliente")
public class ClienteRestService {

	@Autowired
	private ClienteRepository clienteRepository;

	private static final Logger LOGGER = LogManager.getLogger(ClienteRestService.class.getName());

	@RequestMapping(value = "/cadastrar", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Cliente createCliente(@RequestBody Cliente cliente) {
		try {
			LOGGER.info("Service: service/cliente/cadastrar. Method: POST (createCliente) - Iniciando criação de cliente");
			if (cliente.getNome() != null && !cliente.getNome().equals("")) {
				cliente = clienteRepository.save(cliente);
				LOGGER.info("Service: service/cliente/cadastrar. Method: POST (createCliente) - Cliente criado, id " + cliente.getIdCliente());
				return cliente;
			} else {
				LOGGER.info("Service: service/cliente/cadastrar. Method: POST (createCliente) - Campos necessários não preenchidos");
			}
		} catch (Exception e) {
			LOGGER.error("Service: service/cliente/cadastrar. Method: POST (createCliente) - Erro ao criar o cliente", e);
			e.printStackTrace();
		}

		return null;
	}

	@RequestMapping(value = "/consultar", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ArrayList<Cliente> getClientes(@PathParam("idCliente") Long idCliente, @PathParam("nome") String nome) {
		ArrayList<Cliente> result = clienteRepository.find(idCliente, nome);
		} catch (Exception e) {
			LOGGER.error("Service: service/cliente/consultar. Method: GET (getCliente) - Erro ao buscar o cliente", e);
			e.printStackTrace();
		}
		
		return null;
	}

	@RequestMapping(value = "/consultar/todos", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ArrayList<Cliente> getClientes() {
		try {
			LOGGER.info("Service: service/cliente/consultar/todos. Method: GET (getClientes) - Iniciando listagem de todos os clientes");
			return clienteRepository.findAll();
		} catch (Exception e) {
			LOGGER.error("Service: service/cliente/consultar/todos. Method: GET (getClientes) - Erro ao buscar os clientes", e);
			e.printStackTrace();
			return null;
		}
	}

	@RequestMapping(value = "/alterar", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Cliente putCliente(@RequestBody Cliente cliente) {
		try {
			LOGGER.info("Service: service/cliente/alterar. Method: PUT (putCliente) - Iniciando a alteração do cliente");
			if (cliente.getIdCliente() != 0 && cliente.getNome() != null & !cliente.getNome().equals("")) {
				cliente = clienteRepository.save(cliente);
				if (cliente != null) {
					LOGGER.info("Service: service/cliente/alterar. Method: PUT (putCliente) - Alterando o cliente " + cliente.getIdCliente());
					return cliente;
				}
			} else {
				LOGGER.info("Service: service/cliente/alterar. Method: PUT (putCliente) - Campos necessários não preenchidos");
			}
		} catch (Exception e) {
			LOGGER.error("Service: service/cliente/alterar. Method: PUT (putCliente) - Erro ao alterar o cliente", e);
			e.printStackTrace();
		}

		return null;
	}

	@RequestMapping(value = "/deletar", method = RequestMethod.DELETE, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ArrayList<Cliente> deleteCliente(@RequestBody Cliente cliente) {
		try {
			LOGGER.info("Service: service/cliente/deletar. Method: DELETE (deleteCliente) - Iniciando a exclusão do cliente");
			if (cliente.getIdCliente() != 0) {
				LOGGER.info("Service: service/cliente/deletar. Method: DELETE (deleteCliente) - Excluindo o cliente " + cliente.getIdCliente());
				clienteRepository.delete(cliente);
				return clienteRepository.findAll();
			} else {
				LOGGER.info("Service: service/cliente/deletar. Method: DELETE (deleteCliente) - Campos necessários não preenchidos");
			}
		} catch (Exception e) {
			LOGGER.error("Service: service/cliente/deletar. Method: DELETE (deleteCliente) - Erro ao excluir o cliente", e);
			e.printStackTrace();
		}

		return null;
	}
}