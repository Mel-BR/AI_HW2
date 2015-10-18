package csp.entities;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;


public class Parser {

	
        static Map<String,ArrayList<String>> wordsByCateg = new HashMap<String,ArrayList<String>>();
    
	/**
	 * parses the maze txt file into an arraylist matrix of integers
	 * @param filename
	 * @return maze
	 */
	public static Map<String,ArrayList<String>> parseFile(String filename) {
			
            Scanner fileScanner = null;

            try {
                fileScanner = new Scanner(new BufferedReader(new FileReader(filename)));
                while (fileScanner.hasNextLine()) {
                    parseLine(fileScanner.nextLine());
                }
            } catch (FileNotFoundException e) {
                try {

                Path pathAbsolute = Paths.get(filename);
                Path pathBase = Paths.get("src/input");
                Path pathRelative = pathBase.relativize(pathAbsolute);


                URL url = Parser.class.getClassLoader().getResource(pathRelative.toString());

                fileScanner = new Scanner(new BufferedReader(new FileReader(url.getPath())));
                while (fileScanner.hasNextLine()) {
                    parseLine(fileScanner.nextLine());
                }
                } catch (FileNotFoundException ee) {
                    System.out.println("file not found");
                    ee.printStackTrace();
                } finally {
                    if (fileScanner != null) {
                        fileScanner.close();
                    }
                }

            } finally {
                if (fileScanner != null) {
                    fileScanner.close();
                }
            }

            return wordsByCateg;
	}
	
	/**
	 * parses string into a row of the maze
	 * @param line
	 * @return row
	 */
	private static void parseLine(String line) {
            String categName = "";
            ArrayList<String> wordsList = new ArrayList<String>();
            
            Scanner lineScanner = new Scanner(line);
            lineScanner.useDelimiter(":\\t|, ");
            if(lineScanner.hasNext()){
                categName = lineScanner.next();
            }
            while(lineScanner.hasNext()){
                wordsList.add(lineScanner.next());
            }
            wordsByCateg.put(categName, wordsList);
	}

}
