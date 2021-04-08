package com.ifenghui.storybookapi.util.ios;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wslhk on 2017/8/25.
 */
public class Receipt {
    //{"status":0, "environment":"Production", "receipt":{"receipt_type":"Production", "adam_id":1195751596, "app_item_id":1195751596, "bundle_id":"com.ifenghui.storyship"
    // , "application_version":"1", "download_id":68022072124445, "version_external_identifier":823340859, "receipt_creation_date":"2017-08-23 13:10:22 Etc/GMT"
    // , "receipt_creation_date_ms":"1503493822000", "receipt_creation_date_pst":"2017-08-23 06:10:22 America/Los_Angeles", "request_date":"2017-08-24 16:43:01 Etc/GMT"

    // , "request_date_ms":"1503592981133", "request_date_pst":"2017-08-24 09:43:01 America/Los_Angeles", "original_purchase_date":"2017-08-10 13:07:46 Etc/GMT"

    // , "original_purchase_date_ms":"1502370466000", "original_purchase_date_pst":"2017-08-10 06:07:46 America/Los_Angeles", "original_application_version":"4"

    // , "in_app":[{"quantity":"1", "product_id":"storyship_68_buy", "transaction_id":"280000235983741", "original_transaction_id":"280000235983741"
    // , "purchase_date":"2017-08-23 13:10:22 Etc/GMT", "purchase_date_ms":"1503493822000", "purchase_date_pst":"2017-08-23 06:10:22 America/Los_Angeles"
    // , "original_purchase_date":"2017-08-23 13:10:22 Etc/GMT"
    // , "original_purchase_date_ms":"1503493822000", "original_purchase_date_pst":"2017-08-23 06:10:22 America/Los_Angeles", "is_trial_period":"false"}]}}
    String receipt_type;
    long adam_id;
    long app_item_id;
    String bundle_id;
    String application_version;
    long download_id;
    long version_external_identifier;
    String receipt_creation_date;
    long receipt_creation_date_ms;
    String receipt_creation_date_pst;
    String request_date;
    long request_date_ms;
    String request_date_pst;
    String original_purchase_date;
    long original_purchase_date_ms;
    String original_purchase_date_pst;
    String original_application_version;
    List<InApp> in_app=new ArrayList<>();

    public String getReceipt_type() {
        return receipt_type;
    }

    public void setReceipt_type(String receipt_type) {
        this.receipt_type = receipt_type;
    }

    public long getAdam_id() {
        return adam_id;
    }

    public void setAdam_id(long adam_id) {
        this.adam_id = adam_id;
    }

    public long getApp_item_id() {
        return app_item_id;
    }

    public void setApp_item_id(long app_item_id) {
        this.app_item_id = app_item_id;
    }

    public String getBundle_id() {
        return bundle_id;
    }

    public void setBundle_id(String bundle_id) {
        this.bundle_id = bundle_id;
    }

    public String getApplication_version() {
        return application_version;
    }

    public void setApplication_version(String application_version) {
        this.application_version = application_version;
    }

    public long getDownload_id() {
        return download_id;
    }

    public void setDownload_id(long download_id) {
        this.download_id = download_id;
    }

    public long getVersion_external_identifier() {
        return version_external_identifier;
    }

    public void setVersion_external_identifier(long version_external_identifier) {
        this.version_external_identifier = version_external_identifier;
    }

    public String getReceipt_creation_date() {
        return receipt_creation_date;
    }

    public void setReceipt_creation_date(String receipt_creation_date) {
        this.receipt_creation_date = receipt_creation_date;
    }

    public long getReceipt_creation_date_ms() {
        return receipt_creation_date_ms;
    }

    public void setReceipt_creation_date_ms(long receipt_creation_date_ms) {
        this.receipt_creation_date_ms = receipt_creation_date_ms;
    }

    public String getReceipt_creation_date_pst() {
        return receipt_creation_date_pst;
    }

    public void setReceipt_creation_date_pst(String receipt_creation_date_pst) {
        this.receipt_creation_date_pst = receipt_creation_date_pst;
    }

    public String getRequest_date() {
        return request_date;
    }

    public void setRequest_date(String request_date) {
        this.request_date = request_date;
    }

    public long getRequest_date_ms() {
        return request_date_ms;
    }

    public void setRequest_date_ms(long request_date_ms) {
        this.request_date_ms = request_date_ms;
    }

    public String getRequest_date_pst() {
        return request_date_pst;
    }

    public void setRequest_date_pst(String request_date_pst) {
        this.request_date_pst = request_date_pst;
    }

    public String getOriginal_purchase_date() {
        return original_purchase_date;
    }

    public void setOriginal_purchase_date(String original_purchase_date) {
        this.original_purchase_date = original_purchase_date;
    }

    public long getOriginal_purchase_date_ms() {
        return original_purchase_date_ms;
    }

    public void setOriginal_purchase_date_ms(long original_purchase_date_ms) {
        this.original_purchase_date_ms = original_purchase_date_ms;
    }

    public String getOriginal_purchase_date_pst() {
        return original_purchase_date_pst;
    }

    public void setOriginal_purchase_date_pst(String original_purchase_date_pst) {
        this.original_purchase_date_pst = original_purchase_date_pst;
    }

    public String getOriginal_application_version() {
        return original_application_version;
    }

    public void setOriginal_application_version(String original_application_version) {
        this.original_application_version = original_application_version;
    }

    public List<InApp> getIn_app() {
        return in_app;
    }

    public void setIn_app(List<InApp> in_app) {
        this.in_app = in_app;
    }
}
