package com.ifenghui.storybookapi.app.presale.notify;



import com.ifenghui.storybookapi.app.presale.exception.PreSaleNotFoundException;
import com.ifenghui.storybookapi.style.PreSaleWXSignUtils;
import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.Namespace;
import org.jdom.input.SAXBuilder;
import org.xml.sax.InputSource;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Created by narella on 2017/7/4.
 */
public class PreSaleWeixinNotify {

    private static Logger logger = Logger.getLogger(PreSaleWeixinNotify.class);

    public String orderIdstr;
    Long orderId;
    String payAccount;
    String xmlDoc;
    Element root;
    String tradeNo;

    public PreSaleWeixinNotify(HttpServletRequest request) throws IOException, PreSaleNotFoundException {
        InputStream inStream = request.getInputStream();
        ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = inStream.read(buffer)) != -1) {
            outSteam.write(buffer, 0, len);
        }
        outSteam.close();
        inStream.close();
        this.xmlDoc = new String(outSteam.toByteArray(), "utf-8");


        StringReader read = new StringReader(xmlDoc);
        InputSource source = new InputSource(read);
        SAXBuilder sb = new SAXBuilder();
        Document doc = null;
        try {
            doc = sb.build(source);
        } catch (JDOMException e) {
            e.printStackTrace();
        }
        //取的根元素
        root = doc.getRootElement();
//            System.out.println(root.getName());//输出根元素的名称（测试）
        Namespace ns = root.getNamespace();

        //获取 orderId

//            logger.info("--------------------wxpayNotify----prefix----"+prefix);

        this.orderIdstr = root.getChild("out_trade_no", ns).getText();
        this.payAccount = root.getChild("openid", ns).getText();
        this.tradeNo = root.getChild("transaction_id", ns).getText();

        System.out.println("=================================out_trade_no:");
        System.out.println(orderIdstr);
        //没有order.prefix-->pre_sale_out_trade
//        String prefix= PreSaleMyEnv.env.getProperty("order.prefix");
//        String prefix= PreSaleMyEnv.env.getProperty("pre_sale_out_trade");
        if (this.orderIdstr.contains("_")) {
            orderId = Long.parseLong(this.orderIdstr.split("_")[1]);
//            if (!this.orderIdstr.split("_")[0].equals(prefix)) {
//                throw new PreSaleNotFoundException("order format error;not find orderId:"+this.orderIdstr);

            System.out.println("=================================orderId:");
            System.out.println(orderId);
//            }
        }

    }

    public String getOrderIdstr() {
        return orderIdstr;
    }

    public String getPayAccount() {
        return payAccount;
    }

    public String getXmlDoc() {
        return xmlDoc;
    }

    public Element getRoot() {
        return root;
    }

    public String getTradeNo() {
        if (tradeNo == null) {
            return "";
        }
        return tradeNo;
    }

    public Long getOrderId() {
        return orderId;
    }

    /**
     * 微信验签
     *
     * @return true成功 false失败
     */
    public boolean checkWeixinSign(PreSaleWeixinNotify preSaleWeixinNotify, String appid, String wxkey, String mch_id) {
//        logger.info("准备验签");
        //验签
//        String appid = PreSaleMyEnv.env.getProperty("appid");
//        String wxkey=PreSaleMyEnv.env.getProperty("wxkey");
//        //秘钥
////        WeixinConfigUtils config = new WeixinConfigUtils();
//        String mch_id = PreSaleMyEnv.env.getProperty("mch_id");
        //参数：开始生成签名
        SortedMap<Object, Object> parameters = new TreeMap<Object, Object>();
        List<Element> alle = preSaleWeixinNotify.getRoot().getChildren();

        Namespace namespace = preSaleWeixinNotify.getRoot().getNamespace();

        System.out.println(alle.get(1));
        for (int i = 0; i < alle.size(); i++) {
            //Element sstr=alle.get(i);
            String key = alle.get(i).getName();
            if (key.equals("sign") || key.equals("appid") || key.equals("mch_id")) {
                continue;
            }
            parameters.put(key, preSaleWeixinNotify.getRoot().getChild(key, namespace).getText());
        }
        parameters.put("appid", appid);
        parameters.put("mch_id", mch_id);
        String sign = PreSaleWXSignUtils.createSign("UTF-8", parameters, wxkey);
        logger.info("签名是：" + sign);
        logger.info("返回签名是：" + preSaleWeixinNotify.getRoot().getChild("sign", namespace).getText());
        if (!sign.equals(preSaleWeixinNotify.getRoot().getChild("sign", namespace).getText())) {//验签失败
            return false;
        }
        return true;
    }

}