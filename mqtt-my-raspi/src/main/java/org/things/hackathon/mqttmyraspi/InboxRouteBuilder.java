package org.things.hackathon.mqttmyraspi;

import org.apache.camel.builder.RouteBuilder;

/**
 * Builds a Camel route that listens to a specific directory and publishes the
 * content of any added file to a MQTT topic.
 * 
 * The inbox directory can be defined by providing the <code>-inbox</code> start
 * parameter (e.g <code>-inbox /home/pi/inbox</code>).
 * 
 * The MQTT broker can be defined by providing the <code>-broker</code> start
 * parameter (e.g <code>-broker tcp://192.168.1.114:1883</code>).
 * 
 * The MQTT topic can be defined by providing the <code>-topic</code> start
 * parameter (e.g <code>-topic things/device/arduino</code>).
 * 
 * @author Thomas Kriechbaum
 * 
 * @see CamelBootstrap
 */
public class InboxRouteBuilder extends RouteBuilder {

    private String directory;

    private String broker;

    private String topic;

    public InboxRouteBuilder() {
        super();
    }

    public void setBroker(String broker) {
        this.broker = broker;
    }

    public void setDirectory(String directory) {
        this.directory = directory;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    @Override
    public void configure() throws Exception {
        System.out.println("inbox route - directory: " + directory + " broker: " + broker + " topic: " + topic);
        from("file:" + directory).to("mqtt://sandbox?host=" + broker + "&publishTopicName=" + topic);
    }

}
