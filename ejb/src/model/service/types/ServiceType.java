package model.service.types;

import javax.xml.bind.annotation.XmlEnumValue;

public enum ServiceType {
    @XmlEnumValue(value = "tv")
    TV,
    @XmlEnumValue(value = "internet")
    INTERNET,
    @XmlEnumValue(value = "phone")
    PHONE;
}
