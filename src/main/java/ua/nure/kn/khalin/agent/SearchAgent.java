package main.java.ua.nure.kn.khalin.agent;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.TickerBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import main.java.ua.nure.kn.khalin.agent.exceptions.SearchException;
import main.java.ua.nure.kn.khalin.db.DaoFactory;
import main.java.ua.nure.kn.khalin.db.DatabaseException;

import java.util.Collection;

public class SearchAgent extends Agent {
    private DFAgentDescription agentDescription;
    private ServiceDescription serviceDescription;
    private AID[] aids;
    private SearchGui agentGui;

    @Override
    protected void setup() {
        super.setup();
        agentDescription = new DFAgentDescription();
        serviceDescription = new ServiceDescription();
        serviceDescription.setName("JADE-Searching");
        serviceDescription.setType("searching");

        agentGui = new SearchGui(this);
        agentGui.setVisible(true);

        System.out.println("Agent " + getAID().getName() + " was started.");
        addBehaviour(new TickerBehaviour(this, 60000) {
            @Override
            protected void onTick() {
                DFAgentDescription agentDescription = new DFAgentDescription();
                ServiceDescription serviceDescription = new ServiceDescription();
                serviceDescription.setType("searching");
                agentDescription.addServices(serviceDescription);

                DFAgentDescription[] descriptions = null;
                try {
                    descriptions = DFService.search(myAgent, agentDescription);
                } catch (FIPAException e) {
                    e.printStackTrace();
                }

                aids = new AID[descriptions.length];

                for(int i = 0; i < aids.length; i++)
                    aids[i] = descriptions[i].getName();
            }
        });
        addBehaviour(new RequestServer());

        agentDescription.setName(getAID());
        agentDescription.addServices(serviceDescription);

        try {
            DFService.register(this, agentDescription);
        } catch (FIPAException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void takeDown() {
        super.takeDown();
        agentGui.setVisible(false);
        agentGui.dispose();
        System.out.println("Agent " + getAID().getName() + " finished.");
        try {
            DFService.deregister(this);
        } catch (FIPAException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addBehaviour(Behaviour b) {
        super.addBehaviour(b);
    }

    public void search(String firstName, String lastName) throws SearchException {
        try {
            Collection users= DaoFactory.getInstance().getUserDao().findByName(firstName, lastName);
            if(users.size() > 0) {
                showUsers(users);
            }
            else {
                addBehaviour(new SearchRequestBehaviour(new AID[] {}, firstName, lastName));
            }
        } catch (DatabaseException e) {
            e.printStackTrace();
            throw new SearchException("Search ended unsuccessfully");
        }
    }

    public void showUsers(Collection users) {
        agentGui.addUsers(users);
    }
}
