package by.dorohovich.multithreading.port;


import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by User on 11.10.2016.
 */
public class Storage {
    private long storageId;
    private int capacity;
    private int curLoad;
    private Lock lock=new ReentrantLock(true);

    public Storage(long storageId, int capacity, int curLoad) {
        this.storageId = storageId;
        this.capacity = capacity;
        this.curLoad = curLoad;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getCurLoad() {
        return curLoad;
    }

    public void putContainers(int containers) {
        lock.lock();
        try
        {
            curLoad += containers;
        }
        finally {
            lock.unlock();
        }
    }

    public void takeContainers(int containers) {
        lock.lock();
        try
        {
            curLoad -= containers;
        }
        finally {
            lock.unlock();
        }

    }
}
