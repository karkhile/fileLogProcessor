package logs.logProcessor.api;

import java.math.BigInteger;

/**
 * @author praveenkarkhile
 *
 */
public class FileNumMap {
    int fileIndex;
    long numberOfLines;

    /*
     * class to represent the FileIndex and its Linecount
     */
    
    FileNumMap(int fileIndex, long numberOfLines) {
        this.fileIndex = fileIndex;
        this.numberOfLines = numberOfLines;
    }

	public int getFileIndex() {
		return fileIndex;
	}

	public void setFileIndex(int fileIndex) {
		this.fileIndex = fileIndex;
	}

	public long getNumberOfLines() {
		return numberOfLines;
	}

	public void setNumberOfLines(long numberOfLines) {
		this.numberOfLines = numberOfLines;
	}

}
