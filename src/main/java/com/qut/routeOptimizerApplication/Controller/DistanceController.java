
package com.qut.routeOptimizerApplication.Controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.qut.routeOptimizerApplication.properties.RouteOptimzerProperties;
import com.qut.routeOptimizerApplication.service.opta.vehiclerouting.domain.JsonCustomer;
import com.qut.routeOptimizerApplication.service.opta.vehiclerouting.domain.input.JsonFileInput;
import com.qut.routeOptimizerApplication.service.opta.vehiclerouting.domain.input.JsonManualInput;
import com.qut.routeOptimizerApplication.service.opta.vehiclerouting.domain.location.Location;
import com.qut.routeOptimizerApplication.service.opta.vehiclerouting.domain.location.RoadLocation;
import com.qut.routeOptimizerApplication.service.opta.vehiclerouting.persistence.VehicleRoutingDao;
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
	public String generateVRP( JsonManualInput jsonInput)  {
	RouteOptimzerProperties routeOptimzerProperties = new RouteOptimzerProperties();
	List<Location> customerList=new ArrayList<Location>();
	long id=0;
	Location location;
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
    /* CommonApp.prepareSwingEnvironment();
      new VehicleRoutingApp().init();*/
		return "distanceMatrix";
	}
	@RequestMapping(value = "/fileSave",method = RequestMethod.POST)
	public String generateVRPFile( JsonFileInput jsonFileInputBean) throws FileNotFoundException {
      VRPGenerator vrpService=new VRPGenerator();
      VehicleRoutingDao route=new VehicleRoutingDao();
      VehicleRoutingImporter imp=new VehicleRoutingImporter();
      File inputFile=vrpService.generateVrp(jsonFileInputBean.getLocationFile(),jsonFileInputBean.getDepotListSize() ,jsonFileInputBean.getVehicleCount(), jsonFileInputBean.getVehicleCapacity(), GenerationDistanceType.ROAD_DISTANCE_KM, VrpType.BASIC);
      int customerSize=vrpService.readLocationFile(jsonFileInputBean.getLocationFile()).size()-1;
      String outputFileName="cvrp-"+customerSize+"cus-"+jsonFileInputBean.getVehicleCount()+"veh-"+jsonFileInputBean.getVehicleCapacity()+"cap.xml";
      imp.convert(inputFile.getName(), outputFileName);
      /* CommonApp.prepareSwingEnvironment();
      new VehicleRoutingApp().init();*/
      return "distanceMatrix";
	}
	public static void main(String args[]) {
		DistanceController dist=new DistanceController();
		/*JsonManualInput manual=new JsonManualInput();
		List<JsonCustomer> customerList=new ArrayList<JsonCustomer>();
		customerList.add(new JsonCustomer(-27.467834,153.019079,1));
		customerList.add(new JsonCustomer(-27.463306,153.028396,1));
		customerList.add(new JsonCustomer(-27.456195,153.033586,1));
		manual.setVehicleCapacity(5);
		manual.setVehicleCount(1);
		manual.setDepot(new JsonCustomer(-27.456195,153.033586,0));
		manual.setCustomerList(customerList);
		System.out.println("for maual entry"+manual.toString());*/
		
		JsonFileInput fileIn=new JsonFileInput();
		RouteOptimzerProperties routeOptimzerProperties = new RouteOptimzerProperties();
		File locationFile = new File(routeOptimzerProperties.locationFilepath);
		fileIn.setLocationFile(locationFile);
		fileIn.setDepotListSize(1);
		fileIn.setVehicleCount(2);
		fileIn.setVehicleCapacity(5);
		try {
			System.out.println("for file entry"+fileIn.toString());
			dist.generateVRPFile(fileIn);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
