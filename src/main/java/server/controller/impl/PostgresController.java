package server.controller.impl;

import entity.impl.Client;
import entity.impl.Service;
import login.LoginEntity;
import server.controller.Controller;
import server.exceptions.DateConsecutionException;
import server.exceptions.DateFormatException;
import server.exceptions.DbAccessException;
import server.exceptions.WrongServiceTypeException;
import server.model.client.ClientManager;
import server.model.client.impl.ClientManagerPostgres;
import server.model.service.ServiceManager;
import server.model.service.impl.ServiceManagerPostgres;
import server.model.user.UserManager;
import server.model.user.impl.UserManagerPostgres;

import java.util.Collection;

public class PostgresController implements Controller {

    private ClientManager clientManager;
    private ServiceManager serviceManager;
    private UserManager userManager;

    public PostgresController() {
        this.clientManager = new ClientManagerPostgres();
        this.serviceManager = new ServiceManagerPostgres();
        this.userManager = new UserManagerPostgres();
    }

    @Override
    public boolean checkUser(LoginEntity loginEntity) throws DbAccessException {
        return userManager.checkUser(loginEntity);
    }

    @Override
    public void initController() {

    }

    @Override
    public void addClient(Client newClient) throws DbAccessException {
        clientManager.addClient(newClient);
    }

    @Override
    public boolean containsClient(int clientId) throws DbAccessException {
        return clientManager.getClientById(clientId) != null;
    }

    @Override
    public boolean containsService(int serviceId) throws DbAccessException {
        return serviceManager.getServiceById(serviceId) != null;
    }

    @Override
    public void deleteClient(int clientId) throws DbAccessException, WrongServiceTypeException {
        for (Service nextClientService:getClientServices(clientId)) {
            serviceManager.deleteService(nextClientService.getId());
        }
        clientManager.deleteClient(clientId);
    }

    @Override
    public Collection<Client> getAllClients() throws DbAccessException {
        return clientManager.getAllClients();
    }

    @Override
    public void addService(Service newService) throws DbAccessException, DateConsecutionException {
        if(newService.getServiceProvisionDate().after(newService.getServiceDisablingDate())) throw new DateConsecutionException("Provision date must be after disabling date");
        serviceManager.addService(newService);
    }

    @Override
    public void deleteService(int serviceNumber) throws DbAccessException {
        serviceManager.deleteService(serviceNumber);
    }

    @Override
    public void updateService(Service service) throws DbAccessException, DateConsecutionException {
        if(service.getServiceProvisionDate().after(service.getServiceDisablingDate())) throw new DateConsecutionException("Provision date must be after disabling date");
        serviceManager.updateService(service);
    }

    @Override
    public Collection<Service> getAllServices() throws WrongServiceTypeException, DbAccessException, DateFormatException {
        return serviceManager.getAllServices();
    }

    @Override
    public void commit() throws DbAccessException {
        //clientManager.commit();
        serviceManager.commit();
    }

    @Override
    public Collection<Service> getClientServices(int clientId) throws DbAccessException, WrongServiceTypeException {
        return serviceManager.getClientServices(clientId);
    }

    @Override
    public int getServiceClientId(int serviceId) throws DbAccessException {
        return serviceManager.getServiceClientId(serviceId);
    }

    @Override
    public Client getClient(int id) throws DbAccessException {
        return clientManager.getClientById(id);
    }

    @Override
    public Service getService(int id) throws DbAccessException {
        return serviceManager.getServiceById(id);
    }
}
