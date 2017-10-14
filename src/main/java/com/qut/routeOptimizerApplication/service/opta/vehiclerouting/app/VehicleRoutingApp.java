/*
` * Copyright 2012 Red Hat, Inc. and/or its affiliates.
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

package com.qut.routeOptimizerApplication.service.opta.vehiclerouting.app;

import com.qut.routeOptimizerApplication.service.opta.common.app.CommonApp;
import com.qut.routeOptimizerApplication.service.opta.common.persistence.AbstractSolutionImporter;
import com.qut.routeOptimizerApplication.service.opta.common.persistence.SolutionDao;
import com.qut.routeOptimizerApplication.service.opta.vehiclerouting.domain.VehicleRoutingSolution;
import com.qut.routeOptimizerApplication.service.opta.vehiclerouting.persistence.VehicleRoutingDao;
import com.qut.routeOptimizerApplication.service.opta.vehiclerouting.persistence.VehicleRoutingImporter;
import com.qut.routeOptimizerApplication.service.opta.vehiclerouting.swingui.VehicleRoutingPanel;

public class VehicleRoutingApp extends CommonApp<VehicleRoutingSolution> {

    public static final String SOLVER_CONFIG
            = "vehiclerouting/solver/vehicleRoutingSolverConfig.xml";

   /* public static void main(String[] args)	 {
        prepareSwingEnvironment();
        new VehicleRoutingApp().init();
    }*/

    public VehicleRoutingApp() {
        super("Vehicle routing",
                "Official competition name: Capacitated vehicle routing problem (CVRP), " +
                        "optionally with time windows (CVRPTW)\n\n" +
                        "Pick up all items of all customers with a few vehicles.\n\n" +
                        "Find the shortest route possible.\n" +
                        "Do not overload the capacity of the vehicles.\n" +
                        "Arrive within the time window of each customer.",
                SOLVER_CONFIG,
                VehicleRoutingPanel.LOGO_PATH);
    }

    @Override
    protected VehicleRoutingPanel createSolutionPanel() {
        return new VehicleRoutingPanel();
    }

    @Override
    protected SolutionDao createSolutionDao() {
        return new VehicleRoutingDao();
    }

    @Override
    protected AbstractSolutionImporter[] createSolutionImporters() {
        return new AbstractSolutionImporter[]{
                new VehicleRoutingImporter()
        };
    }

}
