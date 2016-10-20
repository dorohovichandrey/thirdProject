package by.dorohovich.multithreading.port;

import by.dorohovich.multithreading.reporter.Reporter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.TimeUnit;

/**
 * Created by User on 11.10.2016.
 */
public class StorageDaemonController extends Thread {

    private static final Reporter REPORTER =Reporter.getReporter();
    private static final Logger LOG = LogManager.getLogger();
    private static final double CRITICAL_LOAD_COEF =0.25;
    private static final double NORMAL_LOAD_COEF=0.5;
    private Storage storage;


    public StorageDaemonController(Storage storage) {
        this.setDaemon(true);
        this.storage = storage;
    }

    private void transferContainersToMainland()
    {
        storage.takeContainers(storage.getCurLoad()-(int)(NORMAL_LOAD_COEF*storage.getCapacity()));
    }

    private void transferContainersFromMainland()
    {
        storage.putContainers((int)(NORMAL_LOAD_COEF*storage.getCapacity())-storage.getCurLoad());
    }


    @Override
    public void run() {
        while (true)
        {
            try
            {
                TimeUnit.MILLISECONDS.sleep(100);
            }
            catch (InterruptedException e)
            {
                LOG.error(e);
            }

            if(storage.getCurLoad()<(int)(storage.getCapacity()* CRITICAL_LOAD_COEF))
            {
                transferContainersFromMainland();
                REPORTER.report("StorageDemonController solve storage problems, storage current load="+storage.getCurLoad());

            }
            else if(storage.getCurLoad()>(int)(storage.getCapacity()*(1.0- CRITICAL_LOAD_COEF)))
            {
                transferContainersToMainland();
                REPORTER.report("StorageDemonController solve storage problems, storage current load="+storage.getCurLoad());
            }

        }
    }


}
