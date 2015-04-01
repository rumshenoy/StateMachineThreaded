import java.util.ArrayList;
import java.util.List;

/**
 * Created by ramyashenoy on 10/23/14.
 */
public class TestMachine {

    public static void main(String args[]){


        State initial = new State("Start");
        State checkout = new State("checkout");
        State fetchAddress = new State("fetch_address");
        State fetchCreditCard = new State("fetch_credit_card");
        State computeTotal = new State("compute_total");
        State verifyCreditCard = new State("verify_credit_card");
        State fetchDifferentCreditCard = new State("fetch_different_credit_card");
        State placeShippingOrder = new State("place_shipping_order");
        State finalState = new State("final");

        Condition approved = new Condition("approved");

        List<Transition> transitions = new ArrayList<Transition>();
        transitions.add(new Transition(initial, checkout));
        transitions.add(new Transition(checkout, fetchAddress));
        transitions.add(new Transition(fetchAddress, fetchCreditCard));
        transitions.add(new Transition(checkout, computeTotal));
        transitions.add(new Transition(fetchCreditCard, verifyCreditCard));
        transitions.add(new Transition(computeTotal, verifyCreditCard));
        transitions.add(new Transition(verifyCreditCard, placeShippingOrder, approved));
        transitions.add(new Transition(verifyCreditCard, fetchDifferentCreditCard, new Condition("")));
        transitions.add(new Transition(fetchDifferentCreditCard, verifyCreditCard));
        transitions.add(new Transition(placeShippingOrder, finalState));

        StateMachine stateMachine = new StateMachine(initial,transitions);
        stateMachine.addState(checkout);
        stateMachine.addState(fetchAddress);
        stateMachine.addState(fetchCreditCard);
        stateMachine.addState(placeShippingOrder);

        Thread newThread = new Thread(stateMachine);
        newThread.start();

    }
}
