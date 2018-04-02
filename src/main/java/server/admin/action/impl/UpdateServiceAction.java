package server.admin.action.impl;

import entity.impl.Service;
import entity.transport.TransportEntity;
import server.admin.action.Action;
import server.admin.block.PessimisticBlockEnum;
import server.controller.Controller;
import server.controller.factory.SingletonControllerFactory;
import server.exceptions.DateConsecutionException;
import server.exceptions.DateFormatException;
import server.exceptions.DbAccessException;
import server.exceptions.WrongServiceTypeException;
import server.main.ServerProperties;

public class UpdateServiceAction implements Action {

    @Override
    public TransportEntity perform(TransportEntity request, String userId) throws DbAccessException, WrongServiceTypeException, DateFormatException, DateConsecutionException {
        Service currentlyUpdatedService = (Service) request.getRequsetObject();
        TransportEntity response = new TransportEntity();
        int serviceId = currentlyUpdatedService.getId();
        Controller controller = SingletonControllerFactory.getControllerFactory().getController(userId);
        if (PessimisticBlockEnum.DELETE_AND_UPDATE_SERVICE_BLOCK.checkPermission(serviceId, userId)) {
            PessimisticBlockEnum.DELETE_CLIENT_BLOCK.addBlock(userId, controller.getServiceClientId(serviceId), ServerProperties.getInstance().getThreadPerformBlock(), false);
            PessimisticBlockEnum.DELETE_AND_UPDATE_SERVICE_BLOCK.addBlock(userId, serviceId, ServerProperties.getInstance().getThreadPerformBlock(), false);
            SingletonControllerFactory.getControllerFactory().getController(userId).updateService(currentlyUpdatedService);
            response.setMessage("Service updated");
            PessimisticBlockEnum.DELETE_CLIENT_BLOCK.removeBlock(userId, controller.getServiceClientId(serviceId));
            PessimisticBlockEnum.DELETE_AND_UPDATE_SERVICE_BLOCK.removeBlock(userId, serviceId);
        } else {
            response.setMessage("Error: Operation is blocked at this time, try again later");
        }
        return response;
    }
}
