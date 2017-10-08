package com.qut.routeOptimizerApplication.service;

import java.util.ArrayList;
import java.util.List;

import com.graphhopper.GHRequest;
import com.graphhopper.GHResponse;
import com.graphhopper.GraphHopper;
import com.graphhopper.routing.util.EncodingManager;
import com.graphhopper.util.StopWatch;
import com.graphhopper.util.shapes.GHPoint;
import com.qut.routeOptimizerApplication.Bean.Address;
import com.qut.routeOptimizerApplication.Bean.ResultsBean;
import com.qut.routeOptimizerApplication.Bean.UploadInvoiceBean;
import com.qut.routeOptimizerApplication.properties.RouteOptimzerProperties;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
public class DistanceService {
	// private final Logger logger = LoggerFactory.getLogger(getClass());
	public ResultsBean getDistance(Address source, Address destination) {
		GHPoint ghPointSource, ghPointDestination;
		GHResponse ghResponse = new GHResponse();
		RouteOptimzerProperties routeOptimzerProperties = new RouteOptimzerProperties();
		ResultsBean resultBean=new ResultsBean();
		resultBean.setSource(source);
		resultBean.setDestination(destination);
		if(source==destination) {
			resultBean.setDistance(0);
			resultBean.setTime(0);
		}
		else {
			GraphHopper graphHopper = new GraphHopper().
					setGraphHopperLocation(routeOptimzerProperties.hopperDirectory)
					.setEncodingManager(new EncodingManager("car")).
					setOSMFile(routeOptimzerProperties.osmFilePath);
			graphHopper.importOrLoad();

			ghPointSource = new GHPoint();
			ghPointSource.lat = Double.parseDouble(source.getLatitude());
			ghPointSource.lon = Double.parseDouble(source.getLongitude());
			ghPointDestination = new GHPoint();
			ghPointDestination.lat = Double.parseDouble(destination.getLatitude());
			ghPointDestination.lon = Double.parseDouble(destination.getLongitude());
			GHRequest request;
			request = new GHRequest(ghPointSource, ghPointDestination)
					.setVehicle("car").
	                setWeighting("fastest");
			ghResponse = graphHopper.route(request);
			resultBean.setDistance(ghResponse.getDistance());
			resultBean.setTime(ghResponse.getMillis());
		}
		return resultBean;
	}

	public List<ResultsBean> calculateDistanceMatrix(UploadInvoiceBean uploadInvoiceBean) {
		int locationLength = uploadInvoiceBean.getLocationList().size();
		DistanceService dist=new DistanceService();
		List<ResultsBean> resultList=new ArrayList<ResultsBean>();
		ResultsBean resultBean;
		 int counter = 0;
	     StopWatch sw = new StopWatch().start();
		for (int i = 0; i < locationLength; i++) {
			for (int j = 0; j < locationLength; j++) {
				resultBean=new ResultsBean();
				resultBean=dist.getDistance(uploadInvoiceBean.getLocationList().get(i), uploadInvoiceBean.getLocationList().get(j));
				counter++;
				resultList.add(resultBean);
			}	
		}

		System.out.println(counter + " routes calculated, took: " + sw.stop().getSeconds());        	
	
		try {
			dist.generateDistancematrixCSV(resultList);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resultList;
	}
	public void generateDistancematrixCSV( List<ResultsBean> resultList) throws FileNotFoundException {
		File file = new File("test.csv");	
		 try {
			 	 if (file.exists()) {
			     file.delete(); 
			     }
			file.createNewFile();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		 PrintWriter pw = new PrintWriter(file);
        StringBuilder sb = new StringBuilder();	
        sb.append("id");
        sb.append(',');
        sb.append("source");
        sb.append(',');
        sb.append("destination");
        sb.append(',');
        sb.append("distance");
        sb.append(',');
        sb.append("time"); 
        sb.append('\n');
        for(int k=0;k<resultList.size();k++) {
        	 sb.append(k);
        	 sb.append(',');
        	 sb.append(resultList.get(k).getSource().toString());
        	 sb.append(',');
        	 sb.append(resultList.get(k).getDestination().toString());
        	 sb.append(',');
        	 sb.append(resultList.get(k).getDistance());
        	 sb.append(',');
        	 sb.append(resultList.get(k).getTime());
        	 sb.append('\n');
        }
        pw.write(sb.toString());
        pw.close();
        System.out.println("done!");
	}
}