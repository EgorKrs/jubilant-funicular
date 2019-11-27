package com.loneliess.servise.comparator;

import com.loneliess.entity.ConeWrapper;

import java.util.Comparator;

public class CompareByVolume implements Comparator<ConeWrapper> {
    @Override
    public int compare(ConeWrapper left, ConeWrapper right) {
        return Double.compare(left.getSubscriber().getVolume(), right.getSubscriber().getVolume());
    }
}
