package logs.util;

import java.util.regex.Pattern;

/**
 * This class has some constants and print message that are required in the application
 * @author Praveen Karkhile
 * @version 1.0
 * @since 08-Aug-2016
 */
public class Constants {
	public final static int MAX_LOG_SIZE = 10000;
	public static final Pattern LOG_FILE_NAME = Pattern.compile("logtest\\.\\d{4}-(0?[1-9]|1[012])-(0?[1-9]|[12][0-9]|3[01])\\.log");
	public static final void usageMessage() {
		System.out.println("Usage : java logs.logprocessor.LogProcessingApp [--help] [--max-threads=value] [--dir=value]\n\n"
                + "These are the arguments that can be provided - \n"
                + "--help           lists available usage options\n"
                + "--max-threads    maximum number of threads to be used. Default value is 1\n"
                + "--dir            directory containing the log files. Default directory is current directory");
	}
}
