package server.controller.factory;

import server.controller.factory.impl.PostgresControllerFactory;
import server.controller.factory.impl.TxtControllerFactory;
import server.main.ServerProperties;

public class SingletonControllerFactory {
    private static ControllerFactory controllerFactoryInstance = null;

    public static ControllerFactory getControllerFactory() {
        if (controllerFactoryInstance == null)
            synchronized (ControllerFactory.class) {
                if (controllerFactoryInstance == null) {
                    if (ServerProperties.getInstance().isDBTxt()) {
                        controllerFactoryInstance = new TxtControllerFactory();
                    } else {
                        try {
                            Class.forName("org.postgresql.Driver");
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                            throw new RuntimeException();
                        }
                        controllerFactoryInstance = new PostgresControllerFactory();
                    }
                }
            }
        return controllerFactoryInstance;
    }
}
