/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csp.entities;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author BR
 */
public class CSP {
    
    private final ArrayList<HashSet<Character>> domainLetter; // Domain for each index of the array
    private final ArrayList<ArrayList<MyPair>> constraints; // For each index of the array, contains pair like "adjective,2" which means the index contains the third(2+1) letter of an adjective word
    private final Map<String, List<Integer>> puzzleConstraints; // For each category contains the index of the letters. Ex : adjective: 2 3 9
    private final Map<String,ArrayList<String>> wordValues; // For each category, list of possible words. Ex: shape:	ARC, DOT, ORB, RAY
    private final ArrayList<ArrayList<String>> letterTraceSummary; // Keep track of all states/assignment for Letter-based problem
    private final HashSet<String> categList; // List of all categories in the current instance of the problem
    private final ArrayList<ArrayList<String>> wordTraceSummary; // Keep track of all states/assignment for Word-based problem
    private final ArrayList<String> categAssignmentOrder; // Keep track of the order in which the variables are assigned for the Word-based problem
    
    public CSP(int arraySize, Map<String,ArrayList<String>> wordValues, Map<String,ArrayList<HashSet<Character>>> varDomain, Map<String, List<Integer>> puzzleConstraints) {
        this.domainLetter = new ArrayList<HashSet<Character>>();
        this.constraints = new ArrayList<ArrayList<MyPair>>(arraySize);
        this.puzzleConstraints = puzzleConstraints;
        this.wordValues = wordValues;
        this.letterTraceSummary = new ArrayList<ArrayList<String>>();
        this.categList = new HashSet<String>();
        this.wordTraceSummary = new ArrayList<ArrayList<String>>();
        this.categAssignmentOrder =  new ArrayList<String>();
        
        // Initializing constraints
        for (int i = 0; i<arraySize; i++){
            constraints.add(new ArrayList<MyPair>());
        }

        // filling constrainsts and categList
        for(Map.Entry<String, List<Integer>> entry : puzzleConstraints.entrySet()) {
            String categ = entry.getKey();
            for(int i = 0; i<3; i++){
                Integer indexInMainArray = puzzleConstraints.get(categ).get(i);
                constraints.get(indexInMainArray-1).add(new MyPair(categ,i)); // -1 because the main array is a zero-based array. and the statement of the problem is a 1-based array
            }
            categList.add(categ);
        }
        
        // filling domainLetter. Goal: determining the domain for each variable, storing t in domain variable member
        for (ArrayList<MyPair> constraintForACell : constraints) { // Browse the constraint variable.
            int pairsListSize = constraintForACell.size(); 
            // Initialization of result with the domain of the first variable in the constraint list for the given cell
            String categ = constraintForACell.get(0).categ(); // Element 1 : of MyPair -> Category
            Integer index = constraintForACell.get(0).index(); // Element 2 : of MyPair -> Index of the letter in the word. Ex : if it is about the 4th cell in the main array and the category is emotion and the index is 2 it means that the second cell of the main array contrains the 3rd(2+1) letter of the word from emotion
            Set<Character> result = new HashSet<Character>(varDomain.get(categ).get(index));
            for (MyPair pair : constraintForACell) { // Browse the list of pair of constraints for a given cell of the main array 
                result.retainAll(varDomain.get(pair.categ()).get(pair.index()));
            }
            domainLetter.add(new HashSet<Character>(result));
            
        }

    }

    /* Determines if the assignment is complete by looking
    at each cell of the main array to see if they are all different
    form null */
    private boolean isCompleteLetter(String[] assignment){
        boolean res = true;
        for (String cellValue : assignment) {
            if (cellValue == null) {
                res = false;
            }  
        }
        return res;
    }
    
    
    /* Determines if the assignment is consistent by going through 
    every constraints */
    private boolean isConsistentLetter(String[] assignment, int var, String value){
        assignment[var] = value;
        
        // For every constraint in puzzle constraint . Ex : "ajdective : 2 3 9"
        for(Map.Entry<String, List<Integer>> entry : puzzleConstraints.entrySet()) {
            boolean partialResult = false;
            String categ = entry.getKey();
            
            // Indexes in the main array corresponding to the 1st, 2nd and 3rd letter of the word
            List<Integer> valueList = entry.getValue();

            // List of possible words for a given category
            ArrayList<String> wordList = this.wordValues.get(categ);

            for (String word : wordList) {
                /* Warning : zero-based index */
                String wordFirstLetter = Character.toString(word.charAt(0));
                String wordSecondLetter = Character.toString(word.charAt(1));
                String wordThirdLetter = Character.toString(word.charAt(2));
                
                String assignmentLetterForCorrespondingIndex1 = assignment[valueList.get(0)-1]; 
                String assignmentLetterForCorrespondingIndex2 = assignment[valueList.get(1)-1]; 
                String assignmentLetterForCorrespondingIndex3 = assignment[valueList.get(2)-1]; 
                
                if ((wordFirstLetter.equals(assignmentLetterForCorrespondingIndex1) || assignmentLetterForCorrespondingIndex1==null) 
                        && (wordSecondLetter.equals(assignmentLetterForCorrespondingIndex2) || assignmentLetterForCorrespondingIndex2==null) 
                        && (wordThirdLetter.equals(assignmentLetterForCorrespondingIndex3) || assignmentLetterForCorrespondingIndex3==null)) {
                    partialResult = true; 
                }
            }
            if (partialResult == false){
                return false;
            }
        }
        assignment[var] = null;
        return true;
    }
    
    /* Old version without heuristic: Return the first letter that has 
    been been assigned     yet in the order of the index of the main array */
    public int selectUnassignedVariableLetter(String[] assignment){
        for(int i = 0 ; i < assignment.length ; i++){
            if(assignment[i] == null){
                return i;
            }
        }
        return -1;
    }
    
    /* Return the first letter that has been been assigned 
    yet in the order of the index of the main array */
    public int selectMostConstrainingUnassignedVariableLetter(String[] assignment){
        int result = -1;
        int size = -1;
        for(int i = 0 ; i < assignment.length ; i++){
            if(assignment[i] == null && constraints.get(i).size() > size){
                result = i;
                size = constraints.get(i).size();
            }
        }
        return result;
    }
    
    
    
    /* Recursive search for the letter-based assignment problem */
    public boolean RecursiveSearchLetter(String[] assignment){
        if (isCompleteLetter(assignment)){
            ArrayList<String> currAssignment = ArrayToArrayList(assignment.clone());
            currAssignment.add("Success");
            letterTraceSummary.add(currAssignment);
            return false; // to get all solutions
        }

        // Choose most constraining value
        int var = selectMostConstrainingUnassignedVariableLetter(assignment);
        // Browse the possible values for this variable
        for(Character character : domainLetter.get(var)) {
            String letter = (String) character.toString();
            if (isConsistentLetter(assignment, var, letter)){
                assignment[var]= letter;
                boolean result = RecursiveSearchLetter(assignment.clone());
                if(result!=false){
                    return true;
                }
                assignment[var]=null;
            }
            else{
                ArrayList<String> currAssignment = ArrayToArrayList(assignment.clone());
                currAssignment.add("Fail");
                letterTraceSummary.add(currAssignment);
            }
        }
        return false;
    }
    
    /* print the trace for the letter-based assignment problem */
    public void printTreeSearchLetter(){
        ArrayList<String> solutionSummary = new ArrayList<String>(); // Will contain all solution
        String res = "";
        System.out.println("search order: Most constraining variables first");
        
        // print first line
        System.out.print("root");
        for(int j=0; j<this.letterTraceSummary.get(0).size();j++){
            System.out.print("->"+letterTraceSummary.get(0).get(j));
            res+=letterTraceSummary.get(0).get(j);
        }
        System.out.println("");
        // We browse the array letterTraceSummary which contrains every list
        // of letters that conducted either to a fail or a complete consistent assignment
        for(int i=1;i<letterTraceSummary.size();i++){
            res = "";
            System.out.print("    ");
            int j = 0;
            // While the letters are the same as the previous entry in letterTraceSummary
            // we just print some spaces.
            while(letterTraceSummary.get(i).get(j)==letterTraceSummary.get(i-1).get(j) && j<letterTraceSummary.get(i).size()){
                System.out.print("   ");
                res+=letterTraceSummary.get(i).get(j);
                j++;
            }
            // While the letter are not the same we print them to the trace
            while(j<letterTraceSummary.get(i).size()-1){
                System.out.print("->"+letterTraceSummary.get(i).get(j));
                res+=letterTraceSummary.get(i).get(j);
                j++;
            }
            // If the last value of the array is Success, we print the result
            // If the last value is fail, we print fail
            if(letterTraceSummary.get(i).get(j).equals("Success") ){
                solutionSummary.add(res);
                System.out.println(" (found  result: " + res+ ")");                
            }

            else 
                System.out.println(" Fail");
            
        }
        // At the end we print a summary of all the solutions 
        // we have found for the given problem
        System.out.println("");
        System.out.println("Solutions summary:");
        for(String solution : solutionSummary){
            System.out.println(solution);
        }
    }
    
    /* Help function to convert a String[] to ArrayList<String> */
    private ArrayList<String> ArrayToArrayList(String[] array){
        ArrayList<String> result = new ArrayList<String>();
        for(int i=0;i<array.length;i++){
            if(array[i]!=null)
                result.add(array[i]);
        }
        return result;
    }
    
    /* Determines if the assignment is complete by looking
    if the number of words currently assigned is the 
    number of the categories in the problem */
    private boolean isCompleteWord(ArrayList<String> wordTrace){
        if(wordTrace.size()==this.categList.size())
            return true;
        else
            return false;
    }
    
    
    /* Determines if the assignment is consistent by going through 
    every constraints */
    private boolean isConsistentWord(String[] assignment){
        
        // For every constraint in puzzle constraint . Ex : "ajdective : 2 3 9"
        for(Map.Entry<String, List<Integer>> entry : puzzleConstraints.entrySet()) {
            boolean partialResult = false;
            
            String categ = entry.getKey();
            // Indexes in the main array corresponding to the 1st, 2nd and 3rd letter of the word
            List<Integer> valueList = entry.getValue();

            // List of possible words for a given category
            ArrayList<String> wordList = this.wordValues.get(categ);

            for (String word : wordList) {
                /* Warning : zero-based index */
                String wordFirstLetter = Character.toString(word.charAt(0));
                String wordSecondLetter = Character.toString(word.charAt(1));
                String wordThirdLetter = Character.toString(word.charAt(2));
                
                String assignmentLetterForCorrespondingIndex1 = assignment[valueList.get(0)-1]; 
                String assignmentLetterForCorrespondingIndex2 = assignment[valueList.get(1)-1]; 
                String assignmentLetterForCorrespondingIndex3 = assignment[valueList.get(2)-1]; 
                
                if ((wordFirstLetter.equals(assignmentLetterForCorrespondingIndex1) || assignmentLetterForCorrespondingIndex1==null) 
                        && (wordSecondLetter.equals(assignmentLetterForCorrespondingIndex2) || assignmentLetterForCorrespondingIndex2==null) 
                        && (wordThirdLetter.equals(assignmentLetterForCorrespondingIndex3) || assignmentLetterForCorrespondingIndex3==null)) {
                    partialResult = true; 
                }
            }
            if (partialResult == false){
                return false;
            }
        }
        return true;
    }
    
    /* Select randomly a word from the set of unassigned words */
    public String selectUnassignedVariableWord(HashSet<String> assignedWords){
        HashSet<String> unassignedWord = new HashSet<String>(this.categList);
        unassignedWord.removeAll(assignedWords);
        Iterator iter = unassignedWord.iterator();
        return (String)iter.next();
    }
    
    /* Recursive search for the word-based assignment problem */
    public boolean RecursiveSearchWord(String[] assignment, HashSet<String> assignedWords,ArrayList<String> wordTrace){
        if (isCompleteWord(wordTrace)){
            ArrayList<String> copy = (ArrayList<String>)wordTrace.clone();
            String res="";
            for(int i=0;i<assignment.length;i++){
                res+=assignment[i];
            }
            copy.add(res);
            copy.add("Success");
            wordTraceSummary.remove(wordTraceSummary.size()-1); // To avoid duplicates
            wordTraceSummary.add(copy);
            return false; // to get all solutions
        }

        // Variable selection
        String categ = selectUnassignedVariableWord(assignedWords);
        ArrayList<String> wordList = this.wordValues.get(categ);
        assignedWords.add(categ);
        // for every word for the given category
        for (String currWord : wordList) { 
            // keep track of the assignment order
            if(!this.categAssignmentOrder.contains(categ)){
                this.categAssignmentOrder.add(categ);
            }
            ArrayList<String> copy = (ArrayList<String>)wordTrace.clone();
            copy.add(currWord);
            wordTraceSummary.add(copy);
            // Save the current values in the main array
            String save0 = assignment[puzzleConstraints.get(categ).get(0)-1];
            String save1 = assignment[puzzleConstraints.get(categ).get(1)-1];
            String save2 = assignment[puzzleConstraints.get(categ).get(2)-1];
            // Try to see if the current word can fit with the words that has already been assigned
            if (save0==null || Character.toString(currWord.charAt(0)).equals(save0) 
                    && (save1==null || Character.toString(currWord.charAt(1)).equals(save1)) 
                    && (save2==null || Character.toString(currWord.charAt(2)).equals(save2))) {
                
                assignment[puzzleConstraints.get(categ).get(0)-1] = Character.toString(currWord.charAt(0));
                assignment[puzzleConstraints.get(categ).get(1)-1] = Character.toString(currWord.charAt(1));
                assignment[puzzleConstraints.get(categ).get(2)-1] = Character.toString(currWord.charAt(2));
            } else {
                continue;
            }
            if (isConsistentWord(assignment.clone())){
                boolean result = RecursiveSearchWord(assignment.clone(), (HashSet<String>)assignedWords.clone(),(ArrayList<String>)copy.clone());
                if(result!=false){
                    return true;
                }
                assignment[puzzleConstraints.get(categ).get(0)-1] = save0;
                assignment[puzzleConstraints.get(categ).get(1)-1] = save1;
                assignment[puzzleConstraints.get(categ).get(2)-1] = save2;
            }
            else {
                assignment[puzzleConstraints.get(categ).get(0)-1] = save0;
                assignment[puzzleConstraints.get(categ).get(1)-1] = save1;
                assignment[puzzleConstraints.get(categ).get(2)-1] = save2;
                copy.add("backtrack");
                wordTraceSummary.remove(wordTraceSummary.size()-1);
                wordTraceSummary.add(copy);
            }
        }
        assignedWords.remove(categ);
        return false;
    }
    
    /* print the trace for the word-based assignment problem */
    public void printTreeSearchWord(){
        
        // get rid of useless traces
        Iterator<ArrayList<String>> iter = this.wordTraceSummary.iterator();
        while (iter.hasNext()) {
          ArrayList<String> curr = iter.next();
          if (!curr.get(curr.size()-1).equals("Success") && !curr.get(curr.size()-1).equals("backtrack")) iter.remove();
        }
        
        // print the search order
        System.out.print("Search order : ");
        for(int l=0; l<this.categAssignmentOrder.size();l++){
            System.out.print(this.categAssignmentOrder.get(l)+", ");
        }
        System.out.println("");
        
        
        //print first line of the trace
        System.out.print("root");
        for(int j=0; j<this.wordTraceSummary.get(0).size();j++)
            System.out.print(" -> "+wordTraceSummary.get(0).get(j));
        
        System.out.println("");
        
        // browse the wordTraceSummary
        for(int i=1;i<wordTraceSummary.size();i++){
            int k = 0;
            System.out.print("    ");
            // While the letters are the same as the previous entry in wordTraceSummary
            // we just print some spaces.
            while(wordTraceSummary.get(i).get(k).equals(wordTraceSummary.get(i-1).get(k)) && k<wordTraceSummary.get(i).size()){
                System.out.print("       ");
                k++;
            }
            // While the words are not the same we print them to the trace
            while(k<wordTraceSummary.get(i).size()){
                if(wordTraceSummary.get(i).get(k).length()<8 || wordTraceSummary.get(i).get(k).equals("backtrack")){
                    System.out.print(" -> "+wordTraceSummary.get(i).get(k));
                    k++;
                }
                else{
                    System.out.print(" (found result: "+wordTraceSummary.get(i).get(k)+")");
                    break;
                }

            }
            System.out.println("");
        }
        // At the end we print a summary of all the solutions 
        // we have found for the given problem
        System.out.println("Solutions summary:");
        for(int i=1;i<wordTraceSummary.size();i++){
            int size = wordTraceSummary.get(i).size();
            if(!wordTraceSummary.get(i).get(size-1).equals("backtrack")){
                System.out.println(wordTraceSummary.get(i).get(size-2));
            }
        }
        
        
    }
    
}
