package aplicacao;

import DAO.ProdutoDAO;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import static aplicacao.TabelaConfiguracao.*;
import static aplicacao.telasBotoes.TelaEditarProduto.editarProduto;
import static aplicacao.telasBotoes.TelaAtualizarProduto.atualizarProduto;
import static aplicacao.telasBotoes.TelaDeletarProduto.deletarProduto;
import static aplicacao.telasBotoes.TelaCadastroProduto.cadastrarProduto;

public class GestaoProduto extends JFrame {

    public static List<Produto> produtos;
    JTable tabelaProduto;
    public static DefaultTableModel modeloTabela;
    private JButton btnEditar, btnDeletar, btnAtualizar;
    private JButton btnCadastrarProduto;
    JTextField campoPesquisa;
    JButton btnPesquisar;

    public static ImageIcon logo = new ImageIcon(Objects.requireNonNull(GestaoProduto.class.getClassLoader().getResource("assets/caixa-de-entrega.png")));

    public GestaoProduto() {
        // Definição configurações da fonte
        Util.definirFontePadrao();

        // Criação de lista dos produtos
        produtos = new ArrayList<>();

        // Criação do painel principal com layout(BorderLayout)
        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new BorderLayout());

        // Criação e configuração da tabela dos produtos
        modeloTabela = criarModeloTabela();
        DefaultTableCellRenderer renderer = criarRenderer();
        tabelaProduto = configurarTabela(modeloTabela, renderer);

        // Criação de uma rolagem na tabela e adicionando ao painel principal
        JScrollPane scroll = new JScrollPane(tabelaProduto);
        panelPrincipal.add(scroll, BorderLayout.CENTER);

        // Criação e configuração da parte superior(norte)
        JPanel northPanel = new JPanel();
        northPanel.setBackground(new Color(53, 14, 132));
        northPanel.setPreferredSize(new Dimension(200, 60));
        northPanel.setLayout(new BoxLayout(northPanel, BoxLayout.Y_AXIS));

        JLabel labelStock = new JLabel("STOCKPRO");
        labelStock.setAlignmentX(Component.CENTER_ALIGNMENT);
        labelStock.setFont(new Font("SansSerif", Font.BOLD, 20));
        labelStock.setForeground(Color.WHITE);

        northPanel.add(Box.createVerticalGlue());
        northPanel.add(labelStock);
        northPanel.add(Box.createVerticalGlue());

        // Adicionando painel norte ao painel principal
        panelPrincipal.add(northPanel, BorderLayout.NORTH);

        // Criação dos botões e atribuição de suas funções
        btnCadastrarProduto = criarBotao("Cadastrar produto", new Color(7, 161, 89));
        btnCadastrarProduto.addActionListener(e -> acaoCadastrarProduto());

        btnEditar = criarBotao("Editar", new Color(0, 119, 182));
        btnEditar.addActionListener(e -> acaoEditar());

        btnAtualizar = criarBotao("Atualizar", new Color(236, 194, 22));
        btnAtualizar.addActionListener(e -> acaoAtualizar());

        btnDeletar = criarBotao("Deletar", new Color(251, 101, 77));
        btnDeletar.addActionListener(e -> acaoDeletar());

        // Criação do painel de pesquisa
        JPanel panelPesquisa = new JPanel();
        panelPesquisa.setBackground(new Color(53, 14, 132));
        panelPesquisa.setBorder(BorderFactory.createEmptyBorder(0, 200, 0, 0));


        // Criação e adição ao painel de pesquisa
        campoPesquisa = new JTextField(20);
        campoPesquisa.setPreferredSize(new Dimension(30, 25));
        btnPesquisar = new JButton(Util.loadImage("src/assets/search.png"));

        // Configuração dos ouvintes do campo
        configuracaoCampoPesquisa();

        // Configuração do botão de pesquisa
        btnPesquisar.setPreferredSize(new Dimension(70, 25));
        btnPesquisar.setBackground(new Color(53, 14, 132));
        btnPesquisar.setForeground(Color.WHITE);
        Border bordaBranca = BorderFactory.createLineBorder(Color.WHITE, 2);
        btnPesquisar.setBorder(bordaBranca);
        btnPesquisar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (campoPesquisa.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Digite um ID ou nome para pesquisar.", "Pesquisa", JOptionPane.WARNING_MESSAGE);
                } else {
                    pesquisarProduto();
                    if (tabelaProduto.getRowCount() == 0) {
                        JOptionPane.showMessageDialog(null, "Nenhum produto encontrado.", "Pesquisa", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            }
        });

        // Adicionando campo e botão ao painel de pesquisa
        panelPesquisa.add(campoPesquisa);
        panelPesquisa.add(btnPesquisar);

        // Criação do painel dos botões, configuração de layout e adição de componentes
        JPanel panelBtn = new JPanel();
        panelBtn.setBackground(new Color(53, 14, 132));
        panelBtn.add(btnCadastrarProduto);
        panelBtn.add(btnEditar);
        panelBtn.add(btnAtualizar);
        panelBtn.add(btnDeletar);
        panelBtn.add(panelPesquisa);

        // Adicionando painel de botões ao sul do painel principal
        panelPrincipal.add(panelBtn, BorderLayout.SOUTH);

        add(panelPrincipal);
    }

    // Configuração pré-definida dos botões
    private JButton criarBotao(String texto, Color cor) {
        JButton button = new JButton(texto);
        button.setBackground(cor);
        button.setForeground(Color.WHITE);
        return button;
    }

    // Ações dos botões
    private void acaoEditar() {
        int selecionado = tabelaProduto.getSelectedRow();
        if (selecionado != -1) {
            editarProduto(selecionado);
        } else {
            alertaSelecionarProduto();
        }
    }

    private void acaoDeletar() {
        int selecionado = tabelaProduto.getSelectedRow();
        if (selecionado != -1) {
            deletarProduto(selecionado);
        } else {
            alertaSelecionarProduto();
        }
    }

    private void acaoAtualizar() {
        int selecionado = tabelaProduto.getSelectedRow();
        if (selecionado != -1) {
            atualizarProduto(selecionado);
        } else {
            alertaSelecionarProduto();
        }
    }

    private void acaoCadastrarProduto() {
        cadastrarProduto();
    }

    // Método para adicionar produto
    public static void addProduto(Produto produto) {
        produtos.add(produto);

        double valorTotal = Util.calcularValorTotal(produto.getQuantidade(), produto.getPreco());
        String nomeFormatado = produto.getNome().toUpperCase();
        String precoFormatado = Util.formatarValores(produto.getPreco());
        String valorTotalFormatado = Util.formatarValores(valorTotal);
        String situacao = produto.getQuantidade() < 10 ? "REPOR" : "NÃO REPOR";

        modeloTabela.addRow(new Object[]{
                produto.getId(),
                nomeFormatado,
                produto.getQuantidade(),
                precoFormatado,
                valorTotalFormatado,
                situacao});
    }

    // Métodos para configuração da pesquisa
    public void pesquisarProduto() {
        String textoPesquisa = campoPesquisa.getText().trim();
        DefaultRowSorter<TableModel, Integer> sorter = new TableRowSorter<>(modeloTabela);
        tabelaProduto.setRowSorter(sorter);
        if (!textoPesquisa.isEmpty()) {
            try {
                // Verifica se o texto de pesquisa é um número (ID)
                if (textoPesquisa.matches("\\d+")) {
                    RowFilter<TableModel, Integer> filterById = RowFilter.regexFilter("(?iu)" + Pattern.quote(textoPesquisa), 0); // 0 indica a coluna do ID na tabela
                    sorter.setRowFilter(filterById);
                } else {
                    RowFilter<TableModel, Integer> filterByName = RowFilter.regexFilter("(?iu)" + Pattern.quote(textoPesquisa));
                    sorter.setRowFilter(filterByName);
                }
            } catch (PatternSyntaxException e) {
                JOptionPane.showMessageDialog(null, "Erro na expressão regular de pesquisa.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            sorter.setRowFilter(null); // Limpar o filtro ao pesquisar vazio
        }
    }

    private void configuracaoCampoPesquisa() {
        campoPesquisa.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                pesquisarProduto();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                pesquisarProduto();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                pesquisarProduto();
            }
        });
    }

    // Método com o alerta para selecionar produto
    private void alertaSelecionarProduto() {
        JOptionPane.showMessageDialog(null, "Por favor, selecione algum produto.", "Nenhum produto selecionado", JOptionPane.WARNING_MESSAGE);
    }

}
