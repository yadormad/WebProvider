package server.admin.connection.handler;

import server.admin.StopThreadException;
import server.admin.action.UserCommands;
import server.admin.block.PessimisticBlockEnum;
import server.exceptions.DateConsecutionException;
import server.exceptions.DateFormatException;
import server.exceptions.DbAccessException;
import server.exceptions.WrongServiceTypeException;
import entity.transport.TransportEntity;

import java.util.concurrent.Callable;

public class UserHandler implements Callable<TransportEntity>{
    private TransportEntity request;
    private String userId;

    public UserHandler(String userId) {
        this.userId = userId;
    }

    public void setRequest(TransportEntity request) {
        this.request = request;
    }

    @Override
    public TransportEntity call() throws WrongServiceTypeException, DbAccessException, DateFormatException, DateConsecutionException {
        String command = request.getCommand();
        TransportEntity response = UserCommands.valueOf(command.toUpperCase()).perform(request, userId);
        if(response.getResponseProviderEntities() != null)
            System.out.println(response.getResponseProviderEntities().size());
        return response;
    }

    public void initBlocks() {
        for (PessimisticBlockEnum blocksMap : PessimisticBlockEnum.values()) {
            blocksMap.addUser(userId);
        }
    }

    public void clearBlocks() {
        for (PessimisticBlockEnum blocksMap : PessimisticBlockEnum.values()) {
            blocksMap.removeUser(userId);
        }
    }

}
