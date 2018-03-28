package server.model.service;

import entity.impl.Client;
import entity.impl.Service;
import login.LoginEntity;
import server.exceptions.DateFormatException;
import server.exceptions.DbAccessException;
import server.exceptions.WrongServiceTypeException;

import java.util.Collection;

public interface ServiceManager {
    Collection<Service> getAllServices() throws DbAccessException, DateFormatException, WrongServiceTypeException;
    void addService(Service newService) throws DbAccessException;
    void deleteService(int serviceId) throws DbAccessException;
    void updateService(Service updService) throws DbAccessException;
    void commit() throws DbAccessException;
    Collection<Service> getClientServices(int clientId) throws DbAccessException, WrongServiceTypeException;
    int getServiceClientId(int serviceId) throws DbAccessException;
    Service getServiceById(int id) throws DbAccessException;
}
