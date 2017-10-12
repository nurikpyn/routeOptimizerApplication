package com.qut.routeOptimizerApplication.service.opta.vrpGenerator;

public enum DataSource {
	AUSTRALIA,
    USA,
    UK_TEAMS;

    public String getOsmPath() {
        switch (this) {
            case AUSTRALIA:
                return "osmMap/australia.pbf";
            case USA:
                return null;
            case UK_TEAMS:
                return null;
            default:
                throw new IllegalArgumentException("Unsupported dataSource (" + this + ").");
        }
    }

    public String getDirName() {
        switch (this) {
            case AUSTRALIA:
                return "australia";
            case USA:
                return "usa";
            case UK_TEAMS:
                return "uk-teams";
            default:
                throw new IllegalArgumentException("Unsupported dataSource (" + this + ").");
        }
    }

}
