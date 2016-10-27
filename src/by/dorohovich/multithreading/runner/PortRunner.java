package by.dorohovich.multithreading.runner;

import by.dorohovich.multithreading.port.provider.Port;
import by.dorohovich.multithreading.port.provider.PortProvider;
import by.dorohovich.multithreading.create.ShipFactory;

/**
 * Created by User on 11.10.2016.
 */
public class PortRunner {

    private static final int NUMBER_OF_SHIPS=15;

    public static void main(String[] args) throws Exception{
        PortProvider portProvider=new PortProvider();
        Port port=portProvider.getPort();
        port.initAndStartStorageDaemonController();
        createAndStartShips(NUMBER_OF_SHIPS);

    }

    public static void createAndStartShips( int numberOfShips)
    {
        ShipFactory shipFactory=new ShipFactory();
        for (int i = 0; i < numberOfShips; i++) {
            shipFactory.getShip().start();
        }

    }

}
