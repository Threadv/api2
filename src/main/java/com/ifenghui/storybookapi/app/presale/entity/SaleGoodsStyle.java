package com.ifenghui.storybookapi.app.presale.entity;

public enum SaleGoodsStyle {

//    DEFAULT_NULL(0, "", 0, 0, 0),
    ENLIGHTEN_LESSON(1,"启蒙版课程",1, 50, 4),
    GROWTH_LESSON(2,"成长版课程",2, 50, 4),
    ENLIGHTEN_LESSON_FIVE(8, "启蒙版-5节", 1, 5, 2),
    ENLIGHTEN_LESSON_TWENTY(9, "启蒙版-20节", 1, 20, 3),
    ENLIGHTEN_LESSON_ALL(10, "启蒙版-50节", 1, 50, 4),
    GROWTH_LESSON_FIVE(11, "成长版-5节", 2, 5, 2),
    GROWTH_LESSON_TWENTY(12, "成长版-20节", 2, 20, 3),
    GROWTH_LESSON_ALL(13, "成长版-50节", 2, 50, 4),
    GROWTH_LESSON_14(14, "启蒙版-20节", 1, 20, 3),
    GROWTH_LESSON_15(15, "启蒙版-20节", 1, 20, 3),
    GROWTH_LESSON_16(16, "成长版-20节", 2, 20, 3),
    GROWTH_LESSON_17(17, "成长版-20节", 2, 20, 3),
    GROWTH_LESSON_19(19, "成长版-1节", 2, 1, 1),
    ENLIGHTEN_LESSON_18(18, "启蒙版-1节", 1, 1, 1),
    ENLIGHTEN_LESSON_20(20, "启蒙版-2节", 1, 2, 5),
    GROWTH_LESSON_21(21, "成长版-2节", 2, 2, 5),
    ENLIGHTEN_LESSON_22(22, "启蒙版-10节", 1, 10, 7),
    GROWTH_LESSON_23(23, "成长版-10节", 2, 10, 7);

    int id;

    String name;

    int targetValue;

    int buyLessonNum;

    int lessonPriceId;

    SaleGoodsStyle(int id, String name, int targetValue, int buyLessonNum, int lessonPriceId) {
        this.id = id;
        this.name = name;
        this.targetValue = targetValue;
        this.buyLessonNum = buyLessonNum;
        this.lessonPriceId = lessonPriceId;
    }

    public boolean isNeedActivateLesson(){
        if(this.targetValue > 0){
            return true;
        } else {
            return false;
        }
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

    public int getTargetValue() {
        return targetValue;
    }

    public void setTargetValue(int targetValue) {
        this.targetValue = targetValue;
    }

    public int getBuyLessonNum() {
        return buyLessonNum;
    }

    public void setBuyLessonNum(int buyLessonNum) {
        this.buyLessonNum = buyLessonNum;
    }

    public int getLessonPriceId() {
        return lessonPriceId;
    }

    public void setLessonPriceId(int lessonPriceId) {
        this.lessonPriceId = lessonPriceId;
    }

    public static SaleGoodsStyle getById(int id){
        for(SaleGoodsStyle goodsStyle:SaleGoodsStyle.values()){
            if(goodsStyle.getId()==id){
                return goodsStyle;
            }
        }
        return null;
//        switch (id){
//            case 1:
//                return ENLIGHTEN_LESSON;
//            case 2:
//                return GROWTH_LESSON;
//            case 8:
//                return ENLIGHTEN_LESSON_FIVE;
//            case 9:
//                return ENLIGHTEN_LESSON_TWENTY;
//            case 10:
//                return ENLIGHTEN_LESSON_ALL;
//            case 11:
//                return GROWTH_LESSON_FIVE;
//            case 12:
//                return GROWTH_LESSON_TWENTY;
//            case 13:
//                return GROWTH_LESSON_ALL;
//            default:
//                return null;
//        }
    }
}
