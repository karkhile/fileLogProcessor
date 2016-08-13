package fileProcessor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import org.junit.Test;

import junit.framework.Assert;
import logs.logProcessor.api.LogProcessor;
import logs.util.Utility;

public class TestLogProcessor {
	private static int NO_OF_THREADS = 2;

	@Test
	public void testCountOfValidFiles() throws Exception {
		String dirName = "src/main/resources/testInput";
		File[] fileList = logs.util.Utility.createDirectoryLookup(dirName);
		Assert.assertEquals(3, (long) fileList.length);
	}

	@SuppressWarnings("deprecation")
	@Test
	public void testLineCount() {
		String filePath = "src/main/resources/testInput/logtest.2016-01-02.log";
		long linecount = Utility.getLineCountForFile(filePath);
		Assert.assertEquals(20, linecount);
	}

	@Test
	public void testValidFileName() {
		String f1 = "test.log";
		String f2 = "logtest.2016-01-02.log";
		String f3 = "logtest.2016-01-21.log";
		String f4 = "logtest.2016-01-82.log";
		String f5 = "logtet.2016-01-02.log";
		boolean result = logs.util.Constants.LOG_FILE_NAME.matcher(f1).matches();
		Assert.assertEquals(false, result);
		result = logs.util.Constants.LOG_FILE_NAME.matcher(f2).matches();
		Assert.assertEquals(true, result);
		result = logs.util.Constants.LOG_FILE_NAME.matcher(f3).matches();
		Assert.assertEquals(true, result);
		result = logs.util.Constants.LOG_FILE_NAME.matcher(f4).matches();
		Assert.assertEquals(false, result);
		result = logs.util.Constants.LOG_FILE_NAME.matcher(f5).matches();
		Assert.assertEquals(false, result);
	}

	@SuppressWarnings("deprecation")
	@Test
	public void testNumberPrepending() throws Exception {
		String dirName = "src/main/resources/testInput";
		LogProcessor.lineNumberProcessor(dirName, NO_OF_THREADS);
		File[] fileList = logs.util.Utility.createDirectoryLookup(dirName);
		long linecount = 1;

		for (File file : fileList) {
			FileInputStream fis = new FileInputStream(file);
			try (BufferedReader br = new BufferedReader(new InputStreamReader(fis))) {
				String line = null;
				while ((line = br.readLine()) != null) {
					String[] tokens = line.split("\\.");
					Assert.assertEquals(linecount, Long.parseLong(tokens[0]));
					linecount ++;
				}
			} catch (Exception ex) {
				System.out.println(ex);
			}
		}
	}
}
