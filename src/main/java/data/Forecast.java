package data;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.Vector;

/**
 * Forecast entity class.
 *
 * @author michael
 */
public class Forecast {

    private String mTime;
    private String mDescription;
    private String mTemp;
    private String mMinTemp;
    private String mMaxTemp;
    private String mHumidity;
    private String mPressure;
    private String mWindSpeed;
    
    //created by steve suggested variables
    private String mLat;
    private String mLon;
    private String mRain;
    private String mName;
    

    /**
     * @param time the time of the particular forecast.
     * @param temp temperature of the forecast.
     */
    public Forecast(String time, String temp) {
        mTime = time;
        mTemp = temp;
    }


    /**
     * @param time the time of the particular forecast.
     * @param minTemp minimum temperature of the forecast.
     * @param maxTemp maximum temperature of the forecast.
     * @param humidity humidity of the forecast.
     * @param pressure pressure of the forecast.
     * @param windSpeed wind speed of the forecast.
     */
    public Forecast(String time, String temp, String minTemp, String maxTemp, String humidity, String pressure, String windSpeed) {
        mTime = time;
        mTemp = temp;
        mMinTemp = minTemp;
        mMaxTemp = maxTemp;
        mHumidity = humidity;
        mPressure = pressure;
        mWindSpeed = windSpeed;
        
    }

    


	public Forecast(String time, String temp, String temp_min, String temp_max, String pressure, String humidity,
			String name, String description, String rain, String lon, String lat, String windSpeed) {
		mTime = time;
		mDescription = description;
		mMinTemp = temp_min;
		mMaxTemp = temp_max;
		mPressure = pressure;
		mWindSpeed = windSpeed;
		mHumidity = humidity;
		mName = name;
		mRain = rain;
		mLat = lat;
		mLon = lon;
		
		
		
		
		
	}

	/**
	 * Creates a vector for the forecast's data including processing the date
	 * string into a more human readable format.
	 * 
	 * @return a vector of forecast properties
	 */
	public Vector<String> getForecastVector() {
		Vector<String> data = new Vector<String>();
		SimpleDateFormat standardDateFormat = new SimpleDateFormat("yyyyMMddhhmmss");
		Date thisDate = new Date(Long.parseLong(getTime()) * 1000);

		Calendar c = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
		c.setTime(thisDate);

		SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm a EE dd/MM/yy");
		String dayOfWeek = dateFormat.format(c.getTime());

		data.addElement(dayOfWeek);
		data.addElement(getTemp());
		data.addElement(getMinTemp());
		data.addElement(getMaxTemp());
		data.addElement(getPressure());
		data.addElement(getWindSpeed());

		return data;
	}
    
    
    /*
    * Getters and setters
    *
    * */
    public String getTime() {
        return mTime;
    }

    public void setTime(String time) {
        mTime = time;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public String getTemp() {
        return mTemp;
    }

    public void setTemp(String temp) {
        mTemp = temp;
    }

    public String getMinTemp() {
        return mMinTemp;
    }

    public void setMinTemp(String minTemp) {
        mMinTemp = minTemp;
    }

    public String getMaxTemp() {
        return mMaxTemp;
    }

    public void setMaxTemp(String maxTemp) {
        mMaxTemp = maxTemp;
    }

    public String getHumidity() {
        return mHumidity;
    }

    public void setHumidity(String humidity) {
        mHumidity = humidity;
    }

    public String getPressure() {
        return mPressure;
    }

    public void setPressure(String pressure) {
        mPressure = pressure;
    }

    public String getWindSpeed() {
        return mWindSpeed;
    }

    public void setWindSpeed(String windSpeed) {
        mWindSpeed = windSpeed;
    }
}
