package com.gormanly.demo.mscs722.model;

import com.fdflib.annotation.FdfIgnore;
import com.fdflib.model.state.CommonState;

public class Car extends CommonState {

    public CarMake make = null;
    public String model = "";
    public Integer year = 0;
    public String color = "";
    public String name = "";
    public String description = "";
    public Boolean isInNeedOfRepair = true;
    public Boolean isOnCall = true;
    public Boolean isOutWorking = null;
    public long currentDriverId = -1L;

    @FdfIgnore
    public Driver currentDriver = null;

    public Car() {
        super();
    }

}
