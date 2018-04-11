package model.service;

import model.service.types.ServiceType;

import javax.ejb.EJBLocalObject;
import java.sql.Date;

public interface LocalService extends EJBLocalObject {
    Integer getClientId();
    String getName();
    void setName(String name);
    ServiceType getType();
    Date getStartDate();
    void setStartDate(Date startDate);
    Date getEndDate();
    void setEndDate(Date endDate);
}
