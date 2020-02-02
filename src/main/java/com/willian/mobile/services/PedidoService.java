package com.willian.mobile.services;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.willian.mobile.domain.ItemPedido;
import com.willian.mobile.domain.PagamentoComBoleto;
import com.willian.mobile.domain.Pedido;
import com.willian.mobile.domain.enums.EstadoPagamento;
import com.willian.mobile.repositories.ItemPedidoRepository;
import com.willian.mobile.repositories.PagamentoRepository;
import com.willian.mobile.repositories.PedidoRepository;
import com.willian.mobile.repositories.ProdutoRepository;
import com.willian.mobile.services.exception.ObjectNotFoundException;

@Service
public class PedidoService {
	
	//instanciar 
	@Autowired
	private PedidoRepository repo;
	@Autowired
	private BoletoService boletoService;

	@Autowired
	private PagamentoRepository pagamentoRepository;

	@Autowired
	private ItemPedidoRepository itemPedidoRepository;

	@Autowired
	private ProdutoService produtoService;

	public Pedido find(Integer id) {
		Optional<Pedido> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Pedido.class.getName()));
	}

	public Pedido insert(Pedido obj) {
		obj.setId(null);
		obj.setInstante(new Date());
		obj.getPagamento().setEstado(EstadoPagamento.PENDENTE);
		obj.getPagamento().setPedido(obj);
		if (obj.getPagamento() instanceof PagamentoComBoleto) {
			PagamentoComBoleto pagto = (PagamentoComBoleto) obj.getPagamento();
			boletoService.preencherPagamentoComBoleto(pagto, obj.getInstante());
		}
		obj = repo.save(obj);
		pagamentoRepository.save(obj.getPagamento());
		for (ItemPedido ip : obj.getItems()) {
			ip.setDesconto(0.0);
			ip.setPreco(produtoService.find(ip.getProduto().getId()).getPreco());
			ip.setPedido(obj);
		}
		itemPedidoRepository.saveAll(obj.getItems());
		return obj;
	}
}	

