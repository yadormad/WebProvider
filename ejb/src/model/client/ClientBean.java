package model.client;

import javax.ejb.*;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

public class ClientBean implements EntityBean {
    private String name, info;
    private DataSource dataSource;
    private EntityContext entityContext;
    public ClientBean() {
    }

    public Integer ejbFindByPrimaryKey(Integer key) throws FinderException {
        Connection conn = null;
        PreparedStatement getClientByIdSQLStatement = null;
        ResultSet clientResultSet = null;
        try {
            conn = dataSource.getConnection();
            getClientByIdSQLStatement = conn.prepareStatement("SELECT id FROM public.clients WHERE id = ?");
            getClientByIdSQLStatement.setInt(1, key);
            clientResultSet = getClientByIdSQLStatement.executeQuery();
            if(!clientResultSet.next()){
                throw new ObjectNotFoundException();
            } else {
                return key;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new EJBException("SELECT error");
        } finally {
            closeAll(clientResultSet, getClientByIdSQLStatement, conn);
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

    public void ejbRemove() throws EJBException {
        Integer clientId = (Integer) entityContext.getPrimaryKey();
        Connection conn = null;
        PreparedStatement deleteClientStatement = null;
        try {
            conn = dataSource.getConnection();
            deleteClientStatement = conn.prepareStatement("DELETE FROM public.clients WHERE id = ?");
            deleteClientStatement.setInt(1, clientId);
            deleteClientStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new EJBException(e.getMessage());
        } finally {
            closeAll(deleteClientStatement, conn);
        }
    }

    public void ejbActivate() throws EJBException {
    }

    public void ejbPassivate() throws EJBException {
    }

    public void ejbLoad() throws EJBException {
        Integer clientId = (Integer) entityContext.getPrimaryKey();
        Connection conn = null;
        PreparedStatement loadClientStatement = null;
        ResultSet clientResultSet = null;
        try {
            conn = dataSource.getConnection();
            loadClientStatement = conn.prepareStatement("SELECT name, info FROM public.clients WHERE id = ?");
            loadClientStatement.setInt(1, clientId);
            clientResultSet = loadClientStatement.executeQuery();
            if(!clientResultSet.next()) {
                throw new NoSuchEntityException();
            }
            name = clientResultSet.getString(1);
            info = clientResultSet.getString(2);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new EJBException("SELECT error");
        } finally {
            closeAll(clientResultSet, loadClientStatement, conn);
        }
    }

    public void ejbStore() throws EJBException {
        Integer clientId = (Integer) entityContext.getPrimaryKey();
        Connection conn = null;
        PreparedStatement storeClientStatement = null;
        try {
            conn = dataSource.getConnection();
            storeClientStatement = conn.prepareStatement("UPDATE public.clients SET name = ?, info = ? WHERE id = ?");
            storeClientStatement.setString(1, name);
            storeClientStatement.setString(2, info);
            storeClientStatement.setInt(3, clientId);
            if(storeClientStatement.executeUpdate() < 1) {
                throw new NoSuchEntityException();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new EJBException("UPDATE error");
        } finally {
            closeAll(storeClientStatement, conn);
        }
    }

    public Integer ejbCreate(String name, String info) {
        Connection conn = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            conn = dataSource.getConnection();
            preparedStatement = conn.prepareStatement("SELECT nextval('public.client_id_sequence') FROM public.clients");
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            Integer id = resultSet.getInt(1);
            preparedStatement = conn.prepareStatement("INSERT INTO public.clients VALUES" +
                    "(?, ?, ?)");
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, name);
            preparedStatement.setString(3, info);
            preparedStatement.executeUpdate();
            this.name = name;
            this.info = info;
            return id;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new EJBException("INSERT exception");
        } finally {
            closeAll(resultSet, preparedStatement, conn);
        }
    }

    public void ejbPostCreate(String name, String info) {

    }

    public Collection ejbFindAll() {
        Connection conn = null;
        PreparedStatement getAllClientsStatement = null;
        ResultSet clientResultSet = null;
        Collection<Integer> idCollection = new ArrayList<>();
        try {
            conn = dataSource.getConnection();
            getAllClientsStatement = conn.prepareStatement("SELECT id FROM public.clients");
            clientResultSet = getAllClientsStatement.executeQuery();
            while(clientResultSet.next()) {
                idCollection.add(clientResultSet.getInt(1));
            }
            return idCollection;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new EJBException("SELECT error");
        } finally {
            closeAll(clientResultSet, getAllClientsStatement, conn);
        }
    }

    public String getName() {
        return name;
    }

    public String getInfo() {
        return info;
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
}
