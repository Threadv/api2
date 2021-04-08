package com.ifenghui.storybookapi.app.transaction.response;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class SvipGift {

    Integer id;

    Integer svipId;

    String imgPath;

    Integer status;

    String title;

    String content;

    Integer isSvip;


    public SvipGift(Integer id, Integer svipId, Integer status, Integer isSvip) {
        this.id = id;
        this.svipId = svipId;
        this.status = status;
        this.isSvip = isSvip;
    }

    public String getImgPath() {
        if (this.id == 1) {
            return "http://storybook.oss-cn-hangzhou.aliyuncs.com/vip/Magazine.png";
        } else if (this.id == 2) {
            if (this.status == 0) {
                return "http://storybook.oss-cn-hangzhou.aliyuncs.com/vip/book2.png";
            } else if (this.status == 1) {
                return "http://storybook.oss-cn-hangzhou.aliyuncs.com/vip/book.png";
            }
        } else if (this.id == 3) {
            return "http://storybook.oss-cn-hangzhou.aliyuncs.com/vip/course.png";
        } else if (this.id == 4) {
            return "http://storybook.oss-cn-hangzhou.aliyuncs.com/vip/APP.png";
        } else if (this.id == 5) {
            return "http://storybook.oss-cn-hangzhou.aliyuncs.com/vip/sale.png";
        } else if (this.id == 6) {
            return "http://storybook.oss-cn-hangzhou.aliyuncs.com/vip/money.png";
        } else if (this.id == 7) {
            return "http://storybook.oss-cn-hangzhou.aliyuncs.com/vip/friend.png";
        }
        return null;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public String getTitle() {
        if (this.id == 1) {
            return "《锋绘》杂志";
        } else if (this.id == 2) {
            return "全年学习计划";
        } else if (this.id == 3) {
            return "飞船阅读课";
        } else if (this.id == 4) {
            return "APP故事\n阅读权";
        } else if (this.id == 5) {
            return "飞船商城\n8折优惠";
        } else if (this.id == 6) {
            return "高额返现";
        } else if (this.id == 7) {
            return "亲友卡";
        }
        return null;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        if (this.id == 1) {
            if (this.svipId == 1) {
                return "全年共23本";
            } else if (this.svipId == 2) {
                return "全年共12本";
            } else if (this.svipId == 3) {
                return "全年共6本";
            }
        } else if (this.id == 2) {
            return "会员期免费";
        } else if (this.id == 3) {
            return "启蒙版+成长版";
        } else if (this.id == 4) {
            return "会员故事\n全部免费";
        } else if (this.id == 5) {
            return "会员购买\n低至8折";
        } else if (this.id == 6) {
            return "推荐好友购买\n会员高额返现";
        } else if (this.id == 7) {
            return "分享送好友\n免费故事";
        }
        return null;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSvipId() {
        return svipId;
    }

    public void setSvipId(Integer svipId) {
        this.svipId = svipId;
    }

    public Integer getIsSvip() {
        if (this.isSvip == 1) {
            return 1;
        } else if (this.isSvip == 0) {
            return 0;
        }
        return null;
    }

    public void setIsSvip(Integer isSvip) {
        this.isSvip = isSvip;
    }
}
