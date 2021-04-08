package com.ifenghui.storybookapi.app.presale.service;




import com.ifenghui.storybookapi.app.presale.entity.PreSaleUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface PreSaleUserService {


    /**
     * unionid获得user
     * @param unionid
     * @return
     */
    PreSaleUser getUserByUnionid(String unionid);

    /**
     * 注册用户
     * @param code
     * @return
     */
    PreSaleUser addUser(String code) throws Exception;

    /**
     * 增加用户，试用于已经授权完成
     * @param preSaleUser
     * @return
     */
    PreSaleUser addUser(PreSaleUser preSaleUser);

    /**
     *通过openId查询user
     * @param openid
     * @return
     */
    PreSaleUser findUserByOpenId(String openid);

    /**
     * 通过id查询user
     * @param userId
     * @return
     */
   PreSaleUser findUserById(Integer userId);


   Page<PreSaleUser> findAll(PreSaleUser preSaleUser, PageRequest pageRequest);

}
