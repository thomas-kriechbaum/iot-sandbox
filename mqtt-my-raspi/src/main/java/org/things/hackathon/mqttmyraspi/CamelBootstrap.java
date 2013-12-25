package org.things.hackathon.mqttmyraspi;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.apache.camel.cdi.CdiCamelContext;
import org.jboss.weld.environment.se.bindings.Parameters;
import org.jboss.weld.environment.se.events.ContainerInitialized;

/**
 * Initializes the required Camel routes.
 * 
 * @author Thomas Kriechbaum
 */
@Singleton
public class CamelBootstrap {

    private static final String DEFAULT_DIRECTORY = "/home/pi/inbox";

    private static final String DEFAULT_BROKER = "tcp://192.168.1.114:1883";

    private static final String DEFAULT_TOPIC = "things/device/arduino";

    private static final String DEFAULT_ARDUINO = "/dev/ttyACM0";

    @Inject
    private KeepAlive keepAlive;

    @Inject
    private ThingsCamelContext camelContext;

    @Inject
    private InboxRouteBuilder inboxRoute;

    @Inject
    private ThingsRouteBuilder thingsRoute;

    @Inject
    private ThingsBean thingsBean;

    public CamelBootstrap() {
        super();
    }

    public void bootstrap(@Observes ContainerInitialized event, @Parameters List<String> parameters) throws Exception {
        System.out.println("Bootstrapping Camel routes ...");

        Map<String, String> properties = new HashMap<String, String>();
        for (int cnt = 0; cnt < parameters.size(); cnt += 2) {
            properties.put(parameters.get(cnt), parameters.get(cnt + 1));
        }

        String directory = getParameter("-inbox", properties, DEFAULT_DIRECTORY);
        String broker = getParameter("-broker", properties, DEFAULT_BROKER);
        String topic = getParameter("-topic", properties, DEFAULT_TOPIC);
        String arduino = getParameter("-arduino", properties, DEFAULT_ARDUINO);

        inboxRoute.setDirectory(directory);
        inboxRoute.setBroker(broker);
        inboxRoute.setTopic(topic);

        thingsRoute.setBroker(broker);
        thingsRoute.setTopic(topic);

        thingsBean.setArduino(arduino);

        camelContext.addRoutes(inboxRoute);
        camelContext.addRoutes(thingsRoute);
        camelContext.start();

        System.out.println("Camel routes have been started.");
    }

    private String getParameter(String name, Map<String, String> values, String defaultValue) {
        String value = values.get(name);
        return value != null && !value.isEmpty() ? value : defaultValue;
    }

}
