package safariSimulator.main.Models.Entity.Human;

import safariSimulator.main.Models.Entity.Animal.Animal;
import safariSimulator.main.Models.Entity.Animal.Carnivore.Carnivore;
import safariSimulator.main.Models.Entity.Animal.Herbivore.Herbivore;
import safariSimulator.main.Models.Entity.Entity;
import safariSimulator.main.Models.Entity.Mover;
import safariSimulator.main.Models.Map;
import safariSimulator.main.Models.Point;
import safariSimulator.main.Models.Tile.Tile;

import java.util.*;

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
        moveTowardsWithPathfinding(targetPoint, map.getTiles());

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




}
