/**
 *  Copyright 2005-2016 Red Hat, Inc.
 *
 *  Red Hat licenses this file to you under the Apache License, version
 *  2.0 (the "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 *  implied.  See the License for the specific language governing
 *  permissions and limitations under the License.
 */
package com.qut.routeOptimizerApplication;

import java.util.ArrayList;
import java.util.List;

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
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index() {
	    return "index";
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
		System.out.println("hello"+request.toString());
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
