package com.qut.routeOptimizerApplication.service.opta;

import com.qut.routeOptimizerApplication.Bean.VehicleRoutingSolution;
public class VehicleRoutingDao extends XStreamSolutionDao<VehicleRoutingSolution> {

    public VehicleRoutingDao() {
        super("vehiclerouting", VehicleRoutingSolution.class);
    }
}
