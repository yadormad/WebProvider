package server.model.user;

import login.LoginEntity;
import server.exceptions.DbAccessException;

public interface UserManager {
    boolean checkUser(LoginEntity loginEntity) throws DbAccessException;
}
