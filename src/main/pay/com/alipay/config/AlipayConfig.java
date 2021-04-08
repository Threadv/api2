package com.alipay.config;

/* *
 *类名：AlipayConfig
 *功能：基础配置类
 *详细：设置帐户有关信息及返回路径
 *版本：1.0
 *日期：2016-06-06
 *说明：
 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 *该代码仅供学习和研究支付宝接口使用，只是提供一个参考。
 */

public class AlipayConfig {
	
//↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓

	//合作身份者ID，签约账号，以2088开头由16位纯数字组成的字符串，查看地址：https://openhome.alipay.com/platform/keyManage.htm?keyType=partner
	public static String partner = "2088911899959822";

	//商户的私钥,需要PKCS8格式，RSA公私钥生成：https://doc.open.alipay.com/doc2/detail.htm?spm=a219a.7629140.0.0.nBDxfy&treeId=58&articleId=103242&docType=1
	public static String private_key = "MIICXQIBAAKBgQCwZJxobact++mfw+GbkFFbDpy0GMBIsBZU79rb0OPNzFOdbW9M\n" +
			"7vFKLgScI74IhvdQ/cvmEkiExtPBhoXWVLVhxJZjW7YxuzfOTnCkesfYdCtArKQ2\n" +
			"ryqnf0RISU3RvB7ckfSiTQW8SYqLaaDOvsPuW6mPJJuP/uA6pYHNUsZqrwIDAQAB\n" +
			"AoGBAIsMZLd1aQo4wOuucBQ/acKL/D+EG1+xX4lJIo+RVWKPpYI+GDTfTtoY+I1V\n" +
			"J6J1MlRoHOmalAwG6DpwHuTcFbZTSJUqYfDN+s850b80w9JqzSR4xE7bsNwW1MMO\n" +
			"NYMGx7OhXQX1BLBCc+08i8nEPeNGiVRfsp9BNzUcQ7c0grahAkEA1Ksdoo1SEOvU\n" +
			"hoM+D5Gnmw2fy2yWYnMa+Q0gptnCM9Lgh5FarNcLMsUDld52FgZ5en6zzVz9ulsE\n" +
			"Ep+bdRXutwJBANRVWlenankczilS2XpeqORMCxJt6IITsEh8+bG7jzBldmvED1Vd\n" +
			"QVTo89cVg6k1I5dH9asIi0qaaQf2hiT068kCQQCKlbOrIPKQqk2FFnNTy/Pvu7Ic\n" +
			"6vcY0q+KCj7z28WM5eeBEHyJU10tyWrrt9s1wSRaacSbl/SUAwmlZTL12sQDAkBX\n" +
			"v95SEzIBU1Iv6blOxQfvMS3yM9G+z/wuONfIB8cpLQU/jAr6MEvrl0oOPSnoJ7nJ\n" +
			"r0dro2BNOHrne438f+thAkA9lkSpT77NuXA7ciASaqBJVp7p1U4gJWmrQn54rRNz\n" +
			"Iu23+UqVXRcnHMxy83nEQxJBNpQlLK9+VrXEfwdZoQ5G";
//	public static String private_key = "
	//支付宝的公钥，查看地址：https://openhome.alipay.com/platform/keyManage.htm?keyType=partner
	public static String alipay_public_key  = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCnxj/9qwVfgoUh/y2W89L6BkRAFljhNhgPdyPuBV64bfQNN1PjbCzkIM6qRdKBoLPXmKKMiFYnkd6rAoprih3/PrQEB/VsW8OoM8fxn67UDYuyBTqA23MML9q1+ilIZwBC2AQ2UBVOrFXfFl75p6/B5KsiNG9zpgmLCUYuLkxpLQIDAQAB";
//    public static String alipay_public_key  = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDDI6d306Q8fIfCOaTXyiUeJHkrIvYISRcc73s3vF1ZT7XN8RNPwJxo8pWaJMmvyTn9N4HQ632qJBVHf8sxHi/fEsraprwCtzvzQETrNRwVxLO5jVmRGi60j8Ue1efIlzPXV9je9mkjzOmdssymZkh2QhUrCmZYI/FCEa3/cNMW0QIDAQAB";

    // 签名方式
	public static String sign_type = "RSA";
	
	// 调试用，创建TXT日志文件夹路径，见AlipayCore.java类中的logResult(String sWord)打印方法。
	public static String log_path ="C://";
		
	// 字符编码格式 目前支持 gbk 或 utf-8
	public static String input_charset = "utf-8";

	// 接收通知的接口名
	public static String service = "mobile.securitypay.pay";

//↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑
}

