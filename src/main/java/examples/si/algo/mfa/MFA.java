package examples.si.algo.mfa;

import org.usa.soc.core.AbsAgent;
import org.usa.soc.core.ds.Markers;
import org.usa.soc.si.SIAlgorithm;
import org.usa.soc.si.ObjectiveFunction;
import org.usa.soc.core.ds.Vector;
import org.usa.soc.util.Randoms;
import org.usa.soc.util.Validator;

import java.awt.*;

public class MFA extends SIAlgorithm {

    private int numberOfMoths;

    private double b;

    public MFA (ObjectiveFunction<Double> objectiveFunction,
                int stepsCount,
                int numberOfDimensions,
                int numberOfMoths,
                double[] minBoundary,
                double[] maxBoundary,
                double b){

        this.objectiveFunction = objectiveFunction;
        this.stepsCount = stepsCount;
        this.numberOfDimensions = numberOfDimensions;
        this.minBoundary = minBoundary;
        this.maxBoundary = maxBoundary;
        this.numberOfMoths = numberOfMoths;
        this.b = b;

        this.gBest = isGlobalMinima.isSet() ? new Vector(numberOfDimensions).setMaxVector() : new Vector(numberOfDimensions).setMinVector();

        try{
            addAgents("moths", Markers.CIRCLE, Color.BLUE);
            addAgents("flames", Markers.CIRCLE, Color.RED);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void step() throws Exception {

            if(currentStep == 0){
                for (int i=0; i<numberOfMoths; i++) {
                    Moth m = (Moth)  getAgents("moths").getAgents().get(i);
                    Flame f = new Flame(m.getPosition().getClonedVector());
                    f.setFitnessValue(m.getFitnessValue());
                    getAgents("flames").getAgents().add(f);
                }
                getAgents("flames").sort(1);
                this.gBest.setVector(getAgents("flames").getAgents().get(0).getPosition());
                this.updateBestValue();
            }else{

                for(AbsAgent a: getAgents("moths").getAgents()){
                    Moth m = (Moth) a;
                    Flame f = new Flame(m.getPosition().getClonedVector());
                    f.calcFitnessValue(getObjectiveFunction());
                    getAgents("flames").getAgents().add(f);
                }
                getAgents("flames").sort(1);
                getAgents("flames").subList(0, numberOfMoths);
                this.gBest.setVector(getAgents("flames").getAgents().get(0).getPosition().getClonedVector());
                updateGBest();
            }

            double a = -1 + (currentStep + 1) * ((-1) / numberOfMoths);
            int flameNo = Math.round(numberOfMoths - (currentStep + 1) * ((numberOfMoths - 1) / stepsCount));
            Flame flameP = (Flame)  getAgents("flames").getAgents().get(flameNo-1);

            for (int i=0 ; i<numberOfMoths ;i++){
                Moth moth = (Moth)getAgents("moths").getAgents().get(i);
                Flame flame = (Flame)getAgents("flames").getAgents().get(i);

                Vector distance = flame.getPosition().getClonedVector()
                        .operate(Vector.OPERATOR.SUB, moth.getPosition().getClonedVector()).toAbs();
                Vector t = Randoms.getRandomVector(numberOfDimensions, 0, 1)
                        .operate(Vector.OPERATOR.MULP, (a - 1.0))
                        .operate(Vector.OPERATOR.ADD, 1.0);
                Vector c1 = t.getClonedVector().operate(Vector.OPERATOR.MULP, 2 * Math.PI).toCos();
                Vector c2 = t.getClonedVector().operate(Vector.OPERATOR.MULP, this.b).toExp();
                Vector firstComponent = c1.operate(Vector.OPERATOR.MULP, c2).operate(Vector.OPERATOR.MULP, distance);

                Vector position;
                if(i <= flameNo){
                    position = flame.getPosition().operate(Vector.OPERATOR.ADD, firstComponent);
                }else{
                    position = flameP.getPosition().operate(Vector.OPERATOR.ADD, firstComponent);
                }
                moth.setPosition(position.getClonedVector().fixVector(minBoundary, maxBoundary));
                moth.calcFitnessValue(getObjectiveFunction());
            }
    }

    private void updateGBest() {
        Flame f = (Flame) getAgents("flames").getAgents().get(0);
        if (Validator.validateBestValue(f.getFitnessValue(), getBestDoubleValue(), isGlobalMinima.isSet())) {
            this.gBest.setVector(f.getPosition());
        }
    }

    @Override
    public void initialize() {
        setInitialized(true);

        for(int i=0; i< numberOfMoths; i++){
            Moth moth = new Moth(Randoms.getRandomVector(numberOfDimensions, minBoundary, maxBoundary));
            moth.calcFitnessValue(getObjectiveFunction());
            getAgents("moths").getAgents().add(moth);
        }
    }
}



