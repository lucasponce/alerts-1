package org.hawkular.alerts.data;

public class Availability extends Data {

    public enum AvailabilityType {
        RED, YELLOW, GREEN, GREY
    };

    private AvailabilityType value;

    public Availability(String id, AvailabilityType value) {
        super(id);

        this.value = value;
    }

    public AvailabilityType getValue() {
        return value;
    }

    public void setValue(AvailabilityType value) {
        this.value = value;
    }


    @Override
    public String toString() {
        return "Availability [value=" + value + ", " + getId() + "]";
    }

}
