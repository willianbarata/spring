package com.willian.mobile.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.willian.mobile.domain.Categoria;
import com.willian.mobile.domain.Produto;
import com.willian.mobile.repositories.CategoriaRepository;
import com.willian.mobile.repositories.ProdutoRepository;
import com.willian.mobile.services.exception.ObjectNotFoundException;

@Service
public class ProdutoService {
	
	//instanciar 
	@Autowired
	private ProdutoRepository repo;
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	public Produto find(Integer id) {
		Optional<Produto> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
		"Objeto não encontrado! Id: " + id + ", Tipo: " + Produto.class.getName()));
		}
	
	//busca paginada
	public Page<Produto> search(String nome,  List<Integer> ids,Integer page, Integer linesPerPage, String orderby, String direction){
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderby);
		List<Categoria> categorias = categoriaRepository.findAllById(ids);
		return repo.search(nome, categorias, pageRequest);
		
	}
}
