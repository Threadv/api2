package com.ifenghui.storybookapi.app.system.service.impl;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.ifenghui.storybookapi.app.system.service.MessageService;
import com.ifenghui.storybookapi.exception.ApiNotFoundException;
import com.ifenghui.storybookapi.style.CheckCodeStyle;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

@Component
public class MessageServiceImpl implements MessageService {

    @Value("${oss.accesskeyid}")
    String accessKeyId;

    @Value("${oss.accesskeysecret}")
    String accessKeySecret;

        //产品名称:云通信短信API产品,开发者无需替换
        static final String product = "Dysmsapi";
        //产品域名,开发者无需替换
        static final String domain = "dysmsapi.aliyuncs.com";


    public String getAccessKeyId() {
        return accessKeyId;
    }

    public void setAccessKeyId(String accessKeyId) {
        this.accessKeyId = accessKeyId;
    }

    public String getAccessKeySecret() {
        return accessKeySecret;
    }

    public void setAccessKeySecret(String accessKeySecret) {
        this.accessKeySecret = accessKeySecret;
    }

    @Override
    public  SendSmsResponse sendSms(String phone, String templateCode, Map<String,String> map) {

        //可自助调整超时时间
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");

        try {
            //初始化acsClient,暂不支持region化
            IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
            DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
            IAcsClient acsClient = new DefaultAcsClient(profile);

            //组装请求对象-具体描述见控制台-文档部分内容
            SendSmsRequest request = new SendSmsRequest();
            //必填:待发送手机号
            request.setPhoneNumbers(phone);
            //必填:短信签名-可在短信控制台中找到
            request.setSignName("故事飞船");

            //必填:短信模板-可在短信控制台中找到
//            request.setTemplateCode("SMS_1000000");
            //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
//            request.setTemplateParam("{\"name\":\"Tom\", \"code\":\"123\"}");

            //根据模板id判断模板类型
            request.setTemplateCode(templateCode);
            //获得map所有键放入set集合中
            Set<Map.Entry<String,String>> entrySet = map.entrySet();
            Iterator<Map.Entry<String, String>> iterator = entrySet.iterator();
            String str = "";
            for(int i=0;i<map.size();i++){
                //将键值取出放入map.entry接口中
                Map.Entry<String, String> mapEntry = iterator.next();
                String key = mapEntry.getKey();
                Object value = mapEntry.getValue();

                //拼接字符串
                if(i<map.size()-1){
                    str +='"'+key+'"'+":"+'"'+value+'"'+",";
                }else {
                    str +='"'+key+'"'+":"+'"'+value+'"';
                }
            }
            //拼接之后完整的字符串放入{}
            request.setTemplateParam("{"+str+"}");
//            if(checkCodeStyle==CheckCodeStyle.BAOBAO||checkCodeStyle==CheckCodeStyle.UNENG){
//                request.setTemplateParam("{\"code\":\"" + map.get("code") + "\"}");
//
//            }else  if(checkCodeStyle==CheckCodeStyle.REGISTER_CODE){
//                request.setTemplateParam("{\"code\":\"" + map.get("code") + "\",\"product\":\""+map.get("product")+"\"}");
//
//            }


//            request.setTemplateParam("{\"gift\":"+ gift +", \"express\":"+express+", \"code\":"+code+", \"number\":"+number+", \"tel\":"+tel+"}");
            //选填-上行短信扩展码(无特殊需求用户请忽略此字段)
            //request.setSmsUpExtendCode("90997");
            //可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
//            request.setOutId("yourOutId");

            //hint 此处可能会抛出异常，注意catch
            SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);

            if(!sendSmsResponse.getCode().equals("OK")){
                throw new ApiNotFoundException("sms error,"+sendSmsResponse.getMessage());
            }
            return sendSmsResponse;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
