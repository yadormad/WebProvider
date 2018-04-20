package model.xml;

import model.client.LocalClient;
import model.client.LocalClientHome;
import model.service.LocalService;
import model.service.LocalServiceHome;

import javax.ejb.CreateException;
import javax.ejb.FinderException;
import javax.ejb.RemoveException;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlID;
import java.util.ArrayList;
import java.util.Collection;

public class ClientXml {
    private Integer id;
    private String name, info;
    private Collection<ServiceXml> services;

    @XmlAttribute
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @XmlElement
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlElement
    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    @XmlElementWrapper(name = "services")
    @XmlElement(name = "service")
    public Collection<ServiceXml> getServices() {
        return services;
    }

    public void setServices(Collection<ServiceXml> services) {
        this.services = services;
    }

    public void loadService(LocalServiceHome serviceHome) {
        services = new ArrayList<>();
        try {
            for (LocalService service : (Collection<LocalService>) serviceHome.findByClientId(id)) {
                ServiceXml serviceXml = new ServiceXml();
                serviceXml.setId((Integer) service.getPrimaryKey());
                serviceXml.setName(service.getName());
                serviceXml.setType(service.getType());
                serviceXml.setStartDate(service.getStartDate());
                serviceXml.setEndDate(service.getEndDate());
                services.add(serviceXml);
            }
        } catch (FinderException e) {
            e.printStackTrace();
        }
    }

    public void storeIntoDb(LocalClientHome clientHome, LocalServiceHome serviceHome) {
        Collection<LocalService> clientServices = new ArrayList<>();
        try {
            clientServices = serviceHome.findByClientId(id);
        } catch (FinderException e) {
            e.printStackTrace();
        }

        for (ServiceXml serviceXml: services) {
            try {
                LocalService serviceBean = serviceHome.findByPrimaryKey(serviceXml.getId());
                if(serviceXml.getType().equals(serviceBean.getType()) && id.equals(serviceBean.getClientId())) {
                    clientServices.remove(serviceBean);
                    serviceBean.setName(serviceXml.getName());
                    serviceBean.setStartDate(new java.sql.Date(serviceXml.getStartDate().getTime()));
                    serviceBean.setEndDate(new java.sql.Date(serviceXml.getEndDate().getTime()));
                }
            } catch (FinderException e) {
                try {
                    serviceHome.create(serviceXml.getId(), id, serviceXml.getName(), serviceXml.getType(),
                            new java.sql.Date(serviceXml.getStartDate().getTime()), new java.sql.Date(serviceXml.getEndDate().getTime()));
                } catch (CreateException e1) {
                    e1.printStackTrace();
                }
            }
        }
        for (LocalService serviceBean:clientServices) {
            try {
                serviceBean.remove();
            } catch (RemoveException e) {
                e.printStackTrace();
            }
        }
    }

    public void parseClientXml(LocalClient clientBean) {
        id = (Integer) clientBean.getPrimaryKey();
        name = clientBean.getName();
        info = clientBean.getInfo();
    }
}
