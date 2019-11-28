package com.loneliess.controller;

import com.loneliess.controller.command_impl.*;

import java.util.HashMap;
import java.util.Map;

public class CommandProvider {
    private final Map<CommandName, Command> repository = new HashMap<>();
    private CommandProvider(){
        repository.put(CommandName.WRONG_REQUEST,new WrongRequest());
        repository.put(CommandName.LOAD_CONE_MAP,new LoadConeMap());
        repository.put(CommandName.ADD_CONE,new AddCone());
        repository.put(CommandName.SAVE_CONE_MAP,new SaveConeMap());
        repository.put(CommandName.CALCULATE_VOLUME_OF_CONE,new CalculateVolumeOfCone());
        repository.put(CommandName.CALCULATE_SIDE_SURFACE_AREA_OF_CONE,new CalculateSideSurfaceAreaOfCone());
        repository.put(CommandName.CALCULATE_SIDE_SURFACE_AREA,new CalculateSideSurfaceArea());
        repository.put(CommandName.CALCULATE_VOLUME_RATIO,new CalculateVolumeRatio());
        repository.put(CommandName.IS_LIES_ON_THE_PLANE_CONE,new IsLiesOnThePlaneCone());
        repository.put(CommandName.DELETE_CONE,new DeleteCone());
        repository.put(CommandName.UPDATE_CONE,new UpdateCone());

    }
    public Map<CommandName, Command> getRepository() {
        return repository;
    }

    public Command getCommand(CommandName commandName){
        return repository.get(commandName);
    }
}
