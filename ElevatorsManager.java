package com.company;

import java.util.ArrayList;
import java.util.Random;

public class ElevatorsManager implements Runnable{

    ArrayList<Elevator> elevators;
    ElevatorsManager(ArrayList<Elevator> elevators){
        this.elevators = elevators;
    }

    public void run(){
        try {
            Random random = new Random();
            int id_counter = 0;
            while (true) {
                int start = random.nextInt(Main.num_floors);
                direction dir;
                if (start == 0){
                    dir = direction.UP;
                } else if (start == Main.num_floors-1){
                    dir = direction.DOWN;
                } else {
                    dir = random.nextInt(2) == 0 ? direction.UP : direction.DOWN;
                }
                Request r = new Request(dir, start, id_counter);
                id_counter++;
                int min_dist = Main.num_floors +1;
                int elev_index = 0;
                for (int i = 0; i < elevators.size(); i++){
                    int dist = Math.abs(r.start - elevators.get(i).current_floor);
                    if (dist < min_dist){
                        min_dist = dist;
                        elev_index = i;
                    }
                }
                elevators.get(elev_index).newRequest(r);
                Thread.sleep((random.nextInt(5)+4)*1000);
            }
        } catch (InterruptedException e){
            e.printStackTrace();
        }
    }

}
