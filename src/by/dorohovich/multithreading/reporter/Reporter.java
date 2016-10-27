package by.dorohovich.multithreading.reporter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by User on 11.10.2016.
 */
public class Reporter {
    private static final Logger LOG= LogManager.getLogger();
    private static final String FILE_NAME="reports/reports.txt";
    private PrintStream printStream;
    private static Reporter reporter;
    private static AtomicBoolean isCreated = new AtomicBoolean(false);
    private static Lock lock=new ReentrantLock();

    private Reporter(){
        try
        {
            printStream = new PrintStream(FILE_NAME);
        }
        catch (FileNotFoundException e)
        {
            LOG.fatal(e);
            throw new RuntimeException("fileName="+FILE_NAME, e);
        }
    }

    public static Reporter getReporter()
    {
        if(!isCreated.get())
        {
            lock.lock();
            try
            {
                reporter = new Reporter();
                isCreated.getAndSet(true);
            }
            finally {
                lock.unlock();
            }
        }
        return reporter;
    }

    public void report(Object object)
    {
        printStream.println(object);
    }

}
