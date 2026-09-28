package ru.example.multithreading;

public class UnsafeCounter {

    private int value = 0;

    public void increment() {
        value++;
    }

    public int get() {
        return value;
    }
}
