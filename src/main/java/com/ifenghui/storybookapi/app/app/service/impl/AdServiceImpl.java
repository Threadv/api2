package com.ifenghui.storybookapi.app.app.service.impl;

import com.ifenghui.storybookapi.app.app.dao.AdDao;
import com.ifenghui.storybookapi.app.app.entity.Ads;
import com.ifenghui.storybookapi.app.app.entity.Ads2;
import com.ifenghui.storybookapi.app.app.response.IndexAds;
import com.ifenghui.storybookapi.app.app.service.AdService;
import com.ifenghui.storybookapi.app.transaction.service.PaySubscriptionPriceService;
import com.ifenghui.storybookapi.app.user.entity.User;
import com.ifenghui.storybookapi.app.user.service.UserService;
import com.ifenghui.storybookapi.util.VersionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by wml on 2016/12/27.
 */
@Transactional
@Component
public class AdServiceImpl implements AdService {

//    @Autowired
//    HttpServletRequest httpServletRequest;

    @Autowired
    UserService userService;

    @Autowired
    PaySubscriptionPriceService paySubscriptionPriceService;

    @Autowired
    AdDao adsDao;
    private static org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(AdServiceImpl.class);
    @Transactional
    @Override
    public List<Ads> getAds(int pageNo, int pageSize,Integer isCheckVer,String channel,String platform,String ver,User user) {
        logger.info("--------------------getAds--------userAgent----"+channel);
//        Pageable pageable = new PageRequest(pageNo, pageSize, new Sort(new Sort.Order(Sort.Direction.DESC,"orderBy"),new Sort.Order(Sort.Direction.DESC,"id")));

        Pageable pageable = new PageRequest(pageNo, pageSize, new Sort(Sort.Direction.DESC,"orderBy","id"));
        //获取广告数据
        Integer status = 1;
        List<Ads> ads;

        if("".equalsIgnoreCase(ver)){
            ver="0.0";
        }
        try {
            if(channel==null){
                channel="";
            }
            channel=URLDecoder.decode(channel,"utf-8");
        }catch (Exception e){
            e.printStackTrace();
        }

        if(isCheckVer == 0){
            if (channel!=null&&channel.indexOf("华为") != -1) {
                logger.info("--------------------getAds--------userAgent---huawei-");
                //在审核中则过滤掉ads中isIosVisul为0
                ads=this.adsDao.getAdsByStatusAndAdsPosition(status,1,pageable);
            }else{
                logger.info("--------------------getAds--------userAgent--no-huawei-");
                //过滤掉id为17的数据
                ads=this.adsDao.getAdsByStatusExceptId(status,platform, new Sort(Sort.Direction.DESC,"orderBy","id"));
            }
        }else{

            Page<Ads> adsPage=this.adsDao.getAdsByStatusAndIsIosVisual(status,platform,pageable);
            ads = adsPage.getContent();

        }
        List<Ads> adsList = new ArrayList<>();
        for(Ads item : ads) {
            if(!item.getId().equals(56L) && !item.getId().equals(62L)) {
                adsList.add(item);
            } else if(item.getId().equals(56L) && VersionUtil.isAllow(ver, "2.5.0")) {
                adsList.add(item);
            } else if(item.getId().equals(62L)) {
//                String ssToken = httpServletRequest.getHeader("sstoken");
                if(user != null) {

                    boolean isNeedOpen = paySubscriptionPriceService.isNeedOpenNotice(user.getId());
                    if(isNeedOpen) {
                        adsList.add(item);
                    }
                }
            }
        }
        return adsList;
    }

    @Override
    public IndexAds getIndexAds() {
        Pageable pageable = new PageRequest(0, 1, new Sort(Sort.Direction.DESC,"orderBy","id"));
        List<Ads> ads = adsDao.getAdsByStatusAndAdsPosition(1,2, pageable);
        if(ads != null && ads.size() > 0){
            return new IndexAds(ads.get(0));
        } else {
            return null;
        }
    }

    @Override
    public Ads findOneAds(Long id){
        return adsDao.findOne(id);
    }

    /**
     * 获取测试广告列表
     * @return
     */
    @Override
    public List<Ads> getTestAds() {
        Pageable pageable = new PageRequest(0, 50, new Sort(Sort.Direction.DESC,"orderBy","id"));
        return adsDao.getAdsByStatusAndAdsPosition(2,1, pageable);
    }

    @Override
    public List<Ads> getAdsByStatusAndAdsPosition(Integer status, Integer adsPosition) {
        Pageable pageable = new PageRequest(0, 20, new Sort(Sort.Direction.DESC,"orderBy","id"));
        List<Ads> ads = adsDao.getAdsByStatusAndAdsPosition(1,3, pageable);
        return ads;
    }

    @Override
    public List<Ads2> getAds2ByStatusAndAdsPosition(Integer status, Integer adsPosition) {
        Pageable pageable = new PageRequest(0, 20, new Sort(Sort.Direction.DESC,"orderBy","id"));
        List<Ads2> ads2 = adsDao.getAds2ByStatusAndAdsPosition(1,3, pageable);
        return ads2;
    }

    @Override
    public Ads getAdsById(Integer id) {
        return adsDao.findOne(id.longValue());
    }

    @Override
    public Page<Ads> getAdsList(Ads ads, PageRequest pageRequest) {
        return adsDao.findAll(adsSepc(ads),pageRequest);
    }

    @Override
    public void del(Integer id) {
        adsDao.delete(id.longValue());
    }

    @Override
    public void save(Ads ads) {
        adsDao.save(ads);
    }

    /**
     * 广告混合条件搜索
     * @param ads
     * @return
     */
    private Specification<Ads> adsSepc(Ads ads){
        return new Specification<Ads>() {

            @Override
            public Predicate toPredicate(Root<Ads> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates=new ArrayList<Predicate>();
                if(ads.getId()!=null){
                    predicates.add(criteriaBuilder.equal(root.get("id"),ads.getId()));
                }
                if(ads.getStatus()!=null){
                    predicates.add(criteriaBuilder.equal(root.get("status"),ads.getStatus()));
                }
                if(ads.getTargetTypeData()!=null){
                    predicates.add(criteriaBuilder.equal(root.get("targetType"),ads.getTargetTypeData()));
                }
                if (ads.getAdsPosition() != null){
                    predicates.add(criteriaBuilder.equal(root.get("adsPosition"),ads.getAdsPosition()));
                }

//                if(ads.getSuccessTimeBegin()!=null){
//                    predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("createTime"),ads.getSuccessTimeBegin()));
//                }
//                if(ads.getSuccessTimeEnd()!=null){
//                    predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("createTime"),ads.getSuccessTimeEnd()));
//                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[]{}));
            }
        };
    }
}
