/**
 * 字符串常用工具类
 * Author:LinBilin
 * Date:2017-12-23
 */
(function () {
    var Open = Open || {};

    Open = {
        openUri: function (title,url) {
            var index = top.window.layer.open({
                skin: 'layui-layer-lan'
                ,title:title
                ,type: 2
                ,area: [(top.window.innerWidth*0.45).toString(), (top.window.innerHeight*0.95).toString()]
                ,content:url
            });
            return index;
        }
    };
    //暴露给window
    window['open'] = Open;
})();