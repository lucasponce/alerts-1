package org.hawkular.alerts.sample;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import org.hawkular.alerts.condition.Alert;
import org.hawkular.alerts.condition.AvailabilityCondition;
import org.hawkular.alerts.condition.CompareCondition;
import org.hawkular.alerts.condition.StringCondition;
import org.hawkular.alerts.condition.ThresholdCondition;
import org.hawkular.alerts.condition.ThresholdCondition.Operator;
import org.hawkular.alerts.condition.ThresholdRangeCondition;
import org.hawkular.alerts.data.Availability;
import org.hawkular.alerts.data.Availability.AvailabilityType;
import org.hawkular.alerts.data.Data;
import org.hawkular.alerts.data.NumericData;
import org.hawkular.alerts.data.StringData;
import org.hawkular.alerts.trigger.Trigger;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.ObjectFilter;

/**
 * This is a sample class to launch a rule.
 */
public class AlertTest {

	public static final Collection<Alert> run(Set<Data> datums) {
		KieSession kSession = null;
		Collection<Alert> result = null;

		try {
			// load up the knowledge base
			KieServices ks = KieServices.Factory.get();
			KieContainer kContainer = ks.getKieClasspathContainer();
			kSession = kContainer.newKieSession("ksession-rules");

			// go !
			Trigger t1 = new Trigger("trigger-1", "NumericData-01-low");
			ThresholdCondition t1c1 = new ThresholdCondition("trigger-1",
					"NumericData-01", 1, 1, Operator.LT, 5.0);

			Trigger t2 = new Trigger("trigger-2", "NumericData-01-02-high");
			ThresholdCondition t2c1 = new ThresholdCondition("trigger-2",
					"NumericData-01", 2, 1, Operator.GTE, 15.0);
			ThresholdCondition t2c2 = new ThresholdCondition("trigger-2",
					"NumericData-02", 2, 2, Operator.GTE, 15.0);

			Trigger t3 = new Trigger("trigger-3", "NumericData-03-range");
			ThresholdRangeCondition t3c1 = new ThresholdRangeCondition(
					"trigger-3", "NumericData-03", 1, 1,
					ThresholdRangeCondition.Operator.INCLUSIVE,
					ThresholdRangeCondition.Operator.INCLUSIVE, 10.0, 15.0,
					true);

			Trigger t4 = new Trigger("trigger-4", "CompareData-01-d1-lthalf-d2");
			CompareCondition t4c1 = new CompareCondition("trigger-4", 1, 1,
					"NumericData-01", CompareCondition.Operator.LT, 0.5,
					"NumericData-02");

			Trigger t5 = new Trigger("trigger-5", "StringData-01-starts");
			StringCondition t5c1 = new StringCondition("trigger-5",
					"StringData-01", 1, 1,
					StringCondition.Operator.STARTS_WITH, "Fred", false);

			Trigger t6 = new Trigger("trigger-6", "Availability-01-NOT-UP");
			AvailabilityCondition t6c1 = new AvailabilityCondition("trigger-6",
					"Availability-01", 1, 1,
					AvailabilityCondition.Operator.NOT_UP);

			kSession.insert(t1);
			kSession.insert(t1c1);

			kSession.insert(t2);
			kSession.insert(t2c1);
			kSession.insert(t2c2);

			kSession.insert(t3);
			kSession.insert(t3c1);

			kSession.insert(t4);
			kSession.insert(t4c1);

			kSession.insert(t5);
			kSession.insert(t5c1);

			kSession.insert(t6);
			kSession.insert(t6c1);

			for (Data d : datums) {
				kSession.insert(d);
				System.out.println(d);
			}

			kSession.fireAllRules();

			result = (Collection<Alert>) kSession
					.getObjects(new ObjectFilter() {
						public boolean accept(Object o) {
							return o instanceof Alert;
						}
					});

		} catch (Throwable t) {
			t.printStackTrace();
		} finally {
			if (null != kSession) {
				kSession.dispose();
			}
		}

		return result == null ? new ArrayList<Alert>() : result;
	}

	public static final void main(String[] args) {
		try {

			Set<Data> datums = new HashSet<Data>();
			Random r = new Random();
			for (int i = 0; i < 10; ++i) {
				NumericData m = new NumericData("NumericData-01",
						r.nextDouble() * 20);
				datums.add(m);
			}
			for (int i = 0; i < 10; ++i) {
				NumericData m = new NumericData("NumericData-02",
						r.nextDouble() * 20);
				datums.add(m);
			}
			for (int i = 0; i < 10; ++i) {
				NumericData m = new NumericData("NumericData-03",
						r.nextDouble() * 20);
				datums.add(m);
			}

			datums.add(new StringData("StringData-01", "Barney"));
			datums.add(new StringData("StringData-01", "Fred and Barney"));
			datums.add(new StringData("StringData-02", "Fred Flintstone"));

			datums.add(new Availability("Availability-01", AvailabilityType.UP));
			datums.add(new Availability("Availability-01", AvailabilityType.UP));
			datums.add(new Availability("Availability-01",
					AvailabilityType.UNAVAILABLE));

			run(datums);

		} catch (Throwable t) {
			t.printStackTrace();
		}
	}

}
