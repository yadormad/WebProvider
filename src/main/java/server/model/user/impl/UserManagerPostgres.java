package server.model.user.impl;

import login.LoginEntity;
import server.exceptions.DbAccessException;
import server.main.ServerProperties;
import server.model.user.UserManager;

import java.sql.*;
import java.util.Objects;

public class UserManagerPostgres implements UserManager {

    @Override
    public boolean checkUser(LoginEntity loginEntity) throws DbAccessException {
        Connection conn = null;
        PreparedStatement getClientByIdSQLStatement = null;
        ResultSet clientResultSet = null;
        boolean answer = false;
        try {
            conn = DriverManager.getConnection(ServerProperties.getInstance().getPostgresDatabaseURL(), ServerProperties.getInstance().getPostgresLogin(), ServerProperties.getInstance().getPostgresPass());
            getClientByIdSQLStatement = conn.prepareStatement("SELECT * FROM public.users WHERE login = ? AND pass = ?");
            getClientByIdSQLStatement.setString(1, loginEntity.getLogin());
            getClientByIdSQLStatement.setString(2, loginEntity.getPass());
            clientResultSet = getClientByIdSQLStatement.executeQuery();
            if(clientResultSet.next()){
                answer = true;
            }
        } catch (SQLException e) {
            throw new DbAccessException("Autentification error");
        } finally {
            try { Objects.requireNonNull(clientResultSet).close(); } catch (Exception e) { /* ignored */ }
            try { Objects.requireNonNull(getClientByIdSQLStatement).close(); } catch (Exception e) { /* ignored */ }
            try { Objects.requireNonNull(conn).close(); } catch (Exception e) { /* ignored */ }
        }
        return answer;
    }
}
