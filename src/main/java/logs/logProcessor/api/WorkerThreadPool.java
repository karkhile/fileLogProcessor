package logs.logProcessor.api;

import static logs.util.Utility.getLineCountForFile;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import logs.util.Constants;
import logs.util.Utility;

/**
 * @author praveenkarkhile
 *
 */
public class WorkerThreadPool {
	private int threadCount;
    private String dirName;

    WorkerThreadPool(int threadCount, String dirName) {
        this.threadCount = threadCount;
        this.dirName = dirName;
    }

    public List<Long> getLineCountPhase(File[] dirFiles) throws Exception {
        ExecutorService executor = Executors.newFixedThreadPool(threadCount);
        List<Long> fileNumMap = new ArrayList<>();
        for (int i = 0; i <= dirFiles.length; i++) fileNumMap.add((long) 0);

        List<Future<FileNumMap>> list = new ArrayList<>();
        int fileIndex = 1;
        for(File targetFile: dirFiles) {
                String filePath = targetFile.getPath();
                Future<FileNumMap> submit = executor.submit(new CountNumberOfLinePhase(fileIndex, filePath ));
                list.add(submit);
                if (list.size() == Constants.MAX_LOG_SIZE) {
                    Utility.saveProcessedLogs(list, fileNumMap);
                }
            fileIndex++;
        }
        if (list.size() > 0) {
        	Utility.saveProcessedLogs(list, fileNumMap);
        }
        executor.shutdown();

        for (int i = 2; i < fileNumMap.size(); i++) {
        	
        	fileNumMap.set(i, fileNumMap.get(i - 1)+fileNumMap.get(i));
        }
        return fileNumMap;
    }

    public void generateNumberPhase(List<Long> fileNumMap, File[] dirFiles) throws Exception {
        ExecutorService executor = Executors.newFixedThreadPool(threadCount);
        List<Future<Void>> list = new ArrayList<>();
        String line; int index = 1;
        for(File targetFile: dirFiles) {
                String filePath = targetFile.getPath();
                Future<Void> submit = executor.submit(new PrefixNumberPhase(filePath, Utility.getStartNumber(index, fileNumMap),Utility.calculateLineCount(index,fileNumMap)));
                list.add(submit);
                if (list.size() == Constants.MAX_LOG_SIZE) {
                    for (Future<Void> future : list) {
                        future.get();
                    }
                    list.clear();
                }
            index++;
        }
        for (Future<Void> future : list) future.get();
        executor.shutdown();
    }    
}
