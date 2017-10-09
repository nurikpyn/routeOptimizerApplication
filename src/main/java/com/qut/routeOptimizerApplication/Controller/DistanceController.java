
package com.qut.routeOptimizerApplication.Controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.qut.routeOptimizerApplication.Bean.Address;
import com.qut.routeOptimizerApplication.Bean.AddressListBean;
import com.qut.routeOptimizerApplication.service.DistanceService;
import com.qut.routeOptimizerApplication.service.vrpGenerator.GenerationDistanceType;
import com.qut.routeOptimizerApplication.service.vrpGenerator.VRPService;
import com.qut.routeOptimizerApplication.service.vrpGenerator.VrpType;

@Controller
public class DistanceController {
	@RequestMapping("/")
	public String welcome(Map<String, Object> model) {
		return "welcome";
	}
	@RequestMapping(value = "/save",method = RequestMethod.GET)
	public String getDistanceMatrix(@ModelAttribute("locations") AddressListBean addressListBean) throws FileNotFoundException {
		System.out.println("inside getDistanceMatrix");
		ModelAndView model=new ModelAndView();
		/*DistanceService locationServiceObject=new DistanceService();
		CustomerService customerServiceObject=new CustomerService();
		DepotService depotService=new DepotService();
		VehicleService vehicleService=new VehicleService();
		Depot depot=depotService.getDepotDetails();*/
		//List<Customer> customerList=customerServiceObject.getCustomerList(addressListBean.getLocationList(),1);
		//double[][] distanceList=locationServiceObject.calculateEdgeMatrix(addressListBean.getLocationList());
		//Vehicle vehicle=vehicleService.getDepotDetails(5, depot);
		//vrpService.createVRPFile(customerList, depot, distanceList, vehicle);
		 File locationFile = new File("C:\\Users\\pretty\\Desktop\\routeOptimizerApplication\\src\\main\\resources\\file\\locationList.csv");
         File hubFile = new File("C:\\Users\\pretty\\Desktop\\routeOptimizerApplication\\src\\main\\resources\\file\\depotList.csv");
         DistanceService dist=new DistanceService();
         VRPService vrpService=new VRPService();
         List<Address> locationList=vrpService.readLocationFile(locationFile);
         double[][] distanceList=dist.calculateEdgeMatrix(locationList);
         vrpService.generateVrp(locationFile, hubFile, 5,distanceList, 1, 5, 100, GenerationDistanceType.ROAD_DISTANCE_KM, VrpType.BASIC);
		model.addObject("distanceArray", distanceList);
		return "distanceMatrix";
	}
}
