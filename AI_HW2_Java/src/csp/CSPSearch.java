package csp;


import csp.entities.CSP;
import csp.entities.Parser;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class CSPSearch {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // Map Key=Categ, Value=Array of Words
        Map<String,ArrayList<String>> variableValues = Parser.parseWordList("src/csp/files/wordlist/wordlist.txt"); 
        
        //Create new Value, Key = Categ, Value = Array of HashSet
        Map<String,ArrayList<HashSet<Character>>> varDomain = new HashMap<String,ArrayList<HashSet<Character>>>();
        for(Entry<String,ArrayList<String>> entry : variableValues.entrySet()) {
            
            String key = entry.getKey();

            varDomain.put(key, new ArrayList<HashSet<Character>>());

            for( int i = 0; i<3 ;i++){
                    varDomain.get(key).add(new HashSet<Character>());
            }

        }

        
         for(Entry<String,ArrayList<String>> entry : variableValues.entrySet()) {
            String key = entry.getKey();
            
            List<String> value = entry.getValue();
            
            Iterator<String> it = value.iterator();
            
            while (it.hasNext()) {
                   String s = it.next();
                   char[] charArray = s.toCharArray();
                   for( int i = 0;i<3;i++){
                       varDomain.get(key).get(i).add(charArray[i]);
                   }
            }
            
        }
        
        
        System.out.println("");
        
        Parser.parsePuzzle("src/csp/files/puzzles/puzzle1.txt"); 
        int problemSize = Parser.getProblemSize();
        HashMap<String, List<Integer>> puzzleConstraints = Parser.getPuzzleConstraints();
        
        
        CSP cspProblem = new CSP(problemSize, variableValues, varDomain, puzzleConstraints);
        
        /* Letter-based assignment */
        System.out.println("Letter-based assignment");
        cspProblem.RecursiveSearchLetter(new String[problemSize]);
        cspProblem.printTreeSearchLetter();   
        
        System.out.println("\n\n\n");
        
        /* Word-based assignment */      
        System.out.println("Word-based assignment");  
        cspProblem.RecursiveSearchWord(new String[problemSize],new HashSet<String>(), new ArrayList<String>());
        cspProblem.printTreeSearchWord();
        
    }
    
}
