package driverstation.states;

import java.util.ArrayList;

public class StateManager {
    public interface StateManagerDelegate {
        void stateManagerWillUpdate(DSState newState);
        void stateManagerWillUpdate(DSRobotState newState);
        void stateManagerWillUpdate(DSRobotEnabledState newState);
        void stateManagerWillUpdate(DSUIState newState);
        void stateManagerDidUpdate(DSState oldState);
        void stateManagerDidUpdate(DSRobotState oldState);
        void stateManagerDidUpdate(DSRobotEnabledState oldState);
        void stateManagerDidUpdate(DSUIState newState);
    }

    private static ArrayList<StateManagerDelegate> delegates = new ArrayList<>();
    private static DSState dsState = DSState.main;
    private static DSRobotState dsRobotState = DSRobotState.teleop;
    private static DSRobotEnabledState dsRobotEnabledState = DSRobotEnabledState.disabled;
    private static DSUIState dsUIState = new DSUIState(false, false);

    public static void addDelegate(StateManagerDelegate delegate) {
        StateManager.delegates.add(delegate);
    }

    public static void removeDelegate(StateManagerDelegate delegate) {
        StateManager.delegates.remove(delegate);
    }

    public static void initialize() {
        updateDSState(dsState);
        updateDSRobotState(dsRobotState);
        updateDSRobotEnabledState(dsRobotEnabledState);
    }

    public static void updateDSState(DSState state) {
        for (StateManagerDelegate delegate: delegates) {
            delegate.stateManagerWillUpdate(state);
        }

        DSState oldState = StateManager.dsState;
        StateManager.dsState = state;
        for (StateManagerDelegate delegate: delegates) {
            delegate.stateManagerDidUpdate(oldState);
        }
    }
    public static void updateDSRobotState(DSRobotState state) {
        for (StateManagerDelegate delegate: delegates) {
            delegate.stateManagerWillUpdate(state);
        }
        DSRobotState oldState = StateManager.dsRobotState;
        StateManager.dsRobotState = state;
        for (StateManagerDelegate delegate: delegates) {
            delegate.stateManagerDidUpdate(oldState);
        }
    }

    public static void updateDSRobotEnabledState(DSRobotEnabledState state) {
        for (StateManagerDelegate delegate: delegates) {
            delegate.stateManagerWillUpdate(state);
        }
        DSRobotEnabledState oldState = dsRobotEnabledState;
        StateManager.dsRobotEnabledState = state;
        for (StateManagerDelegate delegate: delegates) {
            delegate.stateManagerDidUpdate(oldState);
        }
    }

    public static void updateDSUIState(boolean isBirdsEyeViewVisible, boolean isSideViewVisible) {
        DSUIState newState = new DSUIState(isBirdsEyeViewVisible, isSideViewVisible);
        for (StateManagerDelegate delegate: delegates) {
            delegate.stateManagerWillUpdate(newState);
        }
        DSUIState oldState = dsUIState;
        StateManager.dsUIState = newState;
        for (StateManagerDelegate delegate: delegates) {
            delegate.stateManagerDidUpdate(oldState);
        }
    }

    public static DSState getDSState() {
        return dsState;
    }

    public static DSRobotState getDSRobotState() {
        return dsRobotState;
    }

    public static DSRobotEnabledState getDSRobotEnabledState() {
        return dsRobotEnabledState;
    }

    public static DSUIState getDSUIState() {
        return dsUIState;
    }
}
