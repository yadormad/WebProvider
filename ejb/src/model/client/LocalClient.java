package model.client;

import javax.ejb.EJBLocalObject;

public interface LocalClient extends EJBLocalObject {
    String getName();
    String getInfo();
}
