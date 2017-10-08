package com.qut.routeOptimizerApplication.Bean;

import java.util.List;

public class AddressListBean {

	private List<Address> locationList;
	
	public List<Address> getLocationList() {
		return locationList;
	}
	public AddressListBean() {
		
	}
	public AddressListBean(List<Address> locationList, String status) {
		super();
		this.locationList = locationList;
		this.status = status;
	}

	public void setLocationList(List<Address> locationList) {
		this.locationList = locationList;
	}


	private String status;
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
