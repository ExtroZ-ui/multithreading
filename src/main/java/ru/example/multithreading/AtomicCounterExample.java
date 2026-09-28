package ru.example.multithreading;

import java.util.ArrayList;
import java.util.List;

public class AtomicCounterExample {

    public void runDemo(int threadCount, int incrementsPerThread) throws InterruptedException {
        AtomicCounter counter = new AtomicCounter();
        List<Thread> threads = new ArrayList<>();

        for (int i = 0; i < threadCount; i++) {
            Thread thread = new Thread(() -> {
                for (int j = 0; j < incrementsPerThread; j++) {
                    counter.increment();
                }
            }, "counter-worker-" + i);

            threads.add(thread);
        }

        long started = System.nanoTime();

        for (Thread thread : threads) {
            thread.start();
        }

        for (Thread thread : threads) {
            thread.join();
        }

        long elapsedNanos = System.nanoTime() - started;

        int expected = threadCount * incrementsPerThread;

        System.out.println("[AtomicInteger] Ожидаемое значение: " + expected);
        System.out.println("[AtomicInteger] Фактическое значение: " + counter.get());
        System.out.printf("[AtomicInteger] Время: %.3f ms%n", elapsedNanos / 1_000_000.0);
    }
}
