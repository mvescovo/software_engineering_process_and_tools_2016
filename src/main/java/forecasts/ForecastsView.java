package forecasts;

import application.Main;
import data.Forecast;
import data.Observation;
import data.Station;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.labels.StandardXYItemLabelGenerator;
import org.jfree.chart.labels.XYItemLabelGenerator;
import org.jfree.chart.plot.IntervalMarker;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.general.SeriesException;
import org.jfree.data.time.Hour;
import org.jfree.data.time.Minute;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.ui.Layer;
import org.jfree.ui.RectangleAnchor;
import org.jfree.ui.RectangleInsets;
import org.jfree.ui.TextAnchor;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Paint;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

/**
 * User interface for the display of weather forecast data for the selected Station.
 *
 * @author michael
 * @author kendall
 */
public class ForecastsView implements ForecastsContract.View {

    private static final Logger logger = LogManager.getLogger(forecasts.ForecastsView.class);
    private ForecastsContract.UserActionsListener mActionsListener;
    private JProgressBar mJProgressBar;
    private Station mStation;
    private JPanel mFheadPanel;
    private JPanel mTablePanel;
    private JScrollPane mFTableScrollPane;

    /**
     * Constructor.
     *
     * Instantiates the view and adds a progress bar, header panel (for the
     * title and latest weather data), and table panel and a chart of
     * temperature data.
     */
    public ForecastsView() {
        // Add a progress bar
        mJProgressBar = new JProgressBar();
        mJProgressBar.setIndeterminate(true);
        mJProgressBar.setVisible(false);

        // TODO: 18/05/16 Kendall to update...
        // Hi Kendall. Don't know how you want to do this. There could either be a shared progress bar
        // that each view can access, or each have their own local progress bar within their
        // own pane to show exactly which parts are loading.

        // This is what you did for the observations view:

        GridBagConstraints cons = new GridBagConstraints();
        cons.gridx = 1;
        cons.gridy = 0;
        cons.weightx = 0.5;
        cons.weighty = 0;
        cons.fill = GridBagConstraints.HORIZONTAL;
        cons.anchor = GridBagConstraints.CENTER;
        Main.MainWindow.getInstance().getObservationsPanel().add(mJProgressBar, cons);
        
        
        // Add table panel
        mTablePanel = new JPanel();
        mTablePanel.setLayout(new GridBagLayout());
        mTablePanel.setBackground(Main.getColorcontrast1());
        GridBagConstraints tableCons = new GridBagConstraints();
        tableCons.gridx = 0;
        tableCons.gridy = 0;
        tableCons.weighty = 0;
        tableCons.weightx = 0;
        tableCons.insets = new Insets(10,10,0,10);
        tableCons.fill = GridBagConstraints.HORIZONTAL;
        tableCons.anchor = GridBagConstraints.NORTHWEST;
        JLabel tableLabel = new JLabel("Weather forecasts.");
        mTablePanel.add(tableLabel, tableCons);
        cons.gridx = 1;
        cons.gridwidth = 1;
        cons.gridy = 1;
        cons.weightx = 0.5;
        cons.weighty = 0.5;
        cons.insets = new Insets(0,10,10,10);
        cons.fill = GridBagConstraints.BOTH;
        cons.anchor = GridBagConstraints.CENTER;
        Main.MainWindow.getInstance().getObservationsPanel().add(mTablePanel, cons);
        mFTableScrollPane = new JScrollPane();
        tableCons.gridy = 1;
        tableCons.weighty = 1;
        tableCons.weightx = 1;
        tableCons.insets = new Insets(0,10,10,10);
        tableCons.fill = GridBagConstraints.BOTH;
        mTablePanel.add(mFTableScrollPane, tableCons);
        


        
    }

    /**
     * Displays and hides the progress bar used while waiting for data
     *
     * @param active true displays the progress bar and false hides it.
     */
    @Override
    public void setProgressBar(boolean active) {
        if (active) {
            /*Main.MainWindow.getInstance().getStationName().setVisible(false);*/
            mJProgressBar.setVisible(true);
        } else {
            mJProgressBar.setVisible(false);
            /*Main.MainWindow.getInstance().getStationName().setVisible(false);*/
        }
        Main.MainWindow.getInstance().getObservationsPanel().repaint();
    }

    // TODO: 18/05/16 Kendall to update...
    // This is just to show it's working.
    @Override
    public void showForecasts(List<Forecast> forecasts) {
        for (Forecast forecast :
                forecasts) {
            logger.info("Forecast time: " + forecast.getTime());
            logger.info("Forecast temp: " + forecast.getTemp());
        }
        
        mFheadPanel = new JPanel();
        
        GridBagConstraints cons = new GridBagConstraints();
        cons.gridx = 1;
        cons.gridy = 0;
        cons.weightx = 0;
        cons.fill = GridBagConstraints.BOTH;
        cons.anchor = GridBagConstraints.NORTHWEST;
        cons.insets = new Insets(10, 10, 0, 10);
        Main.MainWindow.getInstance().getObservationsPanel().add(mFheadPanel, cons);
        Main.MainWindow.getInstance().getObservationsPanel().repaint();
 
    }

    /**
     * Set the presenter for the view.
     *
     * @param presenter the presenter to use.
     */
    @Override
    public void setActionListener(ForecastsContract.UserActionsListener presenter) {
        mActionsListener = presenter;
    }

    /**
     * When the view has finished initialising load the forecasts from the presenter.
     *
     * @param station the selected weather station
     */
    @Override
    public void onReady(Station station) {
        mStation = station;
        // Test to change the forecast weather source
        mActionsListener.setForecastSite("openweathermap");
        mActionsListener.loadForecasts(mStation, false);
    }

    @Override
    public void setForecastSite(String forecastSite) {
        // TODO: 18/05/16 Kendall to update...
        // Use this to set to either "openweathermap" or "forecastio"
        // I haven't created static variables to hold these values properly. For now I just wanted to get it working.
        // As long as you use those exact values it should be ok. We may need to come up with a better system though.
        // Haven't had time to think about it.
    }
    

	@Override
    public void showLatestForecast(Forecast forecast) {
		 mFheadPanel.setLayout(new GridBagLayout());
		 mFheadPanel.setBackground(Main.getColorcontrast1());
	        GridBagConstraints headCons = new GridBagConstraints();
	        headCons.gridx = 0;
	        headCons.gridy = 0;
	        headCons.weighty = 0;
	        headCons.weightx = 0.5;
	        headCons.insets = new Insets(10,10,0,10);
	        headCons.fill = GridBagConstraints.HORIZONTAL;
	        headCons.anchor = GridBagConstraints.WEST;
	        JLabel title = new JLabel();

	        title.setFont(Main.getFonttitle());
	        mFheadPanel.add(title, headCons);
	        JLabel lblLatest = new JLabel();
	        lblLatest.setText("Forecasts:");
	        lblLatest.setFont(Main.getFontnormalbold());
	        headCons.gridy = 1;
	        headCons.weightx = 1;
	        headCons.gridwidth = 1;
	        headCons.insets = new Insets(0,10,0,10);
	        mFheadPanel.add(lblLatest, headCons);
	        JLabel lblLatestDate = new JLabel();
	        SimpleDateFormat standardDateFormat = new SimpleDateFormat("yyyyMMddhhmmss");
	        Date thisDate = new Date(Long.parseLong(forecast.getTime()) * 1000);


	        Calendar c = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
	        c.setTime(thisDate);
	        SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm a EE dd/MM/yy");
	        lblLatestDate.setText(dateFormat.format(c.getTime()));
	        lblLatestDate.setFont(Main.getFontnormal());
	        headCons.gridy = 2;
	        headCons.weightx = 0;
	        mFheadPanel.add(lblLatestDate, headCons);
	        JLabel lblTemp = new JLabel();
	        lblTemp.setText(forecast.getTemp() + Main.getSymboldegree() + " C");
	        lblTemp.setFont(Main.getFonttitle());
	        headCons.gridx = 1;
	        headCons.gridy = 2;
	        headCons.weightx = 0;
	        headCons.weighty = 1;
	        headCons.gridheight = 2;
	        headCons.anchor = GridBagConstraints.EAST;
	        mFheadPanel.add(lblTemp, headCons);
	        JLabel lblSummary = new JLabel();
	        lblSummary.setText("<html><body>" + forecast.getDescription() + ", " + forecast.getHumidity() + "% humidity. Pressure - " + forecast.getPressure() + " "
	                + "Wind - " + forecast.getWindSpeed() + "kph.<body><html>");
	        lblSummary.setFont(Main.getFontnormal());
	        headCons.gridy = 3;
	        headCons.weightx = 1;
	        headCons.gridx = 0;
	        headCons.gridheight = 1;
	        headCons.anchor = GridBagConstraints.WEST;
	        headCons.insets = new Insets(0,10,30,10);
	        mFheadPanel.add(lblSummary, headCons);
		
	}

	@Override
	public void showForecastTable(List<Forecast> forecasts) {
		  // Table settings
		
        String[] columnNames = { "Date Time", "Temp C" + Main.getSymboldegree(), 
                "Min temp C"  + Main.getSymboldegree(), "Max temp C"  + Main.getSymboldegree(), "Humidity %", "Pressure", "Wind spd kmh", };

        JTable table = new JTable();
        DefaultTableModel dataModel = new DefaultTableModel(columnNames, 0);

        for (Forecast frc : forecasts) {
            dataModel.addRow(frc.getForecastVector());
        }

        table.setModel(dataModel);
        table.setFont(Main.getFontsmall());
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table.getColumn("Date Time").setPreferredWidth(160);
        mFTableScrollPane.setViewportView(table);
		
	}
	
	@Override
	public void showForecastChart(List<Forecast> forecasts) {
        JFreeChart chart = Main.MainWindow.getInstance().getChart();
        XYPlot plot = (XYPlot) chart.getPlot();
        TimeSeriesCollection dataset = (TimeSeriesCollection) plot.getDataset();
        TimeSeries seriesTemp = dataset.getSeries(dataset.indexOf("Temp"));
        TimeSeries seriesMin = dataset.getSeries(dataset.indexOf("Min"));
        TimeSeries seriesMax = dataset.getSeries(dataset.indexOf("Max"));
        TimeSeries series9am = dataset.getSeries(dataset.indexOf("9am"));
        TimeSeries series3pm = dataset.getSeries(dataset.indexOf("3pm"));
        double temp = Double.NaN;
        double minTemp = Double.NaN;
        double maxTemp = Double.NaN;
        List<String> dates = new ArrayList<String>();
        SimpleDateFormat standardDateFormat = new SimpleDateFormat("yyyyMMddhhmmss");
        SimpleDateFormat day_format = new SimpleDateFormat("dd/M/yyyy");
        String previousDay = null;
        Date minDate = null;
        Date maxDate = null;
        for (Forecast forecast : forecasts) {
            if(dates.contains(forecast.getTime())) {
                continue;
            }
            dates.add(forecast.getTime());
            // format the temperature
            try {
                temp = Double.parseDouble(forecast.getTemp());
            } catch (NumberFormatException e) {
                temp = 0.0;
            }

            // format the date
            Date myDate = null;
            Date myHour = null;
            

            myDate = new Date(Long.parseLong(forecast.getTime()) * 1000);
        	String day = day_format.format(myDate); 

            // Check for 9am and 3pm observations
            Calendar myCal = Calendar.getInstance();
            myCal.setTime(myDate);

            
            int hour = myCal.get(Calendar.HOUR_OF_DAY);
            if(hour == 9 ) {
                series9am.addOrUpdate(new Hour(myDate), temp);
            } else if (hour == 15) {
                series3pm.addOrUpdate(new Hour(myDate), temp);
            }
            try {
                seriesTemp.addOrUpdate(new Minute(myDate), temp);
            } catch (SeriesException e) {
                e.printStackTrace();
            }
            if (previousDay == null) {
                previousDay = day;
            }

            if (Double.isNaN(minTemp)) {
                minTemp = temp;
                minDate = myDate;
            }
            if (Double.isNaN(maxTemp)) {
                maxTemp = temp;
                maxDate = myDate;
            }

            if (day.equals(previousDay)) {
                if (temp < minTemp) {
                    minTemp = temp;
                    minDate = myDate;
                }
                if (temp > maxTemp) {
                    maxTemp = temp;
                    maxDate = myDate;
                }

            } else {
                seriesMin.addOrUpdate(new Minute(minDate), minTemp);
                seriesMax.addOrUpdate(new Minute(maxDate), maxTemp);
                minDate = null;
                maxDate = null;
                minTemp = Double.NaN;
                maxTemp = Double.NaN;
            }
            previousDay = day;

        }
        
        
        //dataset.addSeries(series);
        
        long intervalStart = Long.parseLong(forecasts.get(0).getTime()) * 1000;
        long intervalEnd = Long.parseLong(forecasts.get(forecasts.size() - 1).getTime()) * 1000;
        IntervalMarker mark = new IntervalMarker(intervalStart, intervalEnd );
        mark.setPaint(Main.getColorcontrast1());

        plot.setAxisOffset(new RectangleInsets(0, 0, 0, 0));
        
        plot.addDomainMarker(mark, Layer.BACKGROUND);
        mark.setLabel("Forecasts");
        mark.setLabelFont(Main.getFontnormal());
        mark.setLabelAnchor(RectangleAnchor.TOP);
        mark.setLabelOffset(new RectangleInsets(20,0,0,0));
        ValueAxis axis = plot.getDomainAxis();
        
        axis.setLowerMargin(0);
        axis.setUpperMargin(0);
        
        XYItemRenderer r = plot.getRenderer();
        if (r instanceof XYLineAndShapeRenderer) {
            XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) r;
            renderer.setSeriesItemLabelsVisible(plot.getSeriesCount() - 1, false);            
        }
		
	}
	

	public JPanel getmTablePanel() {
		return mTablePanel;
	}

	public void setmTablePanel(JPanel mTablePanel) {
		this.mTablePanel = mTablePanel;
	}

	public JScrollPane getmFTableScrollPane() {
		return mFTableScrollPane;
	}

	public void setmFTableScrollPane(JScrollPane mFTableScrollPane) {
		this.mFTableScrollPane = mFTableScrollPane;
	}




}
