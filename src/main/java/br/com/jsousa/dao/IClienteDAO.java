package br.com.jsousa.dao;

import java.util.List;

import br.com.jsousa.domain.Cliente;

public interface IClienteDAO {
	
	public Integer cadastrar(Cliente cliente) throws Exception;

	public Cliente consultar(String codigo) throws Exception;

	public Integer excluir(Cliente cliente) throws Exception;

	public List<Cliente> buscarTodos() throws Exception;
	
	public Integer atualizar(Cliente cliente) throws Exception;
}	
