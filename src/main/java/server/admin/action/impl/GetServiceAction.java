package server.admin.action.impl;

import entity.ProviderEntity;
import entity.transport.TransportEntity;
import server.admin.action.Action;
import server.controller.factory.SingletonControllerFactory;
import server.exceptions.DateConsecutionException;
import server.exceptions.DateFormatException;
import server.exceptions.DbAccessException;
import server.exceptions.WrongServiceTypeException;

import java.util.ArrayList;

public class GetServiceAction implements Action {
    @Override
    public TransportEntity perform(TransportEntity request, String userId) throws DbAccessException, WrongServiceTypeException, DateFormatException, DateConsecutionException {
        TransportEntity response = new TransportEntity();
        ArrayList<ProviderEntity> responseProviderEntities = new ArrayList<>();
        responseProviderEntities.add(SingletonControllerFactory.getControllerFactory().getController(userId).getService((Integer) request.getRequsetObject()));
        response.setResponseProviderEntities(responseProviderEntities);
        if(responseProviderEntities.isEmpty()) {
            response.setMessage("No such service");
        }
        return response;
    }
}
