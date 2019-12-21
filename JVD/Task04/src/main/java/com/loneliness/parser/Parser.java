package com.loneliness.parser;

import com.loneliness.entity.TouristVouchers;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public interface Parser {
    Logger logger = LogManager.getLogger();
    TouristVouchers unMarshall(String fileName);
    boolean marshall(String fileName, TouristVouchers vouchers);
}
