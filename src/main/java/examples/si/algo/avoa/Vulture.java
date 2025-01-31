package examples.si.algo.avoa;

import org.usa.soc.core.ds.Vector;
import org.usa.soc.si.Agent;
import org.usa.soc.util.Randoms;

public class Vulture extends Agent {

    private Vector lbest;

    private double fitnessValue;

    public Vulture(int numberOfDimensions,double[] minBoundary, double[] maxBoundary) {
        this.minBoundary = minBoundary;
        this.maxBoundary = maxBoundary;
        this.position = Randoms.getRandomVector(numberOfDimensions, minBoundary, maxBoundary, 0,1);
        this.lbest = this.position.getClonedVector();
    }

    public double getFitnessValue() {
        return this.fitnessValue;
    }

    public void setFitnessValue(double fitnessValue) {
        this.fitnessValue = fitnessValue;
    }

    public Vector getPosition() {
        return this.position.getClonedVector();
    }

    public void setPosition(Vector position) {
        this.position.setVector(position);
    }

    public Vector getLbest() {
        return lbest.getClonedVector();
    }

    public void setLbest(Vector lbest) {
        this.lbest.setVector(lbest);
    }
}
