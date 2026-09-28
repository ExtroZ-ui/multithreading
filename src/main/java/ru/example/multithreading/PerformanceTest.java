package ru.example.multithreading;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class PerformanceTest {

    private volatile boolean volatileFlag = false;

    public void runAll(int threadCount, int operationsPerThread) throws InterruptedException {
        System.out.println();
        System.out.println("=== Нагрузочное тестирование ===");

        volatileReadTest(threadCount, operationsPerThread);
        atomicIntegerTest(threadCount, operationsPerThread);
        atomicReferenceTest(threadCount, operationsPerThread);
    }

    private void volatileReadTest(int threadCount, int operationsPerThread) throws InterruptedException {
        List<Thread> threads = new ArrayList<>();

        long started = System.nanoTime();

        for (int i = 0; i < threadCount; i++) {
            Thread thread = new Thread(() -> {
                int local = 0;

                for (int j = 0; j < operationsPerThread; j++) {
                    if (volatileFlag) {
                        local++;
                    }
                }

                if (local == -1) {
                    System.out.println(local);
                }
            });

            threads.add(thread);
            thread.start();
        }

        for (Thread thread : threads) {
            thread.join();
        }

        long elapsed = System.nanoTime() - started;

        System.out.printf("[perf] volatile-read: %.3f ms%n", elapsed / 1_000_000.0);
    }

    private void atomicIntegerTest(int threadCount, int operationsPerThread) throws InterruptedException {
        AtomicInteger counter = new AtomicInteger();
        List<Thread> threads = new ArrayList<>();

        long started = System.nanoTime();

        for (int i = 0; i < threadCount; i++) {
            Thread thread = new Thread(() -> {
                for (int j = 0; j < operationsPerThread; j++) {
                    counter.incrementAndGet();
                }
            });

            threads.add(thread);
            thread.start();
        }

        for (Thread thread : threads) {
            thread.join();
        }

        long elapsed = System.nanoTime() - started;

        System.out.printf(
                "[perf] AtomicInteger: %.3f ms, result=%d%n",
                elapsed / 1_000_000.0,
                counter.get()
        );
    }

    private void atomicReferenceTest(int threadCount, int operationsPerThread) throws InterruptedException {
        AtomicSingletonCache cache = new AtomicSingletonCache();
        List<Thread> threads = new ArrayList<>();

        long started = System.nanoTime();

        for (int i = 0; i < threadCount; i++) {
            Thread thread = new Thread(() -> {
                for (int j = 0; j < operationsPerThread; j++) {
                    cache.getOrCreate();
                }
            });

            threads.add(thread);
            thread.start();
        }

        for (Thread thread : threads) {
            thread.join();
        }

        long elapsed = System.nanoTime() - started;

        System.out.printf(
                "[perf] AtomicReference cache: %.3f ms, creationAttempts=%d%n",
                elapsed / 1_000_000.0,
                cache.getCreationAttempts()
        );
    }
}
