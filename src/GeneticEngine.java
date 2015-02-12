import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Bogdan Kornev
 * on 11.02.2015, 18:21.
 */
public class GeneticEngine {
    private int populationSize;
    private double mutationChance;
    private double eliteRate;
    private double surviveRate;
    private int mazeWidth;
    private int mazeHeight;
    private boolean isLogEnabled;
    private boolean isEliteSurviveEnabled;

    public List<Genome> generateRandomPopulation() {
        List<Genome> population = new ArrayList<Genome>();
        for (int k = 0; k < populationSize; k++) {
            Genome genome = new Genome(mazeWidth, mazeHeight);
            // Fill maze with random genes
            for (int i = 0; i < mazeWidth; i++) {
                for (int j = 0; j < mazeHeight; j++) {
                    Gene gene;

                    if (i == 0) {
                        if (j == 0) {
                            gene = new Gene(true, true);
                        } else {
                            gene = new Gene(Math.round(Math.random()) == 1,
                                    true);
                        }
                    } else if (j == 0) {
                        gene = new Gene(true,
                                Math.round(Math.random()) == 1);
                    } else {
                        gene = new Gene(Math.round(Math.random()) == 1,
                                Math.round(Math.random()) == 1);
                    }

                    genome.setGene(i, j, gene);
                }
            }
            BorderHelper.addFakeBorders(genome);

            population.add(genome);
        }

        return population;
    }

    public List<Genome> mate(List<Genome> population) {
        List<Genome> children = new ArrayList<Genome>();
        int eliteSize = (int) (populationSize * eliteRate);
//        Collections.sort(population);
        // todo do we need to save elites?
//        List<Genome> elites = selectEliteToSurvive(population, eliteSize);
//        children.addAll(elites);
        for (int i = 0; i < populationSize; i++) {
            int firstParentIndex = (int) (Math.random() * populationSize * surviveRate);
            int secondParentIndex = (int) (Math.random() * populationSize * surviveRate);
            Genome firstParent = population.get(firstParentIndex);
            Genome secondParent = population.get(secondParentIndex);

            List<Genome> twoChildren = crossover(firstParent, secondParent);
            checkMutate(twoChildren.get(0));
            checkMutate(twoChildren.get(1));
            children.addAll(twoChildren);
        }
        return children;
    }

    private void checkMutate(Genome genome) {
        if (Math.random() < mutationChance) {
            mutate(genome);
        }
    }

    private void mutate(Genome genome) {
        int locusI = (int) (Math.random() * genome.getWidth());
        int locusJ = (int) (Math.random() * genome.getHeight());
        if (isLogEnabled) {
            System.out.println("Mutation in process... Locus: [" + locusI +"]" + "["+locusJ+"]");
        }

        Gene gene = genome.getGene(locusI, locusJ);
        if (isLogEnabled) {
            System.out.println("Gene before: " + (gene.isUpWall() ? "1" : "0") + "," + (gene.isUpWall() ? "1" : "0"));
        }
        if (locusI == 0) {
            if (locusJ == 0) {
                mutate(genome);
            } else {
                gene = new Gene(Math.round(Math.random()) == 1,
                        true);
            }
        } else if (locusJ == 0) {
            gene = new Gene(true,
                    Math.round(Math.random()) == 1);
        } else {
            gene = new Gene(Math.round(Math.random()) == 1,
                    Math.round(Math.random()) == 1);
        }

        genome.setGene(locusI, locusJ, gene);
        if (isLogEnabled) {
            System.out.println("Gene after: " + (gene.isUpWall() ? "1" : "0") + "," + (gene.isUpWall() ? "1" : "0"));
        }
    }

    private List<Genome> crossover(Genome firstParent, Genome secondParent) {
        Genome firstChild = new Genome(mazeWidth, mazeHeight);
        Genome secondChild = new Genome(mazeWidth, mazeHeight);

        int locusI = (int) (Math.random() * firstParent.getWidth());
        int locusJ = (int) (Math.random() * firstParent.getHeight());
//        int locusI = 2;
//        int locusJ = 2;

        if (isLogEnabled) {
            System.out.println("Crossover in process... Locus: [" + locusI +"]" + "["+locusJ+"]");
        }

        firstParent.copyRangeOfGenesTo(firstChild, locusI, locusJ, true);
        secondParent.copyRangeOfGenesTo(firstChild, locusI, locusJ, false);
        firstParent.copyRangeOfGenesTo(secondChild, locusI, locusJ, false);
        secondParent.copyRangeOfGenesTo(secondChild, locusI, locusJ, true);
        if (isLogEnabled) {
            System.out.println("First parent: \n" + firstParent.toString());
            System.out.println("Second parent: \n" + secondParent.toString());
            System.out.println("First child: \n" + firstChild.toString());
            System.out.println("Second child: \n" + secondChild.toString());
        }

        List<Genome> children = new ArrayList<Genome>();
        children.add(firstChild);
        children.add(secondChild);

        return children;
    }

    public List<Genome> selectEliteToSurvive(List<Genome> genomes, int eliteSize) {
        List<Genome> selected = new ArrayList<Genome>();
        for (int i = 0; i < eliteSize; i++) {
            selected.add(genomes.get(i));
        }
        return selected;
    }

    public int getPopulationSize() {
        return populationSize;
    }

    public void setPopulationSize(int populationSize) {
        this.populationSize = populationSize;
    }

    public double getMutationChance() {
        return mutationChance;
    }

    public void setMutationChance(double mutationChance) {
        this.mutationChance = mutationChance;
    }

    public double getEliteRate() {
        return eliteRate;
    }

    public void setEliteRate(double eliteRate) {
        this.eliteRate = eliteRate;
    }

    public double getSurviveRate() {
        return surviveRate;
    }

    public void setSurviveRate(double surviveRate) {
        this.surviveRate = surviveRate;
    }

    public int getMazeWidth() {
        return mazeWidth;
    }

    public void setMazeWidth(int mazeWidth) {
        this.mazeWidth = mazeWidth;
    }

    public int getMazeHeight() {
        return mazeHeight;
    }

    public void setMazeHeight(int mazeHeight) {
        this.mazeHeight = mazeHeight;
    }

    public void setLogEnabled(boolean isLogEnabled) {
        this.isLogEnabled = isLogEnabled;
    }

    public void setEliteSurviveEnabled(boolean isEliteSurviveEnabled) {
        this.isEliteSurviveEnabled = isEliteSurviveEnabled;
    }
}
