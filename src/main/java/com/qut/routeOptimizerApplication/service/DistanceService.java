package com.qut.routeOptimizerApplication.service;

import java.util.List;

import com.graphhopper.GHRequest;
import com.graphhopper.GHResponse;
import com.graphhopper.GraphHopper;
import com.graphhopper.routing.util.EncodingManager;
import com.graphhopper.util.shapes.GHPoint;
import com.qut.routeOptimizerApplication.properties.RouteOptimzerProperties;
import com.qut.routeOptimizerApplication.service.opta.vehiclerouting.domain.location.Location;

public class DistanceService {
	public double getDistance(Location source, Location destination) {
		GHPoint ghPointSource, ghPointDestination;
		GHResponse ghResponse = new GHResponse();
		double distance=0;
		//ResultsBean resultbean = new ResultsBean();
		RouteOptimzerProperties routeOptimzerProperties = new RouteOptimzerProperties();
		ghPointSource = new GHPoint();
		ghPointSource.lat = source.getLatitude();
		ghPointSource.lon = source.getLongitude();
		//resultbean.setSource(ghPointSource);
		ghPointDestination = new GHPoint();
		ghPointDestination.lat =destination.getLatitude();
		ghPointDestination.lon = destination.getLongitude();
		//resultbean.setDestination(ghPointDestination);
		if (source == destination) {
			distance=0;
		} 
		else {
			GraphHopper graphHopper = new GraphHopper().setGraphHopperLocation(routeOptimzerProperties.hopperDirectory)
					.setEncodingManager(new EncodingManager("car")).setOSMFile(routeOptimzerProperties.osmFilePath);
			graphHopper.importOrLoad();
			GHRequest request = new GHRequest(ghPointSource, ghPointDestination);
			request.putHint("calcPoints", false);
			request.putHint("instructions", true);
			request.setVehicle("car");
			ghResponse = graphHopper.route(request);
			distance=ghResponse.getDistance();
		}
		return distance;
	}

	public double[][] calculateEdgeMatrix(List<Location> uploadInvoiceBean) {
		int locationLength = uploadInvoiceBean.size();
		double[][] edgeMatrix=new double[locationLength][locationLength];

		for (int i = 0; i < locationLength; i++) {
			for (int j = 0; j < locationLength; j++) {
				edgeMatrix[i][j]=getDistance( uploadInvoiceBean.get(i), uploadInvoiceBean.get(j))/1000;
				System.out.println("i"+i+""+uploadInvoiceBean.get(i)+"j"+j+""+uploadInvoiceBean.get(j));
			}
		}
		
		return edgeMatrix;
	}
	
}