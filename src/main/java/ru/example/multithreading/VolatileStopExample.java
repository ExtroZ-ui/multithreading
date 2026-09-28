package ru.example.multithreading;

public class VolatileStopExample {

    private volatile boolean stopRequested = false;

    public void requestStop() {
        stopRequested = true;
    }

    public void runDemo() throws InterruptedException {
        Thread worker = new Thread(() -> {
            System.out.println("[volatile] Рабочий поток запущен.");

            long iterations = 0;
            while (!stopRequested) {
                iterations++;

                // Небольшая вычислительная работа.
                // Не используем sleep внутри цикла, чтобы флаг проверялся часто.
                double value = Math.sqrt(iterations);
                if (value < 0) {
                    System.out.println(value);
                }
            }

            System.out.println("[volatile] Рабочий поток остановлен. Итераций: " + iterations);
        }, "volatile-worker");

        worker.start();

        Thread.sleep(300);

        System.out.println("[volatile] Запрошена остановка.");
        requestStop();

        worker.join();
    }
}
