package com.qut.routeOptimizerApplication.Bean;

import org.optaplanner.core.api.domain.variable.InverseRelationShadowVariable;

public interface Standstill {
	 Address getLocation();

	    /**
	     * @return sometimes null
	     */
	    Vehicle getVehicle();

	    /**
	     * @return sometimes null
	     */
	    @InverseRelationShadowVariable(sourceVariableName = "previousStandstill")
	    Customer getNextCustomer();
	    void setNextCustomer(Customer nextCustomer);

	}

