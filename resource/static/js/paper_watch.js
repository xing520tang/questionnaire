    //initiating jQuery
    jQuery(function ($) {
        $(document).ready(function () {

            $('.scores').stickUp({
                marginTop: 'auto',
            });

        });
    });


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
                $("#il").hide();
                $("#landr").show();
            }
        },
        error: function(result){
            toastr.error("系统错误，请联系管理员！");
        }
    });
})


    $(function () {
        var str = window.location.search.split("?")[1];
        var paperId = str.split("&")[0];
        var recordId = str.split("&")[1];
            $.ajax({
            url:"/paper/watch/" + paperId +"/" + recordId,
            type:"GET",
            success:function(res){
                console.log(res)
                if(res.paperInfo != null) {
                    fillPaperInfo(res.paperInfo)
                    fillHead(res.paperInfo.type)
                }
                else toastr.error("问卷信息加载失败，请联系管理员！")
                if(res.questions != null)
                    fillAnswerInfo(res.questions, res.answers)
                else toastr.error("答案加载失败，请联系管理员！")
            },
            error: function(result){
                toastr.error("系统错误，请联系管理员！");
            }
        });
    })

function fillHead(data) {
    var template = $.templates("#headTmpl");

    var htmlOutput = template.render(data);

    $("#headContainer").html(htmlOutput)
}

function fillPaperInfo(data) {
    var template = $.templates("#paperTmpl");

    var htmlOutput = template.render(data);

    $("#paperContainer").html(htmlOutput)
}

function fillAnswerInfo(data, answers) {

    alterOptionsInfoFrom(data)
    InsertAnswers(data, answers)

    var template = $.templates("#answerTmpl");

    var htmlOutput = template.render(data);

    $("#answerContainer").html(htmlOutput)
    $("#answerContainer").append("<a class=\"btn btn-primary\" data-toggle=\"modal\" href='/'>返回首页</a>\n" +
        "        <h5 class=\"page-header\"></h5>")
}

    function alterOptionsInfoFrom(data) {
        for(var i = 0; i < data.length; i++){
            var options = data[i].optionsInfo;
            var opts = options.split(";")

            var array = [];
            for(var j = 0; j < opts.length; j++){
                var jsonObj = opts[j];
                array.push(jsonObj)
            }
            //console.log(array)
            data[i].optionsInfo = array;
        }
    }

    function InsertAnswers(data, answers) {
        for (var i = 0; i < data.length; i++) {
            data[i]["answerNo"] = answers[i].answerNo;
            data[i]["answerText"] = answers[i].answerText;
        }
    }