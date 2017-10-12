
package com.qut.routeOptimizerApplication.Controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.qut.routeOptimizerApplication.service.opta.vehiclerouting.app.VehicleRoutingApp;
import com.qut.routeOptimizerApplication.service.opta.vehiclerouting.domain.AddressListBean;
import com.qut.routeOptimizerApplication.service.opta.vehiclerouting.persistence.VehicleRoutingDao;
import com.qut.routeOptimizerApplication.service.opta.vehiclerouting.persistence.VehicleRoutingImporter;
import com.qut.routeOptimizerApplication.service.opta.vrpGenerator.GenerationDistanceType;
import com.qut.routeOptimizerApplication.service.opta.vrpGenerator.VRPGenerator;
import com.qut.routeOptimizerApplication.service.opta.vrpGenerator.VrpType;

@Controller
public class DistanceController {
	@RequestMapping("/")
	public String welcome(Map<String, Object> model) {
		return "welcome";
	}
	@RequestMapping(value = "/save",method = RequestMethod.GET)
	public String generateVRP(@ModelAttribute("locations") AddressListBean addressListBean) throws FileNotFoundException {
	File locationFile = new File("C:\\Users\\pretty\\Desktop\\routeOptimizerApplication\\data\\australia.csv");
      VRPGenerator vrpService=new VRPGenerator();
      VehicleRoutingDao route=new VehicleRoutingDao();
      VehicleRoutingImporter imp=new VehicleRoutingImporter();
      File inputFile=vrpService.generateVrp(locationFile, 1, 2, 100, GenerationDistanceType.ROAD_DISTANCE_KM, VrpType.BASIC);
      imp.convert(inputFile.getName(), "cvrp-5customers.xml");
      VehicleRoutingApp vrp=new VehicleRoutingApp();
		return "distanceMatrix";
	}
}
