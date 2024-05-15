package aplicacao;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;

// Classe com as configurações de layout e comportamento da tabela dos produtos
public class TabelaConfiguracao {

    // Criação do modelo da tabela e adicionando colunas
    public static DefaultTableModel criarModeloTabela() {
        DefaultTableModel modeloTabela = new DefaultTableModel();
        modeloTabela.addColumn("ID");
        modeloTabela.addColumn("Nome");
        modeloTabela.addColumn("Quantidade");
        modeloTabela.addColumn("Preço(R$)");
        modeloTabela.addColumn("Valor total(R$)");
        modeloTabela.addColumn("Situação");
        return modeloTabela;
    }

    // Configurações da tabela
    public static JTable configurarTabela(DefaultTableModel modeloTabela, DefaultTableCellRenderer renderer) {
        // Seleciona a tabela
        JTable tabelaProduto = new JTable(modeloTabela) {
            // Define que a tabela não terá as células editáveis
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }

            // Determina se o componente deve ajustar sua largura para coincidir com a largura do contêiner visível
            @Override
            public boolean getScrollableTracksViewportWidth() {
                return getPreferredSize().width < getParent().getWidth();
            }

            //Personaliza a renderização das células da tabela, configurando tooltips com base nos valores das células
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component component = super.prepareRenderer(renderer, row, column);
                if (component instanceof JComponent) {
                    JComponent jComponent = (JComponent) component;
                    Object value = getValueAt(row, column);
                    if (value != null) {
                        jComponent.setToolTipText(value.toString());
                    }
                }
                return component;
            }
        };

        // Configuração dos tooltips (tempo)
        ToolTipManager.sharedInstance().setInitialDelay(500);
        ToolTipManager.sharedInstance().setDismissDelay(10000);

        // Define que não será possível reordenar as colunas da tabela
        tabelaProduto.getTableHeader().setReorderingAllowed(false);

        // Percorre as colunas da tabela
        for (int i = 0; i < tabelaProduto.getColumnCount(); i++) {
            // Define que as colunas não serão redimensionáveis
            tabelaProduto.getColumnModel().getColumn(i).setResizable(false);
            if (i != 1) { // Alinha todas as colunas exceto a segunda ao centro
                tabelaProduto.getColumnModel().getColumn(i).setCellRenderer(new DefaultTableCellRenderer() {
                    @Override
                    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                        Component cellComponent = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                        setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
                        return cellComponent;
                    }
                });
                // Configurações da coluna 2 (nome do produto)
            } else {
                tabelaProduto.getColumnModel().getColumn(i).setCellRenderer(new DefaultTableCellRenderer() {
                    @Override
                    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                        Component cellComponent = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                        // Alinha a esquerda
                        setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
                        // Cor da fonte
                        setForeground(new Color(53, 14, 132));
                        // Espaçamento interno a esquerda
                        setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 6));
                        return cellComponent;
                    }
                });
            }
        }

        //Configura um renderizador de célula personalizado para a coluna 5 (situação)
        tabelaProduto.getColumnModel().getColumn(5).setCellRenderer(renderer);

        // Cor de fundo da tabela (branco)
        tabelaProduto.setBackground(Color.white);

        // Personalização do cabeçalho da tabela
        JTableHeader cabecalho = tabelaProduto.getTableHeader();
        cabecalho.setBackground(new Color(53, 14, 132));
        cabecalho.setForeground(Color.WHITE);

        // Cor ao selecionar uma linha da tabela
        tabelaProduto.setSelectionBackground(new Color(53, 14, 132, 30));

        // Altura das linhas da tabela
        tabelaProduto.setRowHeight(30);

        return tabelaProduto;
    }

    // Cria e retorna um renderizador de célula personalizado para a tabela
    public static DefaultTableCellRenderer criarRenderer() {
        return new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component cellComponent = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                String situacao = (String) table.getValueAt(row, 5);

                if (situacao != null) {
                    if (situacao.equals("REPOR")) {
                        setForeground(Color.red);
                        setBackground(new Color(200, 0, 0, 32));
                    } else if (situacao.equals("NÃO REPOR")) {
                        setForeground(new Color(20, 100, 20));
                        setBackground(new Color(115, 255, 115, 52));
                    }
                }

                setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

                return cellComponent;
            }
        };
    }
}
