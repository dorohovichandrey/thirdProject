package by.dorohovich.multithreading.reporter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileNotFoundException;
import java.io.PrintStream;

/**
 * Created by User on 11.10.2016.
 */
public class Reporter {
    private static final Logger LOG= LogManager.getLogger();
    private static final String FILE_NAME="reports/reports.txt";
    private PrintStream printStream;

    private static Reporter reporter;

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
        if(reporter==null)
        {
            reporter=new Reporter();
        }
        return reporter;
    }

    public void report(Object object)
    {
        printStream.println(object);
    }

}
