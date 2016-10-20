package by.dorohovich.multithreading.port;

import by.dorohovich.multithreading.reporter.Reporter;
import by.dorohovich.multithreading.ship.Ship;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.TimeUnit;

/**
 * Created by User on 11.10.2016.
 */
public class Dock {
    private static final Reporter REPORTER =Reporter.getReporter();
    private static final Logger LOG = LogManager.getLogger();
    private long dockId;
    private Storage storage;
    private static final int TIME_FOR_LOAD_OR_UNLOAD_ONE_CONTAINER=15;
    private static final int TIME_FOR_SLEEP_IN_CASE_OF_PROBLEM=500;

    public Dock(long dockId, Storage storage) {
        this.dockId = dockId;
        this.storage=storage;
    }


    public void using(Ship ship)
    {
        if(ship.isLoaded())
        {
            while (!isShipUnloadPossible(ship))
            {
                signalAboutProblem(ship);
            }
            unloadShip(ship);


        }
        else
        {
            while (!isShipLoadPossible(ship))
            {
                signalAboutProblem(ship);
            }
            loadShip(ship);

        }
    }

    private void signalAboutProblem(Ship ship)
    {
        try
        {
            REPORTER.report(ship+" sleep, because of storage problem");
            TimeUnit.MILLISECONDS.sleep(TIME_FOR_SLEEP_IN_CASE_OF_PROBLEM);
        }
        catch (InterruptedException e)
        {
            LOG.error(e);
        }
    }

    private boolean isShipUnloadPossible(Ship ship)
    {
        return ship.getCapacity()+storage.getCurLoad()<=storage.getCapacity();
    }

    private boolean isShipLoadPossible(Ship ship)
    {
        return storage.getCurLoad()>=ship.getCapacity();
    }

    private void loadShip(Ship ship) {
        transferFromStorage(ship.getCapacity());
        ship.setLoaded(true);
        try
        {
            TimeUnit.MILLISECONDS.sleep(TIME_FOR_LOAD_OR_UNLOAD_ONE_CONTAINER*ship.getCapacity());
            REPORTER.report(ship+" was loaded");
        }
        catch (InterruptedException e)
        {
            LOG.error(e);
        }
    }

    private void unloadShip(Ship ship) {
        ship.setLoaded(false);
        transferToStorage(ship.getCapacity());
        try
        {
            TimeUnit.MILLISECONDS.sleep(TIME_FOR_LOAD_OR_UNLOAD_ONE_CONTAINER*ship.getCapacity());
            REPORTER.report(ship+" was unloaded");
        }
        catch (InterruptedException e)
        {
            LOG.error(e);
        }
    }

    private void transferToStorage(int containers) {
            storage.putContainers(containers);
    }

    private void transferFromStorage(int containers) {

        storage.takeContainers(containers);
    }

    @Override
    public String toString() {
        return "Dock{" +
                "dockId=" + dockId +
                '}';
    }
}
