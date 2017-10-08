package com.qut.routeOptimizerApplication.Bean;

import com.graphhopper.util.shapes.GHPoint;

public class ResultsBean {
	GHPoint source;
	String sourceName;
	GHPoint destination;
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
	public GHPoint getSource() {
		return source;
	}
	public void setSource(GHPoint source) {
		this.source = source;
	}
	public GHPoint getDestination() {
		return destination;
	}
	public void setDestination(GHPoint destination) {
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
