package com.alert.data;

public class Data {

	private String triggerCategory; // Anything such that category + id is
									// unique among all conditions
	private String triggerId; // e.g. Metric|Metric-12

	public Data(String triggerCategory, String triggerId) {
		super();
		this.triggerCategory = triggerCategory;
		this.triggerId = triggerId;
	}

	public String getTriggerCategory() {
		return triggerCategory;
	}

	public void setTriggerCategory(String triggerCategory) {
		this.triggerCategory = triggerCategory;
	}

	public String getTriggerId() {
		return triggerId;
	}

	public void setTriggerId(String triggerId) {
		this.triggerId = triggerId;
	}


}
