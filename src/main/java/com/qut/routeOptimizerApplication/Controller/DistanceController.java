
package com.qut.routeOptimizerApplication.Controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qut.routeOptimizerApplication.properties.RouteOptimzerProperties;
import com.qut.routeOptimizerApplication.service.OptimizerOutput;
import com.qut.routeOptimizerApplication.service.opta.common.app.CommonApp;
import com.qut.routeOptimizerApplication.service.opta.vehiclerouting.app.VehicleRoutingApp;
import com.qut.routeOptimizerApplication.service.opta.vehiclerouting.domain.JsonInputFileName;
import com.qut.routeOptimizerApplication.service.opta.vehiclerouting.domain.input.JsonFileInput;
import com.qut.routeOptimizerApplication.service.opta.vehiclerouting.domain.input.JsonManualInput;
import com.qut.routeOptimizerApplication.service.opta.vehiclerouting.domain.ouput.JsonVehicleRoutingSolution;
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
	/*RouteOptimzerProperties routeOptimzerProperties = new RouteOptimzerProperties();
	List<Location> customerList=new ArrayList<Location>();
	System.out.println("value"+jsonInput.getDepot().getLatitude());
	
	long id=0;
	Location location;
	location=new RoadLocation();
	location.setId(id);
	location.setLatitude(jsonInput.getDepot().getLatitude());
	location.setLongitude(jsonInput.getDepot().getLongitude());
	customerList.add(location);
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
      imp.convert(inputFile.getName(), outputFileName);*/
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
	@RequestMapping(value = "/getSolvedFileList",method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody  List<String> getSolvedList( ) {
     List<String> fileNameList=new ArrayList<String>();
     RouteOptimzerProperties rot=new RouteOptimzerProperties();
     File folder = new File(rot.solved);
     File[] listOfFiles = folder.listFiles();
         for (int i = 0; i < listOfFiles.length; i++) {
           fileNameList.add(listOfFiles[i].getName());
         }
         return fileNameList;
	}
	
	@RequestMapping(value = "/getSolved",method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody JsonVehicleRoutingSolution  getMappedList(@RequestBody JsonInputFileName fileName ) {
		 OptimizerOutput vr=new OptimizerOutput();
		 JsonVehicleRoutingSolution sol=new JsonVehicleRoutingSolution();
		 RouteOptimzerProperties rot=new RouteOptimzerProperties();
		
		 File folder = new File(rot.solved+fileName.getFileName());
		 sol=vr.readSolutionVRP(folder);
		 return sol;
	}
	
	public static void main(String args[]) throws JsonProcessingException {
	
	 /*VRPGenerator vrpService=new VRPGenerator();
	 JsonFileInput jsonFileInputBean=new JsonFileInput();

			 RouteOptimzerProperties routeOptimzerProperties = new RouteOptimzerProperties();
		File locationFile = new File(routeOptimzerProperties.locationFilepath);
		File locationFile2 = new File(routeOptimzerProperties.locationFilepath2);
		jsonFileInputBean.setDepotListSize(1);
		jsonFileInputBean.setVehicleCount(7);
		jsonFileInputBean.setVehicleCapacity(20);
     try {
    	// List<Location> locationlist=vrpService.readLocationFile(locationFile);
    		//int customerSize=locationlist.size()-1;
    	    //System.out.println("filePath received"+locationlist.get(0).getLatitude()+"filePath received"+locationlist.get(0).getLongitude());
    		//VehicleRoutingImporter imp=new VehicleRoutingImporter();
		vrpService.generateVrp(locationFile,jsonFileInputBean.getDepotListSize() ,jsonFileInputBean.getVehicleCount(), jsonFileInputBean.getVehicleCapacity(), GenerationDistanceType.ROAD_DISTANCE_KM, VrpType.BASIC);
		vrpService.generateVrp(locationFile2,jsonFileInputBean.getDepotListSize() ,jsonFileInputBean.getVehicleCount(), jsonFileInputBean.getVehicleCapacity(), GenerationDistanceType.ROAD_DISTANCE_KM, VrpType.BASIC);
		
		
	      String outputFileName="cvrp-"+customerSize+"cus-"+jsonFileInputBean.getVehicleCount()+"veh-"+jsonFileInputBean.getVehicleCapacity()+"cap.xml";
	      imp.convert(inputFile.getName(), outputFileName);
	     
	    
	      VehicleRoutingApp vehApp=new VehicleRoutingApp();
	      CommonApp.prepareSwingEnvironment();
	      vehApp.init();
     } catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}*/
	 


	
	JsonFileInput fileIn=new JsonFileInput();
	
	RouteOptimzerProperties routeOptimzerProperties = new RouteOptimzerProperties();
	File locationFile = new File(routeOptimzerProperties.locationFilepath);
	fileIn.setLocationFilePath(routeOptimzerProperties.locationFilepath);
	fileIn.setDepotListSize(1);
	fileIn.setVehicleCount(5);
	fileIn.setVehicleCapacity(25);
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
	CommonApp.prepareSwingEnvironment();
    new VehicleRoutingApp().init(); 
	}
}
