var exec = require('cordova/exec');

var YouDunCloudInsight = {

// 手动拍照功能是否关闭，默认为打开
 isManualOcr : function (arg0, success, error) {
    exec(success, error, 'YouDunCloudInsight', 'isManualOcr', [arg0]);
},

// 启动认证流程 Android上下文，启动前可先设置参数
 faceAuth : function (arg0, success, error) {
    exec(success, error, 'YouDunCloudInsight', 'faceAuth', [arg0]);
},

// 设置活体难度
// (高)FVSafeHighMode
// (中)FVSafeMediumMode
// (低)FVSafeLowMode
 setFVSafeMode : function (arg0, success, error) {
    exec(success, error, 'YouDunCloudInsight', 'setFVSafeMode', [arg0]);
},

// 是否展示姓名核验详情页
// 是否展示姓名核验详情页，默认为展示
 isShowConfirm : function (arg0, success, error) {
    exec(success, error, 'YouDunCloudInsight', 'isShowConfirm', [arg0]);
}
}


module.exports = YouDunCloudInsight
