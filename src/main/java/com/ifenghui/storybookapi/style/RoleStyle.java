package com.ifenghui.storybookapi.style;


import com.ifenghui.storybookapi.adminapi.controlleradmin.ManagerController;
import com.ifenghui.storybookapi.app.story.entity.SerialStory;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 权限类型，controller不再其中代表全权限可用
 */
public enum RoleStyle {

//    MANAGER(0, ManagerController.class),
//    SERIAL(1, SerialStory.class),
    //用户操作权限只有用户操作本身才可使用，其他权限无权访问,示用post和delete
OPERATIONUSER(0,"操作用户数据权限","/adminapi/ability/clear_user_week_plan_join"
        ,"/adminapi/ability/remove_user_ability_order"
        ,"/adminapi/ability/remove_user_ability_plan_relate"
        ,"/adminapi/user/update_user"
        ,"/adminapi/user/remove_user"

        ,"/sale/presaleadmin/order/delete_order"),


MANAGER(1, "管理员","/adminapi","/sale"),//管理员
    EDITOR(2, "编辑","/adminapi/code/","/adminapi/smsmessage/","/adminapi/expresscenter/","/adminapi/ad/"
            ,"/adminapi/displaygroup/","/adminapi/ipbrand/","/adminapi/iplabel/","/adminapi/iplabelstory/","/adminapi/managerlog/"
            ,"/adminapi/oss/","/adminapi/serial/","/adminapi/serial/","/adminapi/story/"
            ,"/sale/"
            ,"/fenxiao/"),//编辑

    DEVELOPER(3, "开发","/adminapi/manager/"),//编辑
    OPERATORS(4,"运营" ,"/adminapi/user/"),
    ;


    int id;
//    boolean allowGustPath;//没有包含的字目录
    String[] paths;//权限授权的链接
    String title;//权限说明
    Class contorller;

    RoleStyle(int id, Class contorller){
        this.id=id;
        this.contorller=contorller;
    }

    RoleStyle(int id, String title,String ... paths){
        this.id=id;
        this.title=title;
        this.paths=paths;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public static RoleStyle getById(int id){
        for(RoleStyle item: RoleStyle.values()){
            if(item.getId()==id){
                return item;
            }
        }
        return null;
    }

    public static RoleStyle getByKey(String key){
        for(RoleStyle item: RoleStyle.values()){
            if(item.getKey().equals(key)){
                return item;
            }
        }
        return null;
    }



    public String getName() {
        RequestMapping requestMapping=(RequestMapping)this.contorller.getAnnotation(RequestMapping.class);
        if(requestMapping!=null){
            return requestMapping.name();
        }
        return "";
    }

    public String getPath() {
        RequestMapping requestMapping=(RequestMapping)this.contorller.getAnnotation(RequestMapping.class);
        if(requestMapping!=null){
            return requestMapping.value()[0];
        }
        return "";
    }


    public String getKey() {
        return contorller.getSimpleName().replace("Controller","");
    }

    public String[] getPaths() {
        return paths;
    }

    public String getTitle() {
        return title;
    }


    public static boolean hasOperationUser(String[] roles){
        for(String roleItem:roles){
            if(roleItem.toLowerCase().equals(OPERATIONUSER.toString().toLowerCase())){
                return true;
            }
        }
        return false;
    }
}
