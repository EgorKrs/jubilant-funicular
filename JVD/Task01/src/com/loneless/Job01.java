package com.loneless;

public class Job01 implements Execute{
    @Override
    public Object execute(Object num) {
        int firstNum,secondNum,thirdNum,fourthNum;
        firstNum=(Integer)num/1000;
        secondNum=(Integer)num/100-firstNum*10;
        thirdNum=(((Integer)num-(firstNum*1000+secondNum*100))/10);
        fourthNum=(Integer)num-(firstNum*1000+secondNum*100+thirdNum*10);
        return firstNum + secondNum == thirdNum + fourthNum;
    }
}
