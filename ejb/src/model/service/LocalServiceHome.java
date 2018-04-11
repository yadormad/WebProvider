package model.service;

import model.service.types.ServiceType;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;
import java.sql.Date;
import java.util.Collection;

public interface LocalServiceHome extends EJBLocalHome {
    LocalService findByPrimaryKey(Integer key) throws FinderException;
    LocalService create(Integer clientId, String name, ServiceType type, Date startDate, Date endDate) throws CreateException;
    Collection findAll() throws FinderException;
    Collection findByClientId(Integer clientId) throws FinderException;
    Collection findByType(ServiceType type) throws FinderException;
}
