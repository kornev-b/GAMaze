/**
 * Created by Bogdan Kornev
  on 10.02.2015, 21:11.
 */
public class Gene {
    private boolean leftWall;
    private boolean upWall;

    public Gene(boolean leftWall, boolean upWall){
        this.leftWall = leftWall;
        this.upWall = upWall;
    }

    public boolean isLeftWall() {
        return leftWall;
    }

    public boolean isUpWall() {
        return upWall;
    }
}
