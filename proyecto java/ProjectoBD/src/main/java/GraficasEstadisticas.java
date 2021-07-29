import javax.swing.*;

public class GraficasEstadisticas extends JFrame{
    private JPanel panelPrincipal;
    private JTextField campoTexto;
    private JLabel ceciusLabel;
    private JButton botonGraficar;

    public GraficasEstadisticas(String title){
        super(title);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(panelPrincipal);
        this.pack();
    }

    public static void main(String[] args){
         JFrame frame  = new GraficasEstadisticas("our statistic graph/ nuestro gráfico estadístico");
         frame.setVisible(true);

    }

}
