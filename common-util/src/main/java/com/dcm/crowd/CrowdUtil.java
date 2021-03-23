package com.dcm.crowd;

import com.dcm.crowd.constant.CrowdConstant;
import com.dcm.crowd.util.HttpUtils;
import com.dcm.crowd.util.ResultEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;

import javax.servlet.http.HttpServletRequest;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class CrowdUtil {

    public static String md5(String source) {
        if (source == null || source.length() == 0) {
            throw new RuntimeException(CrowdConstant.MESSAGE_STRING_IVALIDATE);
        }


        try {
            String algorithm = "md5";
            MessageDigest instance = MessageDigest.getInstance(algorithm);
            byte[] input = source.getBytes();
            byte[] output = instance.digest(input);
            int signum = 1;
            BigInteger bigInteger = new BigInteger(signum, output);
            String encoded = bigInteger.toString(16).toUpperCase();

            return encoded;

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static boolean judgeRequestType(HttpServletRequest req) {
        String accept = req.getHeader("Accept");
        String xReqWith = req.getHeader("X-Requested-With");

        return (accept != null && accept.contains("application/json")) || xReqWith != null && xReqWith.equals("XMLHttpRequest");


    }

    public static String getSysDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }


    public static ResultEntity<String> sendCodeByShortMessage(

            String host,

            String path,

            String method,

            String mobile,

            String appCode,

            String smsSignId,

            String templateId


    ) {


        Map<String, String> headers = new HashMap<String, String>();

        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + appCode);
        Map<String, String> querys = new HashMap<String, String>();
        querys.put("mobile", mobile);
        StringBuilder sb=new StringBuilder();
        for(int i=0;i<4;i++){
            int random = (int) (Math.random()*10);
            sb.append(random);
        }
        String code=sb.toString();
        querys.put("param", "**code**:"+code+",**minute**:5");
        querys.put("smsSignId", smsSignId);
        querys.put("templateId", templateId);
        Map<String, String> bodys = new HashMap<String, String>();

        try {
            HttpResponse response = HttpUtils.doPost(host, path, method, headers, querys, bodys);

            StatusLine statusLine = response.getStatusLine();

            // 状态码: 200 正常；400 URL无效；401 appCode错误； 403 次数用完； 500 API网管错误
            int statusCode = statusLine.getStatusCode();

            String reasonPhrase = statusLine.getReasonPhrase();

            if (statusCode == 200) {

                // 操作成功，把生成的验证码返回
                return ResultEntity.successWithData(code);
            }

            return ResultEntity.failed(reasonPhrase);

        } catch (Exception e) {
            e.printStackTrace();
            return ResultEntity.failed(e.getMessage());
        }

    }





}
