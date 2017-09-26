package com.qut.routeOptimizerApplication.Bean;

import java.util.List;

public class UploadInvoiceBean {

	private List<Location> locationList;
	
	public List<Location> getLocationList() {
		return locationList;
	}
	public UploadInvoiceBean() {
		
	}
	public UploadInvoiceBean(List<Location> locationList, String status) {
		super();
		this.locationList = locationList;
		this.status = status;
	}

	public void setLocationList(List<Location> locationList) {
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
