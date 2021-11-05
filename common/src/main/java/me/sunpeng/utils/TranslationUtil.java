package me.sunpeng.utils;

import net.dongliu.requests.Requests;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author sp
 * @date 2021-11-04 20:48
 */
public class TranslationUtil {

    private String msg;
    private String url="http://fanyi.youdao.com/translate_o?smartresult=dict&smartresult=rule";
    private String D="n%A-rKaT5fb[Gy?;N5@Tj";
    private String bv;
    private String salt;
    private String sign;
    private String ts;
    private Map<String,Object> header;
    private Map<String,Object> params;
    public TranslationUtil()
    {
        params=new HashMap<String,Object>();
        header=new HashMap<String,Object>();
    }
    private Map<String,Object> getParams(String msg)
    {
        params.put("i", setMsg(msg));//设置msg同时更新ts,salt,sign;
        params.put("from","AUTO");
        params.put("to","AUTO");
        params.put("smartresult","dict");
        params.put("client","fanyideskweb");
        params.put("sign",this.sign);
        params.put("bv",this.bv);
        params.put("ts",this.ts);
        params.put("salt",this.salt);
        params.put("doctype","json");
        params.put("version", "2.1");
        params.put("keyfrom", "fanyi.web");
        params.put("action", "FY_BY_REALTlME");
        return params;
    }
    public String getResult(String msg)
    {
        return Requests.post(url).headers(getHeaders()).body(getParams(msg)).send().readToText();
        //得到json格式的文本
    }
    public  void setHeaders(Map<String,Object> header)
    {
        this.header=header;
    }
    public Map<String,Object> getHeaders()
    {
        if(this.header.get("Referer")==null)
        {
            this.header.put("Referer", "http://fanyi.youdao.com/");
        }
        return this.header;
    }
    public void setUserAgent(String UA)
    {
        this.header.put("User-Agent", UA);
        //设置UserAgent
        String cookie=Requests.get("http://fanyi.youdao.com").headers(this.header).send().getHeader("Set-Cookie").split(";")[0]+";";
        //得到Cookie

        //设置Cookie
        this.header.put("Cookie", cookie);
        this.bv=getBv(UA);//设置UA的同时要更新bv，因为bv是通过加密UA得到的。
    }
    private String getSalt()
    {
        this.salt=String.valueOf(this.ts)+String.valueOf(((int)Math.random()*10));
        return this.salt;

    }
    private String getSign()
    {
        return getMd5("fanyideskweb"+this.msg+this.salt+this.D);
    }
    private String getTs()
    {
        return String.valueOf(System.currentTimeMillis());
    }
    private String getBv(String UserAgent)
    {
        return getMd5(UserAgent);
    }
    private String getMd5(String val)
    {
        return DigestUtils.md5Hex(val);
    }
    public String setMsg(String msg)
    {

        this.msg=msg;
        //设置需要翻译的内容
        this.ts=getTs();//更新ts
        this.salt=getSalt();//更新salt
        //设置翻译内容的同时更新ts,salt
        this.sign=getSign();
        //有了翻译内容,salt才能得到sign,网易主要靠此判断
        return this.msg;
    }
}
