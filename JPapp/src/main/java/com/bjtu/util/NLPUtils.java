package com.bjtu.util;

import com.baidu.aip.nlp.AipNlp;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.util.HashMap;

//@Component

/**
 * 弃用
 */
public class NLPUtils {

    //设置APPID/AK/SK
    public static final String APP_ID = "45255884";
    public static final String API_KEY = "qosW94h4iX6OELcYDMDtwo6a";
    public static final String SECRET_KEY = "X6W5k2I2fFfFzAxNQyubQEakvP4xtNkK";

    //短文本相似度检测（不支持本项目）
    public JSONObject similarityCheck(String text1,String text2){
        // 初始化一个AipNlp
        AipNlp client = new AipNlp(APP_ID, API_KEY, SECRET_KEY);

        // 可选：设置网络连接参数
        client.setConnectionTimeoutInMillis(2000);
        client.setSocketTimeoutInMillis(60000);

        // 传入可选参数调用接口
        HashMap<String, Object> options = new HashMap<String, Object>();
        options.put("model", "BOW");

        // 短文本相似度
        JSONObject res = client.simnet(text1, text2, options);
        return res;
    }

}
