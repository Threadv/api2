package com.ifenghui.storybookapi.style;

import com.ifenghui.storybookapi.util.PreSaleMD5Util;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;

/**
 * ΢��֧��ǩ��
 * @author iYjrg_xiebin
 * @date 2015��11��25������4:47:07
 */
public class PreSaleWXSignUtils {
	//http://mch.weixin.qq.com/wiki/doc/api/index.php?chapter=4_3
	//�̻�Key���ĳɹ�˾����ļ���
	//32λ�������õ�ַ��http://www.sexauth.com/  jdex1hvufnm1sdcb0e81t36k0d0f15nc
	private static String Key = "03f10a28dce58fb09887e41fe9efb49c";

	/**
	 * ΢��֧��ǩ���㷨sign
	 * @param characterEncoding
	 * @param parameters
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static String createSign(String characterEncoding,SortedMap<Object,Object> parameters,String key){
		StringBuffer sb = new StringBuffer();
		Set es = parameters.entrySet();//
		Iterator it = es.iterator();
		while(it.hasNext()) {
			Map.Entry entry = (Map.Entry)it.next();
			String k = (String)entry.getKey();
			Object v = entry.getValue();
			if(null != v && !"".equals(v) 
					&& !"sign".equals(k) && !"key".equals(k)) {
				sb.append(k + "=" + v + "&");
			}
		}
		sb.append("key=" + key);
		System.out.println("字符串拼接后是"+sb.toString());
		String sign = PreSaleMD5Util.MD5Encode(sb.toString(), characterEncoding).toUpperCase();
		System.out.println("MD5加密："+sign);
		return sign;
	}

	

}
