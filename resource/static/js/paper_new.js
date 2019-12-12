var i = 1, ques_num = 1, haveAns = false;
$(function () {

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
                if(result.data.userType != 1)
                    $("#backManager").show();

                $("#landr").hide();
                $("#il").show();
            } else {
                window.location.replace("/");
            }
        },
        error: function(result){
            toastr.error("系统错误，请联系管理员！");
        }
    });


    $(".ques-box").on('click', '.addp', function () {
        //$('#pp').after($("#pp").clone());
        var addP = $(this).parent().clone();
        $(':input', addP).not(':button,:submit,:reset').val('').removeAttr('checked');
        $(this).parent().after(addP);
        var opNum = 0;
        var root = $(this).parent().parent();
        $(root).find('.pp').each(function (i, e) {
            opNum++;
            $(e).children('a').text(i + 1);
            $(e).children('.ques-type-label').children('.ques-type-input').val(i + 1);
        })
        $(root).children('.ques-option-num').val(opNum);
        toastr.success("选项添加成功～");
    });
    $(".ques-box").on("click", '.delp', function () {
        var root = $(this).parent().parent();
        var opNum = $(root).children('.ques-option-num').val();
        //console.log(opNum);
        if (opNum == 1) {
            toastr.warning("就剩一个选项啦！");
            return;
        }
        $(this).parent().remove();
        opNum = 0;
        $(root).find('.pp').each(function (i, e) {
            opNum++;
            $(e).children('a').text(i + 1);
            $(e).children('.ques-type-label').children('.ques-type-input').val(i + 1);
        })
        $(root).children('.ques-option-num').val(opNum);
        //console.log(opNum);
        toastr.success("选项删除成功～");
    });
    $(".ques-box").on('click', '.ques-add', function () {
        var root = $(this).parent().parent();
        //$('#pp').after($("#pp").clone());
        var addP = $(root).clone();
        $(':input', addP).not(':button,:submit,:reset,select').val('').removeAttr('checked');
        $(root).after(addP);

        j_ques_type($(addP).find(".ques-type"));
        ques_num = 0;
        $('.ques-no').each(function (i, e) {
            ques_num++;
            $(e).val(i + 1);
        });
        var opNum = 0;
        $(addP).find('.pp').each(function (i, e) {
            opNum++;
            $(e).children('a').text(i + 1);
            $(e).children('.ques-type-label').children('.ques-type-input').val(i + 1);
        })
        $(addP).children('.ques-option-num').val(opNum);
        //console.log(opNum);
        toastr.success("题目添加成功～");
        $("html,body").animate({ scrollTop: $(addP).offset().top }, 1000);
    });
    $(".ques-box").on("click", '.ques-del', function () {
        //console.log(ques_num);
        if (ques_num == 1) {
            toastr.warning("就剩这一个题啦，不要再删啦～");
            return
        }
        $(this).parent().parent().remove();
        ques_num = 0;
        $('.ques-no').each(function (i, e) {
            ques_num++;
            $(e).val(i + 1);
        })
        toastr.success("题目删除成功～");
    });
});
function j_paper_type() {
    if ($("#paper-type-zero").is(":checked")) {
        haveAns = false;
        $(".ques-type-label").hide();
    }
    if ($("#paper-type-one").is(":checked")) {
        haveAns = true;
        $(".ques-type-label").show();
    }
    $(".ques-type").each(function (i, e) {
        j_ques_type(e);
    });
    //console.log(haveAns);
}
function j_ques_type(me) {
    var op = $(me).val();
    //console.log(op);
    var root = $(me).parent().parent();
    if (haveAns) {
        $(root).find(".stan-scores").show();
        if (op == 0) {
            $(root).find(".stan-text").hide();

            $(root).find(".ques-option").show();
            $(root).find(".ques-type-input").attr("type", "radio");
        }
        else if (op == 1) {
            $(root).find(".stan-text").hide();
            $(root).find(".ques-option").show();
            $(root).find(".ques-type-input").attr("type", "checkbox");
        }
        else if (op == 2) {
            $(root).find(".ques-option").hide();
            $(root).find(".stan-text").show();
        }
    } else {
        $(root).find(".stan-scores").hide();
        if (op != 2) {
            $(root).find(".stan-text").hide();
            $(root).find(".ques-option").show();
        } else {
            $(root).find(".stan-text").hide();
            $(root).find(".ques-option").hide();
        }
    }
}
$(function () {
    j_paper_type();
});


/**********************************/
    $(".jcounter").inputCounter({
        settings: {

            // check the valus is within the min and max values
            checkValue: true,

            // is read only?
            isReadOnly: false

        }
    });
$('.form_datetime').datetimepicker({
    format: 'yyyy-mm-dd hh:ii:ss',
    startDate: new Date(),
    language: 'zh-CN',
    weekStart: 1,
    todayBtn: 1,
    autoclose: 1,
    todayHighlight: 1,
    startView: 2,
    forceParse: 0,
    showMeridian: 1
});

/************************************************/

/** 表单序列化成json字符串的方法  */
function formClass2JsonObj(formClass) {
    var paramArray = [];
    $(formClass).each(function (i, e) {
        paramArray[i] = $(e).serializeArray();
        //console.log(paramArray[i]);
    });
    /*请求参数转json对象*/

    var jsonArray = [];
    for (var i = 0; i < paramArray.length; i++) {
        var jsonObj = {};
        $(paramArray[i]).each(function () {
            if (jsonObj[this.name])
                jsonObj[this.name] += ";" + this.value;
            else
                jsonObj[this.name] = this.value;
        });
        jsonArray.push(jsonObj);
    }
    //var jsonAll = {};
    //jsonAll["rows"] = jsonArray;
    //jsonAll["total"] = jsonArray.length;
    console.log(jsonArray);
    // json对象再转换成json字符串
    return jsonArray;
}
/** 表单序列化成json字符串的方法  */
function formID2JsonString(formID) {
    var paramArray = $(formID).serializeArray();
    /*请求参数转json对象*/
    var jsonObj = {};
    $(paramArray).each(function () {
        jsonObj[this.name] = this.value;
    });
    console.log(jsonObj);
    // json对象再转换成json字符串
    return JSON.stringify(jsonObj);
}





$(function () {
    $("#saveBtn").on('click', function () {
        var questionObj = formClass2JsonObj(".questions");
        $.ajax({
            type: 'POST',
            url: "/paper",
            data: formID2JsonString("#papers")+"&"+JSON.stringify(questionObj),
            contentType: "application/json;charset=utf-8",
            success: function (data) {
                if (data.code == 200) {
                    toastr.success("问卷创建成功，快去问卷列表看看吧~");
                    alert("发布成功！点确定返回主页");
                    window.location.replace("/");
                }
                else {
                    toastr.error("系统错误，请联系管理员！");
                }
            },
            error: function (data) {
                toastr.error("系统或本地错误，请稍后再试！");
            }
        });
    });

    $("#modal-submit").modal('hide');
});