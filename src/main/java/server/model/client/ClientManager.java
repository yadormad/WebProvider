package server.model.client;

import entity.impl.Client;
import login.LoginEntity;
import server.exceptions.DbAccessException;

import java.util.Collection;

public interface ClientManager {
    Collection<Client> getAllClients() throws DbAccessException;
    void addClient(Client newClient) throws DbAccessException;
    void deleteClient(int clientId) throws DbAccessException;
    void commit() throws DbAccessException;

    Client getClientById(int id) throws DbAccessException;
}
