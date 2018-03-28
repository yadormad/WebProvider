package server.admin;

import entity.transport.TransportEntity;
import login.LoginEntity;
import server.admin.connection.UserConnections;
import server.controller.factory.SingletonControllerFactory;
import server.exceptions.DbAccessException;

public class ServerCommunicator {

    public static TransportEntity perform(String userId, TransportEntity requestEntity) throws Throwable {
        return UserConnections.getInstance().perform(userId, requestEntity);
    }

    public static Boolean authorizeNewUser(String userId, LoginEntity loginEntity) {
        try {
            if (SingletonControllerFactory.getControllerFactory().getController(userId).checkUser(loginEntity)) {
                UserConnections.getInstance().addUser(userId);
                System.out.println("User connected id: " + userId);
                return Boolean.TRUE;
            } else {
                SingletonControllerFactory.getControllerFactory().removeController(userId);
                return Boolean.FALSE;
            }
        } catch (DbAccessException e) {
            e.printStackTrace();
            return Boolean.FALSE;
        }
    }
}
