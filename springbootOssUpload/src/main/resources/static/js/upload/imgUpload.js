;(function (window, $, undefined) {
    var ImageUploadService = function () {
        //上传文件域的整体div jquery对象
        this.uploadDiv = $('#uploader');
        //上传控件全局对象
        this.uploader = new Object;//webupload控件
        //文件的主表类型
        this.fnType = 'default';
        //文件的选择按钮名称
        this.uploadBtnLabel = "选择图片";
        //文件的提交按钮名称
        this.submitBtnLabel = "提交";
        //文件提交服务器路径
        this.uploadUrl = 'http://ice-maple.oss-cn-hangzhou.aliyuncs.com';
        //上传控件支持 swf文件的路径
        this.swfUrl = 'js/Uploader.swf';
        //请求上传参数的连接
        this.policyUrl = '/upload/getpolicy';
        // this.uuid = $('#uuid').val();
        //添加的文件数量
        this.fileCount = 0;
        //添加的文件总大小
        this.fileSize = 0;
        this.oosDirectory="declAttach";//oos文件上传路径
        // 优化retina, 在retina下这个值是2
        this.ratio = window.devicePixelRatio || 1;
        // 缩略图大小
        this.thumbnailWidth = 110 * this.ratio;
        this.thumbnailHeight = 110 * this.ratio;
        //所有文件的进度信息
        this.percentages = {};
        //文件状态
        this.state = 'pedding';
        //非图片 显示不了预览图的，需要显示的图片路径
        this.defaultImgUrl = "/img/Seal.gif";
        //预览图显示区域
        this.queue = '';
        this.uploadBtn = '';
        //上传按钮
        this.submitBtn = '';
        //响应成功的文件数目
        this.responseFileCount = 0;
        //控件上传成功的数目
        this.successNum = 0;
        //上传完成后是否允许继续上传
        this.allowUpload = false;
        //是否压缩上传
        this.compress=false;
        //生成的预览图位置
        this.preImgDiv = '';
        //是否自动上传
        this.autoUpload = false;
    };
    ImageUploadService.prototype = {
        //必传的属性为preImgDiv，uploadBtn
        init:function (option) {
            option=option||{};
            $.extend(this,option);
            this.preImgDiv.setTemplateElement("fileList");
            this.preImgDiv.processTemplate({"submitBtnLabel":this.submitBtnLabel});
            this.queue = this.preImgDiv.find('ul.filelist');
        },
        //执行初始化
        build: function (option) {
            if (!WebUploader.Uploader.support()) {
                alert('Web Uploader 不支持您的浏览器！如果你使用的是IE浏览器，请尝试升级 flash 播放器');
                throw new Error('WebUploader does not support the browser you are using.');
            }
            var _this = this;
            //初始化配置
            _this._initOption(option);
            //文件上传中的事件
            _this.uploader.onUploadProgress = function (file, percentage) {
                _this._onUploadProgress(file, percentage);
            };
            //文件被接收的事件
            _this.uploader.onFileQueued = function (file) {
                _this._onFileQueued(file);
            };
            //文件被移除的事件
            _this.uploader.onFileDequeued = function (file) {
                _this._onFileDequeued(file);
            };
            _this.uploader.on('all', function (type) {
                _this._onFileAll(type);
            });
            //文件加入错误的事件
            _this.uploader.onError = function (code) {
                _this._onError(code);
            };
            //文件上传，服务器给出响应，不代表上传成功
            _this.uploader.onUploadSuccess = function (file, response) {
                _this._onUploadSuccess(file, response);
                //将事件发布出去
                jQuery(_this).trigger("onUploadSuccess",[file, response]);
            };
            //文件上传，服务器响应失败
            _this.uploader.onUploadError = function (file, reason) {
                _this._onUploadError(file, reason);
                jQuery(_this).trigger("onUploadError",[file, reason]);
            };
            //所有文件上传完成
            _this.uploader.onUploadFinished =  function () {
                _this._onUploadFinished();
                //将完成事件发布出去，参数为所有文件的上传状态，成功失败数目。。
                jQuery(_this).trigger("onUploadFinished",[_this.uploader.getStats()]);
            };
            //文件提交之前的点击事件
            if(!this.autoUpload){
                _this.submitBtn.on('click', function () {
                    _this._submitClickEvent($(this));
                });
                _this.submitBtn.addClass('state-' + _this.state);
            }
        },
        //初始化配置
        _initOption: function (option) {
            var _this = this;
            if(typeof option.auto !== "undefined"){
                this.autoUpload = option.auto
            }
            var defaultOption = {
                // swf文件路径
                swf: _this.swfUrl,
                disableGlobalDnd: true,
                chunked: false,
                server: _this.uploadUrl,
                // paste: _this.uploadDiv,
                fileNumLimit: 300,
                fileSizeLimit: 5 * 1024 * 1024,    // 200 M
                fileSingleSizeLimit: 10 * 1024 * 1024,// 50 M
                auto:this.autoUpload,
                accept: {
                    title: '只支持EDC格式的文件',
                    extensions: 'edc',
                    mimeTypes: 'application/octet-stream'
                },
                compress:false,
                pick: {
                    id: _this.uploadBtn,
                    label: _this.uploadBtnLabel
                }
            };
            _this.uploader = WebUploader.create($.extend({}, defaultOption, option))
        },
        //添加文件 创建预览图
        _addFile: function (file) {
            var _this = this;
            _this.uploader.on('uploadBeforeSend', function (obj, data, headers) {
                _this._uploadBeforeSend(obj, data, headers, file);
                jQuery(_this).trigger("uploadBeforeSend",[obj, data, headers, file]);
            });
            var $li = $('<li id="' + file.id + '">' +
                    '<a title="点击预览" href="javascript:;" class="imgWrap" title=' + file.name + '></a>' +
                    '<p class="progress"><span></span></p>' +
                    '</li>'),

                $btns = $('<div class="file-panel">' +
                    '<span class="cancel">删除</span>' +
                    '<span class="rotateRight">向右旋转</span>' +
                    '<span class="rotateLeft">向左旋转</span></div>').appendTo($li),
                $prgress = $li.find('p.progress span'),
                $wrap = $li.find('a.imgWrap');

            if (file.getStatus() === 'invalid') {
                _this._showError(file.statusText).appendTo($li);
            } else {
                // @todo lazyload
                $wrap.text('预览中');
                _this.uploader.makeThumb(file, function (error, src) {
                    if (error) {
                        $wrap.attr('alt', '点击预览')
                        if (_this.defaultImgUrl == null) {
                            $wrap.text("无法预览");
                            return;
                        }
                        var img = $("<img src='" + _this.defaultImgUrl + "'>");
                        $wrap.empty().append(img);
                        return;
                    }
                    var img = $('<img src="' + src + '">');
                    $wrap.empty().append(img);
                }, _this.thumbnailWidth, _this.thumbnailHeight);

                _this.percentages[file.id] = [file.size, 0];
                file.rotation = 0;
            }

            file.on('statuschange', function (cur, prev) {
                if (prev === 'progress') {
                    // $prgress.hide().width(0);
                } else if (prev === 'queued') {
                    if(!_this.allowUpload){
                        _this.queue.find('li').off('mouseenter mouseleave');
                        _this.queue.find('.file-panel').remove();
                    }
                }

                // 成功
                if (cur === 'error' || cur === 'invalid') {
                    console.log(file.statusText);
                    _this._showError(file.statusText).appendTo($li);
                    _this.percentages[file.id][1] = 1;
                } else if (cur === 'interrupt') {
                    _this._showError('interrupt').appendTo($li);
                } else if (cur === 'queued') {
                    _this.percentages[file.id][1] = 0;
                } else if (cur === 'progress') {
                    $prgress.css('display', 'block');
                } else if (cur === 'complete') {
                    $prgress.hide();
                    $li.append('<span class="success"></span>');
                }

                $li.removeClass('state-' + prev).addClass('state-' + cur);
            });

            $li.on('mouseenter', function () {
                $btns.stop().animate({height: 30});
            });

            $li.on('mouseleave', function () {
                $btns.stop().animate({height: 0});
            });

            $btns.on('click', '.cancel', function () {
                _this.uploader.removeFile(file);
            });

            $li.appendTo(_this.queue);
        },
        //浏览器支持
        _supportTransition: (function () {
            var s = document.createElement('p').style,
                r = 'transition' in s ||
                    'WebkitTransition' in s ||
                    'MozTransition' in s ||
                    'msTransition' in s ||
                    'OTransition' in s;
            s = null;
            return r;
        })(),
        //删除文件 删除预览样式
        _removeFile: function (file) {
            var $li = $('#' + file.id);
            delete this.percentages[file.id];
            $li.off().find('.file-panel').off().end().remove();
        },
        //设置更新状态
        _setState: function (val) {
            var _this = this;
            var file, stats;

            if (val === _this.state) {
                return;
            }
            if(!this.autoUpload){
                _this.submitBtn.removeClass('state-' + _this.state);
                _this.submitBtn.addClass('state-' + val);
            }
            _this.state = val;

            switch (_this.state) {
                case 'pedding':
                    // _this.queue.parent().removeClass('filled');
                    // _this.queue.hide();
                    _this.statusBar.addClass('element-invisible');
                    _this.uploader.refresh();
                    break;

                case 'ready':
                    // _this.queue.parent().addClass('filled');
                    _this.queue.show();
                    _this.uploader.refresh();
                    break;

                case 'uploading':
                    // $('#filePicker2').addClass('element-invisible');
                    // _this.submitBtn.text('暂停上传');
                    break;

                case 'paused':
                    // _this.submitBtn.text('继续上传');
                    break;

                case 'confirm':
                    if(!this.autoUpload){
                        _this.submitBtn.text(_this.submitBtnLabel)
                    }
                    if(!_this.allowUpload){
                        // _this.uploadDiv.find('#filePicker]').unbind('click');
                        if(!this.autoUpload){
                            _this.submitBtn.addClass('disabled');
                        }
                        _this.uploadDiv.find(".webuploader-container *").unbind()
                    }else{
                        _this.uploader.refresh();
                    }
                    stats = _this.uploader.getStats();
                    if (stats.successNum && !stats.uploadFailNum) {
                        _this._setState('finish');
                        return;
                    }
                    break;
                case 'finish':
                    stats = _this.uploader.getStats();
                    if (stats.successNum) {
                        // layer.msg("上传成功")
                    } else {
                        // 没有成功的图片，重设
                        _this.state = 'done';
                        location.reload();
                    }
                    break;
            }

        },
        //更新进度条
        _onUploadProgress: function (file, percentage) {
            var _this = this;
            var $li = $('#' + file.id),
                $percent = $li.find('.progress span');

            $percent.css('width', percentage * 100 + '%');
            _this.percentages[file.id][1] = percentage;
        },
        //有文件被加入
        _onFileQueued: function (file) {
            var _this = this;
            _this.fileCount++;
            _this.fileSize += file.size;
            _this._addFile(file);
            _this._setState('ready');
        },
        //有文件被移除
        _onFileDequeued: function (file) {
            this.fileCount--;
            this.fileSize -= file.size;

            if (!this.fileCount) {
                this._setState('pedding');
            }

            this._removeFile(file);
        },
        //文件被操作总是会执行的方法
        _onFileAll: function (type) {
            var _this = this;
            switch (type) {
                case 'uploadFinished':
                    _this._setState('confirm');
                    break;

                case 'startUpload':
                    _this._setState('uploading');
                    break;

                case 'stopUpload':
                    _this._setState('paused');
                    break;

            }
        },
        //文件错误
        _onError: function (code) {
            switch (code) {
                case 'F_DUPLICATE':
                    layer.msg('该文件已经上传');
                    break;
                case 'Q_TYPE_DENIED':
                    layer.msg('文件类型上传错误');
                    break;
            }
            // layer.alert('Error: ' + code);
        },
        //提交之前的事件
        _submitClickEvent: function (obj) {
            var _this = this;
            if (obj.hasClass('disabled')) {
                return false;
            }

            if (_this.state === 'ready') {
                _this.uploader.upload();
            } else if (_this.state === 'paused') {
                _this.uploader.upload();
            } else if (_this.state === 'uploading') {
                _this.uploader.stop();
            }
        },
        //重新上传失败的文件
        _retry: function () {
            this.uploader.retry();
        },
        //上传成功的事件
        _onUploadSuccess:function (file, response) {
            var _this = this;
            var $file = $('#' + file.id);
        },
        _uploadBeforeSend:function(obj, data, headers, file){
            var _this = this;
            var $data = data;
            $.ajax({
                type: "POST",
                url: initUpload.policyUrl,
                data:JSON.stringify({oosDirectory:_this.oosDirectory}),
                async: false,
                cache: false,
                contentType:"application/json",
                dataType : "json"
            }).then(function (data) {
                //赋值参数
                $data = $.extend($data, data.policy);
                //对文件名进行随机处理
                var imgName = _this._calculate_object_name($data.name,'random_name');
                //设置文件路径，如果文件名不做设置，将由阿里云自己生成
                $data.key = $data.key + "/" + imgName;
                obj.filepath = $data.key;
                file.path = $data.key;
                //设置请求头
                headers['Access-Control-Allow-Origin'] = "*";
            })
        },
        _calculate_object_name:function(filename,type){
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
        },
        //文件上传错误的事件
        _onUploadError:function (file, reason) {

        },
        //文件上传完成的事件
        _onUploadFinished:function () {
            // successNum 上传成功的文件数
            // progressNum 上传中的文件数
            // cancelNum 被删除的文件数
            // invalidNum 无效的文件数
            // uploadFailNum 上传失败的文件数
            // queueNum 还在队列中的文件数
            // interruptNum 被暂停的文件数
            // if(this.uploader.getStats().uploadFailNum >0 ){
            //     layer.msg('上传失败');
            //     return;
            // }
            // this.successNum = this.uploader.getStats().successNum;
            // console.log('this.successNum=='+this.successNum +" this.responseFileCount=="+ this.responseFileCount);
            // if(this.successNum == this.responseFileCount){
            //     layer.msg('上传成功');
            // }else{
            //     layer.msg('上传失败');
            // }

        },
        //获取上传控件的对象
        getUploader:function () {
            return this.uploader;
        },
        //需要添加额外的上传按钮调用这个时间
        addUploadBtn:function (option) {
            this.uploader.addButton(option);
        },
        _showError:function (code) {
            var _this = this;
            var $info = $('<p class="error"></p>');
            var text = '';
            switch (code) {
                case 'exceed_size':
                    text = '文件大小超出';
                    break;

                case 'interrupt':
                    text = '上传暂停';
                    break;

                default:
                    text = '上传失败，请重试';
                    break;
            }
            $info.html(text);
            return $info;
        },
        //重置上传控件状态
        refresh:function () {
            this.uploader.refresh();
        }
    };

    window.ImageUploadService = ImageUploadService;
})(window,jQuery);


$(document).on('mouseenter', ".filelist li", function () {
    $(this).find('.file-panel').stop().animate({height: "30px"});
});
$(document).on('mouseleave', ".filelist li", function () {
    $(this).find('.file-panel').stop().animate({height: 0});
});
$(document).on('click', ".filelist li .file-panel .cancel", function () {
    // deleteAttatch(this);
});

// function deleteAttatch(obj) {
//     var _self = $(obj);
//     debugger;
//     var ajax = new $ax(Feng.ctxPath + "/upms/attach/imageDelete", function (data) {
//         debugger;
//         _self.parents('li').remove();
//         console.log(data.msg);
//     }, function (data) {
//         Feng.error("删除失败!" + data.msg + "!");
//     });
//
//     var data = {delIds:[_self.attr("attachId")]};
//     ajax.setData(data);
//     ajax.setAsync(true);
//     ajax.start();
// }
var rotate=0;
$(document).on('click', ".filelist li .file-panel .rotateRight", function () {
    var _self = $(this);
    rotate+=90
    _self.parent().siblings('.imgWrap').children().css({"transform":"rotate("+rotate+"deg)"});
});

$(document).on('click', ".filelist li .file-panel .rotateLeft", function () {
    var _self = $(this);
    rotate-=90
    _self.parent().siblings('.imgWrap').children().css({"transform":"rotate("+rotate+"deg)"});
});
// $(document).on('click',"a.imgWrap",function(){
//     var _this = $(this);
//     if(_this.siblings('.preImg').length==0){
//         return false;
//     }
//     layer.open({
//         type: 1,
//         title: false,
//         closeBtn: 0,
//         area: '516px',
//       //  skin: 'layui-layer-nobg', //没有背景色
//         shadeClose: true,
//         content: _this.siblings('.preImg')
//     });
// });