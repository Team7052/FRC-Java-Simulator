package edu.wpi.first.wpilibj.command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Scheduler {
    private static Scheduler instance;
    private HashMap<String, Command> commands;
    public static Scheduler getInstance() {
        if (instance == null) {
            instance = new Scheduler();
        }
        return instance;
    }
    private Scheduler() {
        this.commands = new HashMap<>();
    }

    public void add(Command command) {
        this.commands.put(command.toString(), command);
    }

    public void removeAll() {
        this.commands.clear();
    }

    public void run() {
        for (Map.Entry<String, Command> entrySet: this.commands.entrySet()) {
            entrySet.getValue().execute();
        }
    }
}
