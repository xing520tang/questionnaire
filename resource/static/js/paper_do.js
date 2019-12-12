    //initiating jQuery
    jQuery(function ($) {
        $(document).ready(function () {

            $('#time_conter').stickUp({
                marginTop: 'auto',
            });

        });
    });

/*********************************************************/



var fnTimeCountDown = function (d, o) {


    var f = {
        haomiao: function (n) {
            if (n < 10) return "00" + n.toString();
            if (n < 100) return "0" + n.toString();
            return n.toString();
        },
        zero: function (n) {
            var n = parseInt(n, 10);//解析字符串,返回整数
            if (n > 0) {
                if (n <= 9) {
                    n = "0" + n;
                }
                return String(n);
            } else {
                return "00";
            }
        },
        dv: function () {

            //d = d || Date.UTC(2050, 0, 1); //如果未定义时间，则我们设定倒计时日期是2050年1月1日
            var now = new Date();
            //现在将来秒差值
            //alert(future.getTimezoneOffset());
            var dur = (d - now.getTime()) / 1000, mss = d - now.getTime(), pms = {
                hm: "000",
                sec: "00",
                mini: "00",
                hour: "00",
                day: "00",
                month: "00",
                year: "0"
            };
            if (mss > 0) {
                pms.hm = f.haomiao(mss % 1000);
                pms.sec = f.zero(dur % 60);
                pms.mini = Math.floor((dur / 60)) > 0 ? f.zero(Math.floor((dur / 60)) % 60) : "00";
                pms.hour = Math.floor((dur / 3600)) > 0 ? f.zero(Math.floor((dur / 3600)) % 24) : "00";
                pms.day = Math.floor((dur / 86400)) > 0 ? f.zero(Math.floor((dur / 86400)) % 30) : "00";
                //月份，以实际平均每月秒数计算
                pms.month = Math.floor((dur / 2629744)) > 0 ? f.zero(Math.floor((dur / 2629744)) % 12) : "00";
                //年份，按按回归年365天5时48分46秒算
                pms.year = Math.floor((dur / 31556926)) > 0 ? Math.floor((dur / 31556926)) : "0";
            } else {
                pms.year = pms.month = pms.day = pms.hour = pms.mini = pms.sec = "00";
                pms.hm = "000";
                alert('时间到！点确定提交！');
                $('#saveBtn').click();
                return;
            }
            return pms;
        },
        ui: function () {


            if (o.hm) {
                o.hm.html(f.dv().hm);
            }
            if (o.sec) {
                o.sec.html(f.dv().sec);
            }
            if (o.mini) {
                o.mini.html(f.dv().mini);
            }
            if (o.hour) {
                o.hour.html(f.dv().hour);
            }
            if (o.day) {
                o.day.html(f.dv().day);
            }
            if (o.month) {
                o.month.html(f.dv().month);
            }
            if (o.year) {
                o.year.html(f.dv().year);
            }
            setTimeout(f.ui, 1);
        }
    };
    f.ui();
};

/*******************************************************/

//{{if  gt .paper.TimeLimit 0}}
var zxx = {
    obj: function () {
        return {
            hm: $("#hm"),
            sec: $("#sec"),
            mini: $("#mini"),
            hour: $("#hour"),
            day: $("#day"),
            month: $("#month"),
            year: $("#year")
        }
    }
};

/**********************************************/

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
    var answerDate = Date.now();
    $("#saveBtn").on('click', function () {

        var paperID = $("#paperId").text();
        var userID = $("#userId").text();
        //console.log(paperID);
        //console.log(userID);
        var json1 = formClass2JsonObj('.answers')
        var json = {};
        json["paperId"] = parseInt(paperID);
        json["userId"] = parseInt(userID);
        json["useTime"] = parseInt((Date.now() - answerDate) / 1000 / 60);
        json["answerDate"] = answerDate;

        //console.log(answerDate.format("yyyy-MM-dd HH:mm:ss"));
        $.ajax({
            type: 'POST',
            url: "/paper/do",
            data: JSON.stringify(json)+"&"+JSON.stringify(json1),
            contentType: "application/json;charset=utf-8",
            success: function (data) {
                if (data.code == 200) {
                    toastr.success("提交成功");
                    console.log(data);
                    $("#recordId").text(data.data)
                    alert("提交成功，点击查看答题情况")
                    var paperID = $("#paperId").text();
                    window.location.replace("/views/paper_watch.html?" + paperID + "&" +data.data);
                }
                else {
                    toastr.error("提交失败，请稍后再试！");
                    console.log(data);
                }
            },
            error: function (data) {
                toastr.error(data.message);
                //alert(data.message);
                console.log(data);
            }
        });

        $("#modal-submit").modal("hide");
    });

});



/*****************************获取数据*******************/
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


    var paperId = window.location.search.split("?")[1];
    $.ajax({
        url:"/paper/do/" + paperId,
        type:"GET",
        success:function(res){
            console.log(res);
            if(res.paperInfo != null)
                fillPaperInfo(res.paperInfo)
            else toastr.error("问卷信息加载失败，请联系管理员！")
            if(res.questions != null)
                fillQuestions(res.questions)
            else toastr.error("问题加载失败，请联系管理员！")
        },
        error: function(result){
            toastr.error("系统错误，请联系管理员！");
        }
    });
})

    function dateTransfer(date) {
        return new Date(date).toLocaleString()
    }
    function fillPaperInfo(data) {
        $("#paperId").text(data.id)
        $("#md-title").text(data.title)
        $("#md-subtitle").text(data.subTitle)
        $("#md-author").text(data.nickName)
        if(data.authorId != null)
            $("#md-authorImg").attr('src','/static/img/'+data.authorId+'.jpg')
        $("#md-addDate").text(dateTransfer(data.addDate))
        $("#md-startDate").text(dateTransfer(data.startDate))
        $("#md-endDate").text(dateTransfer(data.endDate))
        $("#md-timelimit").text(data.timeLimit)
        $("#md-description").text(data.description)

        var timeLimit = parseInt(data.timeLimit)
        fnTimeCountDown(Date.now() + 1000 * 60 * timeLimit, zxx.obj());
    }

    function fillQuestions(data) {

        alterOptionsInfoFrom(data)

        var template = $.templates("#questionTmpl");

        var htmlOutput = template.render(data);

        $("#questionArea").html(htmlOutput);
        $("#questionArea").append('<a class="btn btn-primary" data-toggle="modal" href=\'#modal-submit\'>提交</a>\n' +
            '        <h5 class="page-header"></h5>')

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