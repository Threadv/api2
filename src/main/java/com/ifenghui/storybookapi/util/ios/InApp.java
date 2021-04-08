package com.ifenghui.storybookapi.util.ios;

/**
 * Created by wslhk on 2017/8/25.
 */
public class InApp {
    // , "in_app":[{"quantity":"1", "product_id":"storyship_68_buy", "transaction_id":"280000235983741", "original_transaction_id":"280000235983741"

    // , "purchase_date":"2017-08-23 13:10:22 Etc/GMT", "purchase_date_ms":"1503493822000", "purchase_date_pst":"2017-08-23 06:10:22 America/Los_Angeles"
    // , "original_purchase_date":"2017-08-23 13:10:22 Etc/GMT"
    // , "original_purchase_date_ms":"1503493822000", "original_purchase_date_pst":"2017-08-23 06:10:22 America/Los_Angeles", "is_trial_period":"false"}]}}
    String quantity;
    String product_id;
    String transaction_id;
    String original_transaction_id;
    String purchase_date;
    String purchase_date_ms;
    String purchase_date_pst;
    String original_purchase_date;
    String original_purchase_date_ms;
    String original_purchase_date_pst;
    String is_trial_period;

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getTransaction_id() {
        return transaction_id;
    }

    public void setTransaction_id(String transaction_id) {
        this.transaction_id = transaction_id;
    }

    public String getOriginal_transaction_id() {
        return original_transaction_id;
    }

    public void setOriginal_transaction_id(String original_transaction_id) {
        this.original_transaction_id = original_transaction_id;
    }

    public String getPurchase_date() {
        return purchase_date;
    }

    public void setPurchase_date(String purchase_date) {
        this.purchase_date = purchase_date;
    }

    public String getPurchase_date_ms() {
        return purchase_date_ms;
    }

    public void setPurchase_date_ms(String purchase_date_ms) {
        this.purchase_date_ms = purchase_date_ms;
    }

    public String getPurchase_date_pst() {
        return purchase_date_pst;
    }

    public void setPurchase_date_pst(String purchase_date_pst) {
        this.purchase_date_pst = purchase_date_pst;
    }

    public String getOriginal_purchase_date() {
        return original_purchase_date;
    }

    public void setOriginal_purchase_date(String original_purchase_date) {
        this.original_purchase_date = original_purchase_date;
    }

    public String getOriginal_purchase_date_ms() {
        return original_purchase_date_ms;
    }

    public void setOriginal_purchase_date_ms(String original_purchase_date_ms) {
        this.original_purchase_date_ms = original_purchase_date_ms;
    }

    public String getOriginal_purchase_date_pst() {
        return original_purchase_date_pst;
    }

    public void setOriginal_purchase_date_pst(String original_purchase_date_pst) {
        this.original_purchase_date_pst = original_purchase_date_pst;
    }

    public String getIs_trial_period() {
        return is_trial_period;
    }

    public void setIs_trial_period(String is_trial_period) {
        this.is_trial_period = is_trial_period;
    }
}
