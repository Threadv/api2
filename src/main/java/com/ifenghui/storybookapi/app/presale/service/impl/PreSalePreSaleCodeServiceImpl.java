package com.ifenghui.storybookapi.app.presale.service.impl;


import com.ifenghui.storybookapi.app.presale.dao.PreSaleCodeDao;
import com.ifenghui.storybookapi.app.presale.dao.PreSaleGoodsDao;
import com.ifenghui.storybookapi.app.presale.dao.PreSalePayDao;
import com.ifenghui.storybookapi.app.presale.entity.PreSaleCode;
import com.ifenghui.storybookapi.app.presale.entity.PreSaleGoods;
import com.ifenghui.storybookapi.app.presale.entity.PreSalePay;
import com.ifenghui.storybookapi.app.presale.service.PreSaleCodeService;
import com.ifenghui.storybookapi.style.VipGoodsStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;


@Transactional
@Component
public class PreSalePreSaleCodeServiceImpl implements PreSaleCodeService {

    @Autowired
    PreSaleCodeDao codeDao;

    @Autowired
    PreSaleGoodsDao goodsDao;

    @Autowired
    PreSalePayDao preSalePayDao;


    /**
     * 生成兑换码 根据本身id
     * @return
     */
    @Override
    public PreSaleCode addCode(Integer userId,Integer userType,Integer activityId,Integer codeType){

        try{
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            String s = "2099-09-13 22:22:22";
//            Date date = sdf.parse(s);
//            PreSaleGoods goods = goodsDao.findOne(goodsId);
            PreSaleCode code = new PreSaleCode();
            code.setPayId(0);
            code.setUserId(userId);
            code.setActivity_id(activityId);
            code.setStatus(0);
            code.setIsExpire(0);
            code.setCode("");
            Date date = new Date();
            code.setCreateTime(date);
            code.setCodeType(codeType);
            code.setName("");
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);//设置起时间
            cal.add(Calendar.MONTH, 3);//有效期3个月
            cal.set(Calendar.HOUR_OF_DAY, 23);
            cal.set(Calendar.MINUTE, 59);
            cal.set(Calendar.SECOND, 59);
            cal.set(Calendar.MILLISECOND,0);
            code.setEndTime(cal.getTime());
            code.setSaleType(0);
            PreSaleCode saleCode = codeDao.save(code);

            Random random = new Random();
            int r = random.nextInt(90) + 10;
            String id = saleCode.getId() + "";
            String pre = "";
            for (int i = 0; i < 8 - id.length(); i++) {
                pre += "0";
            }
            String codee = r + pre + id;
            String sCode = Long.toString(Long.parseLong(codee), 18);
            saleCode.setCode(sCode);
            codeDao.save(saleCode);
            return  saleCode;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }


    @Override
    public PreSaleCode addCode(Integer payId) {

        //判断此订单是否存在
        List<PreSaleCode> all = codeDao.findAll();
        for (PreSaleCode c : all) {
            if (c.getPayId() == payId.intValue()) {
                return c;
            }
        }

        //通过订单获得userId
        PreSalePay preSalePay = preSalePayDao.findOne(payId);
        int userId = preSalePay.getUserId();
        int goodsId = preSalePay.getGoodsId();
        PreSaleCode newCode = new PreSaleCode();

        newCode.setPayId(payId);
        newCode.setUserId(userId);
        newCode.setActivity_id(1);
        newCode.setCode("");
        newCode.setGoodsId(goodsId);
        if (goodsId == 1) {
            newCode.setName("故事飞船私教课-启蒙版");
        } else {
            newCode.setName("故事飞船私教课-成长版");
        }
        newCode.setStatus(0);
        newCode.setCreateTime(new Date());
        //设置过期时间
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);//设置起时间
        cal.add(Calendar.MONTH, 1);//增加一个月
        newCode.setEndTime(cal.getTime());
        //是否过期
        newCode.setIsExpire(0);
        PreSaleCode saveCode = codeDao.save(newCode);
        /**
         * 获得id通过id生成code
         */
        Random random = new Random();
        int r = random.nextInt(90) + 10;
        String id = saveCode.getId() + "";
        String pre = "";
        for (int i = 0; i < 8 - id.length(); i++) {
            pre += "0";
        }
        String codee = r + pre+ id;
        String sCode = Long.toString(Long.parseLong(codee), 18);
        saveCode.setCode(sCode);
        codeDao.save(saveCode);
        return saveCode;
    }

    @Override
    public Page<PreSaleCode> getCodeListByUserId(Integer userId, Integer pageNo, Integer pageSize) {
        Pageable pageable = new PageRequest(pageNo, pageSize, new Sort(Sort.Direction.DESC, "id"));
        return codeDao.getPreSaleCodesByUserId(userId, pageable);
    }

    /**
     * 通过payId获得code
     *
     * @param payId
     * @return
     */
    @Override
    public PreSaleCode getCodeByPayId(Integer payId) {
        return codeDao.getPreSaleCodeByPayId(payId);
    }

    @Override
    public PreSaleCode getCodeByCode(String code) {
        return codeDao.getPreSaleCodeByCode(code);
    }

    @Override
    public PreSaleCode findCode(Integer id) {
        return codeDao.findOne(id);
    }

    @Transactional
    @Override
    public void usePreSaleCode(PreSaleCode preSaleCode, Integer userId){
        if(preSaleCode.getIsExpire().equals(0)){
            preSaleCode.setStatus(1);
            preSaleCode.setSuccessTime(new Date());
            preSaleCode.setUserId(userId);
            codeDao.save(preSaleCode);
        }
    }

    @Override
    public Page<PreSaleCode> findAllCodes(PreSaleCode code, PageRequest pageRequest) {
        return codeDao.findAll(Example.of(code),pageRequest);
    }

    @Override
    public PreSaleCode update(PreSaleCode code) {
        return codeDao.save(code);
    }

    @Override
    public PreSaleCode add(PreSaleCode code) {
        return codeDao.save(code);
    }


        @Override
    public void setExpire() {
        PreSaleCode preSaleCode = new PreSaleCode();
        preSaleCode.setIsExpire(0);
        List<PreSaleCode> list = codeDao.findAll(Example.of(preSaleCode));
        for (PreSaleCode code : list) {
            if (System.currentTimeMillis() >= code.getEndTime().getTime()) {
                code.setIsExpire(1);
                codeDao.save(code);
            }
        }
    }

    @Override
    public List<PreSaleCode> getCodesByEndTime(Integer codeType,Date endTime) {
        return codeDao.getPreSaleCodeByTime(codeType,endTime,new Date());
    }
}
