package server.admin.action.impl;

import entity.impl.Service;
import entity.transport.TransportEntity;
import server.admin.action.Action;
import server.controller.factory.SingletonControllerFactory;
import server.exceptions.DateConsecutionException;
import server.exceptions.DateFormatException;
import server.exceptions.DbAccessException;
import server.exceptions.WrongServiceTypeException;

import java.util.Collection;


public class ClientServicesAction implements Action {

    @Override
    public TransportEntity perform(TransportEntity request, String userId) throws DbAccessException, WrongServiceTypeException, DateFormatException, DateConsecutionException {
        TransportEntity response = new TransportEntity();
        Collection<Service> clientServices = SingletonControllerFactory.getControllerFactory().getController(userId).getClientServices((Integer)request.getRequsetObject());
        if(clientServices.isEmpty()) {
            response.setMessage("Error: No services added yet for this client");
            response.setResponseProviderEntities(clientServices);
        }
        else
            response.setResponseProviderEntities(clientServices);
        return response;
    }
}
