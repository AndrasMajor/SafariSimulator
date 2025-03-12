package safariSimulator.main.Models.Entity.Human;

public class Visitor extends Human {
    public int happiness;
    public Visitor() {
        super();
        this.happiness = 0;
    }
    public void setHappier(){
        this.happiness = Math.min(100, this.happiness + 10);
    }

    public int getHappiness(){
        return this.happiness;
    }
}
