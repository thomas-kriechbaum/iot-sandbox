package org.things.hackathon.mqttmyraspi;

import javax.inject.Named;

import org.apache.camel.builder.RouteBuilder;

/**
 * Builds a Camel route that listens to a specific MQTT topic and publishes the
 * received commands the the ThingsBean.
 * 
 * The MQTT broker can be defined by providing the <code>-broker</code> start
 * parameter (e.g <code>-broker tcp://192.168.1.114:1883</code>).
 * 
 * The MQTT topic can be defined by providing the <code>-topic</code> start
 * parameter (e.g <code>-topic things/device/arduino</code>).
 * 
 * @author Thomas Kriechbaum
 * 
 * @see ThingsBean
 */
@Named
public class ThingsRouteBuilder extends RouteBuilder {

    private String broker;

    private String topic;

    public ThingsRouteBuilder() {
        super();
    }

    public void setBroker(String broker) {
        this.broker = broker;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    @Override
    public void configure() throws Exception {
        System.out.println("things route - broker: " + broker + " topic: " + topic);
        
        from("mqtt://sandbox?host=" + broker + "&subscribeTopicName=" + topic).transform(body().convertToString()).to("bean:thingsBean");
    }

}
