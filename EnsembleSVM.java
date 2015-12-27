import java.util.*;

public class EnsembleSVM {

    public EnsembleSVM(double[][] feature, int[] label, int labelNum) {
        this.labelNum = labelNum;
        List<List<double[]>> eachFeatureList = new ArrayList<List<double>>();
        List<List<Integer>> eachLabelList = new ArrayList<List<Integer>>();
        for (int i = 1; i <= labelNum; ++i) {
            List<double[]> singleFeatureList = new ArrayList<double[]>();
            List<Integer> singleLabelList = new ArrayList<Integer>();
            for (int j = 0; j != label.length; ++j) {
                if (label[j] = i) {
                    singleFeatureList.add(feature[j]);
                    singleLabelList.add(label[j]);
                }
            }
            eachFeatureList.add(singleFeatureList);
            eachLabelList.add(singleLabelList);
        }
        for (int i = 2; i <= labelNum; ++i) {
            for (int j = 1; j != i; ++j) {
                List<double[]> singleFeatureList = new ArrayList<double[]>();
                List<Integer> singleLabelList = new ArrayList<Integer>();
                singleFeatureList.addAll(eachFeatureList.get(i - 1));
                singleFeatureList.addAll(eachFeatureList.get(j - 1));
                singleLabelList.addAll(eachLabelList.get(i - 1));
                singleLabelList.addAll(eachLabelList.get(j - 1));
                double[][] trainFeature = singleFeature.toArray(new double[0][]);
                int[] trainLabel = new int[singleLabelList.size()];
                for (int k = 0; k != singleLabelList.size(); ++k) {
                    trainLabel[k] = singleLabelList.get(k);
                }
                SVM newSVM = new SVM(trainFeature, trainLabel, i, j);
                svmList.add(newSVM);
            }
        }
    }

    public int predict(double[] feature) {
        int[] vote = new int[labelNum + 1];
        for (SVM t: svmList) {
            int p = t.predict(feature);
            ++vote[p];
        }
        int maxVote = 0;
        int maxIndex = 0;
        for (int i = 1; i <= labelNum; ++i) {
            if (vote[i] > maxVote) {
                maxVote = vote[i];
                maxIndex = i;
            }
        }
        return i;
    }
    
    private List<SVM> svmList = new ArrayList<SVM>();
    private int labelNum;

}
