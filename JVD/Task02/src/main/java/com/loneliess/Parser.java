package com.loneliess;

import com.loneliess.entity.Cone;
import com.loneliess.servise.ServiceFactory;
import com.loneliess.servise.ServiceValidation;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Parser {
    private static final Logger logger = LogManager.getLogger();
    private ServiceValidation validator;

    public Parser(){
       validator =ServiceFactory.getInstance().getServiceValidation();
    }

    public Cone parse(String line) {
        String[] arg = line.split(" ");
        if (arg.length <= 10) {
            try {
                Cone cone = new Cone(Integer.parseInt(arg[0]), Double.parseDouble(arg[1]), Double.parseDouble(arg[2])
                        , Double.parseDouble(arg[3]), Double.parseDouble(arg[4]), Double.parseDouble(arg[5]), Double.parseDouble(arg[6]),
                        Double.parseDouble(arg[7]), Double.parseDouble(arg[8]), Double.parseDouble(arg[9]));
                if (validator.validate(cone).size() == 0) {
                    return cone;
                }
            } catch (NumberFormatException ex) {
               logger.catching(Level.INFO, ex);
            }
        } else {
            logger.error("силишком большая строка");
        }
        return null;
    }
}
