package com.qut.routeOptimizerApplication.Bean;

import org.optaplanner.examples.common.domain.AbstractPersistable;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("VrpDepot")

public class Depot extends AbstractPersistable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected Address location;

    public Address getLocation() {
        return location;
    }

    public void setLocation(Address location) {
        this.location = location;
    }

    // ************************************************************************
    // Complex methods
    // ************************************************************************

    /**
     * @param standstill never null
     * @return a positive number, the distance multiplied by 1000 to avoid floating point arithmetic rounding errors
     */
    public long getDistanceTo(Standstill standstill) {
        return location.getDistanceTo(standstill.getLocation());
    }

    @Override
    public String toString() {
        if (location.getName() == null) {
            return super.toString();
        }
        return location.getName();
    }

}
