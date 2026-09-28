package ru.example.multithreading;

import java.util.concurrent.atomic.AtomicInteger;

public class AtomicCounter {

    private final AtomicInteger value = new AtomicInteger(0);

    public int increment() {
        return value.incrementAndGet();
    }

    public int get() {
        return value.get();
    }
}
