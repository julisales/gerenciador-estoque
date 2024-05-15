package aplicacao.telasBotoes;

import DAO.ProdutoDAO;
import aplicacao.Produto;
import aplicacao.Util;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static aplicacao.GestaoProduto.*;
import static aplicacao.GestaoProduto.modeloTabela;

public class TelaEditarProduto {
    public static void editarProduto(int IndexLinha) {
        // Retorna IndexLinha selecionada
        Produto produtoSelecionado = produtos.get(IndexLinha);

        // Criação e definições do frame
        JFrame frameEditarProduto = new JFrame("Editar produto");
        frameEditarProduto.setSize(300, 200);
        frameEditarProduto.setIconImage(logo.getImage());
        frameEditarProduto.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frameEditarProduto.setLocationRelativeTo(null);

        // Criação do painel Principal com layout (BorderLayout)
        JPanel panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Criação do painel (secundário/filho) para atualização com layout (GridLayout)
        JPanel panelEditarProdutos = new JPanel(new GridLayout(4, 2, 5, 5));

        // Criação dos TextFields e Labels e adicionando ao painel de edição
        JTextField tfId = new JTextField();
        tfId.setText(String.valueOf(produtoSelecionado.getId()));
        tfId.setEnabled(false);
        JTextField tfNome = new JTextField();
        tfNome.setText(produtoSelecionado.getNome());
        JTextField tfQuantidade = new JTextField();
        tfQuantidade.setText(String.valueOf(produtoSelecionado.getQuantidade()));
        JTextField tfPreco = new JTextField();
        tfPreco.setText(String.valueOf(produtoSelecionado.getPreco()));

        panelEditarProdutos.add(new JLabel("ID: "));
        panelEditarProdutos.add(tfId);
        panelEditarProdutos.add(new JLabel("Nome: "));
        panelEditarProdutos.add(tfNome);
        panelEditarProdutos.add(new JLabel("Preço Unitário: "));
        panelEditarProdutos.add(tfPreco);
        panelEditarProdutos.add(new JLabel("Quantidade: "));
        panelEditarProdutos.add(tfQuantidade);

        // Criação do botão e seu evento
        JButton btnConfirmarEdicao = new JButton("Confirmar");
        btnConfirmarEdicao.setBackground(new Color(53, 14, 132));
        btnConfirmarEdicao.setForeground(Color.WHITE);
        btnConfirmarEdicao.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String idStr = tfId.getText().trim();
                    String nome = tfNome.getText();
                    String precoStr = tfPreco.getText().trim();
                    String quantidadeStr = tfQuantidade.getText().trim();

                    if (!idStr.matches("\\d+")) {
                        JOptionPane.showMessageDialog(frameEditarProduto, "O ID deve conter apenas números.", "Erro", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    if (idStr.isEmpty() || nome.isEmpty() || precoStr.isEmpty() || quantidadeStr.isEmpty()) {
                        JOptionPane.showMessageDialog(frameEditarProduto, "Todos os campos devem ser preenchidos.", "Erro", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    int id = Integer.parseInt(idStr);
                    double preco = Double.parseDouble(precoStr);
                    int quantidade = Integer.parseInt(quantidadeStr);

                    if (preco < 0 || quantidade < 0) {
                        JOptionPane.showMessageDialog(frameEditarProduto, "O preço e a quantidade devem ser valores positivos.", "Erro", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    produtoSelecionado.setId(id);
                    produtoSelecionado.setNome(nome);
                    produtoSelecionado.setPreco(preco);
                    produtoSelecionado.setQuantidade(quantidade);

                    ProdutoDAO.atualizarProduto(produtoSelecionado);

                    double valorTotal = Util.calcularValorTotal(produtoSelecionado.getQuantidade(), produtoSelecionado.getPreco());
                    String nomeFormatado = produtoSelecionado.getNome().toUpperCase();
                    String precoFormatado = Util.formatarValores(produtoSelecionado.getPreco());
                    String valorTotalFormatado = Util.formatarValores(valorTotal);
                    String situacao = produtoSelecionado.getQuantidade() < 10 ? "REPOR" : "NÃO REPOR";

                    modeloTabela.setValueAt(id, IndexLinha, 0);
                    modeloTabela.setValueAt(nomeFormatado, IndexLinha, 1);
                    modeloTabela.setValueAt(quantidade, IndexLinha, 2);
                    modeloTabela.setValueAt(precoFormatado, IndexLinha, 3);
                    modeloTabela.setValueAt(valorTotalFormatado, IndexLinha, 4);
                    modeloTabela.setValueAt(situacao, IndexLinha, 5);

                    frameEditarProduto.dispose();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frameEditarProduto, "Certifique-se de preencher o preço e a quantidade corretamente.", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Adicionando o painel de edição e o botão ao painel principal
        panelPrincipal.add(panelEditarProdutos, BorderLayout.CENTER);
        panelPrincipal.add(btnConfirmarEdicao, BorderLayout.SOUTH);

        // Adicionando o painel principal ao frame
        frameEditarProduto.add(panelPrincipal);
        frameEditarProduto.setVisible(true);
    }
}
