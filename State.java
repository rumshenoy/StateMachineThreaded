import java.util.Set;

/**
 * Created by ramyashenoy on 10/23/14.
 */
public class State {
    String name;
    Set<Action> actions;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public State(String name) {
        this.name = name;
    }
}
