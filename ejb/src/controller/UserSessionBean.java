package controller;

import admin.block.PessimisticBlockEnum;
import exceprion.DateOrderException;
import model.client.LocalClient;
import model.client.LocalClientHome;
import model.service.LocalService;
import model.service.LocalServiceHome;
import model.service.types.ServiceType;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.*;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.*;

@Stateless(name = "UserSessionEJB")
public class UserSessionBean {
    private String login;

    @EJB
    private LocalClientHome clientHome;
    @EJB
    private LocalServiceHome serviceHome;

    public UserSessionBean() {
    }

    public void setLogin(String login) {
        if(this.login == null) {
            for (PessimisticBlockEnum blocksMap : PessimisticBlockEnum.values()) {
                blocksMap.addUser(login);
            }
            this.login = login;
        }
    }

    @PostConstruct
    public void init() {
        try {
            clientHome = (LocalClientHome) new InitialContext().lookup("java:module/ClientEJB!model.client.LocalClientHome");
            serviceHome = (LocalServiceHome) new InitialContext().lookup("java:module/ServiceEJB!model.service.LocalServiceHome");
        } catch (NamingException e) {
            e.printStackTrace();
            throw new EJBException(e.getMessage());
        }
    }

    @PreDestroy
    public void removeBlocks() {
        for (PessimisticBlockEnum blocksMap : PessimisticBlockEnum.values()) {
            blocksMap.removeUser(login);
        }
        System.out.println(login + "'s session bean destroyed");
    }

    public Collection<LocalClient> getAllClients() throws FinderException {
        System.out.println(login + " tries to get all clients");
        Collection clientCollection = clientHome.findAll();
        System.out.println(login + " gets all clients");
        return clientCollection;
    }

    public String addClient (String name, String info) throws CreateException {
        clientHome.create(name, info);
        return "Client " + name + " successfully added";
    }

    public String deleteClient (Integer id) throws FinderException, RemoveException {
        LocalClient deletedClient = clientHome.findByPrimaryKey(id);
        String deletedClientName = deletedClient.getName();
        for (LocalService clientsService:getServicesByClientId(id)) {
            clientsService.remove();
        }
        deletedClient.remove();
        return "Client " + deletedClientName + " successfully deleted";
    }

    public String addService(Integer clientId, String name, ServiceType type, Date startDate, Date endDate) throws CreateException {
        if(startDate.after(endDate)) return "ERROR: Provision date can't be after disablicng date";
        serviceHome.create(clientId, name, type, new java.sql.Date(startDate.getTime()), new java.sql.Date(endDate.getTime()));
        return "Service " + name + " successfully added";
    }

    public String deleteService (Integer id) throws FinderException, RemoveException {
        LocalService deletedService = serviceHome.findByPrimaryKey(id);
        String deletedServiceName = deletedService.getName();
        deletedService.remove();
        return "Service " + deletedServiceName + " successfully deleted";
    }

    public String updateService (Integer id, String name, Date startDate, Date endDate) throws FinderException, RemoveException, DateOrderException {
        if(startDate.after(endDate)) return "ERROR: Provision date can't be after disablicng date";
        LocalService updatedService = serviceHome.findByPrimaryKey(id);
        String previousName = updatedService.getName();
        updatedService.setName(name);
        updatedService.setStartDate(new java.sql.Date(startDate.getTime()));
        updatedService.setEndDate(new java.sql.Date(endDate.getTime()));
        return "Service " + previousName + " successfully updated to " + name;
    }

    public LocalClient getClient(Integer id) throws FinderException {
        return clientHome.findByPrimaryKey(id);
    }


    public LocalClient getClientOfService(Integer serviceId) throws FinderException {
        return clientHome.findByPrimaryKey(serviceHome.findByPrimaryKey(serviceId).getClientId());
    }

    public LocalService getService(Integer id) throws FinderException {
        return serviceHome.findByPrimaryKey(id);
    }

    public Collection<LocalService> getServicesByClientId(Integer clientId) throws FinderException {
        return serviceHome.findByClientId(clientId);
    }

    public Collection<ServiceType> getAllServiceTypes() {
        return new ArrayList<>(Arrays.asList(ServiceType.values()));
    }

    public Collection<ServiceType> getUnusedServiceTypes(Integer clientId) throws FinderException {
        ArrayList<ServiceType> unusedTypes = new ArrayList<>(Arrays.asList(ServiceType.values()));
        for (LocalService usedService:getServicesByClientId(clientId)) {
            unusedTypes.remove(usedService.getType());
        }
        return unusedTypes;
    }

    public Map<LocalService, LocalClient> getServiceClientMap(ServiceType type) throws FinderException {
        Collection<LocalService> servicesByType = serviceHome.findByType(type);
        Map<LocalService, LocalClient> serviceClientMap = new HashMap<>();
        for(LocalService service:servicesByType) {
            LocalClient client = getClientOfService((Integer) service.getPrimaryKey());
            serviceClientMap.put(service, client);
        }
        return serviceClientMap;
    }

}
