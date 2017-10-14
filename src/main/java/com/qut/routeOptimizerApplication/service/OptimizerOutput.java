package com.qut.routeOptimizerApplication.service;

import java.awt.Color;
import java.io.File;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import org.optaplanner.core.api.score.buildin.hardsoftlong.HardSoftLongScore;
import org.optaplanner.swing.impl.TangoColorFactory;

import com.qut.routeOptimizerApplication.service.opta.vehiclerouting.domain.Customer;
import com.qut.routeOptimizerApplication.service.opta.vehiclerouting.domain.JsonCustomer;
import com.qut.routeOptimizerApplication.service.opta.vehiclerouting.domain.Vehicle;
import com.qut.routeOptimizerApplication.service.opta.vehiclerouting.domain.VehicleRoutingSolution;
import com.qut.routeOptimizerApplication.service.opta.vehiclerouting.domain.location.Location;
import com.qut.routeOptimizerApplication.service.opta.vehiclerouting.domain.ouput.JsonVehicleRoute;
import com.qut.routeOptimizerApplication.service.opta.vehiclerouting.domain.ouput.JsonVehicleRoutingSolution;
import com.qut.routeOptimizerApplication.service.opta.vehiclerouting.persistence.VehicleRoutingImporter;

public class OptimizerOutput {
	 /*private static final NumberFormat NUMBER_FORMAT = new DecimalFormat("#,##0.00");
	 
	  public VehicleRoutingSolution inputFile(File outputFile) {
		  
		  VehicleRoutingSolution solution=(VehicleRoutingSolution) new VehicleRoutingImporter(true)
                  .readSolution(outputFile);
		  return solution;
	    }
	public JsonVehicleRoutingSolution convertToJsonVehicleRoutingSolution(VehicleRoutingSolution solution) {
        JsonVehicleRoutingSolution jsonSolution = new JsonVehicleRoutingSolution();
        jsonSolution.setName(solution.getName());
        List<JsonCustomer> jsonCustomerList = new ArrayList<>(solution.getCustomerList().size());
        for (Customer customer : solution.getCustomerList()) {
            Location customerLocation = customer.getLocation();
            jsonCustomerList.add(new JsonCustomer(customerLocation.getName(),
                    customerLocation.getLatitude(), customerLocation.getLongitude(), customer.getDemand()));
        }
        jsonSolution.setCustomerList(jsonCustomerList);
        List<JsonVehicleRoute> jsonVehicleRouteList = new ArrayList<>(solution.getVehicleList().size());
        TangoColorFactory tangoColorFactory = new TangoColorFactory();
        for (Vehicle vehicle : solution.getVehicleList()) {
            JsonVehicleRoute jsonVehicleRoute = new JsonVehicleRoute();
            Location depotLocation = vehicle.getDepot().getLocation();
            jsonVehicleRoute.setDepotLocationName(depotLocation.getName());
            jsonVehicleRoute.setDepotLatitude(depotLocation.getLatitude());
            jsonVehicleRoute.setDepotLongitude(depotLocation.getLongitude());
            jsonVehicleRoute.setCapacity(vehicle.getCapacity());
            Color color = tangoColorFactory.pickColor(vehicle);
            jsonVehicleRoute.setHexColor(
                    String.format("#%02x%02x%02x", color.getRed(), color.getGreen(), color.getBlue()));
            Customer customer = vehicle.getNextCustomer();
            int demandTotal = 0;
            List<JsonCustomer> jsonVehicleCustomerList = new ArrayList<>();
            while (customer != null) {
                Location customerLocation = customer.getLocation();
                demandTotal += customer.getDemand();
                jsonVehicleCustomerList.add(new JsonCustomer(customerLocation.getName(),
                        customerLocation.getLatitude(), customerLocation.getLongitude(), customer.getDemand()));
                customer = customer.getNextCustomer();
            }
            jsonVehicleRoute.setDemandTotal(demandTotal);
            jsonVehicleRoute.setCustomerList(jsonVehicleCustomerList);
            jsonVehicleRouteList.add(jsonVehicleRoute);
        }
        jsonSolution.setVehicleRouteList(jsonVehicleRouteList);
        HardSoftLongScore score = solution.getScore();
        jsonSolution.setFeasible(score != null && score.isFeasible());
        jsonSolution.setDistance(solution.getDistanceString(NUMBER_FORMAT));
        return jsonSolution;
    }
*/
}
