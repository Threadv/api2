package com.ifenghui.storybookapi.app.shop.notify;

import com.ifenghui.storybookapi.config.MyEnv;
import com.ifenghui.storybookapi.exception.ApiNotFoundException;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.Namespace;
import org.jdom.input.SAXBuilder;
import org.xml.sax.InputSource;
import weixin.Utils.WXSignUtils;

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
public class ShopWeixinNotify {

    public String orderIdstr;
    Long orderId;
    String payAccount;
    String xmlDoc;
    Element root;
    String tradeNo;
    public ShopWeixinNotify(HttpServletRequest request) throws IOException, ApiNotFoundException {
        InputStream inStream = request.getInputStream();
        ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = inStream.read(buffer)) != -1) {
            outSteam.write(buffer, 0, len);
        }
        outSteam.close();
        inStream.close();
        this.xmlDoc  = new String(outSteam.toByteArray(),"utf-8");


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

        this.orderIdstr= root.getChild("out_trade_no",ns).getText();
        this.payAccount = root.getChild("openid",ns).getText();
        this.tradeNo= root.getChild("transaction_id",ns).getText();

        //可以从订单号中区分 是单行本 还是  app
        String prefix= MyEnv.env.getProperty("shopOrder.prefix");
        if(this.orderIdstr.contains("_")) {
            orderId = Long.parseLong(this.orderIdstr.split("_")[1]);
            if (!this.orderIdstr.split("_")[0].equals(prefix)) {
                throw new ApiNotFoundException("order format error;not find orderId:"+this.orderIdstr);
            }
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
        if(tradeNo==null){
            return "";
        }
        return tradeNo;
    }

    public Long getOrderId() {
        return orderId;
    }

    /**
     * 微信验签
     * @return true成功 false失败
     */
    public boolean checkWeixinSign(ShopWeixinNotify shopWeixinNotify, String appid, String wxkey, String mch_id){

        //参数：开始生成签名
        SortedMap<Object,Object> parameters = new TreeMap<Object,Object>();
        List<Element> alle= shopWeixinNotify.getRoot().getChildren();

        Namespace namespace = shopWeixinNotify.getRoot().getNamespace();

        System.out.println(alle.get(1));
        for(int i=0;i<alle.size();i++){
            //Element sstr=alle.get(i);
            String key=alle.get(i).getName();
            if(key.equals("sign") || key.equals("appid")  || key.equals("mch_id") ){
                continue;
            }
            parameters.put(key, shopWeixinNotify.getRoot().getChild(key,namespace).getText());
        }
        parameters.put("appid", appid);
        parameters.put("mch_id", mch_id);
        String sign = WXSignUtils.createSign("UTF-8", parameters,wxkey);
//        logger.info("签名是："+sign);
//        logger.info("返回签名是："+shopWeixinNotify.getRoot().getChild("sign",namespace).getText());
        if(!sign.equals(shopWeixinNotify.getRoot().getChild("sign",namespace).getText())){//验签失败

            return false;
        }
        return true;
    }
}