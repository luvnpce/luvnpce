package Question_01;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class Test01 {

    public static void main(String[] args) {
        String param = "{\"companyCode\":\"3850618\",\"pbCompanyCode\":\"9773515\",\"sourceType\":10,\"operationType\":47,\"list\":[{\"isCheck\":0,\"netDate\":\"2020-09-21\",\"fundCode\":\"SR9764\",\"productName\":\"洛书进取2号私募基金\",\"netValue\":\"1.8432\",\"acumulateNetValue\":\"1.9432\"},{\"isCheck\":0,\"netDate\":\"2020-09-21\",\"fundCode\":\"SR9764\",\"productName\":\"洛书进取2号私募基金\",\"netValue\":\"2\",\"acumulateNetValue\":\"1.9432\"},{\"isCheck\":0,\"netDate\":\"2020-09-21\",\"fundCode\":\"SR9764\",\"productName\":\"洛书进取2号私募基金\",\"netValue\":\"3\",\"acumulateNetValue\":\"1.9432\"}]}";
        JSONObject jsonObject = JSONObject.parseObject(param);

        JSONArray list = jsonObject.getJSONArray("list");
        int size = list.size();
        for (int i = 0; i < size; i++) {
            JSONObject obj = list.getJSONObject(i);
            System.out.println(obj.get("netValue"));
        }
    }
}
