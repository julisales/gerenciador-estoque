package aplicacao.telasBotoes;

import DAO.ProdutoDAO;

import javax.swing.*;
import java.awt.*;

import static aplicacao.GestaoProduto.modeloTabela;
import static aplicacao.GestaoProduto.produtos;

public class TelaDeletarProduto {
    public static void deletarProduto(int IndexLinha) {
        // Pegando o id do produto
        int idProduto = produtos.get(IndexLinha).getId();

        // Definindo as configurações da fonte
        UIManager.put("OptionPane.messageFont", new Font("Poppins", Font.PLAIN, 14));

        // Cores de fonte e do fundo dos botões
        UIManager.put("Button.background", new Color(53, 14, 132));
        UIManager.put("Button.foreground", Color.WHITE);

        // Criação de fonte em negrito para os botões
        Font boldFont = new Font(UIManager.getFont("Button.font").getName(), Font.BOLD, UIManager.getFont("Button.font").getSize());

        // Adicionando a fonte aos botões
        UIManager.put("OptionPane.buttonFont", boldFont);

        // Criação de um painel para adicionar a pergunta
        JPanel panel = new JPanel();
        panel.add(new JLabel("Tem certeza que deseja deletar o produto?"));

        // Atribuindo a variável resposta a escolha do JOption
        int resposta = JOptionPane.showOptionDialog(null, panel, "Confirmação",
                JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null,
                new String[]{"Sim", "Não"}, null);

        // Condição de acordo com a escolha
        if (resposta == JOptionPane.YES_OPTION) {
            produtos.remove(IndexLinha);
            modeloTabela.removeRow(IndexLinha);
            ProdutoDAO.deletarProduto(idProduto);
        }
    }
}
