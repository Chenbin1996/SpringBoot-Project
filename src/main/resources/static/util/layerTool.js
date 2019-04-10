function Layer(){} ;
Layer.prototype = {
    toplayer : window.top.layer ,   // 获取顶层窗口的layer对象
    topWin : window.top ,           // 获取顶层窗口对象
    colseTime : 1500 ,                // 关闭弹出框的默认时间 1.5S
    width : (top.window.innerWidth*0.45).toString(),                  // 默认窗口的宽度
    height : (top.window.innerHeight*0.70).toString(),              // 默认窗口的高度
    px : 'px' ,                     // 对话框宽高单位


    /**
     * 警告提示框
     * @param msg 警告内容
     */
    warn : function(msg){
        this.toplayer.alert(msg,{icon:0});
    },

    /**
     * 成功提示框 - 带确认按钮回调函数（选填）
     * @param content   成功提示内容
     * @param callback  确认按钮回调函数
     */
    success : function (content,callback){
        var length = arguments.length ;   //  实际传入参数的长度
        var options = {icon:1,time:this.colseTime};
        if(length == 0){  // 没有传入任何参数
            this.toplayer.alert("操作成功",options);
        }else if(length == 1){ // 传入了提示内容
            this.toplayer.alert(content,options);
        }else if(length == 2){  // 有按钮点击回调函数的,将不自动关闭
            this.toplayer.alert(content,{icon:1},callback);
        }
    },
    /**
     * 失败提示框
     * @param content   提示内容
     * @param time      提示框默认关闭时间，0 默认不关闭
     */
    error : function(content,time){
        var length = arguments.length ;   //  实际传入参数的长度
        var options = {icon:2,time:this.colseTime};
        if(length == 0){  // 没有传入任何参数
            this.toplayer.alert("操作失败",options);
        }else if(length == 1){ // 传入了提示内容
            this.toplayer.alert(content,options);
        }else if(length == 2){ // 传入了关闭时间
            options.time = time ;
            this.toplayer.alert(content,options);
        }
    },
    /**
     * 打开一个对话框(没有回调函数)
     * @param {} title       对话框标题(必须)
     * @param {} url        对话框URL(必须)
     * @param {} width        对话框宽度 默认：800px
     * @param {} height        对话框高低 默认：600px
     */
    openDialogNoCallBack : function(title,url,width,height){
        var theme = JSON.parse(localStorage.getItem("layuiAdmin")).theme.color.alias;
        var skin = theme=="green"?"layui-layer-molv":"layui-layer-lan";
        this.toplayer.open({
            type : 2,
            skin:skin,
            title : title ,
            content : url ,
            maxmin: true,
            area: [width, height]
        });
    },
    /**
     * 获取当前的窗口对象
     * @return {}
     */
    getCurrentWin : function(){
        return this.topWin.frames['ifr_center'] ;
    },

    /**
     * 打开一个对话框(带回调函数)
     * @param {} title       对话框标题(必须)
     * @param {} url        对话框URL(必须)
     * @param {} width        对话框宽度 默认：800px
     * @param {} height        对话框高低 默认：600px
     */
    openDialogWithCallBack : function(title,url,width,height,callback){
        var theme = JSON.parse(localStorage.getItem("layuiAdmin")).theme.color.alias;
        var skin = theme=="green"?"layui-layer-molv":"layui-layer-lan";
        this.toplayer.open({
            type : 2,
            skin:skin,
            title : title ,
            content : url ,
            area: [width, height],
            maxmin: true,
            end  : callback
        });
    },
    /**
     * 打开一个对话框
     * @param  title       对话框标题(必须)
     * @param  url        对话框URL(必须)
     * @param  width        对话框宽度 默认：800px
     * @param  height        对话框高低 默认：600px
     * @param  callback   窗口销毁时的回调方法
     */
    openUrl : function(title,url,width,height,callback){
        var length = arguments.length ;   //  实际传入参数的长度
        if(length == 2){   // 默认宽高
            this.openDialogNoCallBack(title,url,this.width,this.height)
        }else if(length == 3){  // 只传入宽度参数
            width += this.px ;
            this.openDialogNoCallBack(title,url,width,this.height)
        }else if(length == 4){  // 传入宽度和高度
            width += this.px ;
            height += this.px ;
            this.openDialogNoCallBack(title,url,width,height)
        }else if(length == 5){   // 带回调函数
            width += this.px ;
            height += this.px ;
            this.openDialogWithCallBack(title,url,width,height,callback);
        }
    },

    /**
     * 关闭弹出层
     * @param  index
     */
    closeLayer : function(index){
        this.toplayer.close(index);
    },
    closeNow : function(index){
        this.toplayer.close(index);
    },
    /**
     * 关闭所有的弹出层
     */
    closeAllLayer : function(){
        this.toplayer.closeAll('iframe');
    },
    /**
     * 显示提示框
     * @param {} content
     */
    showMsg : function(content){
        this.toplayer.msg(content,{time:this.colseTime}) ;
    },
    /**
     * 显示操作成功的提示框
     * @param {} content
     */
    showSucMsg : function(content){
        if(!content) content = "操作成功" ;
        this.toplayer.msg(content,{icon: 1,time:this.colseTime}) ;
    },
    /**
     * 显示验证框
     * @param {} content   提示内容
     * @param {} yesFunction 确定以后的回调函数
     */
    showConfirm : function(content,yesFunction){
        this.toplayer.confirm(content,{
            btn: ['确定', '取消'],
            icon : 3
        },yesFunction);
    }

};
var Layer = new Layer();