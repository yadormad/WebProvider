package server.admin.action.impl;

import entity.impl.Service;
import entity.transport.TransportEntity;
import server.admin.action.Action;
import server.admin.block.PessimisticBlockEnum;
import server.controller.factory.SingletonControllerFactory;
import server.exceptions.DbAccessException;
import server.exceptions.WrongServiceTypeException;
import server.main.ServerProperties;

public class AddServiceAction implements Action {
    @Override
    public TransportEntity perform(TransportEntity request, String userId) throws DbAccessException, WrongServiceTypeException {
        TransportEntity response = new TransportEntity();
        Service newService = (Service) request.getRequsetObject();
        if (PessimisticBlockEnum.ADD_SERVICE_TO_CLIENT_BLOCK.checkPermission(newService.getClientId(), userId)) {
            PessimisticBlockEnum.DELETE_CLIENT_BLOCK.addBlock(userId, newService.getClientId(), ServerProperties.getInstance().getThreadPerformBlock());
            PessimisticBlockEnum.ADD_SERVICE_TO_CLIENT_BLOCK.addBlock(userId, newService.getClientId(), ServerProperties.getInstance().getThreadPerformBlock());
            SingletonControllerFactory.getControllerFactory().getController(userId).addService(newService);
            response.setMessage("Service added");
        } else {
            response.setMessage("Operation is blocked at this time, try again later");
        }
        PessimisticBlockEnum.DELETE_CLIENT_BLOCK.removeBlock(userId, newService.getClientId());
        PessimisticBlockEnum.ADD_SERVICE_TO_CLIENT_BLOCK.removeBlock(userId, newService.getClientId());
        return response;
    }
}
