package ru.example.multithreading;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        int threadCount = Math.max(4, Math.min(8, Runtime.getRuntime().availableProcessors()));
        int incrementsPerThread = 100_000;
        int performanceOperationsPerThread = 250_000;

        System.out.println("Домашнее задание №6");
        System.out.println("Теория и практика многопоточности");
        System.out.println("Доступно процессоров JVM: " + Runtime.getRuntime().availableProcessors());
        System.out.println();

        System.out.println("=== 1. volatile: безопасная остановка потока ===");
        new VolatileStopExample().runDemo();

        System.out.println();
        System.out.println("=== 2. AtomicInteger: потокобезопасный счётчик ===");
        new AtomicCounterExample().runDemo(threadCount, incrementsPerThread);

        System.out.println();
        System.out.println("=== 3. AtomicReference: singleton-кэш ===");
        new AtomicReferenceExample().runDemo(threadCount);

        new PerformanceTest().runAll(threadCount, performanceOperationsPerThread);

        System.out.println();
        System.out.println("=== 4. Некорректный пример: race condition ===");
        new UnsafeCounterExample().runDemo(threadCount, incrementsPerThread);

        System.out.println();
        System.out.println("Задание завершено.");
    }
}
