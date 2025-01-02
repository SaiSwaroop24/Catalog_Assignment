import java.util.*;
import java.io.*;
import java.math.BigInteger;

import org.json.simple.*;
import org.json.simple.parser.*;

import netscape.javascript.JSObject;
public class SSSalgorithm {
    public static void main(String[] args) {
        try {
            JSONParser parser=new JSONParser();
            JSONObject obj=(JSONObject)parser.parse(new FileReader("Testcase.json"));
            JSONObject key=(JSONObject)obj.get("keys");

            int k=Integer.parseInt(key.get("k").toString());
            int n=Integer.parseInt(key.get("n").toString());
            List<BigInteger[]> pts=new ArrayList<>();

            for (int i=1;i<=n;i++) {
                if (obj.containsKey(String.valueOf(i))) {
                    JSONObject pt=(JSONObject)obj.get(String.valueOf(i));
                    int x_coordinate=i;
                    int base=Integer.parseInt(pt.get("base").toString());
                    String value=pt.get("value").toString();
                    BigInteger y_coordinate=new BigInteger(value, base);
                    pts.add(new BigInteger[]{BigInteger.valueOf(x_coordinate),y_coordinate});
                }
            }
            List<BigInteger[]> finalPts = pts.subList(0,k);
            BigInteger const_value=lagrangeerMethod(finalPts);
            System.out.println(const_value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static BigInteger lagrangeerMethod(List<BigInteger[]> points) {
        BigInteger result=BigInteger.ZERO;
        int k=points.size();
        for (int i=0;i<k;i++) {
            BigInteger xi=points.get(i)[0];
            BigInteger yi=points.get(i)[1];
            BigInteger rem=yi;
            for (int j=0;j<k;j++) {
                if (i!=j) {
                    BigInteger xj=points.get(j)[0];
                    rem=rem.multiply(xj.negate()).divide(xi.subtract(xj));
                }
            }
            result=result.add(rem);
        }
        return result;
    }
}
