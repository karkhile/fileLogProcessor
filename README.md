

It is an CommandLine tool implemented to prepend line numbers to every line in the file present in the given directory.


Below are certain assumptions that I had considered.


1.	The total number of files are roughly 1000000.

2.	The naming convention for the small files follows the format “logtest.yyyy-mm-dd.log”

3.	The files that do not follow above naming convention are not processed.

4.	The files are ordered according to the timestamp (oldest to latest). 

5.	The file name is considered to order the files according to timestamp not the last modified date. (as it was not clear in the            problem what to use)


Steps to build & run the project: 

The project requires jre 1.7 and above

1.	Build the project using “mvn clean install” command and then execute the below command: 

	java -classpath < fileLogprocessor-0.0.1-SNAPSHOT.jar > logs.logProcessor.LogProcessingApp --max-threads=<number of threads> --dir= <directory path for the log files>

	eg : java -classpath fileLogprocessor-0.0.1-SNAPSHOT.jar logs.logProcessor.LogProcessingApp --max-thread=4 --dir=src/main/resources/testInput/ 

2.	Import the Project in eclipse and run the project as “ run configuration”, select the main class as LogProcessorApp and  in the          arguments tab provide below details

	--max-threads=<number of threads> --dir= <directory path for the log files>

	eg: --max-thread=4 --dir=src/main/resources/testInput/

