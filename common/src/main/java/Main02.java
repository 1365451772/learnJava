import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baidu.translate.demo.TransApi;

import java.io.UnsupportedEncodingException;

/**
 * @author sp
 * @date 2021-11-05 10:55
 */
public class Main02 {

    // 在平台申请的APP_ID 详见 http://api.fanyi.baidu.com/api/trans/product/desktop?req=developer
    private static final String APP_ID = "";
    private static final String SECURITY_KEY = "";

    public static void main(String[] args) throws UnsupportedEncodingException {
        TransApi api = new TransApi(APP_ID, SECURITY_KEY);

        String query = "cinta rahasia";
        String transResult = api.getTransResult(query, "auto", "zh");
        JSONObject transResultJsonObject = JSONObject.parseObject(transResult);
        System.out.println(transResult);
        JSONArray trans_result = transResultJsonObject.getJSONArray("trans_result");
        System.out.println(trans_result.getJSONObject(0).getString("dst"));
    }
}
