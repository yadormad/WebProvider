package server.admin.action;

import entity.transport.TransportEntity;
import server.exceptions.DateFormatException;
import server.exceptions.DbAccessException;
import server.exceptions.WrongServiceTypeException;

public interface Action {
    TransportEntity perform(TransportEntity request, String userId) throws DbAccessException, WrongServiceTypeException, DateFormatException;
}
