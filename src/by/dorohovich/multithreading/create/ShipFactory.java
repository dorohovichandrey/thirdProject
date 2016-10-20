package by.dorohovich.multithreading.create;


import by.dorohovich.multithreading.ship.Ship;

import java.util.Random;

/**
 * Created by User on 11.10.2016.
 */
public class ShipFactory {
    private static long nextId = 0;
    private static final Random RANDOM =new Random();
    private static final int MIN_CAPACITY=50;
    private static final int MAX_CAPACITY=100;

    public Ship getShip() {
        return new Ship(nextId++,MIN_CAPACITY+ RANDOM.nextInt(MAX_CAPACITY-MIN_CAPACITY+1), RANDOM.nextBoolean());
    }
}
