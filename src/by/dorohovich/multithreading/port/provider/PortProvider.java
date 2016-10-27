package by.dorohovich.multithreading.port.provider;

import by.dorohovich.multithreading.create.DockFactory;
import by.dorohovich.multithreading.create.StorageFactory;
import by.dorohovich.multithreading.port.Dock;
import by.dorohovich.multithreading.port.Storage;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by User on 11.10.2016.
 */
public class PortProvider {
    private static final int NUMBER_OF_DOCKS=5;
    private static Port port;
    private static AtomicBoolean isCreated = new AtomicBoolean(false);
    private static Lock lock=new ReentrantLock();


    public static Port getPort()
    {
        if(!isCreated.get())
        {
            lock.lock();
            try
            {
                Semaphore semaphore = new Semaphore(NUMBER_OF_DOCKS, true);

                StorageFactory storageFactory = new StorageFactory();
                Storage storage = storageFactory.getStorage();

                Queue<Dock> docks = new ArrayDeque<Dock>();
                DockFactory dockFactory = new DockFactory();
                for (int i = 0; i < NUMBER_OF_DOCKS; i++) {
                    docks.add(dockFactory.getDock(storage));
                }

                port = new Port(semaphore, storage, docks);
                isCreated.getAndSet(true);
            }
            finally
            {
                lock.unlock();
            }

        }

            return port;

    }

}
