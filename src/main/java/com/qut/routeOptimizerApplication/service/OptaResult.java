package com.qut.routeOptimizerApplication.service;

import java.io.File;

import com.qut.routeOptimizerApplication.Bean.VehicleRoutingSolution;
import com.qut.routeOptimizerApplication.service.opta.VehicleRoutingImporter;

public class OptaResult {
public void OptimizeVrp(File inputFile) {
	 VehicleRoutingImporter importer = new VehicleRoutingImporter();
	 VehicleRoutingSolution res= importer.readSolution(inputFile);
	 System.out.println("score"+res.getScore());
}
}
