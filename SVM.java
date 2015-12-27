import java.util.*;

public class SVM {
    
    public SVM(double[][] feature, int[] label, int label1, int label2) {
        this.label1 = label1;
        this.label2 = label2;
        int instanceNum = feature.length; 
        int featureNum = feature[0].length;
        w = new double[featureNum];
        T = 10 * instanceNum;
        Random random = new Random();
        for (int t = 1; t != T; ++t) {
            int randomIndex = random.nextInt(instanceNum);
            double yt = 1.0 / (coe * t);
            int tmpLabel = label[randomIndex] == label1? 1 : -1;
            double flag = 0.0;
            for (int i = 0; i != featureNum; ++i) {
                if (randomIndex >= feature.length) {
                    System.out.println("randomIndex: " + randomIndex + "  feature.length: " + feature.length);
                } else if (i >= feature[randomIndex].length) {
                    System.out.println("i: " + i + "  feature length: " + feature[randomIndex].length);
                } else if (i >= w.length) {
                    System.out.println( i + "   " + w.length);
                }
                flag += feature[randomIndex][i] * w[i];
            }
            flag *= tmpLabel;
            if (flag < 1.0) {
                for (int i = 0; i != featureNum; ++i) {
                    w[i] = (1 - yt * coe) * w[i] + yt * tmpLabel * feature[randomIndex][i];
                }
            } else {
                for (int i = 0; i != featureNum; ++i) {
                    w[i] = (1 - yt * coe) * w[i];
                }
            }
        }
    }

    public int predict(double[] feature) {
        double flag = 0.0;
        for (int i = 0; i != feature.length; ++i) {
            flag += feature[i] * w[i];
        }
        return flag > 0? label1 : label2;
    }

    private static final double coe = 0.001;
    private int T;
    private double[] w;
    private int label1;
    private int label2;

}
