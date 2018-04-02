package server.admin.action.impl;

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

public class CheckIdAction implements Action{

    @Override
    public TransportEntity perform(TransportEntity request, String userId) throws DbAccessException, WrongServiceTypeException, DateFormatException, DateConsecutionException {
        TransportEntity response = new TransportEntity();
        response.setMessage("Error: unexpected");
        String command = request.getMessage();
        int entityId = (Integer) request.getRequsetObject();
        Controller controller = SingletonControllerFactory.getControllerFactory().getController(userId);
        switch (command) {
            case "addservice":
                response.setMessage("Error: no client with this id");
                if(controller.containsClient(entityId)) {
                    if (PessimisticBlockEnum.ADD_SERVICE_TO_CLIENT_BLOCK.checkPermission(entityId, userId)) {
                        PessimisticBlockEnum.DELETE_CLIENT_BLOCK.addBlock(userId, entityId, ServerProperties.getInstance().getThreadWaitingBlock(), false);
                        PessimisticBlockEnum.ADD_SERVICE_TO_CLIENT_BLOCK.addBlock(userId, entityId, ServerProperties.getInstance().getThreadWaitingBlock(), true);
                        response.setMessage("true");
                    } else {
                        response.setMessage("Error: Command blocked");
                    }
                }
                break;
            case "updservice":
                response.setMessage("Error: no service with this id");
                if(controller.containsService(entityId)) {
                    if (PessimisticBlockEnum.DELETE_AND_UPDATE_SERVICE_BLOCK.checkPermission(entityId, userId)) {
                        PessimisticBlockEnum.DELETE_CLIENT_BLOCK.addBlock(userId, controller.getServiceClientId(entityId), ServerProperties.getInstance().getThreadWaitingBlock(), false);
                        PessimisticBlockEnum.DELETE_AND_UPDATE_SERVICE_BLOCK.addBlock(userId, entityId, ServerProperties.getInstance().getThreadWaitingBlock(), true);
                        response.setMessage("true");
                    } else {
                        response.setMessage("Error: Command blocked");
                    }
                }
        }
        return response;
    }
}
