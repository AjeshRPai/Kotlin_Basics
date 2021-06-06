package Generics;

class Vehcle {
    private int NumberOfTyres = 0;
    
    public Vehcle(int numberOfTyres) {
        NumberOfTyres = numberOfTyres;
    }
    
    void drive() {
        System.out.println("driving = ");
    }
}
class Car extends Vehcle {
    
    public Car(int numberOfTyres) {
        super(numberOfTyres);
    }
}

class Truck extends Vehcle {
    
    public Truck(int numberOfTyres) {
        super(numberOfTyres);
    }
}
