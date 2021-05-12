package com.company;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.Exchanger;

enum elevator_status{
    WAITING("WAITING"),
    MOVING_UP("MOVING_UP"),
    MOVING_DOWN("MOVING_DOWN");
    private String str;
    elevator_status(String str){this.str = str;}
    String getString(){return str;}
}

enum direction{
    UP("UP"),
    DOWN("DOWN");
    private String str;
    direction(String str){this.str = str;}
    String getString(){return str;}
}


class Request{
    direction dir;
    int start;
    int target;
    int id;
    Request(direction d, int start, int id){
        this.dir = d;
        this.start = start;
        this.id = id;
    }
}

public class Main {
    static int num_floors = 10;
    static int num_elevators = 3;
    static int seconds_between_floors = 3;
    static int max_persons = 4;

    public static void cls() {
        try {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } catch (Exception E) {
            System.out.println(E);
        }
    }

    static void consoleWriter(ArrayList<Elevator> elevators) throws InterruptedException, IOException {

        while (true){
            cls();
            for (int i = 0; i < elevators.size(); i++){
                System.out.println("Elevator # " + (i+1));
                System.out.println("Elevator status: " + elevators.get(i).current_status.getString());
                System.out.println("Current floor: " + elevators.get(i).current_floor + '\n');
                System.out.println("Waiting persons: ");
                for (int j = 0; j < elevators.get(i).requestsWaiting.size(); j++){
                    System.out.println("Person id: " + elevators.get(i).requestsWaiting.get(j).id +
                                    " Start: " + elevators.get(i).requestsWaiting.get(j).start +
                            " Target: " + elevators.get(i).requestsWaiting.get(j).target + "   ");
                }
                System.out.println("Running persons: ");
                for (int j = 0; j < elevators.get(i).requestsRunning.size(); j++){
                    System.out.println("Person id: " + elevators.get(i).requestsRunning.get(j).id +
                            " Start: " + elevators.get(i).requestsRunning.get(j).start +
                            " Target: " + elevators.get(i).requestsRunning.get(j).target + "   ");
                }
                System.out.println("\n\n");
            }
            Thread.sleep(50);
        }
    }
    public static void main(String[] args) throws InterruptedException, IOException {
        ArrayList<Elevator> elevators = new ArrayList<Elevator> ();
	    for (int i = 0; i < num_elevators; i++){
	        elevators.add(new Elevator());
        }
	    Thread [] threads = new Thread[num_elevators];
        for (int i = 0; i < num_elevators; i++){
            threads[i] = new Thread(elevators.get(i));
            threads[i].start();
        }
        ElevatorsManager manager = new ElevatorsManager(elevators);
        Thread managerThread = new Thread(manager);
        managerThread.start();
        consoleWriter(elevators);
    }
}
