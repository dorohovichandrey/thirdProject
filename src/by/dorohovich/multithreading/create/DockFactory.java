package by.dorohovich.multithreading.create;

import by.dorohovich.multithreading.port.Dock;
import by.dorohovich.multithreading.port.Storage;

/**
 * Created by User on 11.10.2016.
 */
public class DockFactory {

    private static long nextId = 0;

    public Dock getDock(Storage storage) {
        return new Dock(nextId++, storage);
    }
}
