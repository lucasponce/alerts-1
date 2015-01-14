package org.hawkular.alerts.sample.mdb;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.ConnectionFactory;
import javax.jms.MessageListener;

import com.google.gson.annotations.Expose;

import org.hawkular.alerts.condition.Alert;
import org.hawkular.alerts.data.Metric;
import org.hawkular.alerts.sample.AlertTest;
import org.hawkular.alerts.sample.mdb.AlertMetricMDB.MetricMessage;
import org.hawkular.bus.common.BasicMessage;
import org.hawkular.bus.common.ConnectionContextFactory;
import org.hawkular.bus.common.Endpoint;
import org.hawkular.bus.common.Endpoint.Type;
import org.hawkular.bus.common.MessageProcessor;
import org.hawkular.bus.common.SimpleBasicMessage;
import org.hawkular.bus.common.consumer.BasicMessageListener;
import org.hawkular.bus.common.producer.ProducerConnectionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@MessageDriven(messageListenerInterface = MessageListener.class, activationConfig = {
 @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
        @ActivationConfigProperty(propertyName = "destination", propertyValue = "MetricsQueueName") // ,
// @ActivationConfigProperty(propertyName = "messageSelector", propertyValue = "MyFilter = 'fnf'")
})
public class AlertMetricMDB extends BasicMessageListener<MetricMessage> {
    private final Logger log = LoggerFactory.getLogger(AlertMetricMDB.class);

    @Resource(mappedName = "java:/HawkularBusConnectionFactory")
    private ConnectionFactory connectionFactory;

    protected void onBasicMessage(MetricMessage msg) {
        MetricData md = msg.getMetricData();
        log.info(" --> " + md);
        Set<Metric> metrics = new HashSet<Metric>();
        for (NumericDataPoint dp : md.dataPoints) {
            metrics.add(new Metric(md.getMetricName(), dp.getValue()));
        }
        Collection<Alert> alerts = AlertTest.run(metrics);
        log.info("Generated [" + alerts.size() + "] Alerts:");
        for (Alert alert : alerts) {
            log.info("\t" + alert);
            sendEmailNotif(alert.toString());
        }
    };

    private void sendEmailNotif(String notif) {
        final String CONN_FACTORY = "/ConnectionFactory";
        final String QUEUE_NAME = "java:/queue/AlertNotifEmailQueueName";
        final Endpoint ENDPOINT = new Endpoint(Type.QUEUE, "AlertNotifEmailQueueName");

        try {
            ConnectionContextFactory factory = new ConnectionContextFactory(connectionFactory);
            ProducerConnectionContext pc = factory.createProducerConnectionContext(ENDPOINT);
            SimpleBasicMessage msg = new SimpleBasicMessage(notif);
            MessageProcessor processor = new MessageProcessor();
            processor.send(pc, msg);

            System.out.println("<h1>Alert Email Notification Sender</h1>");
            System.out.println("<p>Message Sent [" + notif + "]</p>");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * A metric message that is sent over the message bus. Just for testing...
     */
    public static class MetricMessage extends BasicMessage {
        // the basic message body - it will be exposed to the JSON output
        @Expose
        private MetricData metricData;

        // some optional additional details about the basic message
        @Expose
        private Map<String, String> details;

        protected MetricMessage() {
            ; // Intentionally left blank
        }

        public MetricMessage(MetricData metricData) {
            this(metricData, null);
        }

        public MetricMessage(MetricData metricData, Map<String, String> details) {
            this.metricData = metricData;

            // make our own copy of the details data
            if (details != null && !details.isEmpty()) {
                this.details = new HashMap<String, String>(details);
            } else {
                this.details = null;
            }
        }

        public MetricData getMetricData() {
            return metricData;
        }

        public void setMetricData(MetricData metricData) {
            this.metricData = metricData;
        }

        /**
         * Optional additional details about this message. This could be null if there are no additional details
         * associated with this message.
         * 
         * @return the details of this message or null. This is an unmodifiable, read-only map of details.
         */
        public Map<String, String> getDetails() {
            if (details == null) {
                return null;
            }
            return Collections.unmodifiableMap(details);
        }
    }

    public static class MetricData {
        @Expose
        String tenantId;
        @Expose
        String metricName;
        @Expose
        List<NumericDataPoint> dataPoints;

        public MetricData() {
        }

        public String getTenantId() {
            return tenantId;
        }

        public void setTenantId(String tenantId) {
            this.tenantId = tenantId;
        }

        public String getMetricName() {
            return metricName;
        }

        public void setMetricName(String metricName) {
            this.metricName = metricName;
        }

        public List<NumericDataPoint> getDataPoints() {
            return dataPoints;
        }

        public void setDataPoints(List<NumericDataPoint> dataPoints) {
            this.dataPoints = dataPoints;
        }

        @Override
        public String toString() {
            return "MetricData [tenantId=" + tenantId + ", metricName=" + metricName + ", dataPoints=" + dataPoints
                    + "]";
        }
    }

    public static class NumericDataPoint {
        @Expose
        private long timestamp;
        @Expose
        private double value;

        public NumericDataPoint() {
        }

        public NumericDataPoint(long timestamp, double value) {
            this.timestamp = timestamp;
            this.value = value;
        }

        public long getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(long timestamp) {
            this.timestamp = timestamp;
        }

        public double getValue() {
            return value;
        }

        public void setValue(double value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return "NumericDataPoint [timestamp=" + timestamp + ", value=" + value + "]";
        }

    }


}
