package com.ifenghui.storybookapi.style;

/***
 * 信息弹框类型
 */
public enum PromptStyle {
    /**
     * 弹框信息
     */

    //了解详情
    STUDY_PLAN(1,"了解详情","http://admin.storybook.ifenghui.com/public/index.php/editor/pagemake/pagemake.html?id=64","",""),
    //第一份报告
    FIRST_REPORT(2,"第一份报告","","",""),
    //分享类型
    SHARE(3,"分享","","已赠送您30元代金券\n可前往「我的」—「优惠券」查看\n\n杂志请添加客服微信领取\n客服微信：storyship_01\n","http://storybook.oss-cn-hangzhou.aliyuncs.com/weekplan/share/planshare.png");


    int id;
    String name;
    String url;
    String content;
    String imgPath;

    PromptStyle(int id, String name,String url,String content,String imgPath){
        this.id=id;
        this.name=name;
        this.url=url;
        this.content=content;
        this.imgPath=imgPath;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public static PromptStyle getById(int id){
        for(PromptStyle style: PromptStyle.values()){
            if(style.getId()==id){
                return style;
            }
        }
        return null;
    }
}
