
package com.qut.routeOptimizerApplication;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.graphhopper.GHRequest;
import com.graphhopper.GHResponse;
import com.graphhopper.GraphHopper;
import com.graphhopper.routing.util.EncodingManager;
import com.graphhopper.util.Instruction;
import com.graphhopper.util.shapes.GHPoint;
import com.qut.routeOptimizerApplication.Bean.Location;
import com.qut.routeOptimizerApplication.Bean.UploadInvoiceBean;

@RestController
public class HomeController {
	@RequestMapping("/")
	public String welcome(Map<String, Object> model) {
		System.out.println("inside welcomr");
		return "welcome";
	}
	@RequestMapping(value = "/save",method = RequestMethod.POST)
	public ModelAndView getDistance(@ModelAttribute("locations") UploadInvoiceBean uploadInvoiceBean) {
		String s = "";
		RouteOptimzerProperties routeOptimzerProperties=new RouteOptimzerProperties();
		System.out.println(routeOptimzerProperties.hopperDirectory);
		System.out.println(routeOptimzerProperties.osmFilePath);
		GraphHopper graphHopper = new GraphHopper().setGraphHopperLocation(routeOptimzerProperties.hopperDirectory)
				.setEncodingManager(new EncodingManager("car")).setOSMFile(routeOptimzerProperties.osmFilePath);
		graphHopper.importOrLoad();
		List<GHPoint> ghList=new ArrayList<GHPoint>();
		GHPoint ghPoint=new GHPoint();
		
		for(Location address:uploadInvoiceBean.getLocationList())
		{
			System.out.println(address.getLatitude());
			ghPoint.lat=Double.parseDouble(address.getLatitude());
			ghPoint.lon=Double.parseDouble(address.getLongitude());
			ghList.add(ghPoint);
		}
		GHRequest request = new GHRequest(ghList);
		request.putHint("calcPoints", false);
		request.putHint("instructions", true);
		request.setVehicle("car"); 
		GHResponse ghResponse = graphHopper.route(request);
		System.out.println("hello"+ghResponse.toString());
		if (ghResponse.getInstructions() != null) {
			for (Instruction i : ghResponse.getInstructions()) {
				s += "------>\ntime <long>: " + i.getTime() + "\n" + "name: street name" + i.getName() + "\n"
						+ "annotation <InstructionAnnotation>" + i.getAnnotation() + "\n" + "distance" + i.getDistance()
						+ "\n" + "sign <int>:" + i.getSign() + "\n" + "Points <PointsList>: " + i.getPoints() + "\n";
			}
		}
		ModelAndView model=new ModelAndView("index");
		System.out.println(s);
		uploadInvoiceBean.setStatus("SUCCESS");
		model.addObject("msg", s);
		model.addObject("locationList", uploadInvoiceBean);
		return model;
		
	}
}
