
package com.qut.routeOptimizerApplication.Controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.qut.routeOptimizerApplication.properties.RouteOptimzerProperties;
import com.qut.routeOptimizerApplication.service.opta.common.app.CommonApp;
import com.qut.routeOptimizerApplication.service.opta.vehiclerouting.app.VehicleRoutingApp;
import com.qut.routeOptimizerApplication.service.opta.vehiclerouting.domain.AddressListBean;
import com.qut.routeOptimizerApplication.service.opta.vehiclerouting.domain.OptimizerInput;
import com.qut.routeOptimizerApplication.service.opta.vehiclerouting.domain.location.Location;
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
		RouteOptimzerProperties routeOptimzerProperties = new RouteOptimzerProperties();
		File locationFile = new File(routeOptimzerProperties.locationFilepath);
      VRPGenerator vrpService=new VRPGenerator();
      VehicleRoutingImporter imp=new VehicleRoutingImporter();
      addressListBean.setVehicleCount(2);
      addressListBean.setVehicleCapacity(5);
      List<Location> locationList=vrpService.readLocationFile(locationFile);      
      File inputFile=vrpService.generateVrp(locationFile,1 , addressListBean.getVehicleCount(), addressListBean.getVehicleCapacity(), GenerationDistanceType.ROAD_DISTANCE_KM, VrpType.BASIC);
      int customerSize=locationList.size()-1;
      String outputFileName="cvrp-"+customerSize+"cus-"+addressListBean.getVehicleCount()+"veh-"+addressListBean.getVehicleCapacity()+"cap.xml";
      imp.convert(inputFile.getName(), outputFileName);
    /* CommonApp.prepareSwingEnvironment();
      new VehicleRoutingApp().init();*/
		return "distanceMatrix";
	}
	@RequestMapping(value = "/fileSave",method = RequestMethod.GET)
	public String generateVRPFile(@ModelAttribute("locations") OptimizerInput addressListBean) throws FileNotFoundException {
		RouteOptimzerProperties routeOptimzerProperties = new RouteOptimzerProperties();
		File locationFile = new File(routeOptimzerProperties.locationFilepath);
      VRPGenerator vrpService=new VRPGenerator();
      VehicleRoutingDao route=new VehicleRoutingDao();
      VehicleRoutingImporter imp=new VehicleRoutingImporter();
      File inputFile=vrpService.generateVrp(locationFile, 1, 2, 100, GenerationDistanceType.ROAD_DISTANCE_KM, VrpType.BASIC);
      imp.convert(inputFile.getName(), "cvrp-5customers.xml");
      VehicleRoutingApp vrp=new VehicleRoutingApp();
		return "distanceMatrix";
	}
}
