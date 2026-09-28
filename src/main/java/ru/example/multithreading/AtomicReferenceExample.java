package ru.example.multithreading;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class AtomicReferenceExample {

    public void runDemo(int threadCount) throws InterruptedException {
        AtomicSingletonCache cache = new AtomicSingletonCache();
        List<Thread> threads = new ArrayList<>();
        Set<String> observedValues = ConcurrentHashMap.newKeySet();

        for (int i = 0; i < threadCount; i++) {
            Thread thread = new Thread(() -> {
                String value = cache.getOrCreate();
                observedValues.add(value);
            }, "cache-worker-" + i);

            threads.add(thread);
        }

        for (Thread thread : threads) {
            thread.start();
        }

        for (Thread thread : threads) {
            thread.join();
        }

        System.out.println("[AtomicReference] Значение в кэше: " + cache.get());
        System.out.println("[AtomicReference] Различных опубликованных значений: " + observedValues.size());
        System.out.println("[AtomicReference] Попыток создать значение: " + cache.getCreationAttempts());
    }
}
