
package com.qut.routeOptimizerApplication.Controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.qut.routeOptimizerApplication.properties.RouteOptimzerProperties;
import com.qut.routeOptimizerApplication.service.opta.common.app.CommonApp;
import com.qut.routeOptimizerApplication.service.opta.vehiclerouting.app.VehicleRoutingApp;
import com.qut.routeOptimizerApplication.service.opta.vehiclerouting.domain.JsonCustomer;
import com.qut.routeOptimizerApplication.service.opta.vehiclerouting.domain.input.JsonFileInput;
import com.qut.routeOptimizerApplication.service.opta.vehiclerouting.domain.input.JsonManualInput;
import com.qut.routeOptimizerApplication.service.opta.vehiclerouting.domain.location.Location;
import com.qut.routeOptimizerApplication.service.opta.vehiclerouting.domain.location.RoadLocation;
import com.qut.routeOptimizerApplication.service.opta.vehiclerouting.persistence.VehicleRoutingImporter;
import com.qut.routeOptimizerApplication.service.opta.vrpGenerator.GenerationDistanceType;
import com.qut.routeOptimizerApplication.service.opta.vrpGenerator.VRPGenerator;
import com.qut.routeOptimizerApplication.service.opta.vrpGenerator.VrpType;

@Controller
public class DistanceController {
	@RequestMapping("/")
	public String welcome(Map<String, Object> model) {
		return "index";
	}
	@RequestMapping(value = "/save",method = RequestMethod.POST)
	public String generateVRP(@RequestBody JsonManualInput jsonInput)  {
	RouteOptimzerProperties routeOptimzerProperties = new RouteOptimzerProperties();
	List<Location> customerList=new ArrayList<Location>();
	System.out.println("value"+jsonInput.getDepot().getLatitude());
	
	long id=0;
	Location location;
	location=new RoadLocation();
	location.setId(id);
	location.setLatitude(jsonInput.getDepot().getLatitude());
	location.setLongitude(jsonInput.getDepot().getLongitude());
	id++;
	for(JsonCustomer cus:jsonInput.getCustomerList()) {
		location=new RoadLocation();
		location.setId(id);
		location.setLatitude(cus.getLatitude());
		location.setLongitude(cus.getLongitude());
		id++;
		customerList.add(location);
	}
     VRPGenerator vrpService=new VRPGenerator();  
      File inputFile=vrpService.generateManualVrp(customerList,routeOptimzerProperties.country,1 , jsonInput.getVehicleCount(), jsonInput.getVehicleCapacity(), GenerationDistanceType.ROAD_DISTANCE_KM, VrpType.BASIC);
      int customerSize=customerList.size()-1;
      String outputFileName="cvrp-"+customerSize+"cus-"+jsonInput.getVehicleCount()+"veh-"+jsonInput.getVehicleCapacity()+"cap.xml";
      VehicleRoutingImporter imp=new VehicleRoutingImporter();
      imp.convert(inputFile.getName(), outputFileName);
      CommonApp.prepareSwingEnvironment();
      new VehicleRoutingApp().init(); 
		return "distanceMatrix";
	}
	@RequestMapping(value = "/fileSave",method = RequestMethod.POST)
	public String generateVRPFile( @RequestBody JsonFileInput jsonFileInputBean) throws FileNotFoundException {
      VRPGenerator vrpService=new VRPGenerator();
 
      VehicleRoutingImporter imp=new VehicleRoutingImporter();
      File inputFile=vrpService.generateVrp(jsonFileInputBean.getLocationFile(),jsonFileInputBean.getDepotListSize() ,jsonFileInputBean.getVehicleCount(), jsonFileInputBean.getVehicleCapacity(), GenerationDistanceType.ROAD_DISTANCE_KM, VrpType.BASIC);
      int customerSize=vrpService.readLocationFile(jsonFileInputBean.getLocationFile()).size()-1;
      String outputFileName="cvrp-"+customerSize+"cus-"+jsonFileInputBean.getVehicleCount()+"veh-"+jsonFileInputBean.getVehicleCapacity()+"cap.xml";
      imp.convert(inputFile.getName(), outputFileName);
  
      VehicleRoutingApp vehApp=new VehicleRoutingApp();
      CommonApp.prepareSwingEnvironment();
      vehApp.init();
      return "distanceMatrix";
	}
	
}
