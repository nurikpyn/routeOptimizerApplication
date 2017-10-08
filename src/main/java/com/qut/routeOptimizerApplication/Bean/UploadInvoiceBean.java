package com.qut.routeOptimizerApplication.Bean;

import java.util.List;

public class UploadInvoiceBean {
	// private final Logger logger = LoggerFactory.getLogger(getClass());
	private List<Address> locationList;
	
	public List<Address> getLocationList() {
		return locationList;
	}
	public UploadInvoiceBean() {
		
	}
	public UploadInvoiceBean(List<Address> locationList, String status) {
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
