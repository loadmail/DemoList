package com.example.baidumap.MapUtil;

/**
 * 相当于bean
 * 在接口中被传递
 */
public class LocationBean {

	double latitude;

	double longitude;

	String city;

	String addrStr;

	String district;

	String province;

	int locType;

	public LocationBean(double latitude, double longitude, String city, String addrStr, String district, String province, int locType) {
		super();
		this.latitude = latitude;
		this.longitude = longitude;
		this.city = city;
		this.addrStr = addrStr;
		this.district = district;
		this.province = province;
		this.locType = locType;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getAddrStr() {
		return addrStr;
	}

	public void setAddrStr(String addrStr) {
		this.addrStr = addrStr;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public int getLocType() {
		return locType;
	}

	public void setLocType(int locType) {
		this.locType = locType;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

}
