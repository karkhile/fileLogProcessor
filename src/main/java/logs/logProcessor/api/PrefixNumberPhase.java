package logs.logProcessor.api;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.util.concurrent.Callable;

/**
 * @author praveenkarkhile
 *
 */
public class PrefixNumberPhase implements Callable<Void> {

	private String filePath;
	private long startLineNumber;
	private long lineCount;

	PrefixNumberPhase(String filePath, long startNumber,long lineCount) {
		this.filePath = filePath;
		this.startLineNumber = startNumber;
		this.lineCount=lineCount;
	}

	@Override
	public Void call() throws Exception {
		File inputFile = new File(filePath);
		FileInputStream fis = new FileInputStream(inputFile); 
		@SuppressWarnings("resource")
		MappedByteBuffer output = new RandomAccessFile(filePath + ".dump", "rw").getChannel()
				.map(FileChannel.MapMode.READ_WRITE, 0, inputFile.length() + lineCount*12);
		try (BufferedReader br = new BufferedReader(new InputStreamReader(fis))) {
			String line = null;
			String s = null;
			long counter = startLineNumber;
			while ((line = br.readLine()) != null) {
				if((counter-startLineNumber)<lineCount-1)
					s = counter + ". " + line + "\n";
				else{
					s = counter + ". " + line;
				}	
				output.put(s.getBytes("UTF-8"));
				counter++;
			}

		} catch (Exception ex) {
			System.out.println(ex);
		}

		Files.deleteIfExists(inputFile.toPath());
		Files.move(new File(filePath + ".dump").toPath(), new File(filePath).toPath());
		return null;
	}
}
