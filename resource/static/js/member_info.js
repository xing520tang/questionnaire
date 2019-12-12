$(function(){
    $.ajax({
        url:"/user/auth",
        type:"GET",
        success:function(result){
            //console.log(result);
            if (result.code == 210) {
                //userName gaoshangqu
                $('.nick').text(result.data.nickName);

                //user headImg
                var src = $(".headimg")[0].src;
                src = src.substring(0, src.lastIndexOf("/")+1);
                $(".headimg").attr('src', src+result.data.imgName);

                //UserType
                if(result.data.userType != 1){
                    $("#backManager").show();
                    $("#userType").text("管理员")
                } else {
                    $("#userType").text("普通用户")
                }

                $("#landr").hide();
                $("#il").show();
            } else {
                $("#il").hide();
                $("#landr").show();
            }
        },
        error: function(result){
            toastr.error("系统错误，请联系管理员！");
        }
    });

    $.ajax({
        url: "/user",
        type: "GET",
        success: function (data) {
            if (data.code == 200) {
                $("#userName").text(data.data.userName);
                console.log(data.data.registerTime);
                $("#registerTime").text(data.data.registerTime);
            } else {
                toastr.info("数据加载失败！请稍后重试")
            }
        },
        error: function () {
            toastr.error("系统错误，请联系管理员！");
        }
    });

    $('#pwdForm').bootstrapValidator({

        submitButtons: '#submitPwd',
        message: 'This value is not valid',
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            oldPwd: {
                validators: {
                    notEmpty: {
                        message: '密码不能为空'
                    }
                }
            },
            newPwd1: {
                validators: {
                    notEmpty: {
                        message: '密码不能为空'
                    },
                    different: {  //比较
                        field: 'oldPwd', //需要进行比较的input name值
                        message: '新密码不能与旧密码相同'
                    }
                }
            },
            newPwd2: {
                validators: {
                    notEmpty: {
                        message: '密码不能为空'
                    },
                    identical: {  //比较是否相同
                        field: 'newPwd1',  //需要进行比较的input name值
                        message: '两次密码不一致'
                    },
                    different: {  //比较
                        field: 'oldPwd', //需要进行比较的input name值
                        message: '新密码不能与旧密码相同'
                    }

                }
            }
        }
    });
    $('#nickForm').bootstrapValidator({

        submitButtons: '#submitNick',
        message: 'This value is not valid',
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            newNick: {
                message: '昵称验证失败',
                validators: {
                    notEmpty: {
                        message: '昵称不能为空'
                    },
                    regexp: {
                        regexp: /^[^\s]+$/,
                        message: '昵称不能有空格'
                    },
                    stringLength: {
                        min: 2,
                        max: 18,
                        message: '昵称长度必须在2到18位之间'
                    }
                }
            }
        }
    });
});

$('#submitHeadImg').on('click', function () {
    var formData = new FormData();
    var file = $("#uploadInp")[0].files[0];
    formData.append("pic", file);
    $.ajax({
        url: "/user/updateImage",
        type: 'PUT',
        data: formData,
        // 告诉jQuery不要去处理发送的数据
        processData: false,
        // 告诉jQuery不要去设置Content-Type请求头
        contentType: false,
        success: function (data) {
            if (data.code == 200) {
                toastr.success("修改成功");

                var path= $(".headimg")[0].src;
                path = path.substring(0, path.lastIndexOf("/") + 1);
                $(".headimg").attr('src', path+data.data);
            } else if (data.code == 621) {
                toastr.error("图片超过2M，请选择适当大小的图片");
            } else if (data.code == 622) {
                toastr.warning("图片必须是jpg或png类型")
            } else if (data.code == 623) {
                toastr.warning("图片更新错误，请稍后再试")
            } else toastr.warning("修改失败，请联系管理员或稍后再试！");
        },
        error: function (data) {
            if (data.status == 413)
               toastr.warning("图片过大，无法上传！");
            else toastr.error("系统错误，请联系管理员！");
        }
    });

    $('#changeHeadImg').modal('hide');
});


$('#submitPwd').on('click', function () {
    updateUser(2)
});
$('#submitNick').on('click', function () {
    updateUser(1);
});
$('.modal').on('hidden.bs.modal', function () {
    $(':input', this).not(':button,:submit,:reset').val('').removeAttr('checked').removeAttr('checked');

});


function updateUser(type) {
    var $data;
    var bv;
    if (type == 1) {
        $data = $("#nickForm").serialize();
        bv = $("#nickForm").data("bootstrapValidator");
    } else {
        $data = $("#pwdForm").serialize();
        bv = $("#pwdForm").data("bootstrapValidator");
    }

    //表单追加字段
    //$.param({'type': type}) + '&' + $data,
    $.ajax({
        url: "/user",
        type: 'PUT',
        data: $.param({'type': type}) + '&' + $data,
        beforeSend: function () {
            console.log("正在进行，请稍候");
        },
        success: function (data) {
            if (data.code == 200) {
                toastr.success("修改成功~");
                if(type == 1)
                    $('.nick').html(data.data)
            } else if (data.code == 610)
                toastr.warning("输入不能为空！");
            else if (data.code == 230)
                toastr.warning("原始密码错误！");
            else
                toastr.warning("修改失败，请稍后再试！");
            console.log(data);
        },
        error: function () {
            toastr.error("系统错误，请联系管理员！");
        }
    });

    if (type == 1) {
        $("#nickForm")[0].reset();
        $('#changeNick').modal('hide');
    } else {
        $("#pwdForm")[0].reset();
        $("#changePwd").modal('hide');
    }

    bv.resetForm();
}