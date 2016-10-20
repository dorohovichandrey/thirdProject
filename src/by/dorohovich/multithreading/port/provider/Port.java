package by.dorohovich.multithreading.port.provider;

import by.dorohovich.multithreading.port.Dock;
import by.dorohovich.multithreading.port.Storage;
import by.dorohovich.multithreading.port.StorageDaemonController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Queue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by User on 16.10.2016.
 */
public class Port {
    private static final Logger LOG = LogManager.getLogger();
    private Semaphore semaphore;
    private Storage storage;
    private Queue<Dock> docks;
    private StorageDaemonController storageDaemonController;
    private ReentrantLock lock=new ReentrantLock(true);

    Port(Semaphore semaphore, Storage storage, Queue<Dock> docks) {
        this.semaphore = semaphore;
        this.storage = storage;
        this.docks = docks;

    }

    public void initAndStartStorageDaemonController()
    {
        storageDaemonController=new StorageDaemonController(storage);
        storageDaemonController.start();
    }


    public void enterShipInPort()
    {
        try{
            semaphore.acquire();

        }
        catch (InterruptedException e) {
            LOG.error(e);
        }
    }


    public void shipGetOutOfPort()
    {
        semaphore.release();
    }

    public Dock getDock()
    {

        try
        {
            lock.lock();
            return docks.poll();
        }
        finally
        {
            lock.unlock();
        }
    }

    public void returnDock(Dock dock)
    {

        try
        {
            lock.lock();
            docks.add(dock);
        }
        finally
        {
            lock.unlock();
        }
    }

    public Storage getStorage() {
        return storage;
    }


}
