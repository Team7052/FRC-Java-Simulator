package edu.wpi.first.wpilibj.command;

import java.util.ArrayList;

public class CommandGroup extends Command {
    ArrayList<Command> commands;
    public CommandGroup() {
        super("Command group");
        commands = new ArrayList<>();
    }

    public void addParallel(Command command) {
        this.commands.add(command);
    }

    @Override
    protected void execute() {
        super.execute();
        for (Command command: this.commands) {
            command.execute();
        }
    }
}
