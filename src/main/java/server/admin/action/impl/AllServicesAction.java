package server.admin.action.impl;

import entity.transport.TransportEntity;
import server.admin.action.Action;
import server.controller.factory.SingletonControllerFactory;
import server.exceptions.DateFormatException;
import server.exceptions.DbAccessException;
import server.exceptions.WrongServiceTypeException;

public class AllServicesAction implements Action {
    @Override
    public TransportEntity perform(TransportEntity request, String userId) throws DbAccessException, WrongServiceTypeException, DateFormatException {
        TransportEntity response = new TransportEntity();
        response.setResponseProviderEntities(SingletonControllerFactory.getControllerFactory().getController(userId).getAllServices());
        if(SingletonControllerFactory.getControllerFactory().getController(userId).getAllServices().isEmpty()) {
            response.setMessage("No services added yet");
        }
        return response;
    }
}
