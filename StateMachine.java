import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * Created by ramyashenoy on 10/23/14.
 */
public class StateMachine implements Runnable, Cloneable {

    static List<StateMachine> clonedStateMachinesList = new ArrayList<StateMachine>();
    static Map<StateMachine, Thread> stateThreadMap = new HashMap<StateMachine, Thread>();
    ;
    List<Transition> transitions;
    State current;
    Set<State> states;


    StateMachine(State start, List<Transition> transitions) {
        this.current = start;
        this.transitions = transitions;
        this.states = new HashSet<State>();
    }

    List<State> getNextStates(String event) {
        List<State> returnStates = new ArrayList<State>();
        for (Transition transition : this.transitions) {
            if (transition.source.equals(current)) {
                if (transition.getCondition() != null) {
                    if (event.equalsIgnoreCase(transition.getCondition().getName()))
                        returnStates.add(transition.target);
                } else {
                    returnStates.add(transition.target);
                }
            }
        }
        return returnStates;
    }

    @Override
    public void run() {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        while (current.getName() != "final") {
            String event = null;

            try {
                System.out.println(Thread.currentThread().getName() + " CurrentState: " + current.getName() + ", Enter an Event ->");
                System.out.println("---------------------------------------------------------------------------------------");
                event = in.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }

            List<State> returnedStates;

            returnedStates = getNextStates(event);
            if (returnedStates.size() > 1) {
                for (int i = 1; i < returnedStates.size(); i++) {
                    State stateToChange = returnedStates.get(i);
                    try {
                        StateMachine clonedStateMachine = (StateMachine) this.clone();
                        clonedStateMachine.current = stateToChange;
                        clonedStateMachinesList.add(clonedStateMachine);
                        if (stateThreadMap.containsKey(clonedStateMachine)) {
                            Thread threadToKill = stateThreadMap.get(clonedStateMachine);
                            threadToKill.interrupt();
                            clonedStateMachinesList.remove(clonedStateMachine);
                            stateThreadMap.remove(clonedStateMachine);
                        }
                        Thread t = new Thread(clonedStateMachine);
                        stateThreadMap.put(clonedStateMachine, t);
                        t.start();
                    } catch (CloneNotSupportedException e) {
                        e.printStackTrace();
                    }
                }
            }
            current = returnedStates.get(0);
            if (stateThreadMap.containsKey(this)) {
                Thread threadToKill = stateThreadMap.get(this);
                if (threadToKill.getState() != Thread.State.TERMINATED) {
                    threadToKill.interrupt();
                    stateThreadMap.remove(this);
                }
                clonedStateMachinesList.remove(this);
            }
            stateThreadMap.put(this, Thread.currentThread());
        }

        for (StateMachine stateMachine : stateThreadMap.keySet()) {
            Thread thread = stateThreadMap.get(stateMachine);
            if (thread.getState() != Thread.State.TERMINATED) {
                thread.interrupt();
            }
        }
        System.out.println(Thread.currentThread().getName() + " CurrentState: " + current.getName() + ", Enter an Event ->");

    }

    public void addState(State state) {
        this.states.add(state);

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StateMachine that = (StateMachine) o;

        if (current != null ? !current.equals(that.current) : that.current != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = 0;
        result = 31 * result + (current != null ? current.hashCode() : 0);
        return result;
    }
}


