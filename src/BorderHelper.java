/**
 * Created by Bogdan Kornev
 * on 12.02.2015, 13:00.
 */
public class BorderHelper {
    public static void addFakeBorders(Genome genome) {
        // Add fake bottom and right borders gene
        for (int i = 0; i < genome.getHeight(); i++) {
            // bottom fake genes
            genome.setGene(genome.getHeight(), i, new Gene(false, true));
        }

        for (int i = 0; i < genome.getWidth(); i++) {
            // right fake genes
            genome.setGene(i, genome.getWidth(), new Gene(true, false));
        }
    }
}
