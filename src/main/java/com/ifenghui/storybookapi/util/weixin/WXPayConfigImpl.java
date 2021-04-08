package com.ifenghui.storybookapi.util.weixin;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;


public class WXPayConfigImpl extends WXPayConfig {

    private String appId;
    private String mchId;
    private byte[] certData;
    private static WXPayConfigImpl INSTANCE;

    private WXPayConfigImpl(String certpath, String appId, String mchId) throws Exception {
        this.appId = appId;
        this.mchId = mchId;
//        String certPath = "C:\\Users\\Administrator\\Desktop\\cert故事飞船商户号微信证书\\apiclient_cert.p12";
        String certPath = certpath;

        File file = new File(certPath);
        InputStream certStream = new FileInputStream(file);
        this.certData = new byte[(int) file.length()];
        certStream.read(this.certData);
        certStream.close();
    }

    public static WXPayConfigImpl getInstance(String certpath, String appId, String mchId) throws Exception {
        if (INSTANCE == null) {
            synchronized (WXPayConfigImpl.class) {
                if (INSTANCE == null) {
                    INSTANCE = new WXPayConfigImpl(certpath, appId, mchId);
                }
            }
        }
        return INSTANCE;
    }


    @Override
    public String getAppID() {
        return this.appId;
//        return "wx4bc81cc98c93e293";
    }

    @Override
    public String getMchID() {
        return this.mchId;
//        return "1445089302";
//        return "1436256202";
    }



    @Override
    public String getKey() {
        return "gushifeichuan1988abcdefg12345678";
    }

    @Override
    public InputStream getCertStream() {
        ByteArrayInputStream certBis;
        certBis = new ByteArrayInputStream(this.certData);
        return certBis;
    }


    @Override
    public int getHttpConnectTimeoutMs() {
        return 2000;
    }

    @Override
    public int getHttpReadTimeoutMs() {
        return 10000;
    }

    @Override
    IWXPayDomain getWXPayDomain() {
        return WXPayDomainSimpleImpl.instance();
    }

    public String getPrimaryDomain() {
        return "api.mch.weixin.qq.com";
    }

    public String getAlternateDomain() {
        return "api2.mch.weixin.qq.com";
    }

    @Override
    public int getReportWorkerNum() {
        return 1;
    }

    @Override
    public int getReportBatchSize() {
        return 2;
    }
}
