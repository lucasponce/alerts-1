package org.hawkular.alerts.data;

public class StringData extends Data {

	private String value;

	public StringData(String id, String value) {
        super(id);

        this.value = value;
    }


	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
    public String toString() {
		return "StringData [value=" + value + ", " + getId() + "]";
    }

}
