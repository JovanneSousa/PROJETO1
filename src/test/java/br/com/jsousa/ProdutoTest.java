package br.com.jsousa;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import br.com.jsousa.dao.IGenericDAO;
import br.com.jsousa.domain.Produto;
import br.com.jsousa.dao.ProdutoDAO;

public class ProdutoTest {

	private IGenericDAO<Produto> produtoDAO;
	
	@Test
	public void CadastrarTest() throws Exception {
		produtoDAO = new ProdutoDAO();
		Produto produto = new Produto();
		produto.setCodigo("01");
		produto.setNome("Batata");
		
		Integer qtd = produtoDAO.cadastrar(produto);
		assertTrue(qtd == 1);
		
		Produto produtoBD = produtoDAO.consultar(produto.getCodigo());
		assertNotNull(produtoBD);
		assertNotNull(produtoBD.getId());
		assertEquals(produto.getCodigo(), produtoBD.getCodigo());
		assertEquals(produto.getNome(), produtoBD.getNome());
	
		Integer qtdDel = produtoDAO.excluir(produtoBD);
		assertNotNull(qtdDel);
	}
	
	@Test
	public void ConsultarTest() throws Exception {
		produtoDAO = new ProdutoDAO();
		Produto produto = new Produto();
		produto.setCodigo("02");
		produto.setNome("Manga");
		
		Integer qtd = produtoDAO.cadastrar(produto);
		assertTrue(qtd == 1);
		
		Produto produtoBD = produtoDAO.consultar(produto.getCodigo());
		assertNotNull(produtoBD);
		assertEquals(produto.getNome(), produtoBD.getNome());
		assertEquals(produto.getCodigo(), produtoBD.getCodigo());
	
		Integer countDel = produtoDAO.excluir(produtoBD);
		assertNotNull(countDel);
	}
	
	@Test
	public void excluirTest() throws Exception {
		produtoDAO = new ProdutoDAO();
		
		Produto produto = new Produto();
		produto.setCodigo("03");
		produto.setNome("Amora");
		Integer countCad = produtoDAO.cadastrar(produto);
		assertTrue(countCad == 1);
		
		Produto produtoBD = produtoDAO.consultar("03");
		assertNotNull(produtoBD);
		assertEquals(produto.getCodigo(), produtoBD.getCodigo());
		assertEquals(produto.getNome(), produtoBD.getNome());
		
		Integer countDel = produtoDAO.excluir(produtoBD);
		assertTrue(countDel == 1);
	}
	
	@Test
	public void buscarTodosTest() throws Exception {
		produtoDAO = new ProdutoDAO();
		
		Produto produto = new Produto();
		produto.setCodigo("04");
		produto.setNome("Uva");
		Integer countCad = produtoDAO.cadastrar(produto);
		assertTrue(countCad == 1);
		
		Produto produtos = new Produto();
		produtos.setCodigo("05");
		produtos.setNome("Maça");
		Integer countCad2 = produtoDAO.cadastrar(produtos);
		assertTrue(countCad2 == 1);
		
		List<Produto> list = produtoDAO.buscarTodos();
		assertNotNull(list);
		assertEquals(2, list.size());
		
		int countDel = 0;
		for (Produto pro : list) {
			produtoDAO.excluir(pro);
			countDel++;
		}
		assertEquals(list.size(), countDel);
		
		list = produtoDAO.buscarTodos();
		assertEquals(list.size(), 0);
		
	}
	
	@Test
	public void atualizarTest() throws Exception {
		produtoDAO = new ProdutoDAO();
		
		Produto produto = new Produto();
		produto.setCodigo("07");
		produto.setNome("Laranja");
		Integer countCad = produtoDAO.cadastrar(produto);
		assertTrue(countCad == 1);
		
		Produto produtoBD = produtoDAO.consultar("07");
		assertNotNull(produtoBD);
		assertEquals(produto.getCodigo(), produtoBD.getCodigo());
		assertEquals(produto.getNome(), produtoBD.getNome());
		
		produtoBD.setCodigo("08");
		produtoBD.setNome("Morango");
		Integer countUpdate = produtoDAO.atualizar(produtoBD);
		assertTrue(countUpdate == 1);
		
		Produto produtoBD1 = produtoDAO.consultar("07");
		assertNull(produtoBD1);
		
		Produto produtoBD2 = produtoDAO.consultar("08");
		assertNotNull(produtoBD2);
		assertEquals(produtoBD.getId(), produtoBD2.getId());
		assertEquals(produtoBD.getCodigo(), produtoBD2.getCodigo());
		assertEquals(produtoBD.getNome(), produtoBD2.getNome());
		
		List<Produto> list = produtoDAO.buscarTodos();
		for (Produto pro : list) {
			produtoDAO.excluir(pro);
		}
	}
}
