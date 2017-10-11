package com.qut.routeOptimizerApplication.service.vrpGenerator;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.tomcat.util.http.fileupload.IOUtils;

import com.qut.routeOptimizerApplication.Bean.Address;
import com.qut.routeOptimizerApplication.Bean.RoadLocation;
import com.qut.routeOptimizerApplication.service.DistanceService;

public class VRPGenerator {

	@SuppressWarnings("resource")
	public File generateVrp(File locationFile, int depotListSize, int vehicleListSize,
			int capacity, GenerationDistanceType distanceType, VrpType vrpType) throws FileNotFoundException {
		VRPGenerator vrpService = new VRPGenerator();
		List<Address> locationList = vrpService.readLocationFile(locationFile);
		int locationListSize=locationList.size();
		DistanceService dist = new DistanceService();
		double[][] distanceList = dist.calculateEdgeMatrix(locationList);
		String name = locationFile.getName().replaceAll("\\-\\d+\\.csv", "") + distanceType.getFileSuffix()
				+ vrpType.getFileSuffix() + (depotListSize != 1 ? "-d" + depotListSize : "") + "-n" + locationListSize
				+ "-k" + vehicleListSize;
		File vrpOutputFile = createVrpOutputFile(name, distanceType, vrpType, depotListSize != 1);
		BufferedWriter vrpWriter = null;
		try {
			vrpWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(vrpOutputFile), "UTF-8"));
			vrpWriter = writeHeaders(vrpWriter, locationListSize, capacity, distanceType, vrpType, name);
			writeNodeCoordSection(vrpWriter, locationList);
			writeEdgeWeightSection(vrpWriter, distanceType, distanceList, locationList.size());
			writeDemandSection(vrpWriter, locationListSize, depotListSize, vehicleListSize, capacity, locationList,
					vrpType);
			writeDepotSection(vrpWriter, locationList);
		} catch (IOException e) {
			throw new IllegalArgumentException("Could not read the locationFile (" + locationFile.getName()
					+ ") or write the vrpOutputFile (" + vrpOutputFile.getName() + ").", e);
		} finally {
			IOUtils.closeQuietly(vrpWriter);
		}
		System.out.println("Generated: {}" + vrpOutputFile);
		return vrpOutputFile;
	}

	public List<Address> readLocationFile(File locationFile) throws FileNotFoundException {
		List<Address> locationList = new ArrayList<Address>(3000);
		BufferedReader bufferedReader = null;
		int id = 0;
		RoadLocation location=new RoadLocation();
		try {
			bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(locationFile), "UTF-8"));
			for (String line = bufferedReader.readLine(); line != null; line = bufferedReader.readLine()) {
				String[] tokens = line.split(";");
				if (tokens.length != 3) {
					throw new IllegalArgumentException(
							"The line (" + line + ") does not have 4 tokens (" + tokens.length + ").");
				}
				
				location.setId(id);
				id++;
				location.setLatitude(Double.parseDouble(tokens[1]));
				location.setLongitude(Double.parseDouble(tokens[2]));
				locationList.add(location);
			}
		} catch (IOException e) {
			throw new IllegalArgumentException("Could not read the locationFile (" + locationFile + ").", e);
		} finally {
			IOUtils.closeQuietly(bufferedReader);
		}
		System.out.println("Read {} cities." + locationList.size());
		return locationList;
	}

	private File createVrpOutputFile(String name, GenerationDistanceType distanceType, VrpType vrpType,
			boolean multidepot) {
		 String dataSourceDir = "C:\\Users\\pretty\\Desktop\\routeOptimizerApplication\\data\\vehiclerouting\\import\\";
		 File vrpOutputFile = new File(dataSourceDir
	                + "/" + name + ".vrp");
	        if (!vrpOutputFile.getParentFile().exists()) {
	            throw new IllegalArgumentException("The vrpOutputFile parent directory (" + vrpOutputFile.getParentFile()
	                    + ") does not exist.");
	        }
	        return vrpOutputFile;
	}

	private BufferedWriter writeHeaders(BufferedWriter vrpWriter, int locationListSize, int capacity,
			GenerationDistanceType distanceType, VrpType vrpType, String name) throws IOException {
		vrpWriter.write("NAME: " + name + "\n");
		vrpWriter.write("TYPE: " + vrpType.getHeaderType() + "\n");
		vrpWriter.write("DIMENSION: " + locationListSize + "\n");
		if (distanceType.isRoad()) {
			if (distanceType.isSegmented()) {
				vrpWriter.write("EDGE_WEIGHT_TYPE: SEGMENTED_EXPLICIT\n");
				vrpWriter.write("EDGE_WEIGHT_FORMAT: HUB_AND_NEARBY_MATRIX\n");
			} else {
				vrpWriter.write("EDGE_WEIGHT_TYPE: EXPLICIT\n");
				vrpWriter.write("EDGE_WEIGHT_FORMAT: FULL_MATRIX\n");
			}
			vrpWriter.write("EDGE_WEIGHT_UNIT_OF_MEASUREMENT: " + distanceType.getUnitOfMeasurement() + "\n");
		} else {
			vrpWriter.write("EDGE_WEIGHT_TYPE: EUC_2D\n");
		}
		vrpWriter.write("CAPACITY: " + capacity + "\n");
		return vrpWriter;
	}

	private void writeNodeCoordSection(BufferedWriter vrpWriter, List<Address> locationList) throws IOException {
		vrpWriter.write("NODE_COORD_SECTION\n");
		for (Address location : locationList) {
			vrpWriter.write(location.getId() + " " + location.getLatitude() + " " + location.getLongitude()
					+ (location.getName() != null ? " " + location.getName().replaceAll(" ", "_") : "") + "\n");
		}
	}

	private void writeEdgeWeightSection(BufferedWriter vrpWriter, GenerationDistanceType distanceType,
			double[][] distanceList, int locationListSize) throws IOException {
		if (distanceType.isRoad()) {
			DecimalFormat distanceFormat = new DecimalFormat("0.000");
			if (!distanceType.isSegmented()) {
				vrpWriter.write("EDGE_WEIGHT_SECTION\n");
				for (int i = 0; i < locationListSize; i++) {
					for (int j = 0; j < locationListSize; j++) {
						double distance = distanceList[i][j];

						vrpWriter.write(distanceFormat.format(distance) + " ");
					}

					vrpWriter.write("\n");
				}
			}
		}
	}

	private void writeDemandSection(BufferedWriter vrpWriter, int locationListSize, int depotListSize,
			int vehicleListSize, int capacity, List<Address> locationList, VrpType vrpType) throws IOException {
		vrpWriter.append("DEMAND_SECTION\n");
		int maximumDemand = 1;
		int i = 0;
		Random random = new Random(37);
		for (Address location : locationList) {
			String line;
			if (i < depotListSize) {
				line = location.getId() + " 0";
			} else {
				line = location.getId() + " " + (random.nextInt(maximumDemand) + 1);
			}
			vrpWriter.append(line).append("\n");
			i++;
		}
	}

	private void writeDepotSection(BufferedWriter vrpWriter, List<Address> locationList) throws IOException {
		vrpWriter.append("DEPOT_SECTION\n");
		Address location = locationList.get(0);
		vrpWriter.append(Integer.toString(location.getId()));
		vrpWriter.append("\n");
		vrpWriter.append("-1\n");
		vrpWriter.append("EOF\n");
	}

}