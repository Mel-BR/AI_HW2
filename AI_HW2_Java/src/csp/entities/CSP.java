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
    private final int problemSize; // Contains the size of the array
    
    public CSP(int arraySize, Map<String,ArrayList<String>> wordValues, Map<String,ArrayList<HashSet<Character>>> varDomain, Map<String, List<Integer>> puzzleConstraints) {
        this.domainLetter = new ArrayList<HashSet<Character>>();
        this.constraints = new ArrayList<ArrayList<MyPair>>(arraySize);
        this.puzzleConstraints = puzzleConstraints;
        this.wordValues = wordValues;
        this.letterTraceSummary = new ArrayList<ArrayList<String>>();
        this.categList = new HashSet<String>();
        this.wordTraceSummary = new ArrayList<ArrayList<String>>();
        this.categAssignmentOrder =  new ArrayList<String>();
        this.problemSize = arraySize;
        
        // Initializing constraints
        for (int i = 0; i<arraySize; i++){
            constraints.add(new ArrayList<MyPair>());
        }

        for(Map.Entry<String, List<Integer>> entry : puzzleConstraints.entrySet()) {
            String categ = entry.getKey();
            for (int i = 0; i<3; i++){
                constraints.get(puzzleConstraints.get(categ).get(i)-1).add(new MyPair(categ,i));
            }
            categList.add(categ);
        }
        
        // Goal: determining the domain for each variable, storing t in domain variable member
        for (int i = 0; i < arraySize; i++){ // Browse index of solution array
            int pairsListSize = constraints.get(i).size();
            Set<Character> result = new HashSet<Character>(varDomain.get(constraints.get(i).get(0).categ()).get(constraints.get(i).get(0).index()));
            for(int j=0; j<pairsListSize; j++){ // Browse list of Pairs
                result.retainAll(varDomain.get(constraints.get(i).get(j).categ()).get(constraints.get(i).get(j).index()));
            }
            domainLetter.add(new HashSet<Character>(result));
        }

    }

   
    private boolean isCompleteLetter(String[] assignment){
        boolean res = true;
        for(int i = 0 ; i < assignment.length ; i++){
            if(assignment[i] == null){
                res = false;
            }
            
        }
        return res;
    }
    
    private boolean isConsistentLetter(String[] assignment, int var, String value){
        assignment[var]=value;
        for(int i = 0 ; i < assignment.length ; i++){
            for(Map.Entry<String, List<Integer>> entry1 : puzzleConstraints.entrySet()) {
                boolean partialResult = false;
                String categ = entry1.getKey();
                List<Integer> valueList = entry1.getValue();
                
                ArrayList<String> wordList = this.wordValues.get(categ);
                
                for( int j = 0 ; j < wordList.size() ; j++) {
                    if((Character.toString(wordList.get(j).charAt(0)).equals(assignment[valueList.get(0)-1]) || assignment[valueList.get(0)-1]==null)
                            && (Character.toString(wordList.get(j).charAt(1)).equals(assignment[valueList.get(1)-1]) || assignment[valueList.get(1)-1]==null)
                            && (Character.toString(wordList.get(j).charAt(2)).equals(assignment[valueList.get(2)-1]) || assignment[valueList.get(2)-1]==null)){
                        partialResult = true; 
                    }
                }
                if (partialResult == false){
                    return false;
                }
            }
        }
        assignment[var]=null;
        return true;
    }
    
    public int selectUnassignedVariableLetter(String[] assignment){
        for(int i = 0 ; i < assignment.length ; i++){
            if(assignment[i] == null){
                return i;
            }
        }
        return -1;
    }
    

    public boolean RecursiveSearchLetter(String[] assignment){
        if (isCompleteLetter(assignment)){
            ArrayList<String> currAssignment = ArrayToArrayList(assignment);
            currAssignment.add("Success");
            letterTraceSummary.add(currAssignment);
            return false; // to get all solutions
        }

        // Choose the first index of the assignment that is not empty
        int var = selectUnassignedVariableLetter(assignment);
        // Browse the possible values for this variable
        for(Object object : domainLetter.get(var)) {
            String letter = (String) object.toString();
            if (isConsistentLetter(assignment, var, letter)){
                assignment[var]= letter;
                boolean result = RecursiveSearchLetter(assignment.clone());
                if(result!=false){
                    return true;
                }
                assignment[var]=null;
            }
            else{
                ArrayList<String> currAssignment = ArrayToArrayList(assignment);
                currAssignment.add("Fail");
                letterTraceSummary.add(currAssignment);
            }
        }
        return false;
    }
    
    public void printTreeSearchLetter(){
        String res = "";
        System.out.println("search order: index order of the letters in the result array");
        System.out.print("root");
        for(int j=0; j<this.letterTraceSummary.get(0).size();j++){
            System.out.print("->"+letterTraceSummary.get(0).get(j));
            res+=letterTraceSummary.get(0).get(j);
        }
        System.out.println("");
        for(int i=1;i<letterTraceSummary.size();i++){
            res = "";
            System.out.print("    ");
            int j = 0;
            while(letterTraceSummary.get(i).get(j)==letterTraceSummary.get(i-1).get(j) && j<letterTraceSummary.get(i).size()){
                System.out.print("   ");
                res+=letterTraceSummary.get(i).get(j);
                j++;
            }
            while(j<letterTraceSummary.get(i).size()-1){
                System.out.print("->"+letterTraceSummary.get(i).get(j));
                res+=letterTraceSummary.get(i).get(j);
                j++;
            }
            if(letterTraceSummary.get(i).get(j).equals("Success") )
                System.out.println(" (found  result: " + res+ ")");
            else 
                System.out.println(" Fail");
        }
    }
    
    
    private ArrayList<String> ArrayToArrayList(String[] array){
        ArrayList<String> result = new ArrayList<String>();
        int i = 0;
        while(i<array.length && array[i]!=null){
            result.add(array[i]);
            i++;
        }
        return result;
    }
    
    
    private boolean isCompleteWord(ArrayList<String> wordTrace){
        if(wordTrace.size()==this.categList.size())
            return true;
        else
            return false;
    }
    
    
    private boolean isConsistentWord(String[] assignment){
        for(int i = 0 ; i < assignment.length ; i++){
            for(Map.Entry<String, List<Integer>> entry1 : puzzleConstraints.entrySet()) {
                boolean partialResult = false;
                String categ = entry1.getKey();
                List<Integer> valueList = entry1.getValue();
                
                ArrayList<String> wordList = this.wordValues.get(categ);
                
                for( int j = 0 ; j < wordList.size() ; j++) {
                    if((Character.toString(wordList.get(j).charAt(0)).equals(assignment[valueList.get(0)-1]) || assignment[valueList.get(0)-1]==null)
                            && (Character.toString(wordList.get(j).charAt(1)).equals(assignment[valueList.get(1)-1]) || assignment[valueList.get(1)-1]==null)
                            && (Character.toString(wordList.get(j).charAt(2)).equals(assignment[valueList.get(2)-1]) || assignment[valueList.get(2)-1]==null)){
                        partialResult = true; 
                    }
                }
                if (partialResult == false){
                    return false;
                }
            }
        }
        return true;
    }
    
    public String selectUnassignedVariableWord(HashSet<String> assignedWords){
        HashSet<String> unassignedWord = new HashSet<String>(this.categList);
        unassignedWord.removeAll(assignedWords);
        Iterator iter = unassignedWord.iterator();
        return (String)iter.next();
    }
    
    public boolean RecursiveSearchWord(String[] assignment, HashSet<String> assignedWords,ArrayList<String> wordTrace){
        if (isCompleteWord(wordTrace)){
            /*for(int i=0;i<assignment.length;i++){
                System.out.print(assignment[i]);
            }*/
            ArrayList<String> copy = (ArrayList<String>)wordTrace.clone();
            String res="";
            for(int i=0;i<assignment.length;i++){
                res+=assignment[i];
            }
            copy.add(res);
            copy.add("Success");
            wordTraceSummary.remove(wordTraceSummary.size()-1);
            wordTraceSummary.add(copy);
            return false; // to get all solutions
        }

        //variables selections 
        String categ = selectUnassignedVariableWord(assignedWords);
        ArrayList<String> wordList = this.wordValues.get(categ);
        // Can't assign !!
        assignedWords.add(categ);
        for( int j = 0 ; j < wordList.size() ; j++) { // for every word for the given category
            if(!this.categAssignmentOrder.contains(categ)){
                this.categAssignmentOrder.add(categ);
            }
            ArrayList<String> copy = (ArrayList<String>)wordTrace.clone();
            copy.add(wordList.get(j));
            wordTraceSummary.add(copy);
            /*for(int k=0;k<copy.size();k++){
                System.out.print(copy.get(k)+" ");
            }
            System.out.println("");*/
            String save0 = assignment[puzzleConstraints.get(categ).get(0)-1];
            String save1 = assignment[puzzleConstraints.get(categ).get(1)-1];
            String save2 = assignment[puzzleConstraints.get(categ).get(2)-1];
            if(assignment[puzzleConstraints.get(categ).get(0)-1]==null 
                                        || Character.toString(wordList.get(j).charAt(0)).equals(assignment[puzzleConstraints.get(categ).get(0)-1]) 
                &&(assignment[puzzleConstraints.get(categ).get(1)-1]==null 
                                        || Character.toString(wordList.get(j).charAt(1)).equals(assignment[puzzleConstraints.get(categ).get(1)-1]))
                &&(assignment[puzzleConstraints.get(categ).get(2)-1]==null 
                                        || Character.toString(wordList.get(j).charAt(2)).equals(assignment[puzzleConstraints.get(categ).get(2)-1]))){
                
                assignment[puzzleConstraints.get(categ).get(0)-1] = Character.toString(wordList.get(j).charAt(0));
                assignment[puzzleConstraints.get(categ).get(1)-1] = Character.toString(wordList.get(j).charAt(1));
                assignment[puzzleConstraints.get(categ).get(2)-1] = Character.toString(wordList.get(j).charAt(2));
            }  
            else{
                /*copy.add("backtrack");
                wordTraceSummary.remove(wordTraceSummary.size()-1);
                wordTraceSummary.add(copy);*/
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
    
    public void printTreeSearchWord(){
        
        Iterator<ArrayList<String>> iter = this.wordTraceSummary.iterator();
        while (iter.hasNext()) {
          ArrayList<String> curr = iter.next();
          if (!curr.get(curr.size()-1).equals("Success") && !curr.get(curr.size()-1).equals("backtrack")) iter.remove();
        }
        
        System.out.print("Search order : ");
        for(int l=0; l<this.categAssignmentOrder.size();l++){
            System.out.print(this.categAssignmentOrder.get(l)+", ");
        }
        System.out.println("");
        
        System.out.print("root");
        for(int j=0; j<this.wordTraceSummary.get(0).size();j++)
            System.out.print(" -> "+wordTraceSummary.get(0).get(j));
        
        System.out.println("");
        
        for(int i=1;i<wordTraceSummary.size();i++){
            int k = 0;
            System.out.print("    ");
            while(wordTraceSummary.get(i).get(k).equals(wordTraceSummary.get(i-1).get(k)) && k<wordTraceSummary.get(i).size()){
                System.out.print("       ");
                k++;
            }
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
        
        
    }
    
}
