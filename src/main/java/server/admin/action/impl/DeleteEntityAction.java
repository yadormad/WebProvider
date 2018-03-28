package server.admin.action.impl;

import entity.impl.Service;
import entity.transport.TransportEntity;
import server.admin.action.Action;
import server.admin.block.PessimisticBlockEnum;
import server.controller.Controller;
import server.controller.factory.SingletonControllerFactory;
import server.exceptions.DbAccessException;
import server.exceptions.WrongServiceTypeException;
import server.main.ServerProperties;

public class DeleteEntityAction implements Action{

    @Override
    public TransportEntity perform(TransportEntity request, String userId) throws DbAccessException, WrongServiceTypeException {
        TransportEntity response = new TransportEntity();
        String entityName = request.getMessage();
        Integer entityId = (Integer) request.getRequsetObject();
        Controller controller = SingletonControllerFactory.getControllerFactory().getController(userId);
        switch (entityName) {
            case "client":
                if (controller.containsClient(entityId)) {
                    if (PessimisticBlockEnum.DELETE_CLIENT_BLOCK.checkPermission(entityId, userId)) {
                        PessimisticBlockEnum.DELETE_CLIENT_BLOCK.addBlock(userId, entityId, ServerProperties.getInstance().getThreadPerformBlock());
                        for (Service clientService:controller.getClientServices(entityId)) {
                            PessimisticBlockEnum.DELETE_AND_UPDATE_SERVICE_BLOCK.addBlock(userId, clientService.getId(), ServerProperties.getInstance().getThreadPerformBlock());
                        }
                        controller.deleteClient(entityId);
                        response.setMessage("Client deleted");
                    } else {
                        response.setMessage("Error: Operation is blocked at this time, try again later");
                    }
                } else {
                    response.setMessage("Error: no such client");
                }
                break;
            case "service":
                if (controller.containsService(entityId)) {
                    if (PessimisticBlockEnum.DELETE_AND_UPDATE_SERVICE_BLOCK.checkPermission(entityId, userId)) {
                        PessimisticBlockEnum.DELETE_AND_UPDATE_SERVICE_BLOCK.addBlock(userId, entityId, ServerProperties.getInstance().getThreadPerformBlock());
                        PessimisticBlockEnum.DELETE_CLIENT_BLOCK.addBlock(userId, controller.getServiceClientId(entityId), ServerProperties.getInstance().getThreadPerformBlock());
                        controller.deleteService(entityId);
                        response.setMessage("Service deleted");
                    } else {
                        response.setMessage("Error: Operation is blocked at this time, try again later");
                    }
                } else {
                    response.setMessage("Error: no such service");
                }
        }
        PessimisticBlockEnum.DELETE_CLIENT_BLOCK.removeBlock(userId, entityId);
        PessimisticBlockEnum.DELETE_AND_UPDATE_SERVICE_BLOCK.removeBlock(userId, entityId);
        return response;
    }
}
