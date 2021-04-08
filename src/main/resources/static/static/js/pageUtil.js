/**
 * Created by slwei on 14-8-11.
 */
//过滤链接,用于交叉多条件查询
var Filter=new function(){
    this.add=function(name,value){
        //删除搜索框的内容

        //alert('begin');
        var urlpath=location.href;
		if(urlpath.indexOf('[#]')!=-1){
			alert(1);
		urlpath=urlpath.split('[#]')[0];	
		}
        var baseurl='';
        var newparam='';
        if(urlpath.indexOf('?')==-1){
            //没有任何参数
            baseurl=urlpath+'?'+name+'='+value;

        }else{
            var params=urlpath.split('?')[1];
            var arrparam=params.split('&');
            for(var i=0;i<arrparam.length;i++){
                line=arrparam[i];
                if(line.indexOf('=')==-1){
                    continue;
                }
                cname=line.split('=')[0];
                cvalue=line.split('=')[1];
                if(cname==name){
                    continue;
                }

                if(cname=='p'||cname=='title'||cname=='name'){
                    //去掉页号信息
                    continue;
                }

                if(newparam==''){
                    newparam+='?';
                }else{
                    newparam+='&';
                }
                newparam+=cname+'='+cvalue;
            }
            if(newparam==''){newparam='?';}
            baseurl=urlpath.split('?')[0]+newparam+'&'+name+'='+value;
        }

        location.href=baseurl;
    }

    this.remove=function(name){
        var urlpath=location.href;
        //alert(urlpath);
        var baseurl='';
        var newparam='';
        if(urlpath.indexOf('?')==-1){
            //没有任何参数
            //baseurl=urlpath+'?'+name+'='+value;
            //alert('1');
        }else{
            var params=urlpath.split('?')[1];
            var arrparam=params.split('&');
            for(var i=0;i<arrparam.length;i++){

                line=arrparam[i];

                if(line.indexOf('=')==-1){
                    continue;
                }
                cname=line.split('=')[0];
                cvalue=line.split('=')[1];
                if(cname==name){
                    continue;
                }

                if(cname=='p'){
                    //去掉页号信息
                    continue;
                }

                if(newparam==''){
                    newparam+='?';
                }else{
                    newparam+='&';
                }

                newparam+=cname+'='+cvalue;
            }
            baseurl=urlpath.split('?')[0]+newparam;//+'&'+name+'='+value;
        }
        //alert(baseurl);
        location.href=baseurl;
    }
    this.listener=function(){
        //监听
        // alert('lllllllllllll');
        try{
            var onclickvar;
            var lis=$('a');

            for(var i=0;i<lis.length;i++){

                    //alert('--'+lis.eq(i).attr('onclick'));
                    currenthref=lis.eq(i).attr('href');
                    if(typeof(currenthref) == "undefined"|| currenthref==''){continue;}

                    if(location.href.indexOf("?")==-1){
                        return;
                    }
                    parr=location.href.split('?')[1].split('&');
                    locationMap={};
                    for(var j=0;j<parr.length;j++){
                        name=parr[j].split("=")[0];
                        value=parr[j].split("=")[1];
                        locationMap[name]=value;
                    }
                    // alert(1);

                    currentMap={};
                    if(currenthref.indexOf("?")==-1){
                        continue;
                    }
                    currentParams=currenthref.split('?')[1].split('&');

                    for(var j=0;j<currentParams.length;j++){
                        name=currentParams[j].split("=")[0];
                        value=currentParams[j].split("=")[1];
                        currentMap[name]=value;
                    }
                    // alert(2);
                    currentSelected=true;
                // var propertysMap = Object.getOwnPropertyNames(currentMap);
                    for(param in currentMap){
                        // console.log(locationMap);

                        if(locationMap[param]!=currentMap[param]){
                            currentSelected=false;
                            console.log(param+":"+locationMap[param]+"----===--"+currentMap[param]+":"+lis.eq(i).html());
                            break;
                        }
                    }
                    if(currentSelected){
                        lis.eq(i).addClass("active");

                        // lis.eq(i).parent().addClass("active");
                        // lis.eq(i).parent().addClass("in");
                        lis.eq(i).parent().parent().addClass("in");
                    }



            }
        }catch(e){
            //alert(e);
        alert(e);
        return;
        }
    }
}
$().ready(function(){
    // Filter.listener();

});



