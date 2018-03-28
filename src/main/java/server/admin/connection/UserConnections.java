package server.admin.connection;

import entity.transport.TransportEntity;
import server.admin.connection.handler.UserHandler;
import server.exceptions.DateFormatException;
import server.exceptions.DbAccessException;
import server.exceptions.WrongServiceTypeException;
import server.main.ServerProperties;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;

/**
 * This class is responsible for...
 */
public class UserConnections {
    private ThreadPoolExecutor userThreadExecutor;
    private static UserConnections instance;
    private static Map<String, UserHandler> userHandlers;

    private UserConnections() {
        userThreadExecutor = new ThreadPoolExecutor(ServerProperties.getInstance().getThreadCoreSize(), ServerProperties.getInstance().getThreadMaxSize(), ServerProperties.getInstance().getThreadExecTimeout(), TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(ServerProperties.getInstance().getThreadQueueSize()));
        userThreadExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        userHandlers = new HashMap<>();
    }

    public static synchronized UserConnections getInstance() {
        if (instance == null)
            synchronized (UserConnections.class) {
                if (instance == null)
                    instance = new UserConnections();
            }
        return instance;
    }

    public TransportEntity perform(String userId, TransportEntity requestEntity) throws Throwable {
        System.out.println("User tries to perform: " + userId);
        System.out.println("Number of users now: " + userHandlers.size());
        System.out.println("Command: " + requestEntity.getCommand());
        userHandlers.get(userId).setRequest(requestEntity);
        System.out.println("Request is submitted to UserHandler");
        Future<TransportEntity> futureResponseEntity = userThreadExecutor.submit(userHandlers.get(userId));
        System.out.println("Task is submitted to ThreadPoolExecutor");
        TransportEntity responseEntity = null;
        try {
            responseEntity = futureResponseEntity.get();
        } catch (ExecutionException e) {
            throw e.getCause();
        } catch (InterruptedException ignored) {}
        return responseEntity;
    }

    public void addUser(String userId){
        UserHandler newUser = new UserHandler(userId);
        newUser.initBlocks();
        userHandlers.put(userId, newUser);
    }

    public void removeUser(String userId) {
        if(userHandlers.get(userId) != null) {
            userHandlers.get(userId).clearBlocks();
            userHandlers.remove(userId);
            System.out.println("User " + userId + " removed");
        }
    }

}
