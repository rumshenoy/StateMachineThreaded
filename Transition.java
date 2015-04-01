import java.util.HashSet;
import java.util.Set;

/**
 * Created by ramyashenoy on 10/23/14.
 */
public class Transition {
    State source;

    State target;

    Condition condition;

    public Transition(State source, State target) {
        this.source = source;
        this.target = target;
        this.condition = null;
    }

    public State getSource() {
        return source;
    }

    public void setSource(State source) {
        this.source = source;
    }

    public State getTarget() {
        return target;
    }

    public void setTarget(State target) {
        this.target = target;
    }

    public Condition getCondition() {
        return condition;
    }

    public void setCondition(Condition condition) {
        this.condition = condition;
    }

    public Transition(State source, State target, Condition condition) {
        this.condition = condition;
        this.target = target;
        this.source = source;
    }
}
