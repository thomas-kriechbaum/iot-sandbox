package org.things.hackathon.mqttmyraspi;

import javax.inject.Singleton;

/**
 * Holds Weld and Camel routes active.
 * 
 * @author Thomas Kriechbaum
 */
@Singleton
public class KeepAlive {

    private boolean active = true;

    private long interval = 1000;

    public KeepAlive() {
        super();
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public long getInterval() {
        return interval;
    }

    public void setInterval(long interval) {
        this.interval = interval;
    }

}
