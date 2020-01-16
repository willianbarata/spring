package com.willian.mobile.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.willian.mobile.domain.Categoria;
import com.willian.mobile.repositories.CategoriaRepository;

@Service
public class CategoriaService {
	
	//instanciar 
	@Autowired
	private CategoriaRepository repo;
	
	public Categoria buscar(Integer id) {
		Optional<Categoria> obj = repo.findById(id);
		return obj.orElse(null);
	}
}
