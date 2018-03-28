package server.controller.factory.impl;

import server.controller.Controller;
import server.controller.factory.ControllerFactory;
import server.controller.impl.TxtController;
import server.exceptions.DateFormatException;
import server.exceptions.DbAccessException;
import server.exceptions.WrongServiceTypeException;

public class TxtControllerFactory implements ControllerFactory {
    private Controller txtController = null;

    @Override
    public Controller getController(String userId) {
        if(txtController == null) {
            synchronized (TxtControllerFactory.class) {
                if(txtController == null) {
                    txtController = new TxtController();
                    try {
                        txtController.initController();
                    } catch (DbAccessException | DateFormatException | WrongServiceTypeException e) {
                        e.printStackTrace();
                        throw new RuntimeException(e);
                    }
                }
            }
        }
        return txtController;
    }

    @Override
    public void removeController(String userId) {
    }
}
