$(function () {
    //初始化编辑器
    ue = UE.getEditor('noticeContent', {
        serverUrl:"/ue/queryConfig",
        fileUploadUrl:"/ue/fileUpload",
        imageMaxSize: 2048000, /* 上传大小限制，单位B */
        imageAllowFiles: [".png", ".jpg", ".jpeg", ".gif", ".bmp"], /* 上传图片格式显示 */
        imageFieldName: "uploadfile", /* 提交的图片表单名称 */
        imageManagerActionName:"/ue/queryList",//图片列表回显的路径,
        imageManagerUrlPrefix:"",
        imageUrlPrefix:"",
        videoFieldName: "uploadfile", /* 提交的视频表单名称 */
        // "videoPathFormat": "/ueditor/jsp/upload/video/{yyyy}{mm}{dd}/{time}{rand:6}", /* 上传保存路径,可以自定义保存路径和文件名格式 */
        videoUrlPrefix: "", /* 视频访问路径前缀 */
        videoMaxSize: 102400000, /* 上传大小限制，单位B，默认100MB */
        videoAllowFiles: [
            ".flv", ".swf", ".mkv", ".avi", ".rm", ".rmvb", ".mpeg", ".mpg",
            ".ogg", ".ogv", ".mov", ".wmv", ".mp4", ".webm", ".mp3", ".wav", ".mid"], /* 上传视频格式显示 */
        scrawlFieldName: "uploadfile", /* 提交的图片表单名称 */
        // "scrawlPathFormat": "/ueditor/jsp/upload/image/{yyyy}{mm}{dd}/{time}{rand:6}", /* 上传保存路径,可以自定义保存路径和文件名格式 */
        scrawlMaxSize: 2048000, /* 上传大小限制，单位B */
        scrawlUrlPrefix: "", /* 图片访问路径前缀 */
        scrawlActionName:"/ue/scrawlUpload"
    });
})