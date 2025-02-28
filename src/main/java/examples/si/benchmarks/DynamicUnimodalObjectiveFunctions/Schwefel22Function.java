package examples.si.benchmarks.DynamicUnimodalObjectiveFunctions;

import examples.si.benchmarks.DynamicObjectiveFunction;

public class Schwefel22Function extends DynamicObjectiveFunction {

    public Schwefel22Function(int n){
       super(n, -10, 10, 3, 0);
    }

    public Schwefel22Function(int n, double p, double q){
        super(n, p, q, -30, 0);
    }

    @Override
    public Double call() {
        Double sum = 0.0;
        Double dot = 0.0;
        for(int i=0;i< getNumberOfDimensions();i++){
            Double data = Math.abs((Double)super.getParameters()[i]);
            sum += data;
            dot *= data;
        }
        return sum + dot;
    }
}
