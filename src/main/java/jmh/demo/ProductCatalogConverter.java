package jmh.demo;

import com.codoid.products.exception.FilloException;
import com.codoid.products.fillo.Connection;
import com.codoid.products.fillo.Fillo;
import com.codoid.products.fillo.Recordset;
import org.openjdk.jmh.annotations.Benchmark;

import java.util.*;

public class ProductCatalogConverter {

    public static Map<String, List<Map<String,String>>> run(String filePath) throws FilloException {
        Map<String, List<Map<String,String>>> mObj = new HashMap<>();
        Recordset recordSet = fetchRecords(filePath);
        while(recordSet.next()){
            List<Map<String, String>> productList = new LinkedList<>();
            String id = recordSet.getField("Id");
            String productName = recordSet.getField("Products");
            id = removeSpecialChars(id);
            String [] splitProduct = productName.split("_");
            String productCategory = splitProduct[1];
            Map<String,String> storeObj = storeProductData(id,splitProduct[0]);
            productList.add(storeObj);
            if(mObj.containsKey(productCategory)){
                mObj.get(productCategory).add(storeObj);
            }else{
                mObj.put(productCategory, productList);
            }
        }
        return mObj;
    }

    private static String removeSpecialChars(String id) {
        StringBuilder buildString = new StringBuilder();
        for(char c : id.toCharArray()){
            if(Character.isLetterOrDigit(c)){
                buildString.append(c);
            }
        }
        return buildString.toString();
    }

    private static Map<String,String> storeProductData(String id,String productName){
        Map<String,String> storeObj = new LinkedHashMap<>();
        storeObj.put("Id",id);
        storeObj.put("Product_Name",productName);
        return storeObj;
    }

    private static Recordset fetchRecords(String filePath){
        Fillo fillo=new Fillo();
        Connection connection;
        try {
            connection = fillo.getConnection(filePath);
            return connection.executeQuery("Select * from Sheet1");
        } catch (FilloException e) {
            throw new RuntimeException(e);
        }
    }


}
