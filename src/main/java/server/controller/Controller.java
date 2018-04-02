package server.controller;

import entity.impl.Client;
import entity.impl.Service;
import login.LoginEntity;
import server.exceptions.DateConsecutionException;
import server.exceptions.DateFormatException;
import server.exceptions.DbAccessException;
import server.exceptions.WrongServiceTypeException;

import java.util.Collection;

public interface Controller {
    boolean checkUser(LoginEntity loginEntity) throws DbAccessException;
    void initController() throws DbAccessException, DateFormatException, WrongServiceTypeException;
    void addClient(Client newClient) throws DbAccessException;
    boolean containsClient(int clientId) throws DbAccessException;
    boolean containsService(int serviceId) throws DbAccessException;
    void deleteClient(int clientNumber) throws DbAccessException, WrongServiceTypeException;
    Collection<Client> getAllClients() throws DbAccessException;
    void addService(Service newService) throws WrongServiceTypeException, DbAccessException, DateConsecutionException;
    void deleteService(int serviceNumber) throws DbAccessException;
    void updateService(Service service) throws DbAccessException, DateConsecutionException;
    Collection<Service> getAllServices() throws WrongServiceTypeException, DbAccessException, DateFormatException;
    void commit() throws DbAccessException;
    Collection<Service> getClientServices(int clientId) throws DbAccessException, WrongServiceTypeException;
    int getServiceClientId(int serviceId) throws DbAccessException;
    Client getClient(int id) throws DbAccessException;
    Service getService(int id) throws DbAccessException;
}
