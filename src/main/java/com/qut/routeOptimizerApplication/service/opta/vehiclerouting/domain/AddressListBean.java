package com.qut.routeOptimizerApplication.service.opta.vehiclerouting.domain;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.qut.routeOptimizerApplication.service.opta.vehiclerouting.domain.location.RoadLocation;
@XmlRootElement
public class AddressListBean {
private List<RoadLocation>  locationList;
private int vehicleCount;
private int vehicleCapacity;
	public List<RoadLocation> getLocationList() {
		return locationList;
	}
	public AddressListBean() {
		
	}
	

	public AddressListBean(List<RoadLocation> locationList, int vehicleCount, int vehicleCapacity, String status) {
		super();
		this.locationList = locationList;
		this.vehicleCount = vehicleCount;
		this.vehicleCapacity = vehicleCapacity;
		this.status = status;
	}
	public int getVehicleCount() {
		return vehicleCount;
	}
	public void setVehicleCount(int vehicleCount) {
		this.vehicleCount = vehicleCount;
	}
	public int getVehicleCapacity() {
		return vehicleCapacity;
	}
	public void setVehicleCapacity(int vehicleCapacity) {
		this.vehicleCapacity = vehicleCapacity;
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
