import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ReportesGraficos extends JFrame{
    private JPanel mainPanel;
    private JButton reporte2Button;
    private JButton reporte1Button;
    private JPanel ReUnoPanel;
    private JButton reporte3Button;

    public ReportesGraficos(String title){
        super(title);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(mainPanel);
        this.pack();
        reporte1Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Reportes reportes= new Reportes();
                ArrayList<String[]> cadenaDatos=reportes.reportarAsesoresContratadosG();
                DefaultCategoryDataset data= new DefaultCategoryDataset();
                for(int i=0;i<cadenaDatos.size();i++){
                    String[] cosa = cadenaDatos.get(i);
                    data.setValue(Float.parseFloat(cosa[1]),"sueldo" , cosa[0]);
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

}
