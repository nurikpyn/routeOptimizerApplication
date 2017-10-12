package com.qut.routeOptimizerApplication.service.opta.vrpGenerator;

public enum VrpType {
	 BASIC,
	    TIMEWINDOWED;

	    public String getFileSuffix() {
	        switch (this) {
	            case BASIC:
	                return "";
	            case TIMEWINDOWED:
	                return "-tw";
	            default:
	                throw new IllegalArgumentException("Unsupported vrpType (" + this + ").");
	        }
	    }

	    public String getDirName(boolean multidepot) {
	        switch (this) {
	            case BASIC:
	                return multidepot ? "multidepot" : "basic";
	            case TIMEWINDOWED:
	                return multidepot ? "multidepot-timewindowed" : "timewindowed";
	            default:
	                throw new IllegalArgumentException("Unsupported vrpType (" + this + ").");
	        }
	    }

	    public String getHeaderType() {
	        switch (this) {
	            case BASIC:
	                return "CVRP";
	            case TIMEWINDOWED:
	                return "CVRPTW";
	            default:
	                throw new IllegalArgumentException("Unsupported vrpType (" + this + ").");
	        }
	    }
}
