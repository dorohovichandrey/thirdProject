package by.dorohovich.multithreading.ship;

import by.dorohovich.multithreading.port.Dock;
import by.dorohovich.multithreading.port.provider.Port;
import by.dorohovich.multithreading.port.provider.PortProvider;
import by.dorohovich.multithreading.reporter.Reporter;

/**
 * Created by User on 11.10.2016.
 */
public class Ship extends Thread {

    private static final Reporter REPORTER =Reporter.getReporter();
    private long shipId;
    private Port port= PortProvider.getPort();
    private int capacity;
    private boolean loaded;


    public Ship(long shipId, int capacity, boolean loaded) {
        this.shipId = shipId;
        this.capacity=capacity;
        this.loaded=loaded;

    }

    public long getShipId() {
        return shipId;
    }

    public int getCapacity() {
        return capacity;
    }

    public boolean isLoaded() {
        return loaded;
    }

    public void setLoaded(boolean loaded) {
        this.loaded = loaded;
    }

    @Override
    public void run() {
        try
        {
            port.enterShipInPort();
            REPORTER.report(toString() + " enter the port");
            Dock dock = port.getDock();
            REPORTER.report(toString() + " get " + dock);
            dock.using(this);
            REPORTER.report(toString() + " return " + dock);
            port.returnDock(dock);
        }
        finally {
            REPORTER.report(toString()+" leave port");
            port.shipGetOutOfPort();

        }

    }

    @Override
    public String toString() {
        return "Ship{" +
                "shipId=" + shipId +
                ", capacity=" + capacity +
                ", loaded=" + loaded +
                '}';
    }
}
