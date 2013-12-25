package org.things.hackathon.mqttmyraspi;

import static org.things.Things.things;

import javax.inject.Named;
import javax.inject.Singleton;

/**
 * Integrates the Things-API by sending a received command to a connected
 * Arduino board.
 * 
 * The serial port can be defined by providing the <code>-arduino</code> start
 * parameter (e.g <code>-arduino /dev/ttyACM0</code>).
 * 
 * @author Thomas Kriechbaum
 * 
 * @see ThingsRouteBuilder
 * @see CamelBootstrap
 */
@Named
@Singleton
public class ThingsBean {

    private String arduino;

    public ThingsBean() {
        super();
    }

    /**
     * Sets the serial port to which the Arduino board has been connected.
     */
    public void setArduino(String arduino) {
        this.arduino = arduino;
    }

    public String getArduino() {
        return arduino;
    }

    public void execute(String command) {
        System.out.println("sending command " + command + " to Arduino via " + arduino);
        things.execute(getArduino(), command, null);
    }

}
