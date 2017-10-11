/*
 * Copyright 2012 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.qut.routeOptimizerApplication.service.opta;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FilenameUtils;

import com.qut.routeOptimizerApplication.Bean.Address;
import com.qut.routeOptimizerApplication.Bean.Customer;
import com.qut.routeOptimizerApplication.Bean.Depot;
import com.qut.routeOptimizerApplication.Bean.RoadLocation;
import com.qut.routeOptimizerApplication.Bean.Vehicle;
import com.qut.routeOptimizerApplication.Bean.VehicleRoutingSolution;

public class VehicleRoutingImporter extends AbstractTxtSolutionImporter<VehicleRoutingSolution> {

    public VehicleRoutingImporter() {
        super(new VehicleRoutingDao());
    }

    public VehicleRoutingImporter(boolean withoutDao) {
        super(withoutDao);
    }

    @Override
    public String getInputFileSuffix() {
        return "vrp";
    }

    @Override
    public TxtInputBuilder<VehicleRoutingSolution> createTxtInputBuilder() {
        return new VehicleRoutingInputBuilder();
    }

    public static class VehicleRoutingInputBuilder extends TxtInputBuilder<VehicleRoutingSolution> {

        private VehicleRoutingSolution solution;

        private boolean timewindowed;
        private int customerListSize;
        private int vehicleListSize;
        private int capacity;
        private Map<Long, Address> locationMap;
        private List<Depot> depotList;

        @Override
        public VehicleRoutingSolution readSolution() throws IOException {
            String firstLine = readStringValue();
           System.out.println("solution");
            if (firstLine.matches("\\s*NAME\\s*:.*")) {
                // Might be replaced by TimeWindowedVehicleRoutingSolution later on
                solution = new VehicleRoutingSolution();
                solution.setId(0L);
                solution.setName(removePrefixSuffixFromLine(firstLine, "\\s*NAME\\s*:", ""));
                readVrpWebFormat();
            } else if (splitBySpacesOrTabs(firstLine).length == 3) {
                timewindowed = false;
                solution = new VehicleRoutingSolution();
                solution.setId(0L);
                solution.setName(FilenameUtils.getBaseName(inputFile.getName()));
                String[] tokens = splitBySpacesOrTabs(firstLine, 3);
                customerListSize = Integer.parseInt(tokens[0]);
                vehicleListSize = Integer.parseInt(tokens[1]);
                capacity = Integer.parseInt(tokens[2]);
            }
            BigInteger possibleSolutionSize
                    = factorial(customerListSize + vehicleListSize - 1).divide(factorial(vehicleListSize - 1));
            System.out.println("VehicleRoutingSolution {"+ getInputId()+"} has {"+ solution.getDepotList().size()+"} depots, {"+solution.getVehicleList().size()+"} vehicles and {"+solution.getCustomerList().size()+"} customers with a search space of {"+getFlooredPossibleSolutionSize(possibleSolutionSize)+"}");
            return solution;
        }

        public void readVrpWebFormat() throws IOException {
            readVrpWebHeaders();
            readVrpWebAddressList();
            readVrpWebCustomerList();
            readVrpWebDepotList();
            createVrpWebVehicleList();
            readConstantLine("EOF");
        }

        private void readVrpWebHeaders() throws IOException {
            skipOptionalConstantLines("COMMENT *:.*");
            String vrpType = readStringValue("TYPE *:");
            switch (vrpType) {
                case "CVRP":
                    timewindowed = false;
                    break;
                default:
                    throw new IllegalArgumentException("The vrpType (" + vrpType + ") is not supported.");
            }
            customerListSize = readIntegerValue("DIMENSION *:");
            String edgeWeightType = readStringValue("EDGE_WEIGHT_TYPE *:");
            if (edgeWeightType.equalsIgnoreCase("EUC_2D")) {
                solution.setDistanceType(DistanceType.AIR_DISTANCE);
            } else if (edgeWeightType.equalsIgnoreCase("EXPLICIT")) {
                solution.setDistanceType(DistanceType.ROAD_DISTANCE);
                String edgeWeightFormat = readStringValue("EDGE_WEIGHT_FORMAT *:");
                if (!edgeWeightFormat.equalsIgnoreCase("FULL_MATRIX")) {
                    throw new IllegalArgumentException("The edgeWeightFormat (" + edgeWeightFormat + ") is not supported.");
                }
            } else if (edgeWeightType.equalsIgnoreCase("SEGMENTED_EXPLICIT")) {
                solution.setDistanceType(DistanceType.SEGMENTED_ROAD_DISTANCE);
                String edgeWeightFormat = readStringValue("EDGE_WEIGHT_FORMAT *:");
                if (!edgeWeightFormat.equalsIgnoreCase("HUB_AND_NEARBY_MATRIX")) {
                    throw new IllegalArgumentException("The edgeWeightFormat (" + edgeWeightFormat + ") is not supported.");
                }
            } else {
                throw new IllegalArgumentException("The edgeWeightType (" + edgeWeightType + ") is not supported.");
            }
            solution.setDistanceUnitOfMeasurement(readOptionalStringValue("EDGE_WEIGHT_UNIT_OF_MEASUREMENT *:", "distance"));
            capacity = readIntegerValue("CAPACITY *:");
        }

        private void readVrpWebAddressList() throws IOException {
            DistanceType distanceType = solution.getDistanceType();
            locationMap = new LinkedHashMap<>(customerListSize);
            List<Address> customerAddressList = new ArrayList<>(customerListSize);
            readConstantLine("NODE_COORD_SECTION");
            for (int i = 0; i < customerListSize; i++) {
                String line = bufferedReader.readLine();
                String[] lineTokens = splitBySpacesOrTabs(line.trim(), 3, 4);
                Address location;
                switch (distanceType) {
                    case ROAD_DISTANCE:
                        location = new RoadLocation();
                        break;
                    default:
                        throw new IllegalStateException("The distanceType (" + distanceType
                                + ") is not implemented.");

                }
                location=new RoadLocation();
                location.setId(Integer.parseInt(lineTokens[0]));
                location.setLatitude(Double.parseDouble(lineTokens[1]));
                location.setLongitude(Double.parseDouble(lineTokens[2]));
                if (lineTokens.length >= 4) {
                    location.setName(lineTokens[3]);
                }
                customerAddressList.add(location);
                System.out.println(location.getId()+"location"+location.getLatitude()+location.getLongitude());
                locationMap.put((long)location.getId(), location);
            }
            if (distanceType == DistanceType.ROAD_DISTANCE) {
                readConstantLine("EDGE_WEIGHT_SECTION");
                for (int i = 0; i < customerListSize; i++) {
                    RoadLocation location = (RoadLocation) customerAddressList.get(i);
                    Map<RoadLocation, Double> travelDistanceMap = new LinkedHashMap<>(customerListSize);
                    String line = bufferedReader.readLine();
                    String[] lineTokens = splitBySpacesOrTabs(line.trim(), customerListSize);
                    for (int j = 0; j < customerListSize; j++) {
                        double travelDistance = Double.parseDouble(lineTokens[j]);
                        if (i == j) {
                            if (travelDistance != 0.0) {
                                throw new IllegalStateException("The travelDistance (" + travelDistance
                                        + ") should be zero.");
                            }
                        } else {
                        	RoadLocation otherAddress = (RoadLocation) customerAddressList.get(j);
                            travelDistanceMap.put(otherAddress, travelDistance);
                        }
                    }
                    location.setTravelDistanceMap(travelDistanceMap);
                }
            }
            List<Address> locationList;
            locationList = customerAddressList;
        solution.setAddressList(locationList); 
        }

        private void readVrpWebCustomerList() throws IOException {
            readConstantLine("DEMAND_SECTION");
            depotList = new ArrayList<>(customerListSize);
            List<Customer> customerList = new ArrayList<>(customerListSize);
            for (int i = 0; i < customerListSize; i++) {
                String line = bufferedReader.readLine();
                String[] lineTokens = splitBySpacesOrTabs(line.trim(), timewindowed ? 5 : 2);
                long id = Long.parseLong(lineTokens[0]);
                int demand = Integer.parseInt(lineTokens[1]);
                // Depots have no demand
                if (demand == 0) {
                    Depot depot =  new Depot();
                    depot.setId(id);
                    System.out.println("the size"+locationMap.size());
                    Address location = locationMap.get(id);
                    if (location == null) {
                        throw new IllegalArgumentException("The depot with id (" + id
                                + ") has no location (" + location + ").");
                    }
                    depot.setLocation(location);
                    depotList.add(depot);
                } else {
                    Customer customer =new Customer();
                    customer.setId(id);
                    Address location = locationMap.get(id);
                    if (location == null) {
                        throw new IllegalArgumentException("The customer with id (" + id
                                + ") has no location (" + location + ").");
                    }
                    customer.setAddress(location);
                    customer.setDemand(demand);
                    // Notice that we leave the PlanningVariable properties on null
                    customerList.add(customer);
                }
            }
            solution.setCustomerList(customerList);
            solution.setDepotList(depotList);
        }

        private void readVrpWebDepotList() throws IOException {
            readConstantLine("DEPOT_SECTION");
            int depotCount = 0;
            long id = readLongValue();
            while (id != -1) {
                depotCount++;
                id = readLongValue();
            }
            if (depotCount != depotList.size()) {
                throw new IllegalStateException("The number of demands with 0 demand (" + depotList.size()
                        + ") differs from the number of depots (" + depotCount + ").");
            }
        }

        private void createVrpWebVehicleList() throws IOException {
            String inputFileName = inputFile.getName();
            if (inputFileName.toLowerCase().startsWith("tutorial")) {
                vehicleListSize = readIntegerValue("VEHICLES *:");
            } else {
                String inputFileNameRegex = "^.+\\-k(\\d+)\\.vrp$";
                if (!inputFileName.matches(inputFileNameRegex)) {
                    throw new IllegalArgumentException("The inputFileName (" + inputFileName
                            + ") does not match the inputFileNameRegex (" + inputFileNameRegex + ").");
                }
                String vehicleListSizeString = inputFileName.replaceAll(inputFileNameRegex, "$1");
                try {
                    vehicleListSize = Integer.parseInt(vehicleListSizeString);
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("The inputFileName (" + inputFileName
                            + ") has a vehicleListSizeString (" + vehicleListSizeString + ") that is not a number.", e);
                }
            }
            createVehicleList();
        }

        private void createVehicleList() {
            List<Vehicle> vehicleList = new ArrayList<>(vehicleListSize);
            long id =  0;
            for (int i = 0; i < vehicleListSize; i++) {
                Vehicle vehicle = new Vehicle();
                vehicle.setId(id);
                id++;
                vehicle.setCapacity(capacity);
                // Round robin the vehicles to a depot if there are multiple depots
                vehicle.setDepot(depotList.get(i % depotList.size()));
                vehicleList.add(vehicle);
            }
            solution.setVehicleList(vehicleList);
        }
    }
}
