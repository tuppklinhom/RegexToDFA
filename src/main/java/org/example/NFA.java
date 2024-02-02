package org.example;


import java.util.Set;

class NFA {
    Set<Integer> states;
    Set<Character> alphabet;
    Set<Transition> transitions;
    int startState;
    Set<Integer> acceptingStates;

    public NFA(Set<Integer> states, Set<Character> alphabet, Set<Transition> transitions, int startState, Set<Integer> acceptingStates) {
        this.states = states;
        this.alphabet = alphabet;
        this.transitions = transitions;
        this.startState = startState;
        this.acceptingStates = acceptingStates;
    }


}
//
//
//import java.util.ArrayList;
//import java.util.HashMap;
//
//public class NFA {
//    ArrayList<String> states;
//    ArrayList<Character> alphabets;
//    HashMap<String, ArrayList<String>> transitions; //???
//    String startState;
//    ArrayList<String> acceptStates;
//
//    public NFA() {
//        states = new ArrayList<>();
//        alphabets = new ArrayList<>();
//        transitions = new HashMap<>();
//        startState = "";
//        acceptStates = new ArrayList<>();
//    }
//    /**================= add section ===================**/
//    public void addState(String newState) {
//        states.add(newState);
//    }
//
//    public void addAlphabet(char alphabet){
//        alphabets.add(alphabet);
//    }
//
//    public void addTransition(String start, ArrayList<String> destinations){
//        // add destination as ArrayList
//        transitions.put(start, destinations);
//    }
//
//    public void addStartState(String startState){
//        this.startState = startState;
//    }
//
//    public void addAcceptState(String accept){
//        acceptStates.add(accept);
//    }
//    /**=================================================**/
//
//    /**================= get section ===================**/
//    public ArrayList<String> getState() {
//        return states;
//    }
//
//    public HashMap<String, ArrayList<String>> getTransitions() {
//        return transitions;
//    }
//
//    public ArrayList<Character> getAlphabets() {
//        return alphabets;
//    }
//
//    public ArrayList<String> getStates() {
//        return states;
//    }
//
//    public ArrayList<String> getAcceptStates() {
//        return acceptStates;
//    }
//    /**================================================**/
//}
