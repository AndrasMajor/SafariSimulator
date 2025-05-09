package safariSimulator.main.Models.Entity.Human;

import safariSimulator.main.Models.Entity.Animal.Carnivore.Carnivore;
import safariSimulator.main.Models.Entity.Animal.Herbivore.Herbivore;
import safariSimulator.main.Models.Entity.Entity;
import safariSimulator.main.Models.Entity.Mover;
import safariSimulator.main.Models.Map;
import safariSimulator.main.Models.Point;
import safariSimulator.main.Models.Tile.Tile;

import java.util.*;

public class Keeper extends Human {
    public Point targetPoint;
    public final int viewRange = 4;
    public Entity targetEntity;

    public Keeper() {}
    public Keeper(Point pos) {
        super(pos);
        this.price = 1;
        this.speed = 8;
        targetPoint = pos;
        targetEntity = null;
    }

    public void chooseTargetPoint(List<Tile> tiles, List<Entity> entities) {
        if (isPoacherInRange(entities)) {
            this.targetPoint = getPoacher(entities).pos;
            return;
        }

        Tile targetTile;
        do {
            int randInt = new Random().nextInt(tiles.size());
            targetTile = tiles.get(randInt);
        } while (targetTile.health == -1);
        this.targetPoint = targetTile.pos;
    }

    public boolean isPoacherInRange(List<Entity> entities) {
        for(Entity entity : entities) {
            if (entity instanceof Poacher) {
                if (Math.abs(entity.pos.getX() - this.pos.getX()) <= viewRange &&
                    Math.abs(entity.pos.getY() - this.pos.getY()) <= viewRange
                ) {
                    return true;
                }
            }
        }
        return false;
    }

    private Poacher getPoacher(List<Entity> entites) {
        Poacher poacher = null;
        for(Entity entity : entites) {
            if (entity instanceof Poacher) {
                poacher = (Poacher) entity;
                break;
            }
        }
        return poacher;
    }

    public boolean isCarnivoreInShootingRange(Carnivore carnivore) {
        if (Math.abs(carnivore.pos.getX() - this.pos.getX()) <= 2 &&
            Math.abs(carnivore.pos.getY() - this.pos.getY()) <= 2
        ) {
            return true;
        }
        return false;
    }

    public void setTarget(Carnivore target) {
        this.targetEntity = target;
    }


    public boolean isPoacherInShootingRange(List<Entity> entites) {
        Poacher poacher = getPoacher(entites);
        if (poacher == null) return false;

        if (Math.abs(poacher.pos.getX() - this.pos.getX()) <= 5 &&
            Math.abs(poacher.pos.getY() - this.pos.getY()) <= 5
        ) {
            poacher.isVisible = true;
        } else {
            poacher.isVisible = false;
        }

        if (Math.abs(poacher.pos.getX() - this.pos.getX()) <= 2 &&
            Math.abs(poacher.pos.getY() - this.pos.getY()) <= 2
        ) {
            return true;
        }

        return false;
    }

    // -1: poacher won | 1: keeper won | 0: no fight
    public boolean keeperWonInFight() {
        Random rand = new Random();
        return rand.nextDouble() < 0.5;
    }

    public void shootCarnivore(Entity carnivore, Map map) {
        map.sellEntity(carnivore);
        this.targetEntity = null;
        System.out.println("Pew Pew Pew!!");
    }

public void move(Map map) {
    if(this.targetEntity != null) {
        moveTowardsWithPathfinding(targetEntity.getPos(), map.getTiles());
        if(isCarnivoreInShootingRange((Carnivore)targetEntity)){
            shootCarnivore(targetEntity, map);
        }
    }else{
        moveTowardsWithPathfinding(targetPoint, map.getTiles());
    }
}

private void moveTowardsWithPathfinding(Point target, List<Tile> tiles) {
    Point start = this.getPos();

    java.util.Map<Point, Point> cameFrom = new HashMap<>();
    java.util.Map<Point, Integer> costSoFar = new HashMap<>();
    PriorityQueue<Point> frontier = new PriorityQueue<>(Comparator.comparingInt(p -> costSoFar.get(p) + heuristic(p, target)));

    cameFrom.put(start, null);
    costSoFar.put(start, 0);
    frontier.add(start);

    while (!frontier.isEmpty()) {
        Point current = frontier.poll();

        if (current.equals(target)) {
            break;
        }

        for (Point next : getNeighbors(current, tiles)) {
            int newCost = costSoFar.get(current) + 1;

            if (!costSoFar.containsKey(next) || newCost < costSoFar.get(next)) {
                costSoFar.put(next, newCost);
                frontier.add(next);
                cameFrom.put(next, current);
            }
        }
    }

    // reconstruct path
    Point current = target;
    List<Point> path = new ArrayList<>();
    while (current != null && !current.equals(start)) {
        path.add(current);
        current = cameFrom.get(current);
    }

    if (!path.isEmpty()) {
        Point nextStep = path.get(path.size() - 1);
        this.mover = new Mover(start, nextStep, 0.7f);
        this.setPos(nextStep);
    }
}

private int heuristic(Point a, Point b) {
    return Math.abs(a.getX() - b.getX()) + Math.abs(a.getY() - b.getY());
}

private List<Point> getNeighbors(Point current, List<Tile> tiles) {
    List<Point> neighbors = new ArrayList<>();
    int[][] dirs = {{1,0}, {-1,0}, {0,1}, {0,-1}}; // 4 irányba mozgás

    for (int[] dir : dirs) {
        int nx = current.getX() + dir[0];
        int ny = current.getY() + dir[1];
        Point neighbor = new Point(nx, ny);

        if (isValidMove(neighbor, tiles)) {
            neighbors.add(neighbor);
        }
    }

    return neighbors;
}

private boolean isValidMove(Point pos, List<Tile> tiles) {
    for (Tile tile : tiles) {
        if (tile.getPos().getY() == pos.getY() && tile.getPos().getX() == pos.getX()) {
            return tile.getHealth() != -1; // Cannot move onto water
        }
    }
    return false;
}
}
