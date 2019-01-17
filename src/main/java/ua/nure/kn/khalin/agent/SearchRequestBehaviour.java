package main.java.ua.nure.kn.khalin.agent;

import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;

public class SearchRequestBehaviour extends Behaviour {
    private AID[] aids;
    private String firstName;
    private String lastName;

    public SearchRequestBehaviour(AID[] aids, String firstName, String lastName) {
        this.aids = aids;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @Override
    public void action() {
        ACLMessage aclMessage = new ACLMessage(ACLMessage.REQUEST);
        aclMessage.setContent(firstName + "," + lastName);

        for(AID elem:aids) {
            aclMessage.addReceiver(elem);
        }
        myAgent.send(aclMessage);
    }

    @Override
    public boolean done() {
        return true;
    }
}
