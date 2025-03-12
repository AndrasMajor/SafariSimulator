package safariSimulator.main.Models.Entity.Human;

import safariSimulator.main.Models.Entity.Entity;

public class Poacher extends Human {

    public boolean isAlive;
    private Entity target;

    public Poacher() {
        super();
        isAlive = true;
    }
    public void setTarget(Entity target){
        this.target = target;
    }
    public void shoot(Entity target){
        //TODO;
    }
}
