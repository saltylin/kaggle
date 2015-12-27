import java.util.Map;

public class Main {
    
    public static void main(String[] args) {
        String filename = "../resource/train.csv";
        DataReader dataReader = new DataReader(filename, true);
        double[][] numFeature = dataReader.getNumFeature();
        String[][] cateFeature = dataReader.getCateFeature();
        int[] response = dataReader.getResponse();
        int totalNum = numFeature.length;
        int testNum = totalNum / 10;
        int trainNum = totalNum - testNum;
        double[][] trainNumFeature = new double[trainNum][];
        String[][] trainCateFeature = new String[trainNum][];
        int[] trainLabel = new int[trainNum];
        for (int i = 0; i != trainNum; ++i) {
            trainNumFeature[i] = numFeature[i];
            trainCateFeature[i] = cateFeature[i];
            trainLabel[i] = response[i];
        }
        double[][] testNumFeature = new double[testNum][];
        String[][] testCateFeature = new String[testNum][];
        int[] testLabel = new int[testNum];
        for (int i = 0; i != testNum; ++i) {
            testNumFeature[i] = numFeature[i + trainNum];
            testCateFeature[i] = cateFeature[i + trainNum];
            testLabel[i] = response[i + trainNum];
        }
        Cluster cluster = new Cluster(trainNumFeature, trainCateFeature, trainLabel);
        int[] clusterLabel = cluster.getClusterLabel();
        Map<Integer, Integer> clusterLabelMap = cluster.getClusterLabelMap();
        MultiDimension multi = new MultiDimension(trainCateFeature);
        double[][] expandCateFeature = multi.getTrainFeature();
        int numFeatureNum = numFeature[0].length;
        int expandCateFeatureNum = expandCateFeature[0].length;
        int totalFeatureNum = numFeatureNum + expandCateFeatureNum;
        double[][] trainFeature = new double[trainNum][];
        for (int i = 0; i != trainNum; ++i) {
            trainFeature[i] = new double[totalFeatureNum];
            for (int j = 0; j != numFeatureNum; ++j) {
                trainFeature[i][j] = trainNumFeature[i][j];
            }
            for (int j = 0; j != expandCateFeatureNum; ++j) {
                trainFeature[i][j + numFeatureNum] = expandCateFeature[i][j];
            }
        }
        EnsembleSVM model = new EnsembleSVM(trainFeature, clusterLabel, 8 * Cluster.clusterNum);
        double[][] testFeature = new double[testNum][];
        double[][] testExpandCateFeature = multi.getTestFeature(testCateFeature);
        for (int i = 0; i != testNum; ++i) {
            testFeature[i] = new double[totalFeatureNum];
            for (int j = 0; j != numFeatureNum; ++j) {
                testFeature[i][j] = testNumFeature[i][j];
            }
            for (int j = 0; j != expandCateFeatureNum; ++j) {
                testFeature[i][j + numFeatureNum] = testExpandCateFeature[i][j];
            }
        }
        int right = 0;
        for (int i = 0; i != testNum; ++i) {
            int predict = model.predict(testFeature[i]);
            if (testLabel[i] == clusterLabelMap.get(predict)) {
                ++right;
            }
        }
        double accuracy = (double)right / testNum;
        System.out.println("accuracy: " + accuracy);
    }

}
