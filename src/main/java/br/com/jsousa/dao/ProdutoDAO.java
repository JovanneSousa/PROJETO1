package br.com.jsousa.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.jsousa.dao.generic.jdbc.ConnectionFactory;
import br.com.jsousa.domain.Produto;

public class ProdutoDAO implements IGenericDAO<Produto> {

	IGenericDAO<Produto> produtoDAO;
	
	@Override
	public Integer cadastrar(Produto produto) throws Exception {
		Connection connection = null;
		PreparedStatement stm = null;
		
		try {
			connection = ConnectionFactory.getConnection();
			String sql = getSqlInsert();
			stm = connection.prepareStatement(sql);
			adicionaParametrosInsert(stm, produto);
			return stm.executeUpdate();
			
		} catch (Exception e) {
			throw e;
		} finally {
			closeConnection(connection, stm, null);
		}
	}

	@Override
	public Produto consultar(String codigo) throws Exception {
		Connection connection = null;
		PreparedStatement stm = null;
		ResultSet rs = null;
		Produto produto = null;
		
		try {
			connection = ConnectionFactory.getConnection();
			String sql = getSqlSelect();
			stm = connection.prepareStatement(sql);
			adicionaParametrosConsulta(stm, codigo);
			rs = stm.executeQuery();
			
			if (rs.next()) {
				produto = new Produto();
				produto.setCodigo(rs.getString("CODIGO"));
				produto.setNome(rs.getString("NOME"));
				produto.setId(rs.getLong("ID"));
			}
			return produto;
		} catch (Exception e) {
			throw e;
		} finally {
			closeConnection(connection, stm, rs);
		}
		
	}

	@Override
	public Integer excluir(Produto produto) throws Exception {
		Connection connection = null;
		PreparedStatement stm = null;
		
		try {
			connection = ConnectionFactory.getConnection();
			String sql = getSqlDelete();
			stm = connection.prepareStatement(sql);
			adicionarParametrosDelete(stm, produto);
			return stm.executeUpdate();
		} catch (Exception e) {
			throw e;
		} finally {
			closeConnection(connection, stm, null);
		}
	}

	@Override
	public List<Produto> buscarTodos() throws Exception {
		
		Connection connection = null;
		PreparedStatement stm = null;
		ResultSet rs = null;
		List<Produto> list = new ArrayList<Produto>();
		
		try {
			connection = ConnectionFactory.getConnection();
			String sql = getSqlSelectAll();
			stm = connection.prepareStatement(sql);
			Produto produto = null;
			rs = stm.executeQuery();
			
			while (rs.next()) {
				produto = new Produto();
				produto.setNome(rs.getString("NOME"));
				produto.setCodigo(rs.getString("CODIGO"));
				produto.setId(rs.getLong("ID"));
				list.add(produto);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			closeConnection(connection, stm, rs);
		}
		return list;
	}

	@Override
	public Integer atualizar(Produto produto) throws Exception {
		Connection connection = null;
		PreparedStatement stm = null;
		
		try {
			connection = ConnectionFactory.getConnection();
			String sql = getSqlUpdate();
			stm = connection.prepareStatement(sql);
			adicionarParametrosUpdate(stm, produto);
			return stm.executeUpdate();
		} catch (Exception e) {
			throw e;
		} finally {
			closeConnection(connection, stm, null);
		}
	}

	private void adicionarParametrosUpdate(PreparedStatement stm, Produto produto) throws SQLException {
		stm.setString(1, produto.getNome());
		stm.setString(2, produto.getCodigo());
		stm.setLong(3, produto.getId());
	}

	private String getSqlUpdate() {
		StringBuilder sb = new StringBuilder();
		sb.append("UPDATE TB_PRODUTO ");
		sb.append("SET NOME = ?, CODIGO = ? ");
		sb.append("WHERE ID = ?");
		return sb.toString();
	}

	private void adicionarParametrosDelete(PreparedStatement stm, Produto produto) throws SQLException {
		stm.setString(1, produto.getCodigo());
	}

	private String getSqlDelete() {
		StringBuilder sb = new StringBuilder();
		sb.append("DELETE FROM TB_PRODUTO ");
		sb.append("WHERE CODIGO = ?");
		return sb.toString();
	}
	
	private String getSqlInsert() {
		StringBuilder sb = new StringBuilder();
		sb.append("INSERT INTO TB_PRODUTO (ID, CODIGO, NOME) ");
		sb.append("VALUES (nextval('sq_produto'),?,?)");
		return sb.toString();
	}
	
	private void adicionaParametrosInsert(PreparedStatement stm, Produto produto) throws SQLException {
		stm.setString(1, produto.getCodigo());
		stm.setString(2, produto.getNome());
	}
	
	private String getSqlSelectAll() {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT * FROM TB_PRODUTO");
		return sb.toString();
	}
	
	private void adicionaParametrosConsulta(PreparedStatement stm, String codigo) throws SQLException {
		stm.setString(1, codigo);
	}
	
	private String getSqlSelect() {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT * FROM TB_PRODUTO ");
		sb.append("WHERE CODIGO = ?");
		return sb.toString();
	}
	
	private void closeConnection(Connection connection, PreparedStatement stm, ResultSet rs) throws SQLException {
		if (connection != null && !connection.isClosed()) {
			connection.close();
		}
		if (stm != null & !stm.isClosed()) {
			stm.close();
		}
		if (rs != null && !rs.isClosed()) {
			rs.close();
		}
		
	}
}
