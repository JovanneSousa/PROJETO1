package br.com.jsousa.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.jsousa.dao.generic.jdbc.ConnectionFactory;
import br.com.jsousa.domain.Cliente;
import br.com.jsousa.domain.Produto;

public class ClienteDAO implements IGenericDAO<Cliente>{

	@Override
	public Integer cadastrar(Cliente cliente) throws Exception{
		Connection connection = null;
		PreparedStatement stm = null;
		
		try {
			connection = ConnectionFactory.getConnection();
			String sql = getSqlInsert();
			stm = connection.prepareStatement(sql);
			adicionaParametrosInsert(stm, cliente);
			return stm.executeUpdate();
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			closeConnection(connection, stm, null);
		}
	}

	
	@Override
	public Cliente consultar(String codigo) throws Exception {
		Connection connection = null;
		PreparedStatement stm = null;
		ResultSet rs = null;
		Cliente cliente = null;
		try {
			connection = ConnectionFactory.getConnection();
			String sql = getSqlSelect();
			stm = connection.prepareStatement(sql);
			adicionarParametrosSelect(stm, codigo);
			rs = stm.executeQuery();
			
			if (rs.next()) {
				cliente = new Cliente();
				cliente.setId(rs.getLong("ID"));
				cliente.setCodigo(rs.getString("CODIGO"));
				cliente.setNome(rs.getString("NOME"));
			}
			return cliente;
		} catch (Exception e) {
			throw e;
		} finally {
			closeConnection(connection, stm, rs);
		}
		
	}

	@Override
	public Integer excluir(Cliente cliente) throws Exception {
		Connection connection = null;
		PreparedStatement stm = null;
		
		try {
			connection = ConnectionFactory.getConnection();
			String sql = getSqlDelete();
			stm = connection.prepareStatement(sql);
			adicionarParametrosDelete(stm, cliente);
			return stm.executeUpdate();
		} catch (Exception e) {
			throw e;
		} finally {
			closeConnection(connection, stm, null);
		}
	}

	@Override
	public List<Cliente> buscarTodos() throws Exception {
		Connection connection = null;
		PreparedStatement stm = null;
		ResultSet rs = null;
		List<Cliente> list = new ArrayList<Cliente>();
		Cliente cliente = null;
		
		try {
			connection = ConnectionFactory.getConnection();
			String sql = getSqlSelectAll();
			stm = connection.prepareStatement(sql);
			rs = stm.executeQuery();
			
			while (rs.next()) {
				cliente = new Cliente();
				cliente.setCodigo(rs.getString("CODIGO"));
				cliente.setNome(rs.getString("NOME"));
				cliente.setId(rs.getLong("ID"));
				list.add(cliente);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			closeConnection(connection, stm, rs);
		}
		return list;
	}

	@Override
	public Integer atualizar(Cliente cliente) throws Exception {
		Connection connection = null;
		PreparedStatement stm = null;
		
		try {
			connection = ConnectionFactory.getConnection();
			String sql = getSqlUpdate();
			stm = connection.prepareStatement(sql);
			adicionarParametrosUpdate(stm, cliente);
			return stm.executeUpdate();
		} catch (Exception e) {
			throw e;
		} finally {
			closeConnection(connection, stm, null);
		}
	}

	private String getSqlSelectAll() {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT * FROM TB_CLIENTE");
		return sb.toString();
	}
	
	private String getSqlUpdate() {
		StringBuilder sb = new StringBuilder();
		sb.append("UPDATE TB_CLIENTE ");
		sb.append("SET NOME = ?, CODIGO = ? ");
		sb.append("WHERE ID = ?");
		return sb.toString();
	}

	private void adicionarParametrosUpdate(PreparedStatement stm, Cliente cliente) throws SQLException {
		stm.setString(1, cliente.getNome());
		stm.setString(2, cliente.getCodigo());
		stm.setLong(3, cliente.getId());
	}
	
	private void adicionaParametrosInsert(PreparedStatement stm, Cliente cliente) throws SQLException {
		stm.setString(1, cliente.getCodigo());
		stm.setString(2, cliente.getNome());
	}

	private String getSqlInsert() {
		StringBuilder sb = new StringBuilder();
		sb.append("INSERT INTO TB_CLIENTE (ID, CODIGO, NOME) ");
		sb.append("VALUES (nextval('sq_cliente'),?,?)");
		return sb.toString();
	}
	
	private void adicionarParametrosSelect(PreparedStatement stm, String codigo) throws SQLException {
		stm.setString(1, codigo);
	}

	private String getSqlSelect() {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT * FROM TB_CLIENTE ");
		sb.append("WHERE CODIGO = ?");
		return sb.toString();
	}

	private void adicionarParametrosDelete(PreparedStatement stm, Cliente cliente) throws SQLException {
		stm.setString(1, cliente.getCodigo());
	}

	private String getSqlDelete() {
		StringBuilder sb = new StringBuilder();
		sb.append("DELETE FROM TB_CLIENTE ");
		sb.append("WHERE CODIGO = ?");
		return sb.toString();
	}

	private void closeConnection(Connection connection, PreparedStatement stm, ResultSet rs) {
		try {
			if (connection != null && !connection.isClosed()) {
				connection.close();
			}
			if (stm != null && stm.isClosed()) {
				stm.close();
			}
			if (rs != null && !rs.isClosed()) {
				rs.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
