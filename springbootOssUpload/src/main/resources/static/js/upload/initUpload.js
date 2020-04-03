var initUpload = {
    uploadUrl:"",
    oosDirectory:"files",
    policyUrl:"/upload/getpolicy"
};
initUpload.uploadBeforeSend = function(obj, data, headers, file){

};

$(function () {
    var uploadService = new ImageUploadService();
    //设置上传成功后允许继续上传
    uploadService.init({
        allowUpload:true,
        preImgDiv:$('[name="preImgDiv"]'),
        uploadBtn:$('[name="uploadBtn"]')
    });
    //执行初始化
    uploadService.build({auto:true,
        dnd:$('#uploader')});
    //文件上传之前的方法
    jQuery(uploadService).on('uploadBeforeSend',function (event,obj, data, headers, file) {
        initUpload.uploadBeforeSend(obj, data, headers, file);
    });
    //文件上传成功的方法
    jQuery(uploadService).on('onUploadSuccess',function (event,file, response) {
        debugger;
    });
});