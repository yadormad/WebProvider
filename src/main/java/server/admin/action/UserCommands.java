package server.admin.action;

import entity.transport.TransportEntity;
import server.admin.action.impl.*;
import server.exceptions.DateFormatException;
import server.exceptions.DbAccessException;
import server.exceptions.WrongServiceTypeException;

public enum UserCommands {
    ADDCLIENT(new AddClientAction()),
    ADDSERVICE(new AddServiceAction()),
    ALLCLIENTS(new AllClientsAction()),
    ALLSERVICES(new AllServicesAction()),
    CHECKID(new CheckIdAction()),
    GETCLIENT(new GetClientAction()),
    CLIENTSERVICES(new ClientServicesAction()),
    DELETE(new DeleteEntityAction()),
    UPDSERVICE(new UpdateServiceAction()),
    QUIT(new ExitAction()),
    EXIT(new ExitAction());

    private Action action;

    UserCommands(Action action) {
        this.action = action;
    }

    public TransportEntity perform(TransportEntity request, String userId) throws WrongServiceTypeException, DbAccessException, DateFormatException {
        return action.perform(request, userId);
    }
}
