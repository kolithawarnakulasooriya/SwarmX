package org.usa.soc.benchmarks.DynamicUnimodalObjectiveFunctions;

import org.usa.soc.ObjectiveFunction;
import org.usa.soc.benchmarks.DynamicObjectiveFunction;
import org.usa.soc.util.Commons;

import java.util.Arrays;

public class Function1 extends DynamicObjectiveFunction {

    public  Function1(int n){
        super(n, -100, 100, -30, 0);
    }

    public  Function1(int n, double p, double q){
        super(n, p, q, -30, 0);
    }

    @Override
    public Double call() {
        Double sum = 0.0;
        for(int i=0;i< getNumberOfDimensions();i++){
            sum += ( Math.pow((Double)super.getParameters()[i], 2));
        }
        return sum;
    }
}
