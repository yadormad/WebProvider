package server.admin.action.impl;

import entity.ProviderEntity;
import entity.impl.Service;
import entity.impl.ServiceType;
import entity.transport.TransportEntity;
import server.admin.action.Action;
import server.controller.factory.SingletonControllerFactory;
import server.exceptions.DateConsecutionException;
import server.exceptions.DateFormatException;
import server.exceptions.DbAccessException;
import server.exceptions.WrongServiceTypeException;

import java.util.Collection;

public class GetServicesByTypeAction implements Action{

    @Override
    public TransportEntity perform(TransportEntity request, String userId) throws DbAccessException, WrongServiceTypeException, DateFormatException, DateConsecutionException {
        TransportEntity response = new TransportEntity();
        Collection<Service> responseProviderEntities = SingletonControllerFactory.getControllerFactory().getController(userId).getServicesByType(ServiceType.valueOf(request.getMessage()));
        response.setResponseProviderEntities(responseProviderEntities);
        if(responseProviderEntities.isEmpty()) {
            response.setMessage("No services with this type");
        }
        return response;
    }
}
