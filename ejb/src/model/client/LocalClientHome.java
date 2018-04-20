package model.client;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Collection;
public interface LocalClientHome extends EJBLocalHome {
    LocalClient findByPrimaryKey(Integer key) throws FinderException;
    LocalClient create(String name, String info) throws CreateException;
    LocalClient create(Integer id, String name, String info) throws CreateException;
    Collection findAll() throws FinderException;
}
