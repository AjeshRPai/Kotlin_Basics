package Generics;

import java.util.ArrayList;
import java.util.List;

public class GenericsInJava {
    
    public static void main(String[] args) {
        List<String> strs = new ArrayList<>();
//        List<Object> objs = strs;
//        objs.add(1);
        String s = strs.get(0);
        
    }
    
    public void invarianceExample() {
        // There is no subtype to
        // super type relation ship here
        
        List<Car> cars = new ArrayList<>();
//        List<Vehcle> vehcles = cars;
    }
    
    public void contraVariance() {
        List<Car> cars = new ArrayList<>();
        List<Vehcle> vehcles = new ArrayList<>();
        
    }
    
    
    public void coVariant() {
        // List of cars is a subtype of list of vehcles
        List<Vehcle> vehcles = new ArrayList<>();
        List<Car> cars = new ArrayList<>();
        vehcles.addAll(cars);
    }
}

