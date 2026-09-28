package ru.example.multithreading;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class AtomicSingletonCache {

    private final AtomicReference<String> cache = new AtomicReference<>();
    private final AtomicInteger creationAttempts = new AtomicInteger();

    public String getOrCreate() {
        String current = cache.get();

        if (current != null) {
            return current;
        }

        int attempt = creationAttempts.incrementAndGet();
        String candidate = "Cached value created by " +
                Thread.currentThread().getName() +
                ", attempt=" + attempt;

        if (cache.compareAndSet(null, candidate)) {
            return candidate;
        }

        return cache.get();
    }

    public String get() {
        return cache.get();
    }

    public int getCreationAttempts() {
        return creationAttempts.get();
    }
}
