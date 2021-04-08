package com.huawei.pay.callback.demo.servlet;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;

import com.huawei.pay.callback.demo.domain.ResultDomain;
import com.huawei.pay.callback.demo.util.RSA;

@SuppressWarnings("serial")
public class CallbackDemo extends HttpServlet {

	public static  String devPubKey =
			              "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA6G+EO/1bGC1sxE7rNULfE8Hl+pie7dHfqv6sGP+ITUnzRyJUtKULgoz76WCu8AFyNqMKnpSxnMucws1WlGbJs+rqwrXfB8yr5eyxaUiwewQQArQAlt3zD/YQidsglsA+UmqFJBkfP1zcpUo6MYZH98cxO/B34qwQbsVNP5pjfV0OHed0H+W0a/hdp4HQiqZ/Ir3AELjpjWuvxP0XT3t1sYsOVRa/XTYS+Gd1dG9BwHwaaIwJh++Wc8MUHCkMx9tkJQdIPxPQWJ2/oR0SoEMlmPGq/mWC6knUm+eFru7AoKEcbNbIeEorDJcSurklFQdWwxkjMCmZ8jzXmco3UeBXFQIDAQAB";


	/**
	 * Constructor of the object.
	 */
	public CallbackDemo() {
		super();
	}

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");

		Map<String, Object> map = null;
		map = getValue(request);
		if (null == map) {
			return;
		}

		String sign = (String) map.get("sign");
		ResultDomain result = new ResultDomain();
		result.setResult(1);

		if (RSA.rsaDoCheck(map, sign, devPubKey, (String)map.get("signType"))) {
			result.setResult(0);
	        System.out.println("Result : 0!");
		} else {
			result.setResult(1);
	        System.out.println("Result : 1!");
		}

		String resultinfo = convertJsonStyle(result);

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");

		System.out.println("Response string: " + resultinfo);

		PrintWriter out = response.getWriter();

		out.print(resultinfo);
		out.close();
	}

    public void writeFile(String filename, String content) throws Throwable{
        File file = new File(filename);
        //目录如果不存在就创建
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }

        FileOutputStream outSTr = new FileOutputStream(file, true);
        BufferedOutputStream Buff=new BufferedOutputStream(outSTr);

        Buff.write(content.getBytes());
        Buff.flush();
        Buff.close();
        outSTr.close();
    }


	/**
	 * @param request
	 * @return
	 * 本接口Content-Type是：application/x-www-form-urlencoded，对所有参数，会自动进行编码，接收端收到消息也会自动根据Content-Type进行解码。
	 * 同时，接口中参数在发送端并没有进行单独的URLEncode (sign和extReserved、sysReserved参数除外)，所以，在接收端根据Content-Type解码后，即为原始的参数信息。
	 * 但是HttpServletRequest的getParameter()方法会对指定参数执行隐含的URLDecoder.decode(),所以，相应参数中如果包含比如"%"，就会发生错误。
	 * 因此，我们建议通过如下方法获取原始参数信息。
	 *
	 * 注：使用如下方法必须在原始ServletRequest未被处理的情况下进行，否则无法获取到信息。比如，在Struts情况，由于struts层已经对参数进行若干处理，
	 * http中InputStream中其实已经没有信息，因此，本方法不适用。要获取原始信息，必须在原始的，未经处理的ServletRequest中进行。
	 */
	public Map<String, Object> getValue(HttpServletRequest request) {

/*		String path = request.getRealPath("/");
		//path = path.substring(0, path.indexOf("webapps"));
		//path = request.getContextPath();
		if (path.endsWith(File.separator)) {
			path = path;
		}else {
			path = path + File.separator;
		}
		path = path + "../../logs/my.log";*/
		String signInput=request.getParameter("sign");
		String sysReservedInput=request.getParameter("sysReserved");
		String extReservedInput=request.getParameter("extReserved");
		String productNameInput=request.getParameter("productName");
		Map valueMapInput=request.getParameterMap();
		Iterator it=valueMapInput.keySet().iterator();
		Map valueMap=new HashMap();
		while(it.hasNext()){
			String key=(String)it.next();
			String[] values=(String[]) valueMapInput.get(key);
			valueMap.put(key,values[0]);
		}


		if(valueMap.size()==0) {
            return valueMap;
        }


        //接口中，如下参数sign和extReserved是URLEncode的，所以需要decode，其他参数直接是原始信息发送，不需要decode
        try {
	        String sign = (String) valueMap.get("sign");
	        String extReserved = (String) valueMap.get("extReserved");
	        String sysReserved =  (String) valueMap.get("sysReserved");

	        if (null != sign) {
//	        	sign = URLDecoder.decode(sign, "utf-8");
		        valueMap.put("sign", signInput);
	        }
	        if (null != extReserved) {
//	        	extReserved = URLDecoder.decode(extReserved, "utf-8");
		        valueMap.put("extReserved", extReservedInput);
	        }

	        if (null != sysReserved) {
//	        	sysReserved = URLDecoder.decode(sysReserved, "utf-8");
		        valueMap.put("sysReserved", sysReservedInput);
	        }
	        if(null!=productNameInput){
				valueMap.put("productName", productNameInput);
			}

        } catch(Exception e){
        	e.printStackTrace();
        }

        return valueMap;
	}

	public String convertJsonStyle(Object resultMessage)
	{
		ObjectMapper mapper = new ObjectMapper();
		Writer writer = new StringWriter();
		try {
			if ( null != resultMessage) {
				mapper.writeValue(writer, resultMessage);
			}

		} catch (Exception e) {

		}

		return writer.toString();
	}

}
