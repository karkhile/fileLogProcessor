package logs.logProcessor;

import logs.logProcessor.api.LogProcessor;
import logs.util.Constants;

/**
 * This is the main class for the application that drives the LogProcessing task.
 * @author Praveen Karkhile
 * @version 1.0
 * @since 08-Aug-2016
 */
public class LogProcessingApp {
	
	private static final long MEGABYTE = 1024L * 1024L;
	
    /**
     * @param args is the command line argument, function requires two arguments 
     * first an integer value representing max number of threads, 
     * second is a string represents the directory path where the logs are stored.  	  
     */
	public static void main(String[] args){

		long startTime = System.currentTimeMillis();

		if(args == null || args.length < 1 || "--help".equals( args[0])){
			Constants.usageMessage();
			return;
		}
		String dirName = ".";
		int maxThreads = 1;
		for (String argument : args){
			if (argument.startsWith( "--max-threads=" )){
				maxThreads = Integer.valueOf( argument.substring( argument.indexOf( "=" )+1 ) );
			} else if (argument.startsWith( "--dir=" )){
				dirName = argument.substring( argument.indexOf( "=" )+1 );
			} else {
				System.out.println( "incorrect argument - " + argument + "\n" );
				Constants.usageMessage();
			}
		}
		
		try {
			LogProcessor.lineNumberProcessor(dirName, maxThreads);
		} catch (Exception e) {
			
			e.printStackTrace();
		}

		/*
		 * Calculating the runtime of the application
		 * note: added just for testing purpose, this is not the part of actual requirement 
		 */
		long endTime   = System.currentTimeMillis();
		long totalTime = endTime - startTime;
		System.out.println( "Total Processing Time :"+ totalTime);

		/*
		 * Total memory consumption. 
		 * note: added just for testing purpose, this is not the part of actual requirement 
		 */

		Runtime runtime = Runtime.getRuntime();
		// Run the garbage collector
		runtime.gc();
		// Calculate the used memory
		long memory = runtime.totalMemory() - runtime.freeMemory();
		System.out.println("Used memory is bytes: " + memory);
		System.out.println("Used memory is megabytes: "
				+ bytesToMegabytes(memory));	
	}


	public static long bytesToMegabytes(long bytes) {
		return bytes / MEGABYTE;
	}
}
