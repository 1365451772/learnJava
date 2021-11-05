package me.sunpeng.utils;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.io.StreamProgress;
import cn.hutool.core.io.file.FileReader;
import cn.hutool.core.io.file.FileWriter;
import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.ZipUtil;
import cn.hutool.cron.CronUtil;
import cn.hutool.cron.task.Task;
import cn.hutool.extra.qrcode.QrCodeUtil;
import cn.hutool.extra.qrcode.QrConfig;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import cn.hutool.setting.dialect.Props;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.junit.Test;

import java.awt.*;
import java.io.*;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @author sp
 * @date 2021-11-04 10:47
 */
public class HutoolTest {


    /*
     * 测试时间日期转化api
     * */
    @Test
    public void testConvertDate() {

     /*
      如果字符串格式不符合转换要求而无法成功转换的话，会默认返回null，
      我们也可以给toDate方法添加一个默认值，比如上面的data6，如果字符串能成功转换，自然就会返回转换后的内容，
      字符串不能成功转换， 会返回默认值，上面默认值设置的是当前系统时间

       */
        System.out.println(Convert.toDate("2021/11/4"));
        System.out.println(Convert.toDate("20211104182025"));
        System.out.println(Convert.toDate("2021-01-02 18:20:25"));
        System.out.println(Convert.toDate("2021-01-02", new Date()));
    }

    @Test
    public void testToList() {
        String[] arr = {"abc", "hello"};
        final List<String> strings = Convert.toList(String.class, arr);
        for (String o : strings) {
            System.out.println(o);
        }
    }

    @Test
    public void digitToChinese() {
        double d = 67556.32;
        String s = Convert.digitToChinese(d);
        System.out.println(Convert.numberToWord(d));
        System.out.println(s);
    }

    @Test
    public void testDateUtil() {
        //当前时间
        System.out.println(DateUtil.date());

        final long l = System.currentTimeMillis();
        System.out.println(DateUtil.date(l));

        System.out.println(DateUtil.now());

        System.out.println(DateUtil.today());

        String dateStr = "20210102201625";
        Date date = DateUtil.parse(dateStr);
        System.out.println(date);//运行结果：2021-01-02 20:16:25

        //使用默认格式：yyyy-MM-dd HH:mm:ss
        String str = DateUtil.formatDateTime(new Date());
        System.out.println(str);//运行结果：2021-01-02 20:10:16

        //自定义格式
        String time = DateUtil.format(new Date(), "yyyy/MM/dd");
        System.out.println(time);

    }

    @Test
    public void testFIleCopy() {
        String filaPath = "/Users/sunpeng/Desktop/dist.zip";
        String DeskTopPath = "/Users/sunpeng/Desktop/";
        final BufferedInputStream inputStream = FileUtil.getInputStream(filaPath);
        final BufferedOutputStream outputStream = FileUtil.getOutputStream(DeskTopPath + "hutool.zip");
        //使用copy方法进行文件拷贝时，也是可以指定缓存区大小的，比如IoUtil.copy(in,out,1024)就是指缓存区的大小是1024，不指定的话默认是8192
        final long copySize = IoUtil.copy(inputStream, outputStream);
        System.out.println(FileUtil.getSize(copySize));

    }

    @Test
    public void testFIleReaderAndWriter() {

        //默认UTF-8编码，可以在构造中传入第二个参数来自定义编码方式
        FileWriter writer = new FileWriter("/Users/sunpeng/Desktop/a.txt", Charset.forName("gbk"));
        String str = "test123测试使用sdfghj";
        writer.write(str);

        //默认UTF-8编码，可以在构造中传入第二个参数来自定义编码方式
        String filaPath = "/Users/sunpeng/Desktop/a.txt";
        FileReader fileReader = new FileReader(filaPath, "gbk");
        String result = fileReader.readString();
        System.out.println(result);

    }

    @Test
    public void testResouceUtil() {
        final BufferedReader utf8Reader = ResourceUtil.getUtf8Reader("config/config.properties");
        System.out.println(ResourceUtil.readUtf8Str("config/config.properties"));
        final Props props = new Props("config/config.properties", "gbk");
        System.out.println(props.getStr("name"));

        try {
            System.out.println(utf8Reader.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testStrUtil() {
        System.out.println(StrUtil.isAllBlank("123", "test"));
    }

    @Test
    public void testFormat() {
        String template = "{}爱{},就像老鼠爱大米";
        String str = StrUtil.format(template, "wo", "ni");
        System.out.println(str);

    }

    @Test
    public void testZipUtil() {
        String filaPath = "/Users/sunpeng/Desktop/dist2.zip";

        final byte[] bytes = ZipUtil.unzipFileBytes(filaPath, "a.txt");
        String htmlContent = new String(bytes);
        System.out.println(htmlContent);
    }

    @Test
    public void testZipUtil2() {
        File file = ZipUtil.zip("/Users/sunpeng/Desktop/dist", "/Users/sunpeng/Desktop/test.zip");
        System.out.println(file.getName());//运行结果：dist.zip
    }

    @Test
    public void testZipUtil3() {
        //将"E/Users/sunpeng/Desktop/"目录下的多个文件及文件夹打包到"E:\new\abc.zip"文件中
        File file = ZipUtil.zip(FileUtil.file("/Users/sunpeng/Desktop/dist.zip"), true,
                FileUtil.file("/Users/sunpeng/Desktop/log"),
                FileUtil.file("/Users/sunpeng/Desktop/b.txt"),
                FileUtil.file("/Users/sunpeng/Desktop/c"));
        System.out.println(file.getName());//运行结果：abc.zip
    }

    public void testZipUtils4() {
        //将/Users/sunpeng/Desktop/dist.zip 解压到 dist2 目录下，返回值是解压的目录
        final File unzip = ZipUtil.unzip("/Users/sunpeng/Desktop/dist.zip", "/Users/sunpeng/Desktop/dist2");
        System.out.println(unzip.getName());

    }

    @Test
    public void testJsonUtil() {
        //xml 转 Json
        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<DayFlowInfoQryReq>\n" +
                "    <IDType>12</IDType>\n" +
                "    <IDItemRange>13567895432</IDItemRange>\n" +
                "    <BizType>01</BizType>\n" +
                "    <POCont>\n" +
                "        <BooleanTest>true</BooleanTest>\n" +
                "        <OprTime>20210103161025</OprTime>\n" +
                "    </POCont>\n" +
                "    <IdentCode>3240ad5b9b43a</IdentCode>\n" +
                "</DayFlowInfoQryReq>";
        System.out.println(JSONUtil.xmlToJson(xml).toStringPretty());
    }

    @Test
    public void testJsonUtil2() {
        //json 转xml
        String jsonStr = "{\n" +
                "    \"DayFlowInfoQryReq\": {\n" +
                "        \"IDItemRange\": 13567895432,\n" +
                "        \"BizType\": \"01\",\n" +
                "        \"POCont\": {\n" +
                "            \"OprTime\": 20210103161025,\n" +
                "            \"BooleanTest\": true\n" +
                "        },\n" +
                "        \"IDType\": 12,\n" +
                "        \"IdentCode\": \"3240ad5b9b43a\"\n" +
                "    }\n" +
                "}";
        String xmlStr = JSONUtil.toXmlStr(new JSONObject(jsonStr));
        System.out.println(xmlStr);

    }

    @Test
    public void testJsonUtil3() {
        //从文件里读取json或者json数组
        final File file = new File("/a.json");
        System.out.println(JSONUtil.readJSONObject(file, Charset.forName("UTF-8")));
    }

    @Test
    public void testHttpUtil() {
        String requestUrl = "http://learning.happymmall.com/centos.html";
        //最简单单位HTTP请求，可以自动通过header等信息判断编码,不区分HTTP和HTTPS
        System.out.println(HttpUtil.get(requestUrl));
        System.out.println("****");
        //当无法识别页面编码时候，可以自定义请求页面的编码
        System.out.println(HttpUtil.get(requestUrl, CharsetUtil.CHARSET_UTF_8));

        //单独传入http参数，参数自动做URL编码，拼接在URL中
        HashMap<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("city", "北京");
        requestUrl = "https://www.baidu.com";
        System.out.println(HttpUtil.get(requestUrl, paramMap));

    }

    @Test
    public void testHttpUtil1() {
        //发送请求体body到对应地址(可以是xml格式或者json格式的字符串，也可以是a=1&b=2这种格式的字符串)
        String result1 = HttpUtil.post("https://www.baidu.com", "{\"name\":\"123\",\"type\":\"abc\"}");
        System.out.println(result1);

        //发送post表单数据到对应地址
        HashMap<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("name", "123");
        paramMap.put("type", "abc");
        String result2 = HttpUtil.post("https://www.baidu.com", paramMap);
        System.out.println(result2);

    }

    @Test
    public void testhttpUtil3() {
        //url 请求下载文件
        String fileUrl = "http://learning.happymmall.com/nginx/linux-nginx-1.10.2.tar.gz";
        String deskTopPath = "//Users/sunpeng/Desktop";

        StreamProgress streamProgress = new StreamProgress() {
            @Override
            public void start() {
                System.out.println("开始");
            }

            @Override
            public void progress(long l) {
                System.out.println("******  " + l + "   ******");
            }

            @Override
            public void finish() {
                System.out.println("结束");
            }
        };
        final long szie = HttpUtil.downloadFile(fileUrl, new File(deskTopPath), streamProgress);
        System.out.println("size: " + szie);
        final String strSize = FileUtil.readableFileSize(szie);
        System.out.println("readableFileSize: " + strSize);


    }


    @Test
    public void testScheduled() {
        //表示支持秒级别的定时任务
        CronUtil.setMatchSecond(true);
        final int[] i = {0};
        CronUtil.schedule("* * * * * ?", (Task) HutoolTest::run);
        CronUtil.start();
    }


    public static int i = 1;

    public static void run() {
        System.out.println("测试执行情况--->" + (i++));
        if (i > 10) {
            CronUtil.stop();
        }
    }

    @Test
    public void testQrCodeUtil() {
        //生成指定url对应的二维码到文件，宽和高都是300 像素
        final File file = FileUtil.file("/Users/sunpeng/Desktop/QrCode.jpg");
        QrCodeUtil.generate("https://www.bilibili.com/", 300, 300, file);
    }

    @Test
    public void testtestQrCodeUtil1() {
        /*  很多时候，二维码无法识别，这时就要调整纠错级别。
           纠错级别使用zxing的ErrorCorrectionLevel枚举封装，包括：L、M、Q、H几个参数，由低到高。低级别的像素块更大，
           可以远距离识别，但是遮挡就会造成无法识别。高级别则相反，像素块小，允许遮挡一定范围，但是像素块更密集*/
        QrConfig config = new QrConfig();
        //高纠错级别
        config.setErrorCorrection(ErrorCorrectionLevel.H);
        File file = FileUtil.file("/Users/sunpeng/Desktop/QrCode.jpg");
        QrCodeUtil.generate("https://www.bilibili.com/", config, file);

    }

    @Test
    public void testtestQrCodeUtil2(){
        String result = QrCodeUtil.decode(FileUtil.file("/Users/sunpeng/Desktop/QrCode.jpg"));
        System.out.println(result);//运行结果：https://www.bilibili.com/
    }

    @Test
    public void testtestQrCodeUtil3(){
        //设置二维码的宽和高都是300像素
        QrConfig config = new QrConfig(300, 300);
        //设置边距，既二维码和背景之间的边距
        config.setMargin(2);
        //设置前景色，既二维码颜色
        config.setForeColor(new Color(0, 255, 255));
        //设置背景色
        config.setBackColor(new Color(128, 128, 128));
        //生成二维码到文件，也可以到流
        File file = FileUtil.file("/Users/sunpeng/Desktop/QrCode.jpg");
        QrCodeUtil.generate("https://www.bilibili.com/", config, file);


        //设置二维码的宽和高都是300像素
        QrConfig config2 = new QrConfig(300, 300);
        //设置边距，既二维码和背景之间的边距
        config2.setMargin(1);
        //设置二维码中的logo图片
        config2.setImg("/Users/sunpeng/Desktop/logo_small.jpg");

        File file2 = FileUtil.file("/Users/sunpeng/Desktop/QrCode2.jpg");
        QrCodeUtil.generate("https://www.bilibili.com/", config2, file2);


    }


}
