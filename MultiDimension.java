import java.util.*;

public class MultiDimension {

    public MultiDimension(String[][] trainCateFeature) {
        int instanceNum = trainCateFeature.length;
        int cateFeatureNum = trainCateFeature[0].length;
        for (int i = 0; i != cateFeatureNum; ++i) {
            Map<String, Integer> tmpCateValueMap = new HashMap<String, Integer>();
            int index = 0;
            for (int j = 0; j != instanceNum; ++j) {
                String val = trainCateFeature[j][i];
                if (!tmpCateValueMap.containsKey(val)) {
                    ++totalSize;
                    tmpCateValueMap.put(val, index++);
                }
            }
            cateValueMapList.add(tmpCateValueMap);
        }
        trainFeature = expandValue(trainCateFeature);
    }

    public double[][] getTrainFeature() {
        return trainFeature;
    }

    public double[][] getTestFeature(String[][] testCateFeature) {
        return expandValue(testCateFeature);
    }

    private double[][] expandValue(String[][] cateValue) {
        double[][] result = new double[cateValue.length][];
        for (int i = 0; i != result.length; ++i) {
            result[i] = new double[totalSize];
            int index = 0;
            for (int j = 0; j != cateValue[0].length; ++j) {
                Map<String, Integer> tmpMap = cateValueMapList.get(j);
                if (tmpMap.containsKey(cateValue[i][j])) {
                    result[i][index + tmpMap.get(cateValue[i][j])] = 1.0;
                }
                index += tmpMap.size();
            }
        }
        return result;
    }

    private double[][] trainFeature;
    private int totalSize;
    private List<Map<String, Integer>> cateValueMapList = new ArrayList<Map<String, Integer>>();

}
