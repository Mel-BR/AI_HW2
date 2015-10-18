package csp;


import csp.entities.Parser;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class CSP {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // Map Key=Categ, Value=Array of Words
        Map<String,ArrayList<String>> varVal = Parser.parseFile("src/csp/files/wordlist/wordlist.txt"); 
        
        /* Check if every values has been correctly added to the Map structure */
        for(Entry<String,ArrayList<String>> entry : varVal.entrySet()) {
            
            String res = "";
            String key = entry.getKey();
            
            res+=" "+key;
            List<String> value = entry.getValue();
            
            Iterator<String> it = value.iterator();
 
            while (it.hasNext()) {
                   String s = it.next();
                   res+=" "+s;
            }
            
            //System.out.println(res);
        }
        
        //Create new Value, Key = Categ, Value = Array of HashSet
        Map<String,ArrayList<HashSet<Character>>> varDomain = new HashMap<String,ArrayList<HashSet<Character>>>();
        for(Entry<String,ArrayList<String>> entry : varVal.entrySet()) {
            
            String key = entry.getKey();

            varDomain.put(key, new ArrayList<HashSet<Character>>());

            for( int i = 0; i<3 ;i++){
                    varDomain.get(key).add(new HashSet<Character>());
            }

            
        }

        
         for(Entry<String,ArrayList<String>> entry : varVal.entrySet()) {
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
         
        /* Verify if we get the willing result */
        Set s1 = varDomain.get("language").get(1);
        for(Object object : s1) {
            Character element = (Character) object;
            System.out.println(element);
        }        
        
        System.out.println("");
        
        Set s2 = varDomain.get("shape").get(1);
        for(Object object : s2) {
            Character element = (Character) object;
            System.out.println(element);
        }
        
        System.out.println("");
        
        Set<Character> intersection = new HashSet<Character>(s1); // use the copy constructor
        intersection.retainAll(s2);
        for(Object object : intersection) {
            Character element = (Character) object;
            System.out.println(element);
        }
    }
    
}
