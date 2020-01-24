package com.willian.mobile.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.willian.mobile.domain.Pedido;
import com.willian.mobile.repositories.PedidoRepository;
import com.willian.mobile.services.exception.ObjectNotFoundException;

@Service
public class PedidoService {
	
	//instanciar 
	@Autowired
	private PedidoRepository repo;
	
	public Pedido find(Integer id) {
		Optional<Pedido> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
		"Objeto não encontrado! Id: " + id + ", Tipo: " + Pedido.class.getName()));
		}
}
