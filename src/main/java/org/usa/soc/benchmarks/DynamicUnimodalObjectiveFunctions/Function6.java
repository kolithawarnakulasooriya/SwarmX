package org.usa.soc.benchmarks.DynamicUnimodalObjectiveFunctions;

import org.usa.soc.ObjectiveFunction;
import org.usa.soc.benchmarks.DynamicObjectiveFunction;
import org.usa.soc.util.Commons;

import java.lang.reflect.Array;
import java.util.Arrays;

public class Function6 extends DynamicObjectiveFunction {

    public Function6(int n){
        super(n, -100, 100, -750, 0);
    }

    public  Function6(int n, double p, double q){
        super(n, p, q, -30, 0);
    }

    @Override
    public Double call() {
        return Arrays.stream(super.getParameters()).mapToDouble(d-> Math.pow(Math.abs((Double)d + 0.5),2)).sum();
    }
}
