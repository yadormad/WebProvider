package server.admin.action.impl;

import entity.transport.TransportEntity;
import server.admin.action.Action;
import server.controller.factory.SingletonControllerFactory;
import server.exceptions.DbAccessException;

public class AllClientsAction implements Action {

    @Override
    public TransportEntity perform(TransportEntity request, String userId) throws DbAccessException {
        TransportEntity response = new TransportEntity();
        response.setResponseProviderEntities(SingletonControllerFactory.getControllerFactory().getController(userId).getAllClients());
        if(SingletonControllerFactory.getControllerFactory().getController(userId).getAllClients().isEmpty()) {
            response.setMessage("No clients added yet");
        }
        return response;
    }
}
