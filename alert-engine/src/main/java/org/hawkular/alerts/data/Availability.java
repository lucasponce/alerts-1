package org.hawkular.alerts.data;

public class Availability extends Data {

    public enum AvailabilityType {
        UP, DOWN, UNAVAILABLE
    };

    public Availability(String id, AvailabilityType value) {
        super(id, value);
    }
}
