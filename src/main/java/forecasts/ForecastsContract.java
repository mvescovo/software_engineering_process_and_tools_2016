package forecasts;

import data.Forecast;
import data.Station;

import java.util.List;

/**
 * The contract between the view and the presenter.
 *
 * @author michael
 */
public interface ForecastsContract {

    /**
     * The interface for the view.
     */
    interface View {
        /**
         * Enable/disable a progress bar while data is loading.
         *
         * @param active true for enable, false for disable.
         */
        void setProgressBar(boolean active);

        /**
         * Show forecasts in the current view.
         *
         * @param forecasts the observations to show.
         */
        void showForecasts(List<Forecast> forecasts);

        /**
         * Set the Presenter on initialisation.
         *
         * @param presenter the presenter the view will use.
         */
        void setActionListener(ForecastsContract.UserActionsListener presenter);

        /**
         * When the view is initialised.
         *
         * @param station the station to load when the view is ready.
         */
        void onReady(Station station);

        // ***Not currently ready. Ignore for now.***
        // FYI: Interface may change.
        void setForecastSite(String forecastSite);
    
        /**
         * Show latest forecasts
         *
         * @param forecast the forecast to show.
         */
        void showLatestForecast(Forecast forecast);
        
        /**
         * Show forecasts in the current view.
         *
         * @param forecasts the forecasts to show.
         */
        void showForecastTable(List<Forecast> forecasts);
        
        /**
         * Show chart.
         *
         * @param forecasts the forecasts on which to base to chart.
         */
        void showForecastChart(List<Forecast> forecasts);
    
    
    }

    /**
     * The interface for the presenter.
     */
    interface UserActionsListener {

        /**
         * Load forecasts
         *
         * @param station station on which to base forecasts.
         * @param forceUpdate determines weather to use memory or force a refresh to pull latest data
         */
        void loadForecasts(Station station, boolean forceUpdate);

        void setForecastSite(String forecastSite);
    }
}
