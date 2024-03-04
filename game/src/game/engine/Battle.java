package game.engine;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;

import game.engine.base.Wall;
import game.engine.dataloader.DataLoader;
import game.engine.lanes.Lane;
import game.engine.titans.Titan;
import game.engine.titans.TitanRegistry;
import game.engine.weapons.factory.WeaponFactory;

public class Battle {

    private static final int[][] PHASES_APPROACHING_TITANS = { { 1, 1, 1, 2, 1, 3, 4 }, { 2, 2, 2, 1, 3, 3, 4 },
            { 4, 4, 4, 4, 4, 4, 4 } };
    private static final int WALL_BASE_HEALTH = 1000;
    private int numberOfTurns;
    private int resourcesGathered;
    private BattlePhase battlePhase = BattlePhase.EARLY;
    private int numberOfTitansPerTurn = 1;
    private int score;
    private int titanSpawnDistance;
    private final WeaponFactory weaponFactory;
    private final HashMap<Integer, TitanRegistry> titansArchives;
    private final ArrayList<Titan> approachingTitans;
    private PriorityQueue<Lane> lanes; // final
    private ArrayList<Lane> originalLanes; // final

    public int[][] getPHASES_APPROACHING_TITANS() {
        return PHASES_APPROACHING_TITANS;
    }

    public int getNumberOfTurns() {
        return this.numberOfTurns;
    }

    public void setNumberOfTurns(int numberOfTurns) {
        this.numberOfTurns = numberOfTurns;
    }

    public int getResourcesGathered() {
        return this.resourcesGathered;
    }

    public void setResourcesGathered(int resourcesGathered) {
        this.resourcesGathered = resourcesGathered;
    }

    public BattlePhase getBattlePhase() {
        return this.battlePhase;
    }

    public void setBattlePhase(BattlePhase battlePhase) {
        this.battlePhase = battlePhase;
    }

    public int getNumberOfTitansPerTurn() {
        return this.numberOfTitansPerTurn;
    }

    public void setNumberOfTitansPerTurn(int numberOfTitansPerTurn) {
        this.numberOfTitansPerTurn = numberOfTitansPerTurn;
    }

    public int getScore() {
        return this.score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getTitanSpawnDistance() {
        return this.titanSpawnDistance;
    }

    public void setTitanSpawnDistance(int titanSpawnDistance) {
        this.titanSpawnDistance = titanSpawnDistance;
    }

    public WeaponFactory getWeaponFactory() {
        return this.weaponFactory;
    }

    public HashMap<Integer, TitanRegistry> getTitansArchives() {
        return this.titansArchives;
    }

    public ArrayList<Titan> getApproachingTitans() {
        return this.approachingTitans;
    }

    public Battle(int numberOfTurns, int score, int titanSpawnDistance, int initialNumOfLanes,
            int initialResourcesPerLane) throws IOException {
        this.numberOfTurns = numberOfTurns;
        this.score = score;
        this.titanSpawnDistance = titanSpawnDistance;
        this.resourcesGathered = initialNumOfLanes * initialResourcesPerLane;
        this.titansArchives = DataLoader.readTitanRegistry();
        this.weaponFactory = new WeaponFactory();
        this.approachingTitans = new ArrayList<>();
    }

    private void initializeLanes(int numOfLanes) {
        for (int i = 0; i < numOfLanes; i++) {
            Lane l = new Lane(new Wall(WALL_BASE_HEALTH));
            originalLanes.add(l);
            lanes.add(l);
        }
    }
}
