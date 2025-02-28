package examples.si.benchmarks.nonGeneral.classical.unimodal.separable;

import org.usa.soc.si.ObjectiveFunction;
import org.usa.soc.util.Commons;

public class PowellSumFunction extends ObjectiveFunction {
    @Override
    public Double call() {
        Double sum = 0.0;
        for(int i=0;i< getNumberOfDimensions();i++){
            sum += Math.pow(Math.abs((Double) getParameters()[i]), i+2);
        }
        return sum;
    }

    @Override
    public int getNumberOfDimensions() {
        return 10;
    }

    @Override
    public double[] getMin() {
        return Commons.fill(-1, this.getNumberOfDimensions());
    }

    @Override
    public double[] getMax() {
        return Commons.fill(1, this.getNumberOfDimensions());
    }

    @Override
    public double getExpectedBestValue() {
        return 0;
    }

    @Override
    public double[] getExpectedParameters() {
        return Commons.fill(0, this.getNumberOfDimensions());
    }
}
