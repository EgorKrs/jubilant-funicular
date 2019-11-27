package com.loneliess.controller.command_impl;

import com.loneliess.controller.Command;
import com.loneliess.controller.ControllerException;
import com.loneliess.entity.Cone;
import com.loneliess.entity.Point;
import com.loneliess.servise.ServiceFactory;

public class CalculateVolumeRatio implements Command<Cone,Double>,ConeCommand {
    @Override
    public Double execute(Cone request) throws ControllerException {
        Point point=new Point(0,0,0);
        return service.CalculateVolumeRatio(request,point);
    }
}
