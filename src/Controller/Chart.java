/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 *
 * @author Admin
 */
public class Chart {

    public static void chartMonth(int year, DefaultTableModel d) {
        DefaultCategoryDataset line_chart_dataset = new DefaultCategoryDataset();
        for (int i = 1; i < 13; i++) {
            line_chart_dataset.addValue(Double.parseDouble(d.getValueAt(i - 1, 1).toString()), "doanh thu", i + "");
            line_chart_dataset.addValue(Double.parseDouble(d.getValueAt(i - 1, 2).toString()), "kinh phí", i + "");
        }
        JFreeChart lineChartObject = ChartFactory.createLineChart(
                "Doanh thu - kinh phí năm " + year, "Tháng",
                "VND",
                line_chart_dataset, PlotOrientation.VERTICAL,
                true, true, false);

        int width = 700;
        /* Width of the image */
        int height = 500;
        /* Height of the image */
        File lineChart = new File("Doanhthu-Kinhphi.jpeg");
        try {
            ChartUtilities.saveChartAsJPEG(lineChart, lineChartObject, width, height);
        } catch (IOException ex) {
            Logger.getLogger(Chart.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void BarChart(DefaultTableModel d) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (int i = 0; i < d.getRowCount(); i++) {
            dataset.addValue(Double.parseDouble(d.getValueAt(i, 1).toString()), "doanh thu", d.getValueAt(i, 0).toString());
//            dataset.addValue(Double.parseDouble(d.getValueAt(i - 1, 2).toString()), "kinh phí", i + "");
        }
        JFreeChart barChartObject = ChartFactory.createBarChart(
                "Doanh thu theo mỹ phẩm",
                "Mỹ phẩm",
                "VND",
                dataset,
                PlotOrientation.HORIZONTAL,
                true, true, false);

        int width = 700;
        /* Width of the image */
        int height = 500;
        /* Height of the image */
        File barChart = new File("Doanhthu-Kinhphi.jpeg");
        try {
            ChartUtilities.saveChartAsJPEG(barChart, barChartObject, width, height);
        } catch (IOException ex) {
            Logger.getLogger(Chart.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static CategoryDataset createDataset() {

        final DefaultCategoryDataset dataset
                = new DefaultCategoryDataset();

        dataset.addValue(12, "Doanhthu", "Dầu gội");
        dataset.addValue(45, "Doanhthu", "Dầu xả");
        dataset.addValue(34, "Doanhthu", "Kem trụ mụn");
        dataset.addValue(35, "Doanhthu", "Thuốc nhuộm");
        dataset.addValue(35, "Doanhthu", "Thuốc nhuộmdsad");
        dataset.addValue(35, "Doanhthu", "Thuốc nhuộmfasd");
        dataset.addValue(35, "Doanhthu", "Thuốc nhuộmssa");
        dataset.addValue(35, "Doanhthu", "Thuốc nhuộmgdf");
        dataset.addValue(35, "Doanhthu", "Thuốc nhuộmgd");
        dataset.addValue(35, "Doanhthu", "Thuốc nhuộmd");
        dataset.addValue(35, "Doanhthu", "Thuốc nhu");
        dataset.addValue(35, "Doanhthu", "Thuốc nhuộmdfsd");
        dataset.addValue(35, "Doanhthu", "Thuốc nhuộm");
        dataset.addValue(35, "Doanhthu", "Thuốc nhuộm");
        dataset.addValue(35, "Doanhthu", "Thuốc nhuộm");
        dataset.addValue(35, "Doanhthu", "Thuốc nhuộm");
        dataset.addValue(35, "Doanhthu", "Thuốc nhuộm");
        dataset.addValue(35, "Doanhthu", "Thuốc nhuộm");
        dataset.addValue(35, "Doanhthu", "Thuốc nhuộm");
        dataset.addValue(35, "Doanhthu", "Thuốc nhuộm");

        return dataset;
    }

}
