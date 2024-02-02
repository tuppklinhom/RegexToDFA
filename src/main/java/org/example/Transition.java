package org.example;

class Transition {
    int fromState;
    char symbol;
    int toState;

    public Transition(int fromState, char symbol, int toState) {
        this.fromState = fromState;
        this.symbol = symbol;
        this.toState = toState;
    }

    @Override
    public String toString() {
        return "[" + fromState + ":" + symbol + ":" + toState + "]";
    }
}