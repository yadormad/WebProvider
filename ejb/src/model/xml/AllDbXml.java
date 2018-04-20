package model.xml;


import model.client.LocalClient;
import model.client.LocalClientHome;
import model.service.LocalService;
import model.service.LocalServiceHome;

import javax.ejb.CreateException;
import javax.ejb.FinderException;
import javax.ejb.RemoveException;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.Collection;

@XmlRootElement(name = "provider")
public class AllDbXml {
    private Collection<ClientXml> clients;

    @XmlElement(name = "client")
    public Collection<ClientXml> getClients() {
        return clients;
    }

    public void setClients(Collection<ClientXml> clients) {
        this.clients = clients;
    }

    public void loadFromDb(LocalClientHome clientHome, LocalServiceHome serviceHome, Integer clientId) throws FinderException {
        clients = new ArrayList<>();
        if(clientId == null) {
            for (LocalClient client : (Collection<LocalClient>) clientHome.findAll()) {
                ClientXml clientXml = new ClientXml();
                clientXml.parseClientXml(client);
                clientXml.loadService(serviceHome);
                clients.add(clientXml);
            }
        } else {
            LocalClient client = clientHome.findByPrimaryKey(clientId);
            ClientXml clientXml = new ClientXml();
            clientXml.parseClientXml(client);
            clientXml.loadService(serviceHome);
            clients.add(clientXml);
        }
    }

    public void storeIntoDb(LocalClientHome clientHome, LocalServiceHome serviceHome) {
        Collection<LocalClient> allClients = new ArrayList<>();
        try {
            allClients = clientHome.findAll();
        } catch (FinderException e) {
            e.printStackTrace();
        }
        for (ClientXml clientXml:clients) {
            try {
                LocalClient clientBean = clientHome.findByPrimaryKey(clientXml.getId());
                allClients.remove(clientBean);
                clientBean.setName(clientXml.getName());
                clientBean.setInfo(clientXml.getInfo());
            } catch (FinderException e) {
                try {
                    clientHome.create(clientXml.getId(), clientXml.getName(), clientXml.getInfo());
                } catch (CreateException e1) {
                    e1.printStackTrace();
                }
            }
            clientXml.storeIntoDb(clientHome, serviceHome);
        }

        for (LocalClient client:allClients) {
            try {
                for (LocalService clientsService : (Collection<LocalService>)serviceHome.findByClientId((Integer) client.getPrimaryKey())) {
                    clientsService.remove();
                }
                client.remove();
            } catch (RemoveException e) {
                e.printStackTrace();
            } catch (FinderException e) {
                /*ignored*/
            }
        }
    }
}
