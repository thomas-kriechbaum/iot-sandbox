package org.things.hackathon.mqttmyraspi;

import javax.inject.Named;

import org.apache.camel.cdi.CdiCamelContext;

/**
 * Workaround for ambiguous dependency exception ... further investigations needed!
 *
 */
@Named
public class ThingsCamelContext extends CdiCamelContext {
    
    public ThingsCamelContext() {
        super();
    }
}