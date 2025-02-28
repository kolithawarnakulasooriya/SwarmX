package TSOA;

import org.apache.commons.math3.stat.descriptive.moment.StandardDeviation;
import org.usa.soc.si.SIAlgorithm;
import org.usa.soc.core.action.StepAction;
import org.usa.soc.core.ds.Vector;
import org.usa.soc.util.Mathamatics;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Stream.generate;

public class TestExecutor {

    Path path;
    StringBuffer sb;
    public TestExecutor(){
        this.path = createResultFile();
    }

    public String runTest(SIAlgorithm SIAlgorithm, int testCount, int populationSize, String extra){

        int steps = SIAlgorithm.getStepsCount();

        double[] meanBestValueTrial = new double[steps];
        double[] meanMeanBestValueTrial = new double[steps];
        double[] meanConvergence = new double[steps];
        double meanBestValue =0;
        long meanExecutionTime =0;
        double std = 0;
        List<String[]> dataLines = new ArrayList<>();
        double fraction = steps/100;
        double[] bestValuesArray = new double[testCount];
        String filename = "data/"+System.currentTimeMillis() + ".csv";
        Path p = createFile(filename);
        appendToFile(p, SIAlgorithm.getClass().getSimpleName() + ","+ SIAlgorithm.getFunction().getClass().getSimpleName());

        for(int i=0; i<testCount; i++){
            SIAlgorithm.initialize();
            System.out.println();
            SIAlgorithm.addStepAction(new StepAction() {
                @Override
                public void performAction(Vector best, Double bestValue, int step) {

                    if((step % fraction) == 0){
                        System.out.print("\r ["+ Mathamatics.round(bestValue, 3) +"] ["+step/fraction+"%] "  + generate(() -> "#").limit((long)(step/fraction)).collect(joining()));
                    }
                    if(step >1) {
                        meanBestValueTrial[step-2] += bestValue;
                        meanConvergence[step - 2] += SIAlgorithm.getConvergenceValue();
                        meanMeanBestValueTrial[step - 2] += SIAlgorithm.getMeanBestValue();
                    }
                }
            });
            try {
                SIAlgorithm.run();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            meanBestValue += SIAlgorithm.getBestDoubleValue();
            bestValuesArray[i] = SIAlgorithm.getBestDoubleValue();
            meanExecutionTime += SIAlgorithm.getNanoDuration();
        }

        meanBestValue /= testCount;
        meanExecutionTime /= testCount;
        std = new StandardDeviation().evaluate(bestValuesArray, meanBestValue);
        appendToFile(p, "\nMean Best Value: ," + meanBestValue);
        appendToFile(p, "Mean Execution Time: (ms): ,"+ TimeUnit.MILLISECONDS.convert(meanExecutionTime, TimeUnit.NANOSECONDS));
        appendToFile(p, "MBV, MMBV, MC");

        for(int j = 0; j< SIAlgorithm.getStepsCount(); j++){
            meanBestValueTrial[j] /= testCount;
            meanMeanBestValueTrial[j] /= testCount;
            meanConvergence[j] /= testCount;
            appendToFile(p, meanBestValueTrial[j] +","+meanMeanBestValueTrial[j] +","+meanConvergence[j]);
        }
        System.out.println();

        sb = new StringBuffer();
        sb.append(new Date().toString()).append(',');
        sb.append(extra).append(',');
        sb.append(SIAlgorithm.getClass().getSimpleName()).append(',');
        sb.append(SIAlgorithm.getFunction().getClass().getSimpleName()).append(',');
        sb.append(SIAlgorithm.getFunction().getNumberOfDimensions()).append(',');
        sb.append(populationSize).append(',');
        sb.append(SIAlgorithm.getStepsCount()).append(',');
        sb.append(SIAlgorithm.getFunction().getExpectedBestValue()).append(',');
        sb.append(meanBestValue).append(',');
        sb.append(std).append(',');
        sb.append(TimeUnit.MILLISECONDS.convert(meanExecutionTime, TimeUnit.NANOSECONDS)).append(',');
        sb.append(filename).append(',');

        sb.append('\n');

        return sb.toString();

//        List<XYChart> charts = new ArrayList<XYChart>();
//
//        XYChart chart = new XYChartBuilder().xAxisTitle("step").yAxisTitle("best value").width(2000).height(400).build();
//        XYSeries series = chart.addSeries("Best Value", null, meanBestValueTrial);
//        series.setMarker(SeriesMarkers.NONE);
//        charts.add(chart);
//
//        XYChart chart1 = new XYChartBuilder().xAxisTitle("step").yAxisTitle("mean best value").width(600).height(400).build();
//        XYSeries series1 = chart1.addSeries("Mean Best Value", null, meanMeanBestValueTrial);
//        series1.setMarker(SeriesMarkers.NONE);
//        charts.add(chart1);
//
//        XYChart chart2 = new XYChartBuilder().xAxisTitle("step").yAxisTitle("convergence value").width(600).height(400).build();
//        XYSeries series2 = chart2.addSeries("Convergence Value", null, meanConvergence);
//        series2.setMarker(SeriesMarkers.NONE);
//        charts.add(chart2);
//
//        new SwingWrapper<XYChart>(charts).displayChartMatrix();
    }


    public void appendToFile(){
        try {
            System.out.println(sb.toString());
            Files.write(path, sb.toString().getBytes(), StandardOpenOption.APPEND);
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void appendToFile(Path path, String data){
        try {
            System.out.println(data);
            Files.write(path, data.getBytes(), StandardOpenOption.APPEND);
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Path createResultFile(){
        StringBuffer sb = new StringBuffer();
        sb.append("Date").append(',');
        sb.append("Type").append(',');
        sb.append("Algorithm").append(',');
        sb.append("Function").append(',');
        sb.append("Number of Dimensions").append(',');
        sb.append("Agents Count").append(',');
        sb.append("Steps Count").append(',');
        sb.append("Expected Best Value").append(',');
        sb.append("Actual Mean Best Value").append(',');
        sb.append("STD value").append(',');
        sb.append("Execution time").append(',');
        sb.append('\n');

        Path path = Paths.get("data/result.csv");
        try {
            if(Files.exists(path)){
                return path;
            }
            Files.write(path, sb.toString().getBytes(), StandardOpenOption.CREATE);
        }catch (IOException e) {
            e.printStackTrace();
        }
        return path;
    }

    private Path createFile(String filename){
        Path path = Paths.get(filename);
        try {
            if(Files.exists(path)){
                return path;
            }
            Files.write(path, (new Date().toString()+"\n").getBytes(), StandardOpenOption.CREATE);
        }catch (IOException e) {
            e.printStackTrace();
        }
        return path;
    }

    public void runTestOnce(SIAlgorithm algo) throws Exception {

        algo.addStepAction(new StepAction() {
            @Override
            public void performAction(Vector best, Double bestValue, int step) {
                if(step >1) {
                    System.out.println(bestValue);
                }
            }
        });
        algo.initialize();
        algo.run();
        System.out.println("Final Value "+algo.getBestDoubleValue());
    }
}
