var initUpload = {
    uploadUrl:"",
    oosDirectory:"files",
    policyUrl:"/upload/getPolicy"
};
initUpload.uploadBeforeSend = function(obj, data, headers, file){
    var $data = data;
    $.ajax({
        type: "POST",
        url: initUpload.policyUrl,
        data:JSON.stringify({oosDirectory:initUpload.oosDirectory}),
        async: false,
        cache: false,
        contentType:"application/json",
        dataType : "json"
    }).then(function (data) {
        debugger;
        //赋值参数
        $data = $.extend($data, data.policy);
        //对文件名进行随机处理
        var imgName = initUpload.calculate_object_name($data.name,'random_name');
        //设置文件路径，如果文件名不做设置，将由阿里云自己生成
        $data.key = $data.key + "/" + imgName;
        obj.filepath = $data.key;
        file.path = $data.key;
        //设置请求头
        headers['Access-Control-Allow-Origin'] = "*";
    })
};

initUpload.calculate_object_name = function(filename,type){
    if (type && type == 'local_name') {
        return "${filename}";
    } else {//随机生成文件名
        var chars = 'ABCDEFGHJKMNPQRSTWXYZabcdefhijkmnprstwxyz2345678';
        var maxPos = chars.length;
        var random_string = '';
        for (i = 0; i < 10; i++) {
            random_string += chars.charAt(Math.floor(Math.random() * maxPos));
        }
        var pos = filename.lastIndexOf('.')
        var suffix = ''
        if (pos != -1) {
            suffix = filename.substring(pos);
        }
        return random_string + new Date().getTime() + suffix;
    }
};

$(function () {
    var uploadService = new ImageUploadService();
    //设置上传成功后允许继续上传
    uploadService.init({
        allowUpload:true
    });
    //执行初始化
    uploadService.build();
    //文件上传之前的方法
    jQuery(uploadService).on('uploadBeforeSend',function (event,obj, data, headers, file) {
        initUpload.uploadBeforeSend(obj, data, headers, file);
    });
    //文件上传成功的方法
    jQuery(uploadService).on('onUploadSuccess',function (event,file, response) {
        debugger;
    });
});