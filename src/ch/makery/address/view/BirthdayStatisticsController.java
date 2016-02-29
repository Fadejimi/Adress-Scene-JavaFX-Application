/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ch.makery.address.view;

/**
 *
 * @author Test
 */

import ch.makery.address.model.Person;
import java.text.DateFormatSymbols;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.XYChart;

public class BirthdayStatisticsController {
    
    @FXML
    private BarChart<String, Integer> barChart;
    
    @FXML
    private CategoryAxis xAxis;
    
    private ObservableList<String> monthNames = FXCollections.observableArrayList();
    
    @FXML
    private void initialize()
    {
        // Get an array with English month names
        String[] months = DateFormatSymbols.getInstance(Locale.ENGLISH).getMonths();
        // convert it to a list and add it to our observable list of months
        monthNames.addAll(Arrays.asList(months));
        
        // assign the month names as categories for the horizontal data
        xAxis.setCategories(monthNames);
    }
    
    /**
     * Sets the persons to show the statistics for
     */
    public void setPersonData(List<Person> persons) {
        int[] monthCounter = new int[12];
        for (Person p : persons) {
            int month = p.getBirthday().getMonthValue() - 1;
            monthCounter[month]++;
        }
        
        XYChart.Series<String, Integer> series = new XYChart.Series<>();
        
        // Create a xychart.data object for each month. Add it to the series
        for (int i = 0; i < monthCounter.length; i++) {
            series.getData().add(new XYChart.Data<>(monthNames.get(i), monthCounter[i]));
        }
        
        barChart.getData().add(series);
    }
}
