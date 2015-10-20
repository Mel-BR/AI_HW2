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
        static int problemSize = 0;
        static HashMap<String, List<Integer>> puzzleConstraints = new HashMap<String, List<Integer>>();
    

	public static Map<String,ArrayList<String>> parseWordList(String filename) {
			
            Scanner fileScanner = null;

            try {
                fileScanner = new Scanner(new BufferedReader(new FileReader(filename)));
                while (fileScanner.hasNextLine()) {
                    parseLineWordList(fileScanner.nextLine());
                }
            } catch (FileNotFoundException e) {
                try {

                Path pathAbsolute = Paths.get(filename);
                Path pathBase = Paths.get("src/csp/files/wordlist");
                Path pathRelative = pathBase.relativize(pathAbsolute);


                URL url = Parser.class.getClassLoader().getResource(pathRelative.toString());

                fileScanner = new Scanner(new BufferedReader(new FileReader(url.getPath())));
                while (fileScanner.hasNextLine()) {
                    parseLineWordList(fileScanner.nextLine());
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

	private static void parseLineWordList(String line) {
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
        
  
 	public static void parsePuzzle(String filename) {
			
            Scanner fileScanner = null;

            try {
                fileScanner = new Scanner(new BufferedReader(new FileReader(filename)));
                if(fileScanner.hasNext()){
                    problemSize=Integer.parseInt(fileScanner.nextLine());
                }
                while (fileScanner.hasNextLine()) {
                    parseLinePuzzle(fileScanner.nextLine());
                }
            } catch (FileNotFoundException e) {
                try {

                Path pathAbsolute = Paths.get(filename);
                Path pathBase = Paths.get("src/csp/puzzles");
                Path pathRelative = pathBase.relativize(pathAbsolute);


                URL url = Parser.class.getClassLoader().getResource(pathRelative.toString());

                fileScanner = new Scanner(new BufferedReader(new FileReader(url.getPath())));
                if(fileScanner.hasNext()){
                    problemSize=Integer.parseInt(fileScanner.nextLine());
                }
                while (fileScanner.hasNextLine()) {
                    parseLinePuzzle(fileScanner.nextLine());
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
	}
	
        
	private static void parseLinePuzzle(String line) {
            String categName = "";
            ArrayList<Integer> index = new ArrayList<Integer>();
            
            Scanner lineScanner = new Scanner(line);
            lineScanner.useDelimiter(": |, ");
            if(lineScanner.hasNext()){
                categName = lineScanner.next();
            }
            while(lineScanner.hasNext()){
                index.add(Integer.parseInt(lineScanner.next()));
            }
            puzzleConstraints.put(categName, index);
	}
        
        public static int getProblemSize(){
            return problemSize;
        }
        
        public static HashMap<String, List<Integer>> getPuzzleConstraints(){
            return puzzleConstraints;
        }

}
