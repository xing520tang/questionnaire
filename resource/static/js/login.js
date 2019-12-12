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
                        }
                    }
                },
                password: {
                    validators: {
                        notEmpty: {
                            message: '密码不能为空'
                        }
                    }
                }
            }
        });
    });

    $("#mySubmit").click(function () {
        var bv = $('#loginForm').data('bootstrapValidator');

        bv.validate();

        if(bv.isValid()){
            $.ajax({
                url: "/user/login",
                type: "POST",
                data: new FormData($("#loginForm")[0]),
                processData: false,
                contentType: false,
                success: function(data){
                    if (data.code == 200) {
                        window.location.replace("/views/index.html");
                    } else {
                        toastr.info("用户名或密码错误！");
                    }
                },
                error: function(){
                    toastr.error("系统错误，请联系管理员！");
                }
            });
        }
    });