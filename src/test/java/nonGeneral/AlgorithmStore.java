package nonGeneral;

import org.usa.soc.si.SIAlgorithm;
import org.usa.soc.si.ObjectiveFunction;
import examples.si.algo.cs.CS;
import examples.si.algo.fa.FA;
import examples.si.algo.pso.PSO;
import examples.si.algo.cso.CSO;
import examples.si.algo.gso.GSO;
import org.usa.soc.util.Mathamatics;
import examples.si.algo.wso.WSO;

public class AlgorithmStore {

    public SIAlgorithm getPSO(ObjectiveFunction fn, int ac, int sc){
        return new PSO(
                fn,
                ac,
                fn.getNumberOfDimensions(),
                sc,
                1.496180,
                1.496180,
                0.729844,
                fn.getMin(),
                fn.getMax(),
                true);
    }

    public SIAlgorithm getCSO(ObjectiveFunction fn, int ac, int sc){
        return new CSO(
                fn,
                fn.getNumberOfDimensions(),
                sc,
                ac,
                0.2,
                fn.getMin(),
                fn.getMax(),
                10,
                0.2,
                0.2,
                true,
                0.5,
                0.2,
                true
        );
    }

    public SIAlgorithm getGSO(ObjectiveFunction fn, int ac, int sc){
        double sr = Mathamatics.getMaximumDimensionDistance(fn.getMin(), fn.getMax(), fn.getNumberOfDimensions());
        return new GSO(
                fn,
                fn.getNumberOfDimensions(),
                sc,
                ac,
                fn.getMin(),
                fn.getMax(),
                5,
                1,
                0.4,
                0.6,
                3,
                1,
                0.08,
                0.03,
                true
        );
    }

    public SIAlgorithm getWSO(ObjectiveFunction fn, int ac, int sc){
        return new WSO(
                fn,
                sc,
                ac,
                fn.getNumberOfDimensions(),
                fn.getMin(),
                fn.getMax(),
                0.4,
                0.4,
                true
        );
    }
    public SIAlgorithm getCS(ObjectiveFunction fn, int ac, int sc){
        return new CS(
                fn,
                sc,
                fn.getNumberOfDimensions(),
                ac,
                fn.getMin(),
                fn.getMax(),
                1,
                0.25,
                true
        );
    }
    public SIAlgorithm getFA(ObjectiveFunction fn, int ac, int sc){
        return new FA(
                fn,
                sc,
                fn.getNumberOfDimensions(),
                ac,
                fn.getMin(),
                fn.getMax(),
                1,
                0.2,
                1,
                true
        );
    }


}
