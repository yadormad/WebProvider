package admin;

import admin.block.PessimisticBlockEnum;
import controller.UserSessionBean;

import javax.ejb.Stateless;
import javax.naming.AuthenticationException;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.*;
import java.util.Objects;

@Stateless(name = "AuthentificationEJB")
public class AuthentificationBean {
    private DataSource dataSource;
    private InitialContext context;
    public AuthentificationBean() {
        try {
            context = new InitialContext();
            dataSource = (DataSource) context.lookup("java:/PostgresDS");
        } catch (NamingException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public UserSessionBean login(String login, String pass) throws SQLException, NamingException {
        Connection conn = null;
        PreparedStatement userStatement = null;
        ResultSet clientResultSet = null;
        try {
            conn = dataSource.getConnection();
            userStatement = conn.prepareStatement("SELECT * FROM public.users WHERE login = ? AND pass = ?");
            userStatement.setString(1, login);
            userStatement.setString(2, pass);
            clientResultSet = userStatement.executeQuery();
            if(clientResultSet.next()){
                UserSessionBean sessionBean = (UserSessionBean) context.lookup("java:module/UserSessionEJB!controller.UserSessionBean");
                sessionBean.setLogin(login);
                return sessionBean;
            } else {
                throw new AuthenticationException("Wrong login/password");
            }
        } finally {
            try { Objects.requireNonNull(clientResultSet).close(); } catch (Exception e) { e.printStackTrace(); }
            try { Objects.requireNonNull(userStatement).close(); } catch (Exception e) { e.printStackTrace(); }
            try { Objects.requireNonNull(conn).close(); } catch (Exception e) { e.printStackTrace(); }
        }
    }

    private void initBlocks(String login) {
        for (PessimisticBlockEnum blocksMap : PessimisticBlockEnum.values()) {
            blocksMap.addUser(login);
        }
    }
}
