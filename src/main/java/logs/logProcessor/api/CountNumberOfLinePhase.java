package logs.logProcessor.api;

import java.util.concurrent.Callable;

import logs.util.Utility;


/**
 * @author praveenkarkhile
 *
 */
public class CountNumberOfLinePhase implements Callable<FileNumMap> {

    private int fileIndex;
    private String filePath;

    
    CountNumberOfLinePhase(int fileIndex, String filePath) {
        this.fileIndex = fileIndex;
        this.filePath = filePath;
    }
    /*
     * Responsible for generating file & its lineCount map
     */
    
    @Override
    public FileNumMap call() {
        long numberOfLines = Utility.getLineCountForFile(filePath);
        return new FileNumMap(fileIndex, numberOfLines);
    }
}
