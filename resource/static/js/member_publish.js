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


var $table = $('#table').bootstrapTable({
    url: '/paper/user/list',
    method: 'GET',                      //请求方式（*）
    striped: true,                      //是否显示行间隔色
    cache: false,                       //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
    pagination: true,                   //是否显示分页（*）
    sortable: true,
    sortName: "addDate",              //是否启用排序
    sortOrder: "desc",                   //排序方式
    sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）
    pageNumber: 1,                      //初始化加载第一页，默认第一页,并记录
    pageSize: 10,                  //每页的记录行数（*）
    pageList: [10, 25, 50, 100],        //可供选择的每页的行数（*）
    search: true,                      //是否显示表格搜索
    strictSearch: false,
    showColumns: true,                  //是否显示所有的列（选择显示的列）
    showRefresh: true,                  //是否显示刷新按钮
    minimumCountColumns: 2,             //最少允许的列数
    clickToSelect: true,                //是否启用点击选中行
    // height: 500,                      //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
    uniqueId: "id",                     //每一行的唯一标识，一般为主键列
    showToggle: true,                   //是否显示详细视图和列表视图的切换按钮
    cardView: false,                    //是否显示详细视图
    detailView: false,                  //是否显示父子表
    mobileResponsive: true,
    rowAttributes: "rowAttributes",
    //得到查询的参数
    columns: [{
        field: 'id',
        title: '编号',
        sortable: true,
        formatter: idformatter
    },
        {
            field: 'nickName',
            title: '作者',
            formatter: NickNameFormatter
        },
        {
            field: 'title',
            title: '标题',
            sortable: true
        },
        {
            field: 'subTitle',
            title: '副标题',
            sortable: true,
            visible: false
        },
        {
            field: 'addDate',
            title: '发布日期',
            sortable: true,
            formatter: addDateFormatter
        },
        {
            field: 'startDate',
            title: '开始时间',
            sortable: true,
            formatter: startDateFormatter
        },
        {
            field: 'endDate',
            title: '结束时间',
            sortable: true,
            formatter: endDateFormatter
        },
        {
            field: 'timeLimit',
            title: '时间限制',
            sortable: true
        },
        {
            field: 'type',
            title: '问卷类型',
            sortable: true,
            formatter: typeFormatter
        },
        {
            title: '操作',
            width: 120,
            align: 'center',
            valign: 'middle',
            formatter: actionFormatter
        },],
    onLoadSuccess: function () {

        toastr.success("加载成功");
    },
    onLoadError: function () {
        toastr.error("数据加载失败！");
    }
});
function addDateFormatter(value, row, index) {
    return new Date(row.addDate).toLocaleString()
}
function startDateFormatter(value, row, index) {
    return new Date(row.startDate).toLocaleString()
}
function endDateFormatter(value, row, index) {
    return new Date(row.endDate).toLocaleString()
}
function rowAttributes(row, index) {
    return {
        'data-toggle': 'popover',
        'data-placement': 'bottom',
        'data-trigger': 'hover',
        'data-content': [
            "问卷描述：" + row.description
        ].join(', ')
    }
}
//操作栏的格式化
function actionFormatter(value, row, index) {
    var result = "";
    result += "<a href='/views/member_publish_dolist.html?" + row.id + "' class='btn btn-xs green'  title='查看详情'><span class='glyphicon glyphicon-search'></span>查看详情</a>";
    return result;
}
function NickNameFormatter(value, row, index) {
    var result = '<img src="/static/img/' + row.authorId + '.jpg" class=" img-circle" alt="喵喵喵~" width="25px">' + row.nickName
    return result;
}
function idformatter(value, row, index) {
    var now = new Date();
    var old = new Date(row.addDate)
    //console.log(now);
    //console.log(old);
    if (now.getTime() - old.getTime() < 3 * 1000 * 60 * 60 * 24)
        result = '<img src="/static/img/new.png" alt="new" width="35px">&nbsp;' + value;
    else
        result = value;
    return result;
}

function typeFormatter(value, row, index) {
    var result = "";
    if (value == 0) {
        result = "开放式问卷"
    } else {
        result = "带答案问卷"
    }
    return result;
}


$(function () {
    $('#table').on('post-body.bs.table', function (e) {
        $('[data-toggle="popover"]').popover()
    })
})
function dateTransfer(date) {
    return new Date(date).toLocaleString()
}
function InfoViewById(id) {
    data = $table.bootstrapTable('getRowByUniqueId', id);
    console.info(data)
    $('#md-title').html(data.title);
    $('#md-subtitle').html(data.subTitle);
    $('#md-author').html(data.nickName);
    $('#md-startDate').html(dateTransfer(data.startDate));
    $('#md-endDate').html(dateTransfer(data.endDate));
    if (data.timeLimit > 0)
        $('#md-timelimit').html(data.timeLimit + "分钟");
    else
        $('#md-timelimit').html("无限制");
    $('#md-description').html(data.description);
    $("#md-img").attr('src', '/static/img/' + 'paper.jpg?' + Math.random());
    $("#md-authorImg").attr('src', '/static/img/' + data.authorId + '.jpg?' + Math.random());
    $(".enterPaper").attr('href', '/views/paper_do.html?' + data.id);

    $('#info').modal('show');
}