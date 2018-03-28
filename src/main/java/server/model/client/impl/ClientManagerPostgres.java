package server.model.client.impl;

import entity.impl.Client;
import server.exceptions.DbAccessException;
import server.main.ServerProperties;
import server.model.client.ClientManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

public class ClientManagerPostgres implements ClientManager {
    private String dataBaseURL, login, password;
    public ClientManagerPostgres() {
        dataBaseURL = ServerProperties.getInstance().getPostgresDatabaseURL();
        login = ServerProperties.getInstance().getPostgresLogin();
        password = ServerProperties.getInstance().getPostgresPass();
    }

    @Override
    public Collection<Client> getAllClients() throws DbAccessException {
        ArrayList<Client> allClients = new ArrayList<>();

        Connection conn = null;
        PreparedStatement getAllClientsSQLStatement = null;
        ResultSet allClientsResultSet = null;
        try {
            conn = DriverManager.getConnection(dataBaseURL, login, password);
            getAllClientsSQLStatement = conn.prepareStatement("SELECT * FROM public.clients");
            allClientsResultSet = getAllClientsSQLStatement.executeQuery();
            while(allClientsResultSet.next()){
                allClients.add(parseResultSet(allClientsResultSet));
            }
        } catch (SQLException e) {
            throw new DbAccessException("Database connection error");
        } finally {
            try { Objects.requireNonNull(allClientsResultSet).close(); } catch (Exception e) { /* ignored */ }
            try { Objects.requireNonNull(getAllClientsSQLStatement).close(); } catch (Exception e) { /* ignored */ }
            try { Objects.requireNonNull(conn).close(); } catch (Exception e) { /* ignored */ }
        }
        return allClients;
    }

    @Override
    public void addClient(Client newClient) throws DbAccessException {
        Connection conn = null;
        PreparedStatement addClientStatement = null;
        try {
            conn = DriverManager.getConnection(dataBaseURL, login, password);
            addClientStatement = conn.prepareStatement("INSERT INTO public.clients VALUES" +
                    "(nextval('public.client_id_sequence'), ?, ?)");
            addClientStatement.setString(1, newClient.getName());
            addClientStatement.setString(2, newClient.getInfo());
            addClientStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DbAccessException("Database connection error");
        } finally {
            try { Objects.requireNonNull(conn).close(); } catch (Exception e) { /* ignored */ }
            try { Objects.requireNonNull(addClientStatement).close(); } catch (Exception e) { /* ignored */ }
        }
    }

    @Override
    public void deleteClient(int clientId) throws DbAccessException {
        Connection conn = null;
        PreparedStatement deleteClientStatement = null;
        try {
            conn = DriverManager.getConnection(dataBaseURL, login, password);
            deleteClientStatement = conn.prepareStatement("DELETE FROM public.clients WHERE id = ?");
            deleteClientStatement.setInt(1, clientId);
            deleteClientStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DbAccessException("Database connection error");
        } finally {
            try { Objects.requireNonNull(conn).close(); } catch (Exception e) { /* ignored */ }
            try { Objects.requireNonNull(deleteClientStatement).close(); } catch (Exception e) { /* ignored */ }
        }
    }

    @Override
    public void commit() throws DbAccessException {
//        PostgresCommitUtil.commit(dataBaseURL);
    }

    @Override
    public Client getClientById(int clientId) throws DbAccessException {
        Client client = null;
        Connection conn = null;
        PreparedStatement getClientByIdSQLStatement = null;
        ResultSet clientResultSet = null;
        try {
            conn = DriverManager.getConnection(dataBaseURL, login, password);
            getClientByIdSQLStatement = conn.prepareStatement("SELECT * FROM public.clients WHERE id = ?");
            getClientByIdSQLStatement.setInt(1, clientId);
            clientResultSet = getClientByIdSQLStatement.executeQuery();
            if(clientResultSet.next()){
                client = parseResultSet(clientResultSet);
            }
        } catch (SQLException e) {
            throw new DbAccessException("Database connection error");
        } finally {
            try { Objects.requireNonNull(clientResultSet).close(); } catch (Exception e) { /* ignored */ }
            try { Objects.requireNonNull(getClientByIdSQLStatement).close(); } catch (Exception e) { /* ignored */ }
            try { Objects.requireNonNull(conn).close(); } catch (Exception e) { /* ignored */ }
        }
        return client;
    }

    private Client parseResultSet(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String name = resultSet.getString("name");
        String info = resultSet.getString("info");
        return new Client(id, name, info);
    }
}
