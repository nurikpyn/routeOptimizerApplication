
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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qut.routeOptimizerApplication.properties.RouteOptimzerProperties;
import com.qut.routeOptimizerApplication.service.OptimizerOutput;
import com.qut.routeOptimizerApplication.service.opta.common.app.CommonApp;
import com.qut.routeOptimizerApplication.service.opta.vehiclerouting.app.VehicleRoutingApp;
import com.qut.routeOptimizerApplication.service.opta.vehiclerouting.domain.JsonCustomer;
import com.qut.routeOptimizerApplication.service.opta.vehiclerouting.domain.input.JsonFileInput;
import com.qut.routeOptimizerApplication.service.opta.vehiclerouting.domain.input.JsonManualInput;
import com.qut.routeOptimizerApplication.service.opta.vehiclerouting.domain.location.Location;
import com.qut.routeOptimizerApplication.service.opta.vehiclerouting.domain.location.RoadLocation;
import com.qut.routeOptimizerApplication.service.opta.vehiclerouting.domain.ouput.JsonVehicleRoutingSolution;
import com.qut.routeOptimizerApplication.service.opta.vehiclerouting.persistence.VehicleRoutingImporter;
import com.qut.routeOptimizerApplication.service.opta.vrpGenerator.GenerationDistanceType;
import com.qut.routeOptimizerApplication.service.opta.vrpGenerator.VRPGenerator;
import com.qut.routeOptimizerApplication.service.opta.vrpGenerator.VrpType;

@Controller
@RequestMapping("/routeOptimizerApplication")
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
      File locationFileName=new File(jsonFileInputBean.getLocationFilePath());
      File inputFile=vrpService.generateVrp(locationFileName,jsonFileInputBean.getDepotListSize() ,jsonFileInputBean.getVehicleCount(), jsonFileInputBean.getVehicleCapacity(), GenerationDistanceType.ROAD_DISTANCE_KM, VrpType.BASIC);
      int customerSize=vrpService.readLocationFile(locationFileName).size()-1;
      String outputFileName="cvrp-"+customerSize+"cus-"+jsonFileInputBean.getVehicleCount()+"veh-"+jsonFileInputBean.getVehicleCapacity()+"cap.xml";
      imp.convert(inputFile.getName(), outputFileName);
  
      VehicleRoutingApp vehApp=new VehicleRoutingApp();
      CommonApp.prepareSwingEnvironment();
      vehApp.init();
      return "distanceMatrix";
	}
	@RequestMapping(value = "/getSolvedFileList",method = RequestMethod.GET)
	public  List<String> getSolvedList( ) {
     List<String> fileNameList=new ArrayList<String>();
     RouteOptimzerProperties rot=new RouteOptimzerProperties();
     File folder = new File(rot.solved);
     File[] listOfFiles = folder.listFiles();
         for (int i = 0; i < listOfFiles.length; i++) {
           fileNameList.add(listOfFiles[i].getName());
         }
      return fileNameList;
	}
	
	@RequestMapping(value = "/getSolved",method = RequestMethod.GET)
	public JsonVehicleRoutingSolution  getMappedList(String fileName ) {
		 OptimizerOutput vr=new OptimizerOutput();
		 JsonVehicleRoutingSolution sol=new JsonVehicleRoutingSolution();
		 RouteOptimzerProperties rot=new RouteOptimzerProperties();
		 File folder = new File(rot.solved+fileName);
		 sol=vr.readSolutionVRP(folder);
		 return sol;
	}

public static void main(String args[]) throws JsonProcessingException {

		DistanceController dist=new DistanceController();
		List<String> fileNameList=dist.getSolvedList();
		 ObjectMapper map=new ObjectMapper();
		 String p=map.writeValueAsString(fileNameList);
		 System.out.println("fileJson"+p);
		 
		
		 JsonVehicleRoutingSolution sol=dist.getMappedList("cvrp-10cus-2veh-5cap.xml");
		 String q=map.writeValueAsString(sol);
		 System.out.println("solutionList"+q);
}
}
	/*
	JsonFileInput fileIn=new JsonFileInput();
	
	RouteOptimzerProperties routeOptimzerProperties = new RouteOptimzerProperties();
	File locationFile = new File(routeOptimzerProperties.locationFilepath);
	fileIn.setLocationFilePath(routeOptimzerProperties.locationFilepath);
	fileIn.setDepotListSize(1);
	fileIn.setVehicleCount(2);
	fileIn.setVehicleCapacity(5);
	DistanceController dist=new DistanceController();
	try {
		
		 ObjectMapper map=new ObjectMapper();
		 String p=map.writeValueAsString(fileIn);
		 System.out.println(p);
		dist.generateVRPFile(fileIn);
	} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	}
}*/
