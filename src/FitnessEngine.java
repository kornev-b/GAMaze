import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bogdan Kornev
 * on 11.02.2015, 14:05.
 */
public class FitnessEngine {
    private static final int[] dx = {1, 0, -1, 0};
    private static final int[] dy = {0, -1, 0, 1};
    public static boolean logEnabled;

    public static int calcFitness(Genome genome) {
        return solve(genome);
    }

    private static int solve(Genome genome) {
        int[][] waveRates = new int[genome.getWidth()][genome.getHeight()];
        waveRates[0][0] = 1;
//        List<Cell> wavedCells = new ArrayList<Cell>();

        int n = 1;
        boolean noSolution;

        do {
            noSolution = true;
            for (int x = 0; x < genome.getWidth(); x++) {
                for (int y = 0; y < genome.getHeight(); y++) {
                    if (waveRates[x][y] == n) {
                        for (int i = 0; i < 4; i++) {
                            if (canGo(genome, x, y, dx[i], dy[i]) &&
                                    waveRates[x + dx[i]][y + dy[i]] == 0) {
                                noSolution = false;
                                waveRates[x + dx[i]][y + dy[i]] = n + 1;
                                if (x + dx[i] == genome.getWidth() - 1 &&
                                        y + dy[i] == genome.getHeight() - 1) {
                                    if (logEnabled) {
                                        System.out.println("Maze is solved! Fitness of genome \n" + genome.toString());
                                        System.out.println("is equals " + n);
                                    }
                                    return n + genome.getWidth() * 10;
                                }
                            }
                        }
                    }
                }
            }
            n = n + 1;
        } while (!noSolution);

        if (logEnabled) {
            System.out.println("Maze is not solved. Fitness of genome with maze: \n" + genome.toString() + " is equals " + (n - 1));
        }
        return n - 1;
    }

    private static boolean canGo(Genome genome, int x, int y, int dx, int dy) {
        if (dx == -1) {
            return !genome.getGene(x, y).isUpWall();
        } else if (dx == 1) {
            return !genome.getGene(x + 1, y).isUpWall();
        } else if (dy == -1) {
            return !genome.getGene(x, y).isLeftWall();
        } else {
            return !genome.getGene(x, y + 1).isLeftWall();
        }
    }

    private class Cell {
        int x;
        int y;
    }
}
