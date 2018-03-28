package server.admin.action.impl;

import entity.impl.Client;
import entity.transport.TransportEntity;
import server.admin.action.Action;
import server.controller.factory.SingletonControllerFactory;
import server.exceptions.DbAccessException;

public class AddClientAction implements Action {
    @Override
    public TransportEntity perform(TransportEntity request, String userId) throws DbAccessException{
        TransportEntity response = new TransportEntity();
        SingletonControllerFactory.getControllerFactory().getController(userId).addClient((Client)request.getRequsetObject());
        response.setMessage("Client added");
        return response;
    }
}
