
package com.qut.routeOptimizerApplication.Controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.qut.routeOptimizerApplication.Bean.AddressListBean;
import com.qut.routeOptimizerApplication.service.OptaResult;
import com.qut.routeOptimizerApplication.service.vrpGenerator.GenerationDistanceType;
import com.qut.routeOptimizerApplication.service.vrpGenerator.VRPGenerator;
import com.qut.routeOptimizerApplication.service.vrpGenerator.VrpType;

@Controller
public class DistanceController {
	@RequestMapping("/")
	public String welcome(Map<String, Object> model) {
		return "welcome";
	}
	@RequestMapping(value = "/save",method = RequestMethod.GET)
	public String generateVRP(@ModelAttribute("locations") AddressListBean addressListBean) throws FileNotFoundException {
		System.out.println("inside getDistanceMatrix");
		 OptaResult res=new OptaResult();
		ModelAndView model=new ModelAndView();
		 File locationFile = new File("C:\\Users\\pretty\\Desktop\\routeOptimizerApplication\\data\\australia.csv");
         VRPGenerator vrpService=new VRPGenerator();
         File inputFile=vrpService.generateVrp(locationFile, 1, 5, 100, GenerationDistanceType.ROAD_DISTANCE_KM, VrpType.BASIC);
        res.OptimizeVrp(inputFile);
		model.addObject("distanceArray");
		return "distanceMatrix";
	}
}
