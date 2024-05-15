package aplicacao.telasBotoes;

import DAO.ProdutoDAO;
import aplicacao.Produto;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static aplicacao.GestaoProduto.*;
import static aplicacao.GestaoProduto.addProduto;

public class TelaCadastroProduto {
    // Inicializa uma variável com valor 1

    public static void cadastrarProduto() {
        // Criação do frame e suas configurações
        JFrame frameCadastrarProduto = new JFrame("Cadastrar Novo Produto");
        frameCadastrarProduto.setSize(300, 200);
        frameCadastrarProduto.setLocationRelativeTo(null);
        frameCadastrarProduto.setIconImage(logo.getImage());
        frameCadastrarProduto.setResizable(false);
        frameCadastrarProduto.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Criação do painel Principal com layout (BorderLayout)
        JPanel panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Criação do painel (secundário/filho) para cadastro com layout (FlowLayout)
        JPanel panelCadastrarProduto = new JPanel(new GridLayout(4, 2, 5,5));

        // Criação dos TextFields e JLabels e adicionando ao painel
        int proximoId = ProdutoDAO.obterProximoId();
        JTextField tfId = new JTextField();
        tfId.setText(String.valueOf(proximoId));
        tfId.setEnabled(false);
        JTextField tfNome = new JTextField();
        JTextField tfQuantidade = new JTextField();
        JTextField tfPreco = new JTextField();

        panelCadastrarProduto.add(new JLabel("ID: "));
        panelCadastrarProduto.add(tfId);
        panelCadastrarProduto.add(new JLabel("Nome:"));
        panelCadastrarProduto.add(tfNome);
        panelCadastrarProduto.add(new JLabel("Quantidade:"));
        panelCadastrarProduto.add(tfQuantidade);
        panelCadastrarProduto.add(new JLabel("Preço:"));
        panelCadastrarProduto.add(tfPreco);

        // Criação do botão e seu evento
        JButton btnCadastrar = new JButton("Cadastrar");
        btnCadastrar.setBackground(new Color(53, 14, 132));
        btnCadastrar.setForeground(Color.WHITE);
        btnCadastrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                try {
                    String idStr = tfId.getText().trim();
                    String nome = tfNome.getText();
                    String precoStr = tfPreco.getText().trim();
                    String quantidadeStr = tfQuantidade.getText().trim();

                    if (!idStr.matches("\\d+")) {
                        JOptionPane.showMessageDialog(frameCadastrarProduto, "O ID deve conter apenas números.", "Erro",
                                JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    if (idStr.isEmpty() || nome.isEmpty() || precoStr.isEmpty() || quantidadeStr.isEmpty()) {
                        JOptionPane.showMessageDialog(frameCadastrarProduto, "Todos os campos devem ser preenchidos.", "Erro",
                                JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    int id = Integer.parseInt(idStr);
                    double preco = Double.parseDouble(precoStr);
                    int quantidade = Integer.parseInt(quantidadeStr);

                    if (preco < 0 || quantidade < 0) {
                        JOptionPane.showMessageDialog(frameCadastrarProduto, "O preço e a quantidade devem ser valores " +
                                        "positivos.",
                                "Erro", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    ProdutoDAO produtoDAO = new ProdutoDAO();
                    Produto novoProduto = new Produto(proximoId, nome, quantidade, preco);
                    ProdutoDAO.cadastrarProduto(novoProduto);

                    addProduto(novoProduto);

                    frameCadastrarProduto.dispose();

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frameCadastrarProduto, "Certifique-se de preencher o preço e a quantidade " +
                            "corretamente" +
                            ".", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }

        });

        // Adicionando no painel principal o painel de cadastro e o botão
        panelPrincipal.add(panelCadastrarProduto, BorderLayout.CENTER);
        panelPrincipal.add(btnCadastrar, BorderLayout.SOUTH);

        // Adicionando o painel principal ao frame
        frameCadastrarProduto.add(panelPrincipal);

        // Tornando frame visível
        frameCadastrarProduto.setVisible(true);
    }
}
