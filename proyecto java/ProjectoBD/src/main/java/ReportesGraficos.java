import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ReportesGraficos extends JFrame{
    private JPanel mainPanel;
    private JButton reporte2Button;
    private JButton reporte1Button;
    private JPanel ReUnoPanel;

    public ReportesGraficos(String title){
        super(title);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(mainPanel);
        this.pack();
        reporte1Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[][] datos= new String[100][2];
                Reportes reportes= new Reportes();
                String cadenaDatos=reportes.reportarAsesoresContratadosG();
                String[] filas=cadenaDatos.split("\n");
                for(int i=0;i<filas.length;i++){
                    datos[i]=(filas[i]).split(",");
                }
                System.out.println(datos[0][0]);
                System.out.println(datos[0][1]);
                DefaultCategoryDataset data= new DefaultCategoryDataset();
                for(int j=0;j<datos.length;j++){
                        data.setValue(Float.parseFloat(datos[j][1]),"sueldo",datos[j][0]);

                }
                JFreeChart grafico_barras= ChartFactory.createBarChart3D(
                  "Asesores de Gear",
                  "Asesores",
                        "Sueldo",
                        data,
                        PlotOrientation.VERTICAL,
                        true,
                        true,
                        false
                );

                ChartPanel panel= new ChartPanel(grafico_barras);
                panel.setMouseWheelEnabled(true);
                panel.setPreferredSize(new Dimension(400,200));
                ReUnoPanel.setLayout(new BorderLayout());
                ReUnoPanel.add(panel,BorderLayout.NORTH);
                pack();
                repaint();

            }
        });
    }

    public static void main(String[] args) {
        JFrame frame= new ReportesGraficos("Mis reportes");
        frame.setVisible(true);
    }

}
