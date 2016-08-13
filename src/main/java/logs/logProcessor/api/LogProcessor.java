package logs.logProcessor.api;

import java.io.File;
import java.math.BigInteger;
import java.util.List;

import logs.util.Utility;

/**
 * This is class initiate the creation of thread pool and delegates the required tasks accordingly 
 * @author Praveen Karkhile
 * @version 1.0
 * @since 08-Aug-2016
 */
public class LogProcessor {

	/**
	 * The function takes the following parameters and performs the logProcessing task.
	 * @param dirName is the target directory path that contains the log files  
	 * @param numberOfThreads total number of threads that are required to create thread pool
	 * @throws Exception if the directory path is not valid then NotDirectoryException is thrown
	 */
	public static void lineNumberProcessor(String dirName, int numberOfThreads) throws Exception{
    	File[] fileLookup = Utility.createDirectoryLookup(dirName);
        WorkerThreadPool wtp = new WorkerThreadPool(numberOfThreads, dirName);
        List<Long> FileNumMap = wtp.getLineCountPhase(fileLookup);
        wtp.generateNumberPhase(FileNumMap, fileLookup);       
    }
}
