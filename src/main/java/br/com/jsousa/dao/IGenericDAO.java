package br.com.jsousa.dao;

import java.util.List;

import br.com.jsousa.domain.Cliente;

public interface IGenericDAO<T> {
	
	public Integer cadastrar(T entity) throws Exception;

	public T consultar(String codigo) throws Exception;

	public Integer excluir(T entity) throws Exception;

	public List<T> buscarTodos() throws Exception;
	
	public Integer atualizar(T entity) throws Exception;
}	
