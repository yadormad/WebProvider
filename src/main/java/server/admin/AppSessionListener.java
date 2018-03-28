package server.admin;

import server.admin.connection.UserConnections;
import server.controller.factory.SingletonControllerFactory;
import server.main.ServerProperties;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class AppSessionListener implements HttpSessionListener {
    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {
        System.out.println("Session for user " + httpSessionEvent.getSession().getId() + " created");
        //httpSessionEvent.getSession().setMaxInactiveInterval(15/*Math.toIntExact(ServerProperties.getInstance().getThreadExecTimeout())/1000*/);
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {

        System.out.println("Session for user " + httpSessionEvent.getSession() + " destroyed");
        String userId = httpSessionEvent.getSession().getId();
        SingletonControllerFactory.getControllerFactory().removeController(userId);
        UserConnections.getInstance().removeUser(userId);
    }
}
