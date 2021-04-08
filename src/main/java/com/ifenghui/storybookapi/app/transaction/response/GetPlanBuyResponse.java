package com.ifenghui.storybookapi.app.transaction.response;/**
 * @Date: 2018/12/13 16:54
 * @Description:
 */

import com.ifenghui.storybookapi.app.app.response.BaseResponse;

/**
 * @Date: 2018/12/13 16:54
 * @Description:
 */
public class GetPlanBuyResponse extends BaseResponse {

    Integer canBuyTwoFour;
    Integer canBuyFourSix;

    public Integer getCanBuyTwoFour() {
        return canBuyTwoFour;
    }

    public void setCanBuyTwoFour(Integer canBuyTwoFour) {
        this.canBuyTwoFour = canBuyTwoFour;
    }

    public Integer getCanBuyFourSix() {
        return canBuyFourSix;
    }

    public void setCanBuyFourSix(Integer canBuyFourSix) {
        this.canBuyFourSix = canBuyFourSix;
    }
}
