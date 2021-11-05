import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import me.sunpeng.utils.TranslationUtil;

/**
 * @author sp
 * @date 2021-11-04 20:51
 */
public class Main01 {

    public static void main(String[] args) {
        TranslationUtil translationUtil=new TranslationUtil();
        translationUtil.setUserAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/95.0.4638.69 Safari/537.36");
        String st=translationUtil.getResult("hello");
        System.out.print(st);
        JSONObject json= JSONObject.parseObject(st);
        JSONArray ja=(JSONArray) ((JSONArray) json.get("translateResult")).get(0);
        JSONObject js=(JSONObject) ja.get(0);
        System.out.print(js.get("tgt"));
    }
}
