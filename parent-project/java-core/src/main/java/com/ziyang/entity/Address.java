package com.ziyang.entity;

public class Address {
	private String streetNumber;

	private String streetName;

	private String streetDirection;

	private String province;

	private String country;

	private String city;

	private String addressType;

	@Override
	public String toString() {
		return "Address [streetNumber=" + streetNumber + ", streetName=" + streetName + ", streetDirection="
				+ streetDirection + ", province=" + province + ", country=" + country + ", city=" + city
				+ ", addressType=" + addressType + "]";
	}

	public String getStreetNumber() {
		return streetNumber;
	}

	public void setStreetNumber(String streetNumber) {
		this.streetNumber = streetNumber;
	}

	public String getStreetName() {
		return streetName;
	}

	public void setStreetName(String streetName) {
		this.streetName = streetName;
	}

	public String getStreetDirection() {
		return streetDirection;
	}

	public void setStreetDirection(String streetDirection) {
		this.streetDirection = streetDirection;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getAddressType() {
		return addressType;
	}

	public void setAddressType(String addressType) {
		this.addressType = addressType;
	}

}
