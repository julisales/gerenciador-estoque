package aplicacao;

import DAO.ProdutoDAO;

import javax.swing.JFrame;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class App {
	public static void main(String[] args) {
		// Cria um instância da classe GestaoProduto
        GestaoProduto gp = new GestaoProduto();
        List<Produto> listaProdutos = ProdutoDAO.buscarTodosProdutos();

        // Limpar o modelo da tabela
        DefaultTableModel modeloTabela = (DefaultTableModel) gp.tabelaProduto.getModel();
        modeloTabela.setRowCount(0);

        // Adicionar os produtos ao modelo da tabela
        for (Produto produto : listaProdutos) {
            GestaoProduto.addProduto(produto);
        }

        gp.setTitle("STOCKPRO - Gerenciador de Estoque"); // Define um título
        gp.setSize(1040, 700); // Define a altura e largura
        gp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Para a execução do programa ao fechar a janela
        gp.setResizable(false); // Impede o redimensionamento da janela
        gp.setIconImage(GestaoProduto.logo.getImage()); // Define o ícone
        gp.setLocationRelativeTo(null); // Centraliza janela


        // Tornando visível
        gp.setVisible(true);
    }
}
