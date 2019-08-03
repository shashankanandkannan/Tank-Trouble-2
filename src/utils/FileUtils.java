package utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class FileUtils {
	public static String loadAsString(String path) {
		StringBuilder result = new StringBuilder();
		
		BufferedReader reader;
		
		try{
			File file = new File(path);
			InputStream is = new FileInputStream(file);
			InputStreamReader isr = new InputStreamReader(is);
			
			reader = new BufferedReader(isr);
		
			String line = "";
			while ((line = reader.readLine()) != null) {
				result.append(line).append("\n");
			}
		}
		catch(IOException e) {
			System.err.println("Couldn't find the file at " + path);
			
		}
		
		return result.toString();
	}
}
