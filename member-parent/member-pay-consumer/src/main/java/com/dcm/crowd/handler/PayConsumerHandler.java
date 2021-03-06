package com.dcm.crowd.handler;


import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
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

        orderVO.setOrderAmount(orderAmount);
        session.setAttribute("orderVO", orderVO);

        return sendRequestToAliPay(orderNum, orderAmount, orderProjectVO.getProjectName(), orderProjectVO.getReturnContent());




    }


    private String sendRequestToAliPay(String outTradeNo,Double totalAmount ,String subject,String body) throws AlipayApiException {
        //??????????????????AlipayClient
        AlipayClient alipayClient = new DefaultAlipayClient(
                alipayConfig.getGatewayUrl(),
                alipayConfig.getAppId(),
                alipayConfig.getMerchantPrivateKey(),
                "json",
                alipayConfig.getCharset(),
                alipayConfig.getAlipayPublicKey(),
                alipayConfig.getSignType());

        //??????????????????
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        alipayRequest.setReturnUrl(alipayConfig.getReturnUrl());
        alipayRequest.setNotifyUrl(alipayConfig.getNotifyUrl());

        //?????????????????????????????????????????????????????????????????????


        alipayRequest.setBizContent("{\"out_trade_no\":\""+ outTradeNo +"\","
                + "\"total_amount\":\""+ totalAmount +"\","
                + "\"subject\":\""+ subject +"\","
                + "\"body\":\""+ body +"\","
                + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");

        //?????????BizContent?????????????????????????????????????????????????????????????????????timeout_express???????????????
        //alipayRequest.setBizContent("{\"out_trade_no\":\""+ out_trade_no +"\","
        //		+ "\"total_amount\":\""+ total_amount +"\","
        //		+ "\"subject\":\""+ subject +"\","
        //		+ "\"body\":\""+ body +"\","
        //		+ "\"timeout_express\":\"10m\","
        //		+ "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");
        //?????????????????????????????????????????????API??????-alipay.trade.page.pay-?????????????????????

        //??????
        return alipayClient.pageExecute(alipayRequest).getBody();
    }

    @ResponseBody
    @RequestMapping("/return")
    public String returnUrlMethod(HttpServletRequest request, HttpSession session) throws AlipayApiException, UnsupportedEncodingException {

        //???????????????GET??????????????????
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
            //???????????????????????????????????????????????????
            valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            params.put(name, valueStr);
        }

        boolean signVerified = AlipaySignature.rsaCheckV1(
                params,
                alipayConfig.getAlipayPublicKey(),
                alipayConfig.getCharset(),
                alipayConfig.getSignType()); //??????SDK????????????

        //????????????????????????????????????????????????????????????????????????
        if(signVerified) {
            //???????????????
            String orderNum = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");


            //??????????????????
            String payOrderNum = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");
            logger.info("payOrderNum="+payOrderNum);


            //????????????
            String orderAmount = new String(request.getParameter("total_amount").getBytes("ISO-8859-1"),"UTF-8");

            //??????????????????

            // 1.???Session????????????OrderVO??????
            OrderVO orderVO = (OrderVO) session.getAttribute("orderVO");
            logger.info(orderVO.toString());

            // 2.??????????????????????????????OrderVO?????????
            orderVO.setPayOrderNum(payOrderNum);
            logger.info(orderVO.toString());

            // 3.?????????MySQL???????????????
            ResultEntity<String> resultEntity = mySQLRemoteService.saveOrderRemote(orderVO);
            logger.info("Order save result="+resultEntity.getResult());


            //ResultEntity<String> resultEntity=mySQLRemoteService.saveOrderRemote(orderVO);


            return "trade_no:"+payOrderNum+"<br/>out_trade_no:"+orderNum+"<br/>total_amount:"+orderAmount;

        }else {
           return "????????????";
        }

    }

    @RequestMapping("/notify")
    public void notifyUrlMethod(HttpServletRequest request) throws AlipayApiException, UnsupportedEncodingException {
//???????????????POST??????????????????
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
            //???????????????????????????????????????????????????
            valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            params.put(name, valueStr);
        }

        boolean signVerified = AlipaySignature.rsaCheckV1(
                params,
                alipayConfig.getAlipayPublicKey(),
                alipayConfig.getCharset(),
                alipayConfig.getSignType()); //??????SDK????????????

        //????????????????????????????????????????????????????????????????????????

	/* ?????????????????????????????????????????????????????????
	1????????????????????????????????????out_trade_no?????????????????????????????????????????????
	2?????????total_amount?????????????????????????????????????????????????????????????????????????????????
	3?????????????????????seller_id?????????seller_email) ?????????out_trade_no??????????????????????????????????????????????????????????????????????????????seller_id/seller_email???
	4?????????app_id???????????????????????????
	*/
        if(signVerified) {//????????????
            //???????????????
            String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");

            //??????????????????
            String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");

            //????????????
            String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"),"UTF-8");

            if(trade_status.equals("TRADE_FINISHED")){
                //????????????????????????????????????????????????????????????
                //?????????????????????????????????????????????out_trade_no????????????????????????????????????????????????????????????????????????????????????????????????
                //??????????????????????????????????????????????????????

                //?????????
                //????????????????????????????????????????????????????????????????????????????????????????????????????????????
            }else if (trade_status.equals("TRADE_SUCCESS")){
                //????????????????????????????????????????????????????????????
                //?????????????????????????????????????????????out_trade_no????????????????????????????????????????????????????????????????????????????????????????????????
                //??????????????????????????????????????????????????????

                //?????????
                //????????????????????????????????????????????????????????????
            }

            logger.info("trade_status="+trade_status);
            logger.info("out_trade_no="+out_trade_no);
            logger.info("trade_no="+trade_no);

        }else {//????????????

            logger.info("????????????");
            //???????????????????????????????????????????????????????????????
            //String sWord = AlipaySignature.getSignCheckContentV1(params);
            //AlipayConfig.logResult(sWord);
        }






    }



}
