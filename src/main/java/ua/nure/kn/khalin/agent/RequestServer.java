package main.java.ua.nure.kn.khalin.agent;

import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import main.java.ua.nure.kn.khalin.User;
import main.java.ua.nure.kn.khalin.agent.util.UserParametersValidator;
import main.java.ua.nure.kn.khalin.db.DaoFactory;
import main.java.ua.nure.kn.khalin.db.DatabaseException;

import java.util.*;

public class RequestServer extends CyclicBehaviour {
    @Override
    public void action() {
        ACLMessage message = myAgent.receive();
        if(Objects.nonNull(message)) {
            if(message.getPerformative() == ACLMessage.REQUEST) {
                myAgent.send(createReply(message));
            }
            else if(message.getPerformative() == ACLMessage.INFORM) {
                Collection users = parseMessage(message);
                ((SearchAgent) myAgent).showUsers(users);
            }
        }
        else block();
    }

    private Collection parseMessage(ACLMessage message) {
        Collection users = new LinkedList();
        String messageContent = message.getContent();
        StringTokenizer mcTokenizer = new StringTokenizer(messageContent, ";");

        while(mcTokenizer.hasMoreTokens()) {
            StringTokenizer parametersTokenizer = new StringTokenizer(mcTokenizer.nextToken(), ",");
            Long id = Long.parseLong(parametersTokenizer.nextToken());
            String firstName = parametersTokenizer.nextToken();
            String lastName = parametersTokenizer.nextToken();

            if(UserParametersValidator.validateUserParameters(id, firstName, lastName, new Date())) {
                User user = new User(id, firstName, lastName, new Date());
                ((LinkedList) users).add(user);
            }
        }
        return users;
    }

    private ACLMessage createReply(ACLMessage message) {
        ACLMessage replyMessage = message.createReply();

        String content = message.getContent();
        StringTokenizer tokenizer = new StringTokenizer(content, ",");
        if(tokenizer.countTokens() == 2) {
            String firstName = tokenizer.nextToken();
            String lastName = tokenizer.nextToken();

            Collection users = null;
            try {
                users = DaoFactory.getInstance().getUserDao().findByName(firstName, lastName);
            } catch (DatabaseException e) {
                e.printStackTrace();
                users = new ArrayList(0);
            }
            StringBuffer buffer = new StringBuffer();
            for(Iterator it = users.iterator(); it.hasNext();) {
                User user = (User) it.next();
                buffer.append(user.getId()).append(",");
                buffer.append(user.getFirstName()).append(",");
                buffer.append(user.getLastName()).append(",");
            }
            replyMessage.setContent(buffer.toString());
        }
        return replyMessage;
    }
}
