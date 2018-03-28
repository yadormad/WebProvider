package server.controller.factory.impl;

import server.controller.Controller;
import server.controller.factory.ControllerFactory;
import server.controller.impl.PostgresController;

import java.util.HashMap;
import java.util.Map;

public class PostgresControllerFactory implements ControllerFactory {
    private Map<String, Controller> userControllerMapping = new HashMap<>();

    @Override
    public Controller getController(String userId) {
        Controller userController = userControllerMapping.get(userId);
        if(userController == null) {
            userController = new PostgresController();
            userControllerMapping.put(userId, userController);
        }
        return userController;
    }

    @Override
    public void removeController(String userId) {
        userControllerMapping.remove(userId);
    }
}
