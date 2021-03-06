package data;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.Vector;

/**
 * Observation entity containing all available properties populated with a call
 * to the Australian Bureau of Meteorology JSON web service.
 *
 * @author michael
 * @author kendall
 * @author steve
 */
public class Observation {

	private String mId;
	private String mName;
	private String mStateName;
	private String mDateTime;
	private String mApparentTemp;
	private String mCloud;
	private String mAirtemp;
	private String mRain;
	private String mHumidity;
	private String mDewpt;
	private String mDelta_t;
	private String mWindDir;
	private String mWindSpdKmh;
	private String mGustKmh;
	private String mWindSpdKt;
	private String mGustKt;
	private String mPressQnh;
	private String mPressMsl;

	/**
	 * Constructor for a weather observation with all properties.
	 * 
	 * @param airTemp
	 * @param name
	 * @param wmo
	 * @param history_product
	 * @param local_date_time
	 * @param local_date_time_full
	 * @param aifstime_utc
	 * @param lat
	 * @param lon
	 * @param apparent_t
	 * @param cloud
	 * @param cloud_base_m
	 * @param cloud_oktas
	 * @param cloud_type
	 * @param cloud_type_id
	 * @param delta_t
	 * @param gust_kmh
	 * @param dewpt
	 * @param press
	 * @param press_msl
	 * @param press_qnh
	 * @param press_tend
	 * @param rain_trace
	 * @param rel_hum
	 * @param sea_state
	 * @param swell_dir_worded
	 * @param swell_height
	 * @param swell_period
	 * @param vis_km
	 * @param weather
	 * @param wind_dir
	 * @param wind_spd_kmh
	 * @param wind_spd_kt
	 */
	public Observation(String airTemp, String name, String wmo, String history_product, String local_date_time,
			String local_date_time_full, String aifstime_utc, String lat, String lon, String apparent_t, String cloud,
			String cloud_base_m, String cloud_oktas, String cloud_type, String cloud_type_id, String delta_t,
			String gust_kmh, String dewpt, String press, String press_msl, String press_qnh, String press_tend,
			String rain_trace, String rel_hum, String sea_state, String swell_dir_worded, String swell_height,
			String swell_period, String vis_km, String weather, String wind_dir, String wind_spd_kmh,
			String wind_spd_kt) {

		mId = 
		mAirtemp = airTemp;
		mName = name;
		mDateTime = local_date_time_full;
		mApparentTemp = apparent_t;
		mCloud = cloud.equals("-") ? "Clear" : cloud;
		mRain = rain_trace;
		mHumidity = rel_hum;
		mDewpt = dewpt;
		mDelta_t = delta_t;
		mWindDir = wind_dir;
		mWindSpdKmh = wind_spd_kmh;
		mGustKmh = gust_kmh;
		mWindSpdKt = wind_spd_kt;
		mGustKt = "-";
		mPressQnh = press_qnh;
		mPressMsl = press_msl;

	}

	/**
	 * Constructor for a weather observation with minimal properties.
	 * 
	 * @param id
	 * @param name
	 * @param dateTime
	 * @param apparentTemp
	 * @param cloud
	 * @param airtemp
	 * @param rain
	 * @param humidity
	 */
	public Observation(String id, String name, String dateTime, String apparentTemp, String cloud, String airtemp,
			String rain, String humidity) {
		mId = id;
		mName = name;
		mDateTime = dateTime;
		mApparentTemp = apparentTemp;
		mCloud = cloud;
		mAirtemp = airtemp;
		mRain = rain;
		mHumidity = humidity;
	}

	/**
	 * Creates a vector for the observation's data including processing the date
	 * string into a more human readable format.
	 * 
	 * @return a vector of observation properties
	 */
	public Vector<String> getObsVector() {
		Vector<String> data = new Vector<String>();
		SimpleDateFormat standardDateFormat = new SimpleDateFormat("yyyyMMddhhmmss");
		Date thisDate = null;
		try {
			thisDate = standardDateFormat.parse(getmDateTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}

		Calendar c = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
		c.setTime(thisDate);

		SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm a EE dd/MM/yy");
		String dayOfWeek = dateFormat.format(c.getTime());

		data.addElement(dayOfWeek);
		data.addElement(getmAirtemp());
		data.addElement(getmApparentTemp());
		data.addElement(getmDewpt());
		data.addElement(getmHumidity());
		data.addElement(getmDelta_t());
		data.addElement(getmWindDir());
		data.addElement(getmWindSpdKmh());
		data.addElement(getmGustKmh());
		data.addElement(getmWindSpdKt());
		data.addElement(getmGustKt());
		data.addElement(getmPressQnh());
		data.addElement(getmPressMsl());
		data.addElement(getmRain());

		return data;
	}

	/*
	 * Getters and setters for the class.
	 */
	public String getmId() {
		return mId;
	}

	public void setmId(String mId) {
		this.mId = mId;
	}

	public String getmName() {
		return mName;
	}

	public void setmName(String mName) {
		this.mName = mName;
	}

	public String getmDateTime() {
		return mDateTime;
	}

	public void setmDateTime(String mDateTime) {
		this.mDateTime = mDateTime;
	}

	public String getmApparentTemp() {
		return mApparentTemp;
	}

	public void setmApparentTemp(String mApparentTemp) {
		this.mApparentTemp = mApparentTemp;
	}

	public String getmCloud() {
		return mCloud;
	}

	public void setmCloud(String mCloud) {
		this.mCloud = mCloud;
	}

	public String getmAirtemp() {
		return mAirtemp;
	}

	public void setmAirtemp(String mAirtemp) {
		this.mAirtemp = mAirtemp;
	}

	public String getmRain() {
		return mRain;
	}

	public void setmRain(String mRain) {
		this.mRain = mRain;
	}

	public String getmHumidity() {
		return mHumidity;
	}

	public void setmHumidity(String mHumidity) {
		this.mHumidity = mHumidity;
	}

	private String getmDewpt() {
		return mDewpt;
	}

	public void setmDewpt(String mDewpt) {
		this.mDewpt = mDewpt;
	}

	private String getmDelta_t() {
		return mDelta_t;
	}

	public void setmDelta_t(String mDelta_t) {
		this.mDelta_t = mDelta_t;
	}

	public String getmWindDir() {
		return mWindDir;
	}

	public void setmWindDir(String mWindDir) {
		this.mWindDir = mWindDir;
	}

	public String getmWindSpdKmh() {
		return mWindSpdKmh;
	}

	public void setmWindSpdKmh(String mWindSpdKmh) {
		this.mWindSpdKmh = mWindSpdKmh;
	}

	private String getmGustKmh() {
		return mGustKmh;
	}

	public void setmGustKmh(String mGustKmh) {
		this.mGustKmh = mGustKmh;
	}

	private String getmWindSpdKt() {
		return mWindSpdKt;
	}

	public void setmWindSpdKt(String mWindSpdKt) {
		this.mWindSpdKt = mWindSpdKt;
	}

	private String getmGustKt() {
		return mGustKt;
	}

	public void setmGustKt(String mGustKt) {
		this.mGustKt = mGustKt;
	}

	private String getmPressQnh() {
		return mPressQnh;
	}

	public void setmPressQnh(String mPressQnh) {
		this.mPressQnh = mPressQnh;
	}

	private String getmPressMsl() {
		return mPressMsl;
	}

	public void setmPressMsl(String mPressMsl) {
		this.mPressMsl = mPressMsl;
	}

	public String getmStateName() {
		return mStateName;
	}

	void setmStateName(String mStateName) {
		this.mStateName = mStateName;
	}

}
