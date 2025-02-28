package examples.multiagent.drone_network_heatbeat;

import examples.multiagent.common.E;
import org.usa.soc.core.ds.Margins;
import org.usa.soc.core.ds.Vector;
import org.usa.soc.multiagent.Agent;
import org.usa.soc.multiagent.runners.Executor;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class DroneAgent extends Agent {
    public int index;
    public int layer = 0;

    public E edge;

    public Vector velocityStar = new Vector(2).setValues(new double[]{0, 1});
    public Vector velocity = new Vector(2).setValues(new double[]{0,0});

    private double omega = 0;

    private double uOmega = 0;
    private double lastOmega = 0;
    private double lastUOmega = 0;

    private long lastTime = 0;

    double Theta = 0.5;
    double K1 = 0;

    public boolean isDisconnected = false;

    public DroneAgent(int i, Margins m, double x, double y, int layer, E edge){
        this.index = i;
        this.layer = layer;
        this.edge = edge;
        this.initPosition(m, x, y);
    }

    public void setA(int index, int value){
        this.edge.A[index] = value;
    }

    public void setB(int index, int value){
        this.edge.B[index] = value;
    }

    void identifyDisconnectedDrones(){

        uOmega =0;
        for(int i=0; i< this.edge.B.length; i++){
            if(!Controller.dronesMap.containsKey(i)){
                continue;
            }
            if(this.edge.A[i] != 0){
                uOmega += this.edge.B[i]*(this.omega - Controller.dronesMap.get(i).omega);
            }
        }
        uOmega *= -1;

        double dOmega = (omega - lastOmega)/500;
        double dUOmega = (uOmega - lastUOmega)/500;
        lastOmega = omega;
        lastUOmega = uOmega;
        Executor.getInstance().updateData(String.valueOf(index), "dOmega", dOmega * 100);
        Executor.getInstance().updateData(String.valueOf(index), "dUOmega", dUOmega * 100);
        Executor.getInstance().updateData("Constants", "K1", Theta);

        if(dUOmega != 0){

            if(dOmega < 0 && dUOmega != 0){
                this.velocity.resetAllValues(0.0);
                try{
                    System.out.println(index+" "+K1 +" "+dUOmega);
                    Executor.getAlgorithm().getAgents(Controller.agentGroup.name).removeAgent(this);
                }catch (Exception e){
                }
                Executor.getAlgorithm().getAgents(Controller.dAgentGroup.name).addAgent(this);
            }
        }
    }
    @Override
    public void step() {
        if(Controller.cAgentGroup.getAgents().contains(this) || isDisconnected){
            return;
        }

        if(this.layer == 0){
            velocity.setVector(velocityStar);
        }else{
            Vector vStar = new Vector(2);
            Vector vU = new Vector(2);

            for(int i = 0; i< this.edge.B.length; i++){
                if(!Controller.dronesMap.containsKey(i) || Controller.dronesMap.get(i).isDisconnected){
                    continue;
                }
                vU.updateVector(this.getPosition().getClonedVector()
                        .operate(Vector.OPERATOR.SUB, Controller.dronesMap.get(i).getPosition().getClonedVector()
                                .operate(Vector.OPERATOR.MULP, (double)this.edge.B[i])
                                .operate(Vector.OPERATOR.MULP, (double)this.edge.A[i])));
                if(this.edge.B[i] < 0){
                    vStar.setVector(Controller.dronesMap.get(i).velocity.getClonedVector());
                }
            }
            K1 = generateK1();
            this.velocity.setVector(vStar.operate(Vector.OPERATOR.ADD,vU.operate(Vector.OPERATOR.MULP, K1)));
        }
        this.updatePosition(this.getPosition().operate(Vector.OPERATOR.ADD, this.velocity));

        // Omega calculation
        if(this.layer == 0){
            this.omega = Controller.OmegaLeader;
        }else{
            double l = 0;
            for(int i = 0; i< this.edge.B.length; i++){
                if(!Controller.dronesMap.containsKey(i) || Controller.dronesMap.get(i).isDisconnected){
                    continue;
                }
                if(this.edge.A[i] != 0){
                    l = Math.max(Controller.dronesMap.get(i).omega * Controller.alpha, l);
                }
            }
            this.omega = l;
        }

        long t = System.currentTimeMillis();

        if(t - lastTime > 50){
            lastTime = t;

            identifyDisconnectedDrones();
        }

    }

    private Double generateK1() {
        if(Theta > 2){
            Theta = 0.5;
        }
        Theta += 1;
        return -0.0004 * Math.sin(Theta * Math.PI);
    }
}
