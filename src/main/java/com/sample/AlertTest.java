package com.sample;

import java.util.Random;

import com.alert.condition.ThresholdCondition;
import com.alert.condition.ThresholdCondition.Operator;
import com.alert.data.Metric;
import com.alert.trigger.Trigger;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

/**
 * This is a sample class to launch a rule.
 */
public class AlertTest {

    public static final void main(String[] args) {
        try {
            // load up the knowledge base
            KieServices ks = KieServices.Factory.get();
            KieContainer kContainer = ks.getKieClasspathContainer();
            KieSession kSession = kContainer.newKieSession("ksession-rules");

            // go !
            Trigger t1 = new Trigger("trigger-1", "metric-01-low");
            ThresholdCondition cs1c1 = new ThresholdCondition("trigger-1", "Metric-01", 1, 1, Operator.LT, 5.0);

            Trigger t2 = new Trigger("trigger-2", "metric-01-02-high");
            ThresholdCondition cs2c1 = new ThresholdCondition("trigger-2", "Metric-01", 2, 1, Operator.GTE, 15.0);
            ThresholdCondition cs2c2 = new ThresholdCondition("trigger-2", "Metric-02", 2, 2, Operator.GTE, 15.0);

            kSession.insert(t1);
            kSession.insert(t2);
            kSession.insert(cs1c1);
            kSession.insert(cs2c1);
            kSession.insert(cs2c2);

            Random r = new Random();
            for (int i = 0; i < 10; ++i) {
                Metric m = new Metric("Metric-01", r.nextDouble() * 20);
                kSession.insert(m);
                System.out.println(m);
            }
            for (int i = 0; i < 10; ++i) {
                Metric m = new Metric("Metric-02", r.nextDouble() * 20);
                kSession.insert(m);
                System.out.println(m);
            }

            kSession.fireAllRules();
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

}
