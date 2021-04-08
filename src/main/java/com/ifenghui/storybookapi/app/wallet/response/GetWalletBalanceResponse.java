package com.ifenghui.storybookapi.app.wallet.response;

import com.ifenghui.storybookapi.api.response.base.ApiPageResponse;
import com.ifenghui.storybookapi.app.wallet.entity.Wallet;

/**
 * Created by wml on 2017/2/16.
 */
public class GetWalletBalanceResponse extends ApiPageResponse {

    Wallet wallet;

    public Wallet getWallet() {
        return wallet;
    }

    public void setWallet(Wallet wallet) {
        this.wallet = wallet;
    }
}
