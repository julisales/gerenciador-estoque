package DAO;

import aplicacao.Produto;
import connection.ConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProdutoDAO {
	public static int obterProximoId() {
	    int proximoId = 1; // Define o próximo ID como 1 por padrão

	    Connection conn = null;
	    PreparedStatement stmt = null;
	    ResultSet rs = null;

	    try {
	        conn = DriverManager.getConnection("jdbc:mysql://stockpro.czsw8uie83iv.us-east-1.rds.amazonaws.com/stockpro", "admin", "admin123");

	        // Verifica se há registros na tabela
	        stmt = conn.prepareStatement("SELECT COUNT(*) AS total FROM produto");
	        rs = stmt.executeQuery();

	        if (rs.next()) {
	            int totalRegistros = rs.getInt("total");
	         
	            if (totalRegistros == 0) {
	                proximoId = 1;
	            } else {
	                stmt = conn.prepareStatement("SELECT MAX(id) + 1 AS proximo_id FROM produto");
	                rs = stmt.executeQuery();
	                
	                if (rs.next()) {
	                    proximoId = rs.getInt("proximo_id");
	                }
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        closeResources(conn, stmt, rs);
	    }

	    return proximoId;
	}


    public static void cadastrarProduto(Produto produto){
        String sql = "INSERT INTO produto (nome, quantidade, preco) VALUES (?, ?, ?)";

        PreparedStatement ps = null;

        try{
            ps = ConnectionFactory.getConnection().prepareStatement(sql);
            ps.setString(1, produto.getNome());
            ps.setInt(2, produto.getQuantidade());
            ps.setDouble(3, produto.getPreco());

            ps.execute();
            ps.close();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static void atualizarProduto(Produto produto) {
        String sql = "UPDATE produto SET nome = ?, quantidade = ?, preco = ? WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, produto.getNome());
            ps.setInt(2, produto.getQuantidade());
            ps.setDouble(3, produto.getPreco());
            ps.setInt(4, produto.getId());

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deletarProduto(int id) {
        String sql = "DELETE FROM produto WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<Produto> buscarTodosProdutos() {
        List<Produto> produtos = new ArrayList<>();
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            conn = ConnectionFactory.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM produto");

            while (rs.next()) {
                int id = rs.getInt("id");
                String nome = rs.getString("nome");
                int quantidade = rs.getInt("quantidade");
                double preco = rs.getDouble("preco");
                Produto produto = new Produto(id, nome, quantidade, preco);
                produtos.add(produto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Fechar recursos
            closeResources(conn, stmt, rs);
        }
        return produtos;
    }

    private static void closeResources(Connection conn, Statement stmt, ResultSet rs) {
        try {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            if (conn != null) conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
