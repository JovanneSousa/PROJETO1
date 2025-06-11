package br.com.jsousa;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

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
}
