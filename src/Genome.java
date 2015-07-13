/**
 * Created by Bogdan Kornev
 * on 11.02.2015, 13:59.
 */
public class Genome implements Comparable<Genome> {
    private final int width;
    private final int height;
    private Gene[][] data;
    private int fitnessRate = -1;

    public Genome(int width, int height) {
        this.width = width;
        this.height = height;
        data = new Gene[width + 1][height + 1];
    }

    public void setGene(int i, int j, Gene gene) {
        if (i < width + 1 && j < height + 1) {
            data[i][j] = gene;
        }
    }

    public Gene getGene(int i, int j) {
        return data[i][j];
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    @Override
    public int compareTo(Genome other) {
        calcFitnessRate();
        other.calcFitnessRate();
        if (fitnessRate > other.fitnessRate) {
            return -1;
        } else if (fitnessRate < other.fitnessRate) {
            return 1;
        }
        return 0;
    }

    public int getFitnessRate() {
        return fitnessRate;
    }

    public void calcFitnessRate() {
        fitnessRate = FitnessEngine.calcFitness(this);
    }

    public void copyRangeOfGenesTo(Genome other, int locusI, int locusJ, boolean beforeLocus) {
        if (locusI < 0|| locusJ < 0 || locusI > getWidth() || locusJ > getHeight()) {
            throw new ArrayIndexOutOfBoundsException("copyRangeOfGenesTo:\ni=" + locusI + ", j=" + locusJ + ". " +
                    "Width is " + getWidth() + ". Height is " + height);
        }
        if (beforeLocus) {
            for (int i = 0; i < width; i++) {
                for (int j = 0; j < height; j++) {
                    if (i == locusI && j == locusJ) {
                        return;
                    }
                    other.setGene(i, j, getGene(i, j));
                }
            }
        } else {
            for (int i = locusI; i < getWidth(); i++) {
                for (int j = 0; j < getHeight(); j++) {
                    if (i == locusI && j <= locusJ) {
                        continue;
                    }
                    other.setGene(i, j, getGene(i, j));
                }
            }
        }
        if (locusI < getWidth() && locusJ < getHeight()) {
            other.setGene(locusI, locusJ, getGene(locusI, locusJ));
        }
        BorderHelper.addFakeBorders(other);
    }

    @Override
    public String toString() {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                System.out.print(getGene(i, j).isUpWall() ? "+---" : "    ");
            }
            System.out.println("+");
            for (int j = 0; j < height; j++) {
                System.out.print(getGene(i, j).isLeftWall() ? "|   " : "    ");
            }
            System.out.println("|");
        }
        // draw the bottom line
        for (int j = 0; j < width; j++) {
            System.out.print("+---");
        }
        System.out.println("+");

        return "";
    }
}
