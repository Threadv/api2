package weixin.Utils;

import java.io.IOException;
import java.io.StringReader;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.xml.sax.InputSource;

import weixin.entity.UnifiedorderResult;
import weixin.entity.WXPayResult;

/**

 */
public class JdomParseXmlUtils {
	
	/**
	 * @param xml
	 */
	@SuppressWarnings("unchecked")
	public static UnifiedorderResult getUnifiedorderResult(String xml){
		UnifiedorderResult unifieorderResult = new UnifiedorderResult();
		try { 
			StringReader read = new StringReader(xml);
			InputSource source = new InputSource(read);
			SAXBuilder sb = new SAXBuilder();
			Document doc;
			doc = (Document) sb.build(source);

			Element root = doc.getRootElement();
			List<Element> list = root.getChildren();

			if(list!=null&&list.size()>0){
				for (Element element : list) {
					/*
					 * <xml>
						   <return_code><![CDATA[SUCCESS]]></return_code>
						   <return_msg><![CDATA[OK]]></return_msg>
						   <appid><![CDATA[wx2421b1c4370ec43b]]></appid>
						   <mch_id><![CDATA[10000100]]></mch_id>
						   <nonce_str><![CDATA[IITRi8Iabbblz1Jc]]></nonce_str>
						   <sign><![CDATA[7921E432F65EB8ED0CE9755F0E86D72F]]></sign>
						   <result_code><![CDATA[SUCCESS]]></result_code>
						   <prepay_id><![CDATA[wx201411101639507cbf6ffd8b0779950874]]></prepay_id>
						   <trade_type><![CDATA[JSAPI]]></trade_type>
						</xml>
					 */
					System.out.println(""+element.getName()+""+element.getText());
					
					if("return_code".equals(element.getName())){
						unifieorderResult.setReturn_code(element.getText());
					}
					
					if("return_msg".equals(element.getName())){
						unifieorderResult.setReturn_msg(element.getText());
					}
					
					if("appid".equals(element.getName())){
						unifieorderResult.setAppid(element.getText());
					}
					
					
					if("mch_id".equals(element.getName())){
						unifieorderResult.setMch_id(element.getText());
					}
					
					if("nonce_str".equals(element.getName())){
						unifieorderResult.setNonce_str(element.getText());
					}
					
					if("sign".equals(element.getName())){
						unifieorderResult.setSign(element.getText());
					}
					
					if("result_code".equals(element.getName())){
						unifieorderResult.setResult_code(element.getText());
					}
					
					if("prepay_id".equals(element.getName())){
						unifieorderResult.setPrepay_id(element.getText());
					}
					
					if("trade_type".equals(element.getName())){
						unifieorderResult.setTrade_type(element.getText());
					}
				}
			}

		} catch (JDOMException e) {
			e.printStackTrace();
		}  catch (IOException e) {
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return unifieorderResult;
	}
	
	
	/**
	 * @param xml
	 */
	@SuppressWarnings("unchecked")
	public static WXPayResult getWXPayResult(String xml){
		WXPayResult wXPayResult = new WXPayResult();
		try { 
			StringReader read = new StringReader(xml);

			InputSource source = new InputSource(read);

			SAXBuilder sb = new SAXBuilder();

			Document doc;
			doc = (Document) sb.build(source);

			Element root = doc.getRootElement();// ???????????????????
			List<Element> list = root.getChildren();

			if(list!=null&&list.size()>0){
				for (Element element : list) {
					System.out.println("key"+element.getName()+""+element.getText());
					
					if("return_code".equals(element.getName())){
						wXPayResult.setReturn_code(element.getText());
					}
					
					if("return_msg".equals(element.getName())){
						wXPayResult.setReturn_msg(element.getText());
					}
					
					
					if("appid".equals(element.getName())){
						wXPayResult.setAppid(element.getText());
					}
					
					if("mch_id".equals(element.getName())){
						wXPayResult.setMch_id(element.getText());
					}
					
					if("nonce_str".equals(element.getName())){
						wXPayResult.setNonce_str(element.getText());
					}
					
					if("sign".equals(element.getName())){
						wXPayResult.setSign(element.getText());
					}
					
					if("result_code".equals(element.getName())){
						wXPayResult.setResult_code(element.getText());
					}
					
					if("return_msg".equals(element.getName())){
						wXPayResult.setReturn_msg(element.getText());
					}
					
					if("return_msg".equals(element.getName())){
						wXPayResult.setReturn_msg(element.getText());
					}
					
					if("return_msg".equals(element.getName())){
						wXPayResult.setReturn_msg(element.getText());
					}
					
					if("return_msg".equals(element.getName())){
						wXPayResult.setReturn_msg(element.getText());
					}
					
					if("return_msg".equals(element.getName())){
						wXPayResult.setReturn_msg(element.getText());
					}
					
					if("return_msg".equals(element.getName())){
						wXPayResult.setReturn_msg(element.getText());
					}
					
					if("return_msg".equals(element.getName())){
						wXPayResult.setReturn_msg(element.getText());
					}
					
					if("return_msg".equals(element.getName())){
						wXPayResult.setReturn_msg(element.getText());
					}
					
				}
			}

		} catch (JDOMException e) {
			e.printStackTrace();
		}  catch (IOException e) {
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return wXPayResult;
	}

}
