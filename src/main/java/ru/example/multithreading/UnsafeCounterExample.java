package ru.example.multithreading;

import java.util.ArrayList;
import java.util.List;

public class UnsafeCounterExample {

    public void runDemo(int threadCount, int incrementsPerThread) throws InterruptedException {
        UnsafeCounter counter = new UnsafeCounter();
        List<Thread> threads = new ArrayList<>();

        for (int i = 0; i < threadCount; i++) {
            Thread thread = new Thread(() -> {
                for (int j = 0; j < incrementsPerThread; j++) {
                    counter.increment();
                }
            }, "unsafe-worker-" + i);

            threads.add(thread);
        }

        for (Thread thread : threads) {
            thread.start();
        }

        for (Thread thread : threads) {
            thread.join();
        }

        int expected = threadCount * incrementsPerThread;

        System.out.println("[unsafe int] Ожидаемое значение: " + expected);
        System.out.println("[unsafe int] Фактическое значение: " + counter.get());
        System.out.println("[unsafe int] Потеряно инкрементов: " + (expected - counter.get()));
    }
}
