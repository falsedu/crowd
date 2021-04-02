package com.dcm.crowd.handler;


import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.dcm.crowd.api.MySQLRemoteService;
import com.dcm.crowd.config.PayProperties;
import com.dcm.crowd.entity.vo.OrderProjectVO;
import com.dcm.crowd.entity.vo.OrderVO;
import com.dcm.crowd.util.ResultEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class PayConsumerHandler {

    @Autowired
    private PayProperties alipayConfig;

    @Autowired
    private MySQLRemoteService mySQLRemoteService;



    private Logger logger = LoggerFactory.getLogger(PayConsumerHandler.class);

    @ResponseBody
    @RequestMapping("/generate/order")
    public String generateOrder(HttpSession session, OrderVO orderVO ) throws AlipayApiException {

        OrderProjectVO orderProjectVO = (OrderProjectVO) session.getAttribute("orderProjectVO");
        orderVO.setOrderProjectVO(orderProjectVO);
        String time=new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String uuid= UUID.randomUUID().toString().replaceAll("-","");
        String orderNum=time+uuid;
        orderVO.setOrderNum(orderNum);

        Double orderAmount= Double.valueOf(orderProjectVO.getReturnCount()*orderProjectVO.getSupportPrice()+orderProjectVO.getFreight());
        System.out.println(orderAmount);
        orderVO.setOrderAmount(orderAmount);


        return sendRequestToAliPay(orderNum, orderAmount, orderProjectVO.getProjectName(), orderProjectVO.getReturnContent());




    }


    private String sendRequestToAliPay(String outTradeNo,Double totalAmount ,String subject,String body) throws AlipayApiException {


        //获得初始化的AlipayClient
        AlipayClient alipayClient = new DefaultAlipayClient(
                alipayConfig.getGatewayUrl(),
                alipayConfig.getAppId(),
                alipayConfig.getMerchantPrivateKey(),
                "json",
                alipayConfig.getCharset(),
                alipayConfig.getAlipayPublicKey(),
                alipayConfig.getSignType());

        //设置请求参数
        AlipayTradeQueryRequest alipayRequest = new AlipayTradeQueryRequest();
        alipayRequest.setReturnUrl(alipayConfig.getReturnUrl());
        alipayRequest.setNotifyUrl(alipayConfig.getNotifyUrl());

        //商户订单号，商户网站订单系统中唯一订单号，必填




        alipayRequest.setBizContent("{\"out_trade_no\":\""+ outTradeNo +"\","
                + "\"total_amount\":\""+ totalAmount +"\","
                + "\"subject\":\""+ subject +"\","
                + "\"body\":\""+ body +"\","
//                + "\"trade_no\":\""+ null +"\","
                + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");

        //若想给BizContent增加其他可选请求参数，以增加自定义超时时间参数timeout_express来举例说明
        //alipayRequest.setBizContent("{\"out_trade_no\":\""+ out_trade_no +"\","
        //		+ "\"total_amount\":\""+ total_amount +"\","
        //		+ "\"subject\":\""+ subject +"\","
        //		+ "\"body\":\""+ body +"\","
        //		+ "\"timeout_express\":\"10m\","
        //		+ "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");
        //请求参数可查阅【电脑网站支付的API文档-alipay.trade.page.pay-请求参数】章节

        //请求
        return alipayClient.pageExecute(alipayRequest).getBody();


    }

    @ResponseBody
    @RequestMapping("/return")
    public String returnUrlMethod(HttpServletRequest request, HttpSession session) throws AlipayApiException, UnsupportedEncodingException {

        //获取支付宝GET过来反馈信息
        Map<String,String> params = new HashMap<String,String>();
        Map<String,String[]> requestParams = request.getParameterMap();
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用
            valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            params.put(name, valueStr);
        }

        boolean signVerified = AlipaySignature.rsaCheckV1(
                params,
                alipayConfig.getAlipayPublicKey(),
                alipayConfig.getCharset(),
                alipayConfig.getSignType()); //调用SDK验证签名

        //——请在这里编写您的程序（以下代码仅作参考）——
        if(signVerified) {
            //商户订单号
            String orderNum = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");


            //支付宝交易号
            String payOrderNum = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");


            //付款金额
            String orderAmount = new String(request.getParameter("total_amount").getBytes("ISO-8859-1"),"UTF-8");

            //保存到数据库
            OrderVO orderVO=(OrderVO) session.getAttribute("orderVO");

            orderVO.setPayOrderNum(payOrderNum);


            //ResultEntity<String> resultEntity=mySQLRemoteService.saveOrderRemote(orderVO);


            return "trade_no:"+orderNum+"<br/>out_trade_no:"+payOrderNum+"<br/>total_amount:"+orderAmount;

        }else {
           return "验签失败";
        }

    }
    @ResponseBody
    @RequestMapping("/notify")
    public void notifyUrlMethod(HttpServletRequest request) throws AlipayApiException, UnsupportedEncodingException {

        Map<String,String> params = new HashMap<String,String>();
        Map<String,String[]> requestParams = request.getParameterMap();
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用
            valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            params.put(name, valueStr);
        }

        boolean signVerified = AlipaySignature.rsaCheckV1(
                params,
                alipayConfig.getAlipayPublicKey(),
                alipayConfig.getCharset(),
                alipayConfig.getSignType()); //调用SDK验证签名

        //——请在这里编写您的程序（以下代码仅作参考）——

	/* 实际验证过程建议商户务必添加以下校验：
	1、需要验证该通知数据中的out_trade_no是否为商户系统中创建的订单号，
	2、判断total_amount是否确实为该订单的实际金额（即商户订单创建时的金额），
	3、校验通知中的seller_id（或者seller_email) 是否为out_trade_no这笔单据的对应的操作方（有的时候，一个商户可能有多个seller_id/seller_email）
	4、验证app_id是否为该商户本身。
	*/
        if(signVerified) {//验证成功
            //商户订单号
            String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");

            //支付宝交易号
            String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");

            //交易状态
            String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"),"UTF-8");


            logger.info("trade_status="+trade_status);
            logger.info("out_trade_no="+out_trade_no);
            logger.info("trade_no="+trade_no);


        }else {//验证失败
            logger.info("验证失败");

            //调试用，写文本函数记录程序运行情况是否正常
            //String sWord = AlipaySignature.getSignCheckContentV1(params);
            //AlipayConfig.logResult(sWord);
        }




    }



}
