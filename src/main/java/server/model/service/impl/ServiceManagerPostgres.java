package server.model.service.impl;

import entity.impl.Service;
import entity.impl.ServiceType;
import server.exceptions.DbAccessException;
import server.exceptions.WrongServiceTypeException;
import server.main.ServerProperties;
import server.model.service.ServiceManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Objects;

public class ServiceManagerPostgres implements ServiceManager{
    private String dataBaseURL, login, password;

    public ServiceManagerPostgres() {
        dataBaseURL = ServerProperties.getInstance().getPostgresDatabaseURL();
        login = ServerProperties.getInstance().getPostgresLogin();
        password = ServerProperties.getInstance().getPostgresPass();
    }

    @Override
    public Collection<Service> getAllServices() throws DbAccessException, WrongServiceTypeException {
        ArrayList<Service> allServices = new ArrayList<>();
        Connection conn = null;
        PreparedStatement getAllServicesSQLStatement = null;
        ResultSet allServicesResultSet = null;
        try {
            conn = DriverManager.getConnection(dataBaseURL, login, password);
            getAllServicesSQLStatement = conn.prepareStatement("SELECT * FROM public.services");
            allServicesResultSet = getAllServicesSQLStatement.executeQuery();
            fillServiceCollectionFromResultSet(allServicesResultSet, allServices);
        } catch (SQLException e) {
            throw new DbAccessException("Database connection error");
        } finally {
            try { Objects.requireNonNull(allServicesResultSet).close(); } catch (Exception e) { /* ignored */ }
            try { Objects.requireNonNull(getAllServicesSQLStatement).close(); } catch (Exception e) { /* ignored */ }
            try { Objects.requireNonNull(conn).close(); } catch (Exception e) { /* ignored */ }
        }
        return allServices;
    }

    @Override
    public void addService(Service newService) throws DbAccessException {
        Connection conn = null;
        PreparedStatement addServiceStatement = null;
        try {
            conn = DriverManager.getConnection(dataBaseURL, login, password);
            addServiceStatement = conn.prepareStatement("INSERT INTO public.services VALUES" +
                            "(nextval('public.service_id_sequence'), ?, ?, ?, ?, ?)");
            addServiceStatement.setInt(1, newService.getClientId());
            addServiceStatement.setString(2, newService.getName());
            addServiceStatement.setString(3, newService.getType().toString());
            addServiceStatement.setDate(4, new java.sql.Date(newService.getServiceProvisionDate().getTime()));
            addServiceStatement.setDate(5, new java.sql.Date(newService.getServiceDisablingDate().getTime()));
            addServiceStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DbAccessException("Database connection error");
        } finally {
            try { Objects.requireNonNull(conn).close(); } catch (Exception e) { /* ignored */ }
            try { Objects.requireNonNull(addServiceStatement).close(); } catch (Exception e) { /* ignored */ }
        }
    }

    @Override
    public void deleteService(int serviceId) throws DbAccessException {
        Connection conn = null;
        PreparedStatement deleteServiceStatement = null;
        try {
            conn = DriverManager.getConnection(dataBaseURL, login, password);
            deleteServiceStatement = conn.prepareStatement("DELETE FROM public.services WHERE id = ?");
            deleteServiceStatement.setInt(1, serviceId);
            deleteServiceStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DbAccessException("Database connection error");
        } finally {
            try { Objects.requireNonNull(conn).close(); } catch (Exception e) { /* ignored */ }
            try { Objects.requireNonNull(deleteServiceStatement).close(); } catch (Exception e) { /* ignored */ }
        }
    }

    @Override
    public void updateService(Service updService) throws DbAccessException {
        Connection conn = null;
        PreparedStatement updateServiceStatement = null;
        try {
            conn = DriverManager.getConnection(dataBaseURL, login, password);
            if(updService.getName() != null) {
                updateServiceStatement = conn.prepareStatement("UPDATE public.services SET name = ? WHERE id = ?");
                updateServiceStatement.setString(1, updService.getName());
                updateServiceStatement.setInt(2, updService.getId());
                updateServiceStatement.executeUpdate();
            }
            if(updService.getServiceProvisionDate() != null) {
                updateServiceStatement = conn.prepareStatement("UPDATE public.services SET startdate = ? WHERE id = ?");
                updateServiceStatement.setDate(1, new java.sql.Date(updService.getServiceProvisionDate().getTime()));
                updateServiceStatement.setInt(2, updService.getId());
                updateServiceStatement.executeUpdate();
            }
            if(updService.getServiceDisablingDate() != null) {
                updateServiceStatement = conn.prepareStatement("UPDATE public.services SET enddate = ? WHERE id = ?");
                updateServiceStatement.setDate(1, new java.sql.Date(updService.getServiceDisablingDate().getTime()));
                updateServiceStatement.setInt(2, updService.getId());
                updateServiceStatement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new DbAccessException("Database connection error");
        } finally {
            try { Objects.requireNonNull(conn).close(); } catch (Exception e) { /* ignored */ }
            try { Objects.requireNonNull(updateServiceStatement).close(); } catch (Exception e) { /* ignored */ }
        }
    }

    @Override
    public void commit() {
    }

    @Override
    public Collection<Service> getClientServices(int clientId) throws DbAccessException, WrongServiceTypeException {
        ArrayList<Service> clientServices = new ArrayList<>();
        Connection conn = null;
        PreparedStatement getClientServicesSQLStatement = null;
        ResultSet clientServicesResultSet = null;
        try {
            conn = DriverManager.getConnection(dataBaseURL, login, password);
            getClientServicesSQLStatement = conn.prepareStatement("SELECT * FROM public.services WHERE clientid = ?");
            getClientServicesSQLStatement.setInt(1, clientId);
            clientServicesResultSet = getClientServicesSQLStatement.executeQuery();
            fillServiceCollectionFromResultSet(clientServicesResultSet, clientServices);
        } catch (SQLException e) {
            throw new DbAccessException("Database connection error");
        } finally {
            try { Objects.requireNonNull(clientServicesResultSet).close(); } catch (Exception e) { /* ignored */ }
            try { Objects.requireNonNull(getClientServicesSQLStatement).close(); } catch (Exception e) { /* ignored */ }
            try { Objects.requireNonNull(conn).close(); } catch (Exception e) { /* ignored */ }
        }
        return clientServices;
    }

    @Override
    public int getServiceClientId(int serviceId) throws DbAccessException {
        Connection conn = null;
        PreparedStatement getServiceClientIdSQLStatement = null;
        ResultSet serviceResultSet = null;
        int clientId;
        try {
            conn = DriverManager.getConnection(dataBaseURL, login, password);
            getServiceClientIdSQLStatement = conn.prepareStatement("SELECT clientid FROM public.services WHERE id = ?");
            getServiceClientIdSQLStatement.setInt(1, serviceId);
            serviceResultSet = getServiceClientIdSQLStatement.executeQuery();
            serviceResultSet.next();
            clientId = serviceResultSet.getInt(1);
        } catch (SQLException e) {
            throw new DbAccessException("Database connection error");
        } finally {
            try { Objects.requireNonNull(serviceResultSet).close(); } catch (Exception e) { /* ignored */ }
            try { Objects.requireNonNull(getServiceClientIdSQLStatement).close(); } catch (Exception e) { /* ignored */ }
            try { Objects.requireNonNull(conn).close(); } catch (Exception e) { /* ignored */ }
        }
        return clientId;
    }

    @Override
    public Service getServiceById(int serviceId) throws DbAccessException {
        Service service = null;
        Connection conn = null;
        PreparedStatement getServiceByIdSQLStatement = null;
        ResultSet serviceResultSet = null;
        try {
            conn = DriverManager.getConnection(dataBaseURL, login, password);
            getServiceByIdSQLStatement = conn.prepareStatement("SELECT * FROM public.services WHERE id = ?");
            getServiceByIdSQLStatement.setInt(1, serviceId);
            serviceResultSet = getServiceByIdSQLStatement.executeQuery();
            if(serviceResultSet.next()){
                service = parseResultSet(serviceResultSet);
            }
        } catch (SQLException e) {
            throw new DbAccessException("Database connection error");
        } finally {
            try { Objects.requireNonNull(serviceResultSet).close(); } catch (Exception e) { /* ignored */ }
            try { Objects.requireNonNull(getServiceByIdSQLStatement).close(); } catch (Exception e) { /* ignored */ }
            try { Objects.requireNonNull(conn).close(); } catch (Exception e) { /* ignored */ }
        }
        return service;
    }

    private void fillServiceCollectionFromResultSet(ResultSet resultSet, Collection<Service> serviceCollection) throws WrongServiceTypeException, SQLException {
        try {
            while (resultSet.next()) {
                serviceCollection.add(parseResultSet(resultSet));
            }
        } catch (IllegalArgumentException e) {
            throw new WrongServiceTypeException("Service " + parseResultSet(resultSet).getId() + " has wrong type");
        }
    }

    private Service parseResultSet(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        int clientId = resultSet.getInt("clientid");
        String name = resultSet.getString("name");
        ServiceType type = ServiceType.valueOf(resultSet.getString("type").toUpperCase());
        Date startDate = resultSet.getDate("startdate");
        Date endDate = resultSet.getDate("enddate");
        return new Service(id, clientId, name, type, startDate, endDate);
    }
}
