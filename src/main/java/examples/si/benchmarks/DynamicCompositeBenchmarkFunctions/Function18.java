package examples.si.benchmarks.DynamicCompositeBenchmarkFunctions;

import examples.si.benchmarks.DynamicMultiModalObjectiveFunctions.Function10;
import examples.si.benchmarks.DynamicMultiModalObjectiveFunctions.Schwefel226;
import examples.si.benchmarks.DynamicMultiModalObjectiveFunctions.Function9;
import examples.si.benchmarks.DynamicUnimodalObjectiveFunctions.*;
import org.usa.soc.si.ObjectiveFunction;
import org.usa.soc.util.Commons;

import java.util.ArrayList;
import java.util.List;

public class Function18 extends ObjectiveFunction {

    private int numberOfDimensions = 2;
    private double[] min;
    private double[] max;

    private double[] expected;

    private List<ObjectiveFunction> functions;
    private double [] sigmas;
    private double [] lambdas;

    public Function18(int n){
        this.numberOfDimensions = n;
        min = Commons.fill(-5, n);
        max = Commons.fill(5, n);
        expected = Commons.fill(-400, n);

        functions = new ArrayList<>();

        functions.add(new Function1(n, -5, 5));
        functions.add(new Schwefel22Function(n, -5, 5));
        functions.add(new Schwefel12Function(n, -5, 5));
        functions.add(new Function4(n, -5, 5));
        functions.add(new GeneralizedRosenbrok(n, -5, 5));
        functions.add(new Function6(n, -5, 5));
        functions.add(new Function7(n, -5, 5));
        functions.add(new Schwefel226(n, -5, 5));
        functions.add(new Function9(n, -5, 5));
        functions.add(new Function10(n, -5, 5));

        sigmas = Commons.fill(1,10);
        lambdas = new double[]{
                0.15625, 0.15625, 1, 1, 10, 10, 0.05, 0.05, 0.05, 0.05
        };
    }

    @Override
    public Double call() {
        double sum =0;
        for(int i=0; i< 10; i++){
            ObjectiveFunction fn = functions.get(i);
            fn.setParameters(super.getParameters());
            sum += fn.call() * Commons.computeWeight(sigmas[i], lambdas[i]);
        }
        return sum;
    }

    @Override
    public int getNumberOfDimensions() {
        return numberOfDimensions;
    }

    @Override
    public double[] getMin() {
        return min;
    }

    @Override
    public double[] getMax() {
        return max;
    }

    @Override
    public double getExpectedBestValue() {
        return 0;
    }

    @Override
    public double[] getExpectedParameters() {
        return expected;
    }
}
