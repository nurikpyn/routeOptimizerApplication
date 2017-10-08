package com.qut.routeOptimizerApplication.Bean;

public class ResultsBean {
	Address source;
	String sourceName;
	Address destination;
	String destinationName;
	double distance;
	double time;
	
	public String getSourceName() {
		return sourceName;
	}
	public void setSourceName(String sourceName) {
		this.sourceName = sourceName;
	}
	public String getDestinationName() {
		return destinationName;
	}
	public void setDestinationName(String destinationName) {
		this.destinationName = destinationName;
	}
	public Address getSource() {
		return source;
	}
	public void setSource(Address source) {
		this.source = source;
	}
	public Address getDestination() {
		return destination;
	}
	public void setDestination(Address destination) {
		this.destination = destination;
	}
	public double getDistance() {
		return distance;
	}
	public void setDistance(double distance) {
		this.distance = distance;
	}
	public double getTime() {
		return time;
	}
	public void setTime(double time) {
		this.time = time;
	}
}
