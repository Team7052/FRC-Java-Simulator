package driverstation.states;

public enum DSState implements DriverStationStates {
    main, test, settings, joystick, lightning;
    public static DSState toState(String string) {
        for (DSState state: values()) {
            if (state.toString().equals(string)) return state;
        }
        return null;
    }
}