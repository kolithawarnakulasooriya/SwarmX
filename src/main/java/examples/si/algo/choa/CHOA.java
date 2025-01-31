package examples.si.algo.choa;

import org.usa.soc.core.AbsAgent;
import org.usa.soc.si.SIAlgorithm;
import org.usa.soc.si.ObjectiveFunction;
import org.usa.soc.core.ds.Vector;
import org.usa.soc.util.Randoms;
import org.usa.soc.util.Validator;

import java.util.ArrayList;

public class CHOA extends SIAlgorithm {

    private int populationSize;

    Chimp attacker, chaser, barrier, divider;

    double fUpper, f;
    private Chaotics.type chaoticType;


    public CHOA(
            ObjectiveFunction objectiveFunction,
            int populationSize,
            int numberOfIterations,
            int numberOfDimensions,
            double[] minBoundary,
            double[] maxBoundary,
            boolean isGlobalMinima,
            double fUpper,
            Chaotics.type type
    ) {

        this.objectiveFunction = objectiveFunction;
        this.populationSize = populationSize;
        this.stepsCount = numberOfIterations;
        this.minBoundary = minBoundary;
        this.maxBoundary = maxBoundary;
        this.numberOfDimensions = numberOfDimensions;
        this.gBest = Randoms.getRandomVector(numberOfDimensions, minBoundary, maxBoundary);
        this.isGlobalMinima.setValue(isGlobalMinima);
        this.fUpper = fUpper;
        this.chaoticType = type;

        this.setFirstAgents("chimps", new ArrayList<>(populationSize));
    }

    @Override
    public void step() throws Exception{
        try{
                sort();
                f = fUpper*(1 - ((currentStep+1)/ stepsCount));
                attacker = (Chimp) getFirstAgents().get(populationSize-1);
                attacker.updateFMAC(f, chaoticType);
                chaser = (Chimp)  getFirstAgents().get(populationSize-2);
                chaser.updateFMAC(f, chaoticType);
                barrier = (Chimp)  getFirstAgents().get(populationSize-3);
                barrier.updateFMAC(f, chaoticType);
                divider = (Chimp)  getFirstAgents().get(populationSize-4);
                divider.updateFMAC(f, chaoticType);

                for(AbsAgent agent : getFirstAgents()){
                    Chimp chimp = (Chimp)agent;
                    chimp.updateFMAC(f, chaoticType);
                    chimp.updateDValues(attacker, chaser, barrier, divider);
                }

                for(AbsAgent agent : getFirstAgents()){
                    Chimp chimp = (Chimp)agent;
                    Vector newX;
                    double u = Randoms.rand(0,1);
                    if(u < 0.5){
                        if(Math.abs(chimp.getA()) < 1){
                            newX = chimp.getPositionFromPrey(this.gBest, f);
                        }else{
                            newX = Randoms.getRandomVector(numberOfDimensions, minBoundary, maxBoundary);
                        }
                    }else if(u > 0.5){
                        Vector x1 = attacker.getPosition().operate(Vector.OPERATOR.SUB, chimp.getDa());
                        Vector x2 = attacker.getPosition().operate(Vector.OPERATOR.SUB, chimp.getDb());
                        Vector x3 = attacker.getPosition().operate(Vector.OPERATOR.SUB, chimp.getDc());
                        Vector x4 = attacker.getPosition().operate(Vector.OPERATOR.SUB, chimp.getDd());

                        newX = x1.operate(Vector.OPERATOR.ADD, x2)
                                .operate(Vector.OPERATOR.ADD, x3)
                                .operate(Vector.OPERATOR.ADD, x4)
                                .operate(Vector.OPERATOR.DIV, 4.0);
                    }else{
                        newX = chimp.getPosition();
                    }

                    chimp.setPosition(newX.fixVector(minBoundary, maxBoundary));
                    chimp.setFitnessValue(getObjectiveFunction().setParameters(chimp.getPosition().getPositionIndexes()).call());
                }

                for(AbsAgent agent : getFirstAgents()){
                    updateGBest((Chimp) agent);
                }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void initialize() {

        this.setInitialized(true);
        Validator.checkPopulationSize(populationSize, 4);

        for(int i=0;i <populationSize; i++){
            Chimp chimp = new Chimp(numberOfDimensions, minBoundary, maxBoundary);
            chimp.setFitnessValue(getObjectiveFunction().setParameters(chimp.getPosition().getPositionIndexes()).call());
            getFirstAgents().add(chimp);

            updateGBest(chimp);
        }

    }

    private void updateGBest(Chimp chimp) {
        double fgbest = getObjectiveFunction().setParameters(this.gBest.getClonedVector().getPositionIndexes()).call();
        if(Validator.validateBestValue(chimp.getFitnessValue(), fgbest, isGlobalMinima.isSet())){
            this.gBest.setVector(chimp.getPosition());
        }
    }
}
