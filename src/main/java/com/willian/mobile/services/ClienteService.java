package com.willian.mobile.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.willian.mobile.domain.Cliente;
import com.willian.mobile.repositories.ClienteRepository;
import com.willian.mobile.services.exception.ObjectNotFoundException;

@Service
public class ClienteService {
	
	//instanciar 
	@Autowired
	private ClienteRepository repo;
	
	public Cliente find(Integer id) {
		Optional<Cliente> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
		"Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));
		}
}
