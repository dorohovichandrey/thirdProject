package by.dorohovich.multithreading.port;


/**
 * Created by User on 11.10.2016.
 */
public class Storage {
    private long storageId;
    private int capacity;
    private int curLoad;

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
        curLoad+=containers;
    }

    public void takeContainers(int containers) {
        curLoad-=containers;
    }
}
