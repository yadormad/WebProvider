package model.client;

import javax.ejb.EJBLocalObject;

public interface LocalClient extends EJBLocalObject {
    String getName();
    void setName(String name);
    String getInfo();
    void setInfo(String info);
}
