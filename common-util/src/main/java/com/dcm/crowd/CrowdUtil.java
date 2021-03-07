package com.dcm.crowd;

import com.dcm.crowd.constant.CrowdConstant;

import javax.servlet.http.HttpServletRequest;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


public class CrowdUtil {

    public static String md5(String source){
        if(source==null||source.length()==0){
            throw new RuntimeException(CrowdConstant.MESSAGE_STRING_IVALIDATE);
        }


        try {
            String algorithm="md5";
            MessageDigest instance = MessageDigest.getInstance(algorithm);
            byte[]input=source.getBytes();
            byte[] output = instance.digest(input);
            int signum=1;
            BigInteger bigInteger=new BigInteger(signum,output);
            String encoded = bigInteger.toString(16).toUpperCase();

            return encoded;

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }



    public static boolean judgeRequestType(HttpServletRequest req){
        String accept = req.getHeader("Accept");
        String xReqWith = req.getHeader("X-Requested-With");

        return (accept!=null&&accept.contains("application/json"))||xReqWith!=null&&xReqWith.equals("XMLHttpRequest");



    }
    public static String  getSysDate(){
        SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date=new Date();
        return dateFormat.format(date);
    }
}
