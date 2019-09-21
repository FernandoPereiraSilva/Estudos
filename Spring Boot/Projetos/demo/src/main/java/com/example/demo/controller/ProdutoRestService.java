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

import com.example.demo.model.Produto;
import com.example.demo.repository.ProdutoRepository;

@RestController
@RequestMapping("/service/produto")
public class ProdutoRestService {

	@Autowired
	private ProdutoRepository produtoRepository;

	private static final Logger LOGGER = LogManager.getLogger(ProdutoRestService.class.getName());

	@RequestMapping(value = "/cadastrar", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Produto createProduto(@RequestBody Produto produto) {
		try {
			LOGGER.info("Service: service/produto/cadastrar. Method: POST (createProduto) - Iniciando criação de produto");
			if (produto.getNome() != null && !produto.getNome().equals("")) {
				produto = produtoRepository.save(produto);
				LOGGER.info("Service: service/produto/cadastrar. Method: POST (createProduto) - Produto criado, id " + produto.getIdProduto());
				return produto;
			} else {
				LOGGER.info("Service: service/produto/cadastrar. Method: POST (createProduto) - Campos necessários não preenchidos");
			}
		} catch (Exception e) {
			LOGGER.error("Service: service/produto/cadastrar. Method: POST (createProduto) - Erro ao criar o produto", e);
			e.printStackTrace();
		}

		return null;
	}

	@RequestMapping(value = "/consultar", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Produto getProduto(@PathParam("idProduto") Long idProduto, @PathParam("nome") String nome) {
		try {
			if (idProduto != null) {
				LOGGER.info("Service: service/produto/consultar. Method: GET (getProduto) - Iniciando busca de produto por idProduto");
				Produto produto = produtoRepository.findById(idProduto);
				if (produto != null) {
					LOGGER.info("Service: service/produto/consultar. Method: GET (getProduto) - Retornando o produto " + idProduto);
					return produto;
				}
				LOGGER.info("Service: service/produto/consultar. Method: GET (getProduto) - Produto id " + idProduto + " não encontrado");
			} else if (nome != null) {
				Produto produto = produtoRepository.findByNome(nome);
				if (produto != null) {
					LOGGER.info("Service: service/produto/consultar. Method: GET (getProduto) - Retornando o produto " + nome);
					return produto;
				}
				LOGGER.info("Service: service/produto/consultar. Method: GET (getProduto) - Produto nome " + nome + " não encontrado");
			}
			LOGGER.info("Service: service/produto/consultar. Method: GET (getProduto) - Campos necessários não preenchidos");
		} catch (Exception e) {
			LOGGER.error("Service: service/produto/consultar. Method: GET (getProduto) - Erro ao buscar o produto", e);
			e.printStackTrace();
		}
		
		return null;
	}

	@RequestMapping(value = "/consultar/todos", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ArrayList<Produto> getProdutos() {
		try {
			LOGGER.info("Service: service/produto/consultar/todos. Method: GET (getProdutos) - Iniciando listagem de todos os produtos");
			return produtoRepository.findAll();
		} catch (Exception e) {
			LOGGER.error("Service: service/produto/consultar/todos. Method: GET (getProdutos) - Erro ao buscar os produtos", e);
			e.printStackTrace();
			return null;
		}
	}

	@RequestMapping(value = "/alterar", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Produto putProduto(@RequestBody Produto produto) {
		try {
			LOGGER.info("Service: service/produto/alterar. Method: PUT (putProduto) - Iniciando a alteração do produto");
			if (produto.getIdProduto() != 0 && produto.getNome() != null & !produto.getNome().equals("")) {
				produto = produtoRepository.save(produto);
				if (produto != null) {
					LOGGER.info("Service: service/produto/alterar. Method: PUT (putProduto) - Alterando o produto " + produto.getIdProduto());
					return produto;
				}
			} else {
				LOGGER.info("Service: service/produto/alterar. Method: PUT (putProduto) - Campos necessários não preenchidos");
			}
		} catch (Exception e) {
			LOGGER.error("Service: service/produto/alterar. Method: PUT (putProduto) - Erro ao alterar o produto", e);
			e.printStackTrace();
		}

		return null;
	}

	@RequestMapping(value = "/deletar", method = RequestMethod.DELETE, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ArrayList<Produto> deleteProduto(@RequestBody Produto produto) {
		try {
			LOGGER.info("Service: service/produto/deletar. Method: DELETE (deleteProduto) - Iniciando a exclusão do produto");
			if (produto.getIdProduto() != 0) {
				LOGGER.info("Service: service/produto/deletar. Method: DELETE (deleteProduto) - Excluindo o produto " + produto.getIdProduto());
				produtoRepository.delete(produto);
				return produtoRepository.findAll();
			} else {
				LOGGER.info("Service: service/produto/deletar. Method: DELETE (deleteProduto) - Campos necessários não preenchidos");
			}
		} catch (Exception e) {
			LOGGER.error("Service: service/produto/deletar. Method: DELETE (deleteProduto) - Erro ao excluir o produto", e);
			e.printStackTrace();
		}

		return null;
	}
}