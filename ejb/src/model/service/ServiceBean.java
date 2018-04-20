package model.service;

import model.service.types.ServiceType;

import javax.ejb.*;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

public class ServiceBean implements EntityBean {

    private Integer clientId;
    private String name;
    private ServiceType type;
    private Date startDate, endDate;
    private DataSource dataSource;
    private EntityContext entityContext;

    public Integer getClientId() {
        return clientId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ServiceType getType() {
        return type;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public ServiceBean() {
    }

    public Integer ejbCreate(Integer clientId, String name, ServiceType type, Date startDate, Date endDate) throws CreateException {
        Connection conn = null;
        PreparedStatement createServiceStatement = null;
        ResultSet resultSet = null;
        try {
            conn = dataSource.getConnection();
            Integer serviceId = null;
            while (privateCreate(serviceId, clientId, name, type, startDate, endDate) == null) {
                createServiceStatement = conn.prepareStatement("SELECT nextval('public.service_id_sequence')");
                resultSet = createServiceStatement.executeQuery();
                resultSet.next();
                serviceId = resultSet.getInt(1);
            }
            return serviceId;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new EJBException(e.getMessage());
        } finally {
            closeAll(resultSet, createServiceStatement, conn);
        }
    }

    public Integer ejbCreate(Integer id, Integer clientId, String name, ServiceType type, Date startDate, Date endDate) throws CreateException {
        return privateCreate(id, clientId, name, type, startDate, endDate);
    }

    private Integer privateCreate(Integer id, Integer clientId, String name, ServiceType type, Date startDate, Date endDate) {
        try {
            ejbFindByPrimaryKey(id);
            return null;
        } catch (FinderException e) {
            Connection conn = null;
            PreparedStatement createServiceStatement = null;
            try {
                conn = dataSource.getConnection();
                createServiceStatement = conn.prepareStatement("INSERT INTO public.services VALUES" +
                        "(?, ?, ?, ?, ?, ?)");
                createServiceStatement.setInt(1, id);
                createServiceStatement.setInt(2, clientId);
                createServiceStatement.setString(3, name);
                createServiceStatement.setString(4, type.name());
                createServiceStatement.setDate(5, startDate);
                createServiceStatement.setDate(6, endDate);
                createServiceStatement.executeUpdate();
                this.clientId = clientId;
                this.name = name;
                this.type = type;
                this.startDate = startDate;
                this.endDate = endDate;
                return id;
            } catch (SQLException e1) {
                e1.printStackTrace();
                throw new EJBException(e1.getMessage());
            } finally {
                closeAll(createServiceStatement, conn);
            }
        }
    }

    public Integer ejbFindByPrimaryKey(Integer key) throws FinderException {
        Connection conn = null;
        PreparedStatement getServiceByIdSQLStatement = null;
        ResultSet clientResultSet = null;
        try {
            conn = dataSource.getConnection();
            getServiceByIdSQLStatement = conn.prepareStatement("SELECT id FROM public.services WHERE id = ?");
            getServiceByIdSQLStatement.setInt(1, key);
            clientResultSet = getServiceByIdSQLStatement.executeQuery();
            if(!clientResultSet.next()){
                throw new ObjectNotFoundException();
            } else {
                return key;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new EJBException("SELECT error");
        } finally {
            closeAll(clientResultSet, getServiceByIdSQLStatement, conn);
        }
    }

    public void setEntityContext(EntityContext entityContext) throws EJBException {
        this.entityContext = entityContext;
        try {
            dataSource = (DataSource) new InitialContext().lookup("java:/PostgresDS");
        } catch (NamingException e) {
            e.printStackTrace();
            throw new EJBException(e);
        }
    }

    public void unsetEntityContext() throws EJBException {
        this.entityContext = null;
    }

    public void ejbRemove() throws RemoveException, EJBException {
        Integer serviceId = (Integer) entityContext.getPrimaryKey();
        Connection conn = null;
        PreparedStatement deleteServiceStatement = null;
        try {
            conn = dataSource.getConnection();
            deleteServiceStatement = conn.prepareStatement("DELETE FROM public.services WHERE id = ?");
            deleteServiceStatement.setInt(1, serviceId);
            deleteServiceStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new EJBException("DELETE error");
        } finally {
            closeAll(deleteServiceStatement, conn);
        }
    }

    public void ejbActivate() throws EJBException {
    }

    public void ejbPassivate() throws EJBException {
    }

    public void ejbLoad() throws EJBException {
        Integer serviceId = (Integer) entityContext.getPrimaryKey();
        Connection conn = null;
        PreparedStatement loadServiceStatement = null;
        ResultSet clientResultSet = null;
        try {
            conn = dataSource.getConnection();
            loadServiceStatement = conn.prepareStatement("SELECT * FROM public.services WHERE id = ?");
            loadServiceStatement.setInt(1, serviceId);
            clientResultSet = loadServiceStatement.executeQuery();
            if(!clientResultSet.next()) {
                throw new NoSuchEntityException();
            }
            clientId = clientResultSet.getInt(2);
            name = clientResultSet.getString(3);
            type = ServiceType.valueOf(clientResultSet.getString(4));
            startDate = clientResultSet.getDate(5);
            endDate = clientResultSet.getDate(6);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new EJBException("DELETE error");
        } finally {
            closeAll(clientResultSet, loadServiceStatement, conn);
        }
    }

    public void ejbStore() throws EJBException {
        Integer serviceId = (Integer) entityContext.getPrimaryKey();
        Connection conn = null;
        PreparedStatement storeServiceStatement = null;
        try {
            conn = dataSource.getConnection();
            storeServiceStatement = conn.prepareStatement("UPDATE public.services SET clientid = ?, name = ?, type = ?, startdate = ?, enddate = ? WHERE id = ?");
            storeServiceStatement.setInt(1, clientId);
            storeServiceStatement.setString(2, name);
            storeServiceStatement.setString(3, type.name());
            storeServiceStatement.setDate(4, startDate);
            storeServiceStatement.setDate(5, endDate);
            storeServiceStatement.setInt(6, serviceId);
            if(storeServiceStatement.executeUpdate() < 1) {
                throw new NoSuchEntityException();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new EJBException("UPDATE error");
        } finally {
            closeAll(storeServiceStatement, conn);
        }
    }

    public Collection ejbFindAll() throws FinderException {
        Connection conn = null;
        PreparedStatement getAllServicesStatement = null;
        ResultSet allServicesResultSet = null;
        Collection<Integer> idCollection = new ArrayList<>();
        try {
            conn = dataSource.getConnection();
            getAllServicesStatement = conn.prepareStatement("SELECT id FROM public.services");
            allServicesResultSet = getAllServicesStatement.executeQuery();
            while(allServicesResultSet.next()) {
                idCollection.add(allServicesResultSet.getInt(1));
            }
            return idCollection;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new EJBException("SELECT error");
        } finally {
            closeAll(allServicesResultSet, getAllServicesStatement, conn);
        }
    }

    public Collection ejbFindByClientId(Integer clientId) throws FinderException {
        Connection conn = null;
        PreparedStatement getClientsServicesStatement = null;
        ResultSet clientsServicesResultSet = null;
        Collection<Integer> idCollection = new ArrayList<>();
        try {
            conn = dataSource.getConnection();
            getClientsServicesStatement = conn.prepareStatement("SELECT id FROM public.services WHERE clientid = ?");
            getClientsServicesStatement.setInt(1, clientId);
            clientsServicesResultSet = getClientsServicesStatement.executeQuery();
            while(clientsServicesResultSet.next()) {
                idCollection.add(clientsServicesResultSet.getInt(1));
            }
            return idCollection;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new EJBException("SELECT error");
        } finally {
            closeAll(clientsServicesResultSet, getClientsServicesStatement, conn);
        }
    }

    public Collection ejbFindByType(ServiceType type) throws FinderException {
        Connection conn = null;
        PreparedStatement getServicesByTypeStatement = null;
        ResultSet servicesByTypeResultSet = null;
        Collection<Integer> idCollection = new ArrayList<>();
        try {
            conn = dataSource.getConnection();
            getServicesByTypeStatement = conn.prepareStatement("SELECT id FROM public.services WHERE type = ?");
            getServicesByTypeStatement.setString(1, type.name());
            servicesByTypeResultSet = getServicesByTypeStatement.executeQuery();
            while(servicesByTypeResultSet.next()) {
                idCollection.add(servicesByTypeResultSet.getInt(1));
            }
            return idCollection;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new EJBException("SELECT error");
        } finally {
            closeAll(servicesByTypeResultSet, getServicesByTypeStatement, conn);
        }
    }

    private void closeAll(ResultSet resultSet, PreparedStatement preparedStatement, Connection connection) {
        try { Objects.requireNonNull(resultSet).close(); } catch (Exception e) {
            e.printStackTrace();
        }
        try { Objects.requireNonNull(preparedStatement).close(); } catch (Exception e) {
            e.printStackTrace();
        }
        try { Objects.requireNonNull(connection).close(); } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void closeAll(PreparedStatement preparedStatement, Connection connection) {
        try { Objects.requireNonNull(preparedStatement).close(); } catch (Exception e) {
            e.printStackTrace();
        }
        try { Objects.requireNonNull(connection).close(); } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void ejbPostCreate(Integer id, Integer clientId, String name, ServiceType type, Date startDate, Date endDate) throws CreateException {

    }

    public void ejbPostCreate(Integer clientId, String name, ServiceType type, Date startDate, Date endDate) throws CreateException {

    }
}
