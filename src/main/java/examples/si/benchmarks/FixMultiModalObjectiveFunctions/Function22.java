package examples.si.benchmarks.FixMultiModalObjectiveFunctions;

import examples.si.benchmarks.DynamicObjectiveFunction;

public class Function22 extends DynamicObjectiveFunction {

    public Function22(){

        super(2, -10,10, new double[]{1.34941, -1.34941, -1.34941, 1.34941, -1.34941, -1.34941, 1.34941, 1.34941},-2.06261 );

    }

    @Override
    public Double call() {
        Double x = (Double) super.getParameters()[0];
        Double y = (Double) super.getParameters()[1];

        double d1 = Math.abs(100 - ( Math.sqrt( Math.pow(x,2) + Math.pow(y,2)) / Math.PI));
        d1 = Math.sin(x)* Math.sin(y)*Math.exp(d1);
        return Math.pow(Math.abs(d1) + 1, 0.1) * (-0.0001);
    }

}
