
package com.qut.routeOptimizerApplication.Controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.qut.routeOptimizerApplication.Bean.UploadInvoiceBean;
import com.qut.routeOptimizerApplication.service.DistanceService;

@Controller
public class DistanceController {
	@RequestMapping("/")
	public String welcome(Map<String, Object> model) {
		System.out.println("inside welcomr");
		return "welcome";
	}
	@RequestMapping(value = "/save",method = RequestMethod.GET)
	public String getDistanceMatrix(@ModelAttribute("locations") UploadInvoiceBean uploadInvoiceBean) {
		ModelAndView model=new ModelAndView();
		DistanceService locationServiceObject=new DistanceService();
		double[][] distanceList=locationServiceObject.calculateDistanceMatrix(uploadInvoiceBean);
		model.addObject("distanceArray", distanceList);
		return "distanceMatrix";
	}
}
