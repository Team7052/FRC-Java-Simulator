package driverstation.states;

public enum DSRobotState implements DriverStationStates {
    teleop, autonomous, practice, test;

    public static driverstation.states.DSRobotState toState(String str) {
        String lowercased = str.toLowerCase();
        for (driverstation.states.DSRobotState state: driverstation.states.DSRobotState.values()) {
            if (state.toString().equals(lowercased)) return state;
        }
        return null;
    }
}
