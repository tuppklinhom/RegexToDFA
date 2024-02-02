package org.example;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.Stack;

public class RegexToNFA {
    private static int stateCounter = 0;

    private static int newCounter = 0;
    public static NFA regexToNFA(String regex) {
        Stack<NFA> stack = new Stack<>();
        char c;


        for (int i = 0; i < regex.length(); i++) {
            c = regex.charAt(i);
            if (isBasicSymbol(c)) {
                NFA nfa = createBasicNFA(c);
                //---------------------------------------
                System.out.println("===========basic==============");
                System.out.println("State: " + nfa.states);
                System.out.println("alphabets" + nfa.alphabet);
                System.out.println("Transition" + nfa.transitions.toString());
                System.out.println("Start: "+ nfa.startState);
                System.out.println("final: " + nfa.acceptingStates);
                System.out.println("===========basic==============");
                //-----------------------------------------
                stack.push(nfa);
            } else if (c == '*') {
                NFA nfa = createBasicNFA(regex.charAt(i-1));
                if(regex.charAt(i-1) == ')'){
                    nfa = stack.pop();
                }
                NFA closureNFA = applyClosure(nfa);
                //---------------------------------------
                System.out.println("===========star==============");
                System.out.println("State: " + closureNFA.states);
                System.out.println("alphabets" + closureNFA.alphabet);
                System.out.println("Transition" + closureNFA.transitions.toString());
                System.out.println("Start: "+ closureNFA.startState);
                System.out.println("final: " + closureNFA.acceptingStates);
                System.out.println("===========star==============");
                //-----------------------------------------
                stack.push(closureNFA);
            } else if (c == '|') {
                NFA nfa2 = regexToNFA(regex.substring(i+1));
                if(newCounter > i){
                    i = newCounter;
                }
                NFA nfa1 = stack.pop();
                NFA unionNFA = applyUnion(nfa1, nfa2);
                //---------------------------------------
                System.out.println("===========union==============");
                System.out.println("State: " + unionNFA.states);
                System.out.println("alphabets" + unionNFA.alphabet);
                System.out.println("Transition" + unionNFA.transitions.toString());
                System.out.println("Start: "+ unionNFA.startState);
                System.out.println("final: " + unionNFA.acceptingStates);
                System.out.println("===========union==============");
                //-----------------------------------------
                stack.push(unionNFA);
            } else if (c == '(') {
                int end = regex.indexOf(')');
                System.out.println("end = "+ end);
                NFA nfa = regexToNFA(regex.substring(i+1, end));
                if(newCounter > i){
                    i = newCounter;
                }
                //---------------------------------------
//                System.out.println("State: " + concatNFA.states);
//                System.out.println("alphabets" + concatNFA.alphabet);
//                System.out.println("Transition" + concatNFA.transitions.toString());
//                System.out.println("Start: "+ concatNFA.startState);
//                System.out.println("final: " + concatNFA.acceptingStates);
                //-----------------------------------------
                stack.push(nfa);
            }
            if(regex.length()-1 < i){
                if (stack.size() > 1 && regex.charAt(i+1) == '*') {
                    NFA nfa2 = stack.pop();
                    NFA nfa1 = stack.pop();
                    NFA concatNFA = applyConcatenation(nfa1, nfa2);
                    System.out.println("===========concatNFA==============");
                    System.out.println("State: " + concatNFA.states);
                    System.out.println("alphabets" + concatNFA.alphabet);
                    System.out.println("Transition" + concatNFA.transitions.toString());
                    System.out.println("Start: "+ concatNFA.startState);
                    System.out.println("final: " + concatNFA.acceptingStates);
                    System.out.println("===========concatNFA==============");
                    stack.push(concatNFA);
                }
            }

            System.out.println("=========" + i + "=============");
            newCounter++;

        }
//
        while (stack.size() > 1) {
            NFA nfa2 = stack.pop();
            NFA nfa1 = stack.pop();
            NFA concatNFA = applyConcatenation(nfa1, nfa2);
            stack.push(concatNFA);
        }

        System.out.println("Current regex: " + regex);
        return stack.pop();
    }

    private static boolean isBasicSymbol(char c) {
        // Check if the character is a basic symbol (not *, |, or .)
        return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z');
    }

    public static NFA createBasicNFA(char symbol) {
        int startState = stateCounter++;
        int endState = stateCounter++;
        Set<Integer> states = new HashSet<>();
        states.add(startState);
        states.add(endState);
        Set<Character> alphabet = new HashSet<>();
        alphabet.add(symbol);
        Set<Transition> transitions = new HashSet<>();
        transitions.add(new Transition(startState, symbol, endState));
        Set<Integer> acceptingStates = new HashSet<>();
        acceptingStates.add(endState);
        return new NFA(states, alphabet, transitions, startState, acceptingStates);
    }

    private static NFA applyClosure(NFA nfa) {
        int newStartState = stateCounter++;
        int newEndState = stateCounter++;
        Set<Integer> newStates = new HashSet<>(nfa.states);
        newStates.add(newStartState);
        newStates.add(newEndState);
        Set<Transition> newTransitions = new HashSet<>(nfa.transitions);
        newTransitions.add(new Transition(newStartState, 'ε', nfa.startState));
        newTransitions.add(new Transition(nfa.acceptingStates.iterator().next(), 'ε', newEndState));
        newTransitions.add(new Transition(newEndState, 'ε', newStartState));
        Set<Integer> newAcceptingStates = new HashSet<>();
        newAcceptingStates.add(newEndState);
        return new NFA(newStates, nfa.alphabet, newTransitions, newStartState, newAcceptingStates);
    }

    private static NFA applyUnion(NFA nfa1, NFA nfa2) {
        int newStartState = stateCounter++;
        int newEndState = stateCounter++;
        Set<Integer> newStates = new HashSet<>(nfa1.states);
        newStates.addAll(nfa2.states);
        newStates.add(newStartState);
        newStates.add(newEndState);
        Set<Transition> newTransitions = new HashSet<>(nfa1.transitions);
        newTransitions.addAll(nfa2.transitions);
        newTransitions.add(new Transition(newStartState, 'ε', nfa1.startState));
        newTransitions.add(new Transition(newStartState, 'ε', nfa2.startState));
        newTransitions.add(new Transition(nfa1.acceptingStates.iterator().next(), 'ε', newEndState));
        newTransitions.add(new Transition(nfa2.acceptingStates.iterator().next(), 'ε', newEndState));
        Set<Integer> newAcceptingStates = new HashSet<>();
        newAcceptingStates.add(newEndState);
        return new NFA(newStates, unionAlphabets(nfa1.alphabet, nfa2.alphabet), newTransitions, newStartState, newAcceptingStates);
    }

    private static NFA applyConcatenation(NFA nfa1, NFA nfa2) {
        Set<Integer> newState = unionState(nfa1.states, nfa2.states);
        Set<Transition> newTransitions = new HashSet<>(nfa1.transitions);
        newTransitions.addAll(nfa2.transitions);
        newTransitions.add(new Transition(nfa1.acceptingStates.iterator().next(), 'ε', nfa2.startState));
        Set<Integer> newAcceptingStates = new HashSet<>(nfa2.acceptingStates);
        return new NFA(newState, unionAlphabets(nfa1.alphabet, nfa2.alphabet), newTransitions, nfa1.startState, newAcceptingStates);
    }

    private static Set<Character> unionAlphabets(Set<Character> alphabet1, Set<Character> alphabet2) {
        Set<Character> unionAlphabet = new HashSet<>(alphabet1);
        unionAlphabet.addAll(alphabet2);
        return unionAlphabet;
    }

    private static Set<Integer> unionState(Set<Integer> state1, Set<Integer> state2){
        Set<Integer> newStates = new HashSet<>(state1);
        newStates.addAll(state2);
        return newStates;
    }

    public static void main(String[] args) {
        Scanner reader = new Scanner(System.in);
        System.out.println("Enter Regular Expression");

        String regex = reader.nextLine();

        NFA nfa = regexToNFA(regex);

        System.out.println("===========main==============");
        System.out.println("State: " + nfa.states);
        System.out.println("alphabets" + nfa.alphabet);
        System.out.println("Transition" + nfa.transitions.toString());
        System.out.println("Start: "+ nfa.startState);
        System.out.println("final: " + nfa.acceptingStates);


        // Print or use the resulting NFA as needed
    }
}