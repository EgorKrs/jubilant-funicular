package entity;

import javax.xml.bind.annotation.XmlEnumValue;

public enum Type {
    @XmlEnumValue(value = "day off")
    DAY_OFF,
    @XmlEnumValue(value = "sightseeing")
    SIGHTSEEING,
    @XmlEnumValue(value = "pilgrimage")
    PILGRIMAGE

}
