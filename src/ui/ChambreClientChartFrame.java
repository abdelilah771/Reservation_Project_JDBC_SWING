package ui;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.awt.*;
import Service.ChambreService;
import Service.ClientService;

public class ChambreClientChartFrame extends JFrame {

    private final ChambreService chambreService;
    private final ClientService clientService;

    public ChambreClientChartFrame() {
        this.chambreService = new ChambreService();
        this.clientService = new ClientService();

        setTitle("Chambres and Clients Comparison");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create and add the chart panel
        JPanel chartPanel = createChambreClientChartPanel();
        add(chartPanel, BorderLayout.CENTER);
    }

    private JPanel createChambreClientChartPanel() {
        // Create the dataset
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        // Get the counts of Chambres and Clients
        int chambreCount = chambreService.findAll().size();
        int clientCount = clientService.findAll().size();

        // Add data to the dataset
        dataset.addValue(chambreCount, "Chambres", "Entities");
        dataset.addValue(clientCount, "Clients", "Entities");

        // Create the bar chart
        JFreeChart chart = ChartFactory.createBarChart(
                "Chambres and Clients Count",  // Chart title
                "Entity Type",                // X-axis label
                "Count",                      // Y-axis label
                dataset,                      // Dataset
                PlotOrientation.VERTICAL,     // Orientation
                true,                         // Show legend
                true,                         // Use tooltips
                false                         // URLs
        );

        // Wrap the chart in a panel
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(800, 500));
        return chartPanel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ChambreClientChartFrame frame = new ChambreClientChartFrame();
            frame.setVisible(true);
        });
    }
}
