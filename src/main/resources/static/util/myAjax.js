/*****************************************************************
 jQuery Ajax封装通用类 (Yangxiaohui)
 *****************************************************************/
$(function(){
    /**
     * Ajax 请求 - POST
     * @param url       请求地址
     * @param data      请求参数
     * @param successfn 请求成功回调
     * @param errorfn   请求失败回调
     */
    jQuery.post_http=function(url,data,successfn,errorfn){
        successfn = (successfn==null || successfn=="" || typeof(successfn)=="undefined")? function () {} : successfn;
        errorfn = (errorfn==null || errorfn=="" || typeof(errorfn)=="undefined")? function () {}: errorfn;
        $.ajax({
            type: "POST",
            async: "true",
            data: data,
            url: url,
            dataType: "json",
            success: function(d){
                successfn(d);
            },
            error: function(e){
                errorfn(e);
            }
        });
    };
    /**
     * Ajax 请求 - GET
     * @param url       请求地址
     * @param data      请求参数
     * @param successfn 请求成功回调
     * @param errorfn   请求失败回调
     */
    jQuery.get_http=function(url,data,successfn,errorfn){
        successfn = (successfn==null || successfn=="" || typeof(successfn)=="undefined")? function () {} : successfn;
        errorfn = (errorfn==null || errorfn=="" || typeof(errorfn)=="undefined")? function () {}: errorfn;
        $.ajax({
            type: "GET",
            async: "true",
            data: data,
            url: url,
            dataType: "json",
            success: function(d){
                successfn(d);
            },
            error: function(e){
                errorfn(e);
            }
        });
    };
    /**
     * Ajax 请求 - DELETE
     * @param url       请求地址
     * @param data      请求参数
     * @param successfn 请求成功回调
     * @param errorfn   请求失败回调
     */
    jQuery.delete_http=function(url,data,successfn,errorfn){
        successfn = (successfn==null || successfn=="" || typeof(successfn)=="undefined")? function () {} : successfn;
        errorfn = (errorfn==null || errorfn=="" || typeof(errorfn)=="undefined")? function () {}: errorfn;
        $.ajax({
            type: "DELETE",
            async: "true",
            data: data,
            url: url,
            dataType: "json",
            success: function(d){
                successfn(d);
            },
            error: function(e){
                errorfn(e);
            }
        });
    };
    /**
     * Ajax 请求 - PUT
     * @param url       请求地址
     * @param data      请求参数
     * @param successfn 请求成功回调
     * @param errorfn   请求失败回调
     */
    jQuery.put_http=function(url,data,successfn,errorfn){
        successfn = (successfn==null || successfn=="" || typeof(successfn)=="undefined")? function () {} : successfn;
        errorfn = (errorfn==null || errorfn=="" || typeof(errorfn)=="undefined")? function () {}: errorfn;
        $.ajax({
            type: "PUT",
            async: "true",
            data: data,
            url: url,
            dataType: "json",
            success: function(d){
                successfn(d);
            },
            error: function(e){
                errorfn(e);
            }
        });
    };
    /**
     * 刷新上个打开的页面 父级页面
     */
    jQuery.reloadParentDom=function(){
        var preOpenIframe = $(window.parent.document.getElementsByClassName("layadmin-tabsbody-item layui-show")[0].children[0]);
        preOpenIframe.attr("src",preOpenIframe.attr("src"));
    };

    /**
     * 封装全局状态码处理
     */
    $.ajaxSetup({
        headers: { // 默认添加请求头
            "Access-Token": window.localStorage.getItem("Access-Token") == null ? "":window.localStorage.getItem("Access-Token")
        },
        complete:function(XMLHttpRequest){
            /* 通过XMLHttpRequest获取浏览器响应码 */
            var browserCode =XMLHttpRequest.status;
            //通过XMLHttpRequest取得响应结果
            var res = XMLHttpRequest.responseJSON;
            try{
                /* 判断浏览器状态码是否正常 */
               if(browserCode != 200){
                    console.log(JSON.stringify(res));
                    return false;
                }
                var responseCode = res.code;
                if(responseCode == 101){
                    window.location.href="/index";
                }else if(responseCode == 102){/!* 未登录 *!/
                    window.location.href="/login/loginUI";
                }
                /* 判断浏览器状态码是否正常 */
                /*if(browserCode != 200){
                    console.log(JSON.stringify(res));
                    return false;
                }
                var responseCode = res.code;
                if(responseCode == 200){
                    console.log("ajax请求成功：----------->"+res)
                }/!*else if(responseCode == 1002){
                    window.location.href="/error_404";
                }else if(responseCode == 400){
                    window.location.href="/index";
                }else if(responseCode == 500){
                    window.location.href="/login/loginUI";
                }*!/else if(responseCode == 101){
                    // window.location.href="/index";
                }else if(responseCode == 102){/!* 未登录 *!/
                    window.location.href="/login/loginUI";
                }else{
                    return true;
                }*/
            }catch(e){
                console.log(JSON.stringify(e));
                return true;
            }
        }
    });


});