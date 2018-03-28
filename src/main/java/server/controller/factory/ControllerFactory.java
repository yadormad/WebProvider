package server.controller.factory;

import server.controller.Controller;

public interface ControllerFactory {
    Controller getController(String userId);
    void removeController(String userId);
}
