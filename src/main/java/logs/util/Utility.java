package logs.util;

import java.io.File;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.LineNumberReader;
import java.nio.file.NotDirectoryException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.Future;

import logs.logProcessor.api.FileNumMap;

/**
 * @author praveenkarkhile
 *
 */
public class Utility {
	public static File[] createDirectoryLookup(String dirName) throws Exception {
		File dir = new File(dirName);
		if (!dir.exists() || !dir.isDirectory()) {
			throw new NotDirectoryException(dir.getName());
		}
		File[] listOfFiles = dir.listFiles(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				return Constants.LOG_FILE_NAME.matcher(name).matches();
			}
		});
		Arrays.sort(listOfFiles, new Comparator<File>(){
		    public int compare(File file1, File file2)
		    {
		    	return file1.getName().compareTo(file2.getName());
		    } });	
		return listOfFiles;
	}
	
	public static long getLineCountForFile(String filePath) {
		long lineCount = 0;
		try (LineNumberReader lineNumberReader = new LineNumberReader(new FileReader(filePath))) {
        lineNumberReader.skip( Long.MAX_VALUE );
        lineCount = lineNumberReader.getLineNumber();
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
		return lineCount+1;
	}

	public static long getStartNumber(int index, List<Long>fileNumMap) {
		if (index == 1)
			return 1;
		return fileNumMap.get(index - 1) + 1;
	}

	public static void saveProcessedLogs(List<Future<FileNumMap>> list, List<Long> FileNumMap) throws Exception {
		for (Future<FileNumMap> map : list) {
			FileNumMap fileMap = map.get();
			FileNumMap.set(fileMap.getFileIndex(), fileMap.getNumberOfLines());
		}
		list.clear();
	}

	public static long calculateLineCount(int index, List<Long> fileNumMap) {
		if(index==1) return fileNumMap.get(index);
		return fileNumMap.get(index)- fileNumMap.get(index-1);
	}
}
