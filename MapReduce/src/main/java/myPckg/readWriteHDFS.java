package myPckg;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;



public class readWriteHDFS {
	public static void main(String[] args) throws IOException {
		try {
			String hdfsFile = "hdfs://localhost:9000/raj/test_file.txt";
			Path path = new Path(hdfsFile);
		   FileSystem fileSystem = FileSystem.get(new Configuration());
		   BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileSystem.open(path)));
		   String line = bufferedReader.readLine();
		   while (line != null) {
		    System.out.println(line);
		    line = bufferedReader.readLine();
		   }
		}
		catch (IOException e) {
			   e.printStackTrace();
			  }
	}

}
