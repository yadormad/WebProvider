package server.controller.impl;

import entity.impl.Client;
import entity.impl.Service;
import login.LoginEntity;
import server.controller.sequence.TxtIdSequence;
import server.exceptions.DateFormatException;
import server.exceptions.DbAccessException;
import server.exceptions.WrongServiceTypeException;
import server.controller.Controller;
import server.main.ServerProperties;
import server.model.client.ClientManager;
import server.model.client.impl.ClientManagerTxt;
import server.model.service.ServiceManager;
import server.model.service.impl.ServiceManagerTxt;

import java.util.ArrayList;
import java.util.Collection;

public class TxtController implements Controller{
    private static ArrayList<Client> clients;
    private static ArrayList<Service> services;
    private ClientManager clientManager;
    private ServiceManager serviceManager;
    private TxtIdSequence clientTxtIdSequence, serviceTxtIdSequence;

    public TxtController(){
        this.clientManager = new ClientManagerTxt(ServerProperties.getInstance().getTxtDatabasePath() + "clients.txt");
        this.serviceManager = new ServiceManagerTxt(ServerProperties.getInstance().getTxtDatabasePath() + "services.txt");
    }

    @Override
    public boolean checkUser(LoginEntity loginEntity) {
        return true;
    }

    @Override
    public void initController() throws DbAccessException, DateFormatException, WrongServiceTypeException {
        clients = (ArrayList<Client>) clientManager.getAllClients();
        services = (ArrayList<Service>) serviceManager.getAllServices();
        clientTxtIdSequence = new TxtIdSequence(clients);
        serviceTxtIdSequence = new TxtIdSequence(services);

    }

    @Override
    public void addClient(Client newClient) {
        newClient.setId(clientTxtIdSequence.getNextId());
        clients.add(newClient);
    }

    @Override
    public boolean containsClient(int clientId) {
        for (Client nextClient:clients) {
            if (Integer.valueOf(nextClient.getId()).equals(clientId))
                return true;
        }
        return false;
    }

    @Override
    public boolean containsService(int serviceId) {
        for (Service nextService:services) {
            if (Integer.valueOf(nextService.getId()).equals(serviceId))
                return true;
        }
        return false;
    }

    @Override
    public void deleteClient(int clientId) {
        if(!clients.isEmpty()) {
            Collection<Object> clientServices = new ArrayList<>();
            for (Service nextService : services) {
                if (((Integer) nextService.getClientId()).equals(clientId)) {
                    clientServices.add(nextService);
                }
            }
            services.removeAll(clientServices);
            for (Client nextClient : clients) {
                if (((Integer) nextClient.getId()).equals(clientId)) {
                    clients.remove(nextClient);
                    return;
                }
            }
        }
    }

    @Override
    public Collection<Client> getAllClients() {
        return clients;
    }

    @Override
    public void addService(Service newService) throws WrongServiceTypeException {
        if(!clients.isEmpty()) {
            ArrayList<Service> clientServices = (ArrayList<Service>) getClientServices(newService.getClientId());
            if(!clientServices.isEmpty()) {
                for (Service nextClientService : clientServices) {
                    if(nextClientService.getType().equals(newService.getType()))
                        throw new WrongServiceTypeException("This client already has a service with this type");
                }
            }
            for (Client client : clients) {
                if (((Integer) client.getId()).equals(newService.getClientId())) {
                    newService.setId(serviceTxtIdSequence.getNextId());
                    services.add(newService);
                    return;
                }
            }
        }
    }

    @Override
    public void deleteService(int serviceId) {
        if(!services.isEmpty()) {
            for (Service nextService : services) {
                if (((Integer) nextService.getId()).equals(serviceId)) {
                    services.remove(nextService);
                    return;
                }
            }
        }
    }

    @Override
    public void updateService(Service service) {
        if(!services.isEmpty()) {
            for (Service nextService : services) {
                if (((Integer) nextService.getId()).equals(service.getId())) {
                    nextService.setName(service.getName());
                    nextService.setServiceProvisionDate(service.getServiceProvisionDate());
                    nextService.setServiceDisablingDate(service.getServiceDisablingDate());
                    return;
                }
            }
        }
    }

    @Override
    public Collection<Service> getAllServices() {
        return services;
    }

    @Override
    public void commit() throws DbAccessException {
        clientManager.commit();
        serviceManager.commit();
    }

    @Override
    public Collection<Service> getClientServices(int clientId) {
        if(!services.isEmpty()) {
            ArrayList<Service> clientServices = new ArrayList<>();
            for (Service nextService : services) {
                if (((Integer) nextService.getClientId()).equals(clientId)) {
                    clientServices.add(nextService);
                }
            }
            return clientServices;
        } else
            return services;
    }

    @Override
    public int getServiceClientId(int serviceId) {
        if(!services.isEmpty()) {
            for (Service nextService : services) {
                if (((Integer) nextService.getId()).equals(serviceId)) {
                    return nextService.getId();
                }
            }
        }
        return -1;
    }

    @Override
    public Client getClient(int id) {
        return null;
    }

    @Override
    public Service getService(int id) {
        return null;
    }
}
