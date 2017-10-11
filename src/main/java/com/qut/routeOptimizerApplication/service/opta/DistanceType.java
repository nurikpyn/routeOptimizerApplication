package com.qut.routeOptimizerApplication.service.opta;

public enum DistanceType {
	 /**
     * Requires that all {@link Location} instances are of type {@link AirLocation}.
     */
    AIR_DISTANCE,
    /**
     * Requires that all {@link Location} instances are of type {@link RoadLocation}.
     */
    ROAD_DISTANCE,
    /**
     * Requires that all {@link Location} instances are of type {@link RoadSegmentLocation} or {@link HubSegmentLocation}.
     */
    SEGMENTED_ROAD_DISTANCE;
}
