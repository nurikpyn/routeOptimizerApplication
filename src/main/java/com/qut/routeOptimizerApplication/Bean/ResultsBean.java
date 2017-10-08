package com.qut.routeOptimizerApplication.Bean;

public class ResultsBean {
	Location source;
	String sourceName;
	Location destination;
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
	public Location getSource() {
		return source;
	}
	public void setSource(Location source) {
		this.source = source;
	}
	public Location getDestination() {
		return destination;
	}
	public void setDestination(Location destination) {
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
