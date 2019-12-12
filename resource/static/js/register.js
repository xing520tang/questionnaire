$('form').bind('keydown', function (event) {
    if (event.keyCode == "13") {
        //回车执行查询
        $('#mySubmit').click();
    }
});
$(function () {

    $('form').bootstrapValidator({

        submitButtons: '#mySubmit',
        message: 'This value is not valid',
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            userName: {
                message: '用户名验证失败',
                validators: {
                    notEmpty: {
                        message: '用户名不能为空'
                    },
                    stringLength: {
                        min: 2,
                        max: 18,
                        message: '用户名长度必须在2到18位之间'
                    },
                    regexp: {
                        regexp: /^[a-zA-Z0-9_]+$/,
                        message: '用户名只能包含大写、小写、数字和下划线'
                    },
                    remote: { //ajax校验，获得一个json数据（{'valid': true or false}）
                        url: '/register/verify',                  //验证地址
                        message: '用户已存在',   //提示信息
                        type: 'GET',                   //请求方式
                        data: function (validator) {  //自定义提交数据，默认为当前input name值
                            return {
                                //act: 'is_registered',
                                userName: $("input[name='userName']").val()
                            }
                        },
                        delay: 2000
                    }
                }
            },
            nickName: {
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
            },
            pwd1: {
                validators: {
                    notEmpty: {
                        message: '密码不能为空'
                    },
                    different: {  //比较
                        field: 'username', //需要进行比较的input name值
                        message: '密码不能与用户名相同'
                    }
                }
            },
            password: {
                validators: {
                    notEmpty: {
                        message: '密码不能为空'
                    },
                    identical: {  //比较是否相同
                        field: 'pwd1',  //需要进行比较的input name值
                        message: '两次密码不一致'
                    }

                }
            }
        }
    });
    
    
    $("#confirmbtn").click(function () {
        $('#tipModal').modal('hide');
        window.location.replace("/views/login.html");
    });
});


    $('#mySubmit').click(function(){
        var bv = $('#registerForm').data('bootstrapValidator'); //<%--得到表单对象--%>

        bv.validate(); //<%--验证表单的全部字段--%>
        if(bv.isValid()){  //<%--如果全部验证成功--%>
        $("#modalTitle").empty().append("请稍后");
            $("#modalProgress").show();
            $("#modalContent").hide();
            $("#modalFooter").hide();
            $('#tipModal').modal({
                backdrop: false
            }); //<%--放进度条--%>

            $.ajax({
                url:"/register",
                type: "POST",
                data: new FormData($("#registerForm")[0]),
                processData: false,
                contentType: false,
                success:function(data){
                //<%--重置表单--%>
                    $('#registerForm')[0].reset(); //<%--清空表单内的值--%>
                    bv.resetForm();//<%--重置验证情况，全部变为未验证--%>
                    $('#registerForm').modal("hide");

                //<%--修改模态框--%>
                    $("#modalTitle").empty().append("提示");
                    $("#modalProgress").hide();
                    $("#modalContent").empty();
                    if (data.code == 200) {
                        $("#modalContent").append("注册成功，点击确定跳转至登录页面~");
                        $("#confirmbtn").show();
                        $("#getOff").hide();
                    }
                    else {
                        $("#modalContent").append("请求出错，请稍后重试或联系管理员！");
                        $("#confirmbtn").hide();
                        $("#getOff").show();
                    }
                    $("#modalContent").show();
                    $("#modalFooter").show();
                //<%--打开模态框--%>
                    $('#tipModal').modal({
                        backdrop: false
                    });
                },
                error: function(){
                    $("#modalTitle").empty().append("错误");
                    $("#modalProgress").hide();
                    $("#modalContent").empty();
                    $("#modalContent").append("系统错误，请联系管理员！");
                    $("#modalContent").show();
                    $("#modalFooter").show();
               // <%--打开模态框--%>
                    $('#tipModal').modal({
                        backdrop: false
                    });
                }
            });
        }
    })