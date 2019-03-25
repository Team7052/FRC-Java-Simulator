package edu.wpi.first.wpilibj.command;

public class Command {
    private String name;
    public Command(String name) {
        this.name = name;
    }

    public void requires(Subsystem subsystem) {

    }

    protected void initialize() {

    }
    protected void execute() {

    }

    public String getName() {
        return this.name;
    }


    protected boolean isFinished() {
        return true;
    }
}
