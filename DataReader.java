import java.io.*;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;

public class DataReader {

    public DataReader(String filename, boolean isTrainingFile) {
        String[] numAttrName = numAttrStr.split(",");
        Set<String> numAttrNameSet = new HashSet<String>();
        for (String name: numAttrName) {
            numAttrNameSet.add(name.trim());
        }
        try {
            Scanner scanner = new Scanner(new FileInputStream(new File(filename)));
            String[] str = scanner.nextLine();
            str = str.substring(1, str.length - 1);
            String[] attrName = str.split("[,\"]+");
            int numFeatureNum = 0;
            int cateFeatureNum = 0;
            int attrNum = isTrainingFile? attrName.length - 2: attrName.length - 1;
            DataType[] dataType = new DataType[attrNum];
            for (int i = 0; i != dataType.length; ++i) {
                if (numAttrNameSet.contains(attrName[i + 1])) {
                    ++numFeatureNum;
                    dataType[i] = DataType.numeric;
                } else {
                    ++cateFeatureNum;
                    dataType[i] = DataType.categorical;
                }
            }
            numFeatureList = new ArrayList<Double[]>();
            cateFeatureList = new ArrayList<String[]>();
            if (isTrainingFile) {
                responseList = new ArrayList<Integer>();
            }
            while(scanner.hasNextLine()) {
                String[] attrValue = scanner.nextLine().split("[,\"]+");
                Double[] singleNumFeature = new Double[numFeatureNum];
                String[] singleCateFeature = new String[cateFeatureNum];
                int numI = 0;
                int cateI = 0;
                for (int i = 0; i != dataType.length; ++i) {
                    if (dataType[i] == DataType.numeric) {
                        singleNumFeature[numI++] = Double.valueOf(attrValue[i + 1]);
                    } else {
                        singleCateFeature[cateI++] = attrValue[i + 1];
                    }
                }
                numFeatureList.add(singleNumFeature);
                cateFeatureList.add(singleCateFeature);
                if (isTrainingFile) {
                    Integer singleResponse = Integer.valueOf(attrValue[attrValue.length - 1]);
                    responseList.add(singleResponse);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public List<Double[]> getNumFeatureList() {
        return numFeatureList;
    }

    public List<String[]> getCateFeatureList() {
        return cateFeatureList;
    }

    public List<Integer> getResponseList() {
        return responseList;
    }

    private List<Double[]> numFeatureList;
    private List<String[]> cateFeatureList;
    private List<Integer> responseList;
    private static final String numAttrStr = "Product_Info_4, Ins_Age, Ht, Wt, BMI, Employment_Info_1, Employment_Info_4, Employment_Info_6, Insurance_History_5, Family_Hist_2, Family_Hist_3, Family_Hist_4, Family_Hist_5, Medical_History_1, Medical_History_10, Medical_History_15, Medical_History_24, Medical_History_32";

}
