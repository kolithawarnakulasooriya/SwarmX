package LF;

import examples.multiagent.leader_election.Main;
import examples.multiagent.leader_election.core.WalkType;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.usa.soc.util.StringFormatter;

import java.util.ArrayList;
import java.util.List;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class L1T5 {

    @RepeatedTest(10)
    public void L1T5Raft(RepetitionInfo t) {
        try{
            Main m = new Main(5, 5, 1, 15,0.001, 0.0001, WalkType.FORWARD);
            Core.executeForwardRaft(m, "L1T5", t.getCurrentRepetition(), 150, 350);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @RepeatedTest(10)
    public void L1T5Random(RepetitionInfo t) {
        try{
            Main m = new Main(5, 5, 1, 15,0.001, 0.0001, WalkType.FORWARD);
            Core.executeForwardRandom(m, "L1T5", t.getCurrentRepetition(), 150, 350);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @RepeatedTest(10)
    public void L1T5GHS(RepetitionInfo t) {
        try{
            Main m = new Main(5, 5, 1, 15,0.001, 0.0001, WalkType.FORWARD);
            Core.executeForwardGHS(m, "L1T5", t.getCurrentRepetition(), 150, 350);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @RepeatedTest(10)
    public void L1T5TSOA(RepetitionInfo t) {
        try{
            Main m = new Main(5, 5, 1, 15,0.001, 0.0001, WalkType.FORWARD);
            Core.executeForwardTSOAInd(m, "L1T5", t.getCurrentRepetition(), 150, 350);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
