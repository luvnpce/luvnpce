package rpcdemo.service;

import rpcdemo.service.Car;

public class MyCar implements Car {

    @Override
    public String foo(String msg) {
        System.out.println("MyCar server side: " + msg);
        return "server result" + msg;
    }
}
