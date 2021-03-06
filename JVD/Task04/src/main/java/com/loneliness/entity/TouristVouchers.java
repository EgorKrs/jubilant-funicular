package com.loneliness.entity;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@XmlType(propOrder = {"vouchers"})
@XmlRootElement
public class TouristVouchers {

    @XmlElementWrapper(name = "vouchers")
    @XmlElement(name = "TravelPackages")
    public List<TravelPackagesBuilder.TravelPackages> vouchers = new ArrayList<>();

    public List<TravelPackagesBuilder.TravelPackages> getVouchers() {
        return vouchers;
    }
    public void add(TravelPackagesBuilder.TravelPackages travelPackages){
        vouchers.add(travelPackages);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TouristVouchers vouchers1 = (TouristVouchers) o;
        return vouchers.equals(vouchers1.vouchers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(vouchers);
    }

    @Override
    public String toString() {
        return "TouristVouchers{" +
                "vouchers=" + vouchers +
                '}';
    }
}
