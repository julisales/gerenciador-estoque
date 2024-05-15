package aplicacao.telasBotoes;

import DAO.ProdutoDAO;
import aplicacao.Produto;
import aplicacao.Util;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.Objects;

import static aplicacao.GestaoProduto.*;
import static aplicacao.GestaoProduto.modeloTabela;

public class TelaAtualizarProduto {
    public static void atualizarProduto(int IndexLinha) {
        // Retorna IndexLinha selecionada
        Produto produtoSelecionado = produtos.get(IndexLinha);

        // Criação e definições do frame
        JFrame frameAtualizarProduto = new JFrame("Atualizar estoque do produto");
        frameAtualizarProduto.setSize(330, 170);
        frameAtualizarProduto.setIconImage(logo.getImage());
        frameAtualizarProduto.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frameAtualizarProduto.setLocationRelativeTo(null);
        frameAtualizarProduto.setResizable(false);
        
        // Criação do painel Principal com layout (BorderLayout)
        JPanel panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.setBorder(new EmptyBorder(10, 10, 10, 10)); // Espaçamento 
        
        // Criação do painel (secundário/filho) para atualização com layout (FlowLayout)
        JPanel panelAtualizarProduto = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
        
        // Criação do TextField e do ComboBox
        JTextField tfQuantidade = new JTextField();
        tfQuantidade.setPreferredSize(new Dimension(150, 30));
        JComboBox<String> cbOperacao;
        String[] opcoes = {"Selecione a operação", "Entrada", "Saída"};
        cbOperacao = new JComboBox<>(opcoes);
        cbOperacao.setPreferredSize(new Dimension(150, 30));
        
        // Adicionando ao painel de atualização
        panelAtualizarProduto.add(new JLabel("Quantidade: "));
        panelAtualizarProduto.add(tfQuantidade);
        panelAtualizarProduto.add(new JLabel("Operação:    "));
        panelAtualizarProduto.add(cbOperacao);
        
        // Criação do botão e seu evento
        JButton btnConfirmar = new JButton("Confirmar");
        btnConfirmar.setBackground(new Color(53, 14, 132));
        btnConfirmar.setForeground(Color.WHITE);
        btnConfirmar.addActionListener(e -> {
            String operacaoSelecionada = (String) cbOperacao.getSelectedItem();

            if (Objects.equals(operacaoSelecionada, "Selecione a operação")) {
                JOptionPane.showMessageDialog(null, "Selecione a operação!", "Operação não selecionada",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                int quantidade = Integer.parseInt(tfQuantidade.getText());

                if (quantidade < 0) {
                    JOptionPane.showMessageDialog(null, "A quantidade não pode ser negativa!", "Quantidade inválida",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (Objects.equals(operacaoSelecionada, "Entrada")) {
                    produtoSelecionado.setQuantidade(produtoSelecionado.getQuantidade() + quantidade);
                } else if (Objects.equals(operacaoSelecionada, "Saída")) {
                    if (quantidade > produtoSelecionado.getQuantidade()) {
                        JOptionPane.showMessageDialog(null, "Quantidade de saída maior que o estoque atual!",
                                "Quantidade inválida", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    produtoSelecionado.setQuantidade(produtoSelecionado.getQuantidade() - quantidade);
                }

                ProdutoDAO.atualizarProduto(produtoSelecionado);

                double valorTotal = Util.calcularValorTotal(produtoSelecionado.getQuantidade(),
                        produtoSelecionado.getPreco());
                String valorTotalFormatado = Util.formatarValores(valorTotal);
                String situacao = produtoSelecionado.getQuantidade() < 10 ? "REPOR" : "NÃO REPOR";

                modeloTabela.setValueAt(produtoSelecionado.getQuantidade(), IndexLinha, 2);
                modeloTabela.setValueAt(valorTotalFormatado, IndexLinha, 4);
                modeloTabela.setValueAt(situacao, IndexLinha, 5);
                frameAtualizarProduto.dispose();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Insira uma quantidade válida!", "Erro",
                        JOptionPane.ERROR_MESSAGE);
            }
        });
        
        // Adicionando ao painel principal o painel de atualização e o botão
        panelPrincipal.add(panelAtualizarProduto, BorderLayout.CENTER);
        panelPrincipal.add(btnConfirmar, BorderLayout.SOUTH);
        
        // Adicionando ao frame
        frameAtualizarProduto.add(panelPrincipal);
        
        // Tornando visível
        frameAtualizarProduto.setVisible(true);
    }
}
