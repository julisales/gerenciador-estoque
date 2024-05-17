package aplicacao;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

// Classe com métodos úteis para o desenvolvimento do programa
public class Util {

    // Calcula valor total em estoque
    public static double calcularValorTotal(int quantidade, double preco) {
        return quantidade * preco;
    }

    // Definições das fontes
    public static void definirFontePadrao() {
        Font fontePadrao = new Font("SansSerif", Font.PLAIN, 14);
        Font fonteNegrito = new Font("SansSerif", Font.BOLD, 14);
        Font fonteTabela = new Font("SansSerif", Font.PLAIN, 15);


        UIManager.put("Label.font", fonteNegrito);
        UIManager.put("TextField.font", fontePadrao);
        UIManager.put("TextArea.font", fontePadrao);
        UIManager.put("Button.font", fonteNegrito);
        UIManager.put("ComboBox.font", fontePadrao);
        UIManager.put("RadioButton.font", fontePadrao);
        UIManager.put("CheckBox.font", fontePadrao);
        UIManager.put("Table.font", fonteTabela);
        UIManager.put("OptionPane.messageFont", fontePadrao);
        UIManager.put("OptionPane.buttonFont", fontePadrao);

        UIManager.put("TableHeader.font", fonteNegrito);
    }

    // Converte valores para a formatação correta (R$)
    public static String formatarValores(double valor) {
        DecimalFormatSymbols simbolos = DecimalFormatSymbols.getInstance();
        simbolos.setDecimalSeparator(',');
        simbolos.setGroupingSeparator('.');

        DecimalFormat formato = new DecimalFormat("#,##0.00", simbolos);
        return formato.format(valor);
    }

    static ImageIcon loadImage(String resourcePath) {
        try {
            BufferedImage image = ImageIO.read(new File(resourcePath));
            if (image != null) {
                return new ImageIcon(image);
            } else {
                System.err.println("A imagem não pôde ser carregada: " + resourcePath);
            }
        } catch (IOException e) {
            System.err.println("Erro ao carregar imagem: " + e.getMessage());
        }

        return null; 
    }

}

