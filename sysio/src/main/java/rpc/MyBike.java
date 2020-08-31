package rpc;

public class MyBike implements Bike {

    @Override
    public void fooo(String msg) {
        System.out.println("MyBike server side: " + msg);
    }
}
