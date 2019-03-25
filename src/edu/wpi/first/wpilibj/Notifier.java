package edu.wpi.first.wpilibj;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Notifier implements Runnable {
    private Thread t;
    private ScheduledExecutorService timer;
    private Runnable runnable;

    private boolean isRunning;

    public Notifier(Runnable runnable) {
        this.runnable = runnable;
        t = new Thread(runnable, "Notifier_Thread");
        timer = Executors.newSingleThreadScheduledExecutor();
        timer.scheduleAtFixedRate(runnable, 0, 10, TimeUnit.MILLISECONDS);
        this.isRunning = true;
        t.start();
    }

    @Override
    public void run() {
        System.out.println("Ot");
        if (isRunning) {
            runnable.run();
        }
    }

    public void startPeriodic(double period) {
        this.isRunning = true;
    }
    public void stop() {
        this.isRunning = false;
    }
}
