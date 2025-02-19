package examples.si.algo.alo;

import org.usa.soc.core.ds.Vector;
import org.usa.soc.si.Agent;
import org.usa.soc.util.Randoms;

public class Ant extends Agent {

    private double fitnessValue;

    public Ant(int numberOfDimensions, double[] minBoundary, double[] maxBoundary) {
        this.maxBoundary =maxBoundary;
        this.minBoundary = minBoundary;
        this.position = Randoms.getRandomVector(numberOfDimensions, minBoundary, maxBoundary, 0 , 1);
    }

    public Vector getPosition() {
        return position.getClonedVector();
    }

    public void setPosition(Vector position) {
        this.position.setVector(position, minBoundary, maxBoundary);
    }

    public void setFitnessValue(double fitnessValue) {
        this.fitnessValue = fitnessValue;
    }

    public double getFitnessValue() {
        return fitnessValue;
    }

    public Ant cloneAnt(){
        Ant a = new Ant(getPosition().getNumberOfDimensions(), minBoundary, maxBoundary);
        a.setPosition(this.position);
        a.setFitnessValue(a.fitnessValue);
        return a;
    }
}
