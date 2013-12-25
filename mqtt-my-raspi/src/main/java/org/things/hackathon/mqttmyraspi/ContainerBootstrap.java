package org.things.hackathon.mqttmyraspi;

import org.jboss.weld.environment.se.StartMain;
import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;

/**
 * Bootstraps the Weld CDI container.
 * 
 * @author Thomas Kriechbaum
 */
public class ContainerBootstrap {

    public static void main(String[] args) {
        try {
            // Weld specific mechanism to access arguments via CDI producer
            StartMain.PARAMETERS = args;

            Weld weld = new Weld();
            WeldContainer container = weld.initialize();
            KeepAlive keepAlive = container.instance().select(KeepAlive.class).get();

            // hook to shutdown Weld correctly
            Runtime.getRuntime().addShutdownHook(new ShutdownHook(weld));

            // holds Weld and Camel active as long
            // as any route sets KeepAlive.active to false
            while (keepAlive.isActive()) {
                Thread.sleep(keepAlive.getInterval());
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }

    }

    static class ShutdownHook extends Thread {
        private final Weld weld;

        ShutdownHook(Weld weld) {
            this.weld = weld;
        }

        public void run() {
            weld.shutdown();
        }
    }

}
