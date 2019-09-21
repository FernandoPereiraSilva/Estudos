package com.example.demo.repository;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.example.demo.model.PedidoStatus;

public interface PedidoStatusRepository extends PagingAndSortingRepository<PedidoStatus, String> {
	@Query("SELECT PS FROM PedidoStatus PS ORDER BY ID_STATUS ASC")
	public ArrayList<PedidoStatus> findAll();
}