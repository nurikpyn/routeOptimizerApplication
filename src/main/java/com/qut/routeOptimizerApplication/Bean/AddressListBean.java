package com.qut.routeOptimizerApplication.Bean;

import java.util.List;

public class AddressListBean {

	private List<RoadLocation> locationList;
	
	public List<RoadLocation> getLocationList() {
		return locationList;
	}
	public AddressListBean() {
		
	}
	public AddressListBean(List<RoadLocation> locationList, String status) {
		super();
		this.locationList = locationList;
		this.status = status;
	}

	public void setLocationList(List<RoadLocation> locationList) {
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
