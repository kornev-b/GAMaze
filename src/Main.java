import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Bogdan Kornev
 * on 10.02.2015, 22:47.
 */
public class Main {
    private static final int POPULATION_SIZE = 100;
    private static final double ELITE_RATE = 0.1;
    private static final double SURVIVE_RATE = 0.5;
    private static final double MUTATION_RATE = 0.2;
    private static final int MAX_ITER = 1000;
    private static final int MAZE_WIDTH = 10;
    private static final int MAZE_HEIGHT = 10;

    public static void main(String[] args) throws FileNotFoundException {
        FitnessEngine.logEnabled = false;
        GeneticEngine geneticEngine = new GeneticEngine();
        geneticEngine.setEliteRate(ELITE_RATE);
        geneticEngine.setMutationChance(MUTATION_RATE);
        geneticEngine.setPopulationSize(POPULATION_SIZE);
        geneticEngine.setSurviveRate(SURVIVE_RATE);
        geneticEngine.setMazeWidth(MAZE_WIDTH);
        geneticEngine.setMazeHeight(MAZE_HEIGHT);
        geneticEngine.setLogEnabled(false);

        List<Genome> population = geneticEngine.generateRandomPopulation();

        for (int i = 0; i < MAX_ITER; i++) {
            Collections.sort(population);
//            System.out.println("Best elite genome is: \n" + population.get(population.size() - 1).toString());
//            if (population.get(0).getFitnessRate() == Integer.MAX_VALUE) {
//                break;
//            }
            population = geneticEngine.mate(population);
        }
        Collections.sort(population);
        System.out.println("Best elite genome is: \n" + population.get(0).toString());
//        System.out.println("Final population: \n" + population.toString());
    }

    private static Genome generateMaze() throws FileNotFoundException {
        Scanner sc = new Scanner(new File("maze.txt"));

        int width = sc.nextInt();
        int height = sc.nextInt();

        Genome genome = new Genome(width, height);

        int i = 0;
        int j = 0;

        for (int k = 0; k < width * height; k++) {
            if (j == width) {
                j = 0;
                i++;
            }
            int x = sc.nextInt();
            int y = sc.nextInt();
            Gene gene = new Gene(y == 1, x == 1);
            genome.setGene(i, j, gene);
            j++;
        }

        for (int k = 0; k < height; k++) {
            // bottom fake genes
            genome.setGene(height, k, new Gene(false, true));
        }

        for (int k = 0; k < width; k++) {
            // right fake genes
            genome.setGene(k, width, new Gene(true, false));
        }

        return genome;
    }
}
