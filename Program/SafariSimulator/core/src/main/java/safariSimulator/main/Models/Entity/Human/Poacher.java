package safariSimulator.main.Models.Entity.Human;

import safariSimulator.main.Models.Entity.Animal.Animal;
import safariSimulator.main.Models.Entity.Animal.Carnivore.Carnivore;
import safariSimulator.main.Models.Entity.Animal.Herbivore.Herbivore;
import safariSimulator.main.Models.Entity.Entity;
import safariSimulator.main.Models.Map;
import safariSimulator.main.Models.Point;
import safariSimulator.main.Models.Tile.Tile;

import java.util.List;
import java.util.Random;

public class Poacher extends Human {
    public Point startPont;
    public Animal target;
    public Point targetPoint;

    public Poacher(Map map, Point startPoint) {
        super(startPoint);
        chooseTarget(map.getEntities());
        Point p = new Point(startPoint.getX(), startPoint.getY());
        this.startPont = p;
    }

    public void chooseTarget(List<Entity> entities) {
        Entity target;
        do {
            int randInt = new Random().nextInt(entities.size());
            target = entities.get(randInt);
        } while (!(target instanceof Animal));
        this.target = (Animal) target;
        this.targetPoint = target.pos;
    }

    public boolean move(Map map) {
        if (target != null) targetPoint = target.pos;
        moveStepTowards(targetPoint, map);

        if (
            target instanceof Herbivore &&
            this.pos.getX() == target.getPos().getX() &&
            this.pos.getY() == target.getPos().getY()
        ) {
            map.entities.remove(target);
            targetPoint = startPont;
            target = null;
            return true;
        } else if (target instanceof Carnivore && isInRange(this.pos, target.getPos())) {
            map.entities.remove(target);
            targetPoint = startPont;
            target = null;
            return true;
        } else if (
            target == null &&
            this.pos.getX() == startPont.getX() &&
            this.pos.getY() == startPont.getY()
        ) {
            map.entities.remove(this);
            return true;
        }
        return false;
    }

    private void moveStepTowards(Point target, Map map) {
        Point current = this.getPos();
        int dx = Integer.compare(target.getX(), current.getX());
        int dy = Integer.compare(target.getY(), current.getY());

        Point newPos = new Point(current.getX() + dx, current.getY() + dy);

        if (isValidMove(newPos, map.tiles)) {
            this.setPos(newPos);
        } else {
            avoidWaterMove(map);
        }
    }

    private boolean isValidMove(Point pos, List<Tile> tiles) {
        for (Tile tile : tiles) {
            if (tile.getPos().getY() == pos.getY() && tile.getPos().getX() == pos.getX()) {
                return tile.getHealth() != -1; // Cannot move onto water
            }
        }
        return false;
    }

    private boolean isInRange(Point p1, Point p2) {
        return Math.abs(p1.getX() - p2.getX()) <= 2 && Math.abs(p1.getY() - p2.getY()) <= 2;
    }


    private void avoidWaterMove(Map map) {
        List<Point> possibleMoves = List.of(
            new Point(pos.getX() + 1, pos.getY()),
            new Point(pos.getX() - 1, pos.getY()),
            new Point(pos.getX(), pos.getY() + 1),
            new Point(pos.getX(), pos.getY() - 1),
            new Point(pos.getX() + 1, pos.getY() + 1),
            new Point(pos.getX() - 1, pos.getY() + 1),
            new Point(pos.getX() - 1, pos.getY() - 1),
            new Point(pos.getX() + 1, pos.getY() - 1)
        );

        Random random = new Random();
        int randInt;
        do {
            randInt = random.nextInt(possibleMoves.size());
        } while (!isValidMove(possibleMoves.get(randInt), map.tiles));
        this.setPos(possibleMoves.get(randInt));
    }
}
