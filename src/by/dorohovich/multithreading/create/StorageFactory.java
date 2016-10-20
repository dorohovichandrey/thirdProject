package by.dorohovich.multithreading.create;

import by.dorohovich.multithreading.port.Storage;

/**
 * Created by User on 11.10.2016.
 */
public class StorageFactory {
    private static long nextId = 0;
    private final static int STORAGE_CAPACITY = 220;
    private final static int STORAGE_INITIAL_CUR_LOAD = 110;

    public Storage getStorage() {
        return new Storage(nextId, STORAGE_CAPACITY, STORAGE_INITIAL_CUR_LOAD);
    }
}
