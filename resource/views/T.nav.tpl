<!--{{define "nav"}}-->
<nav class="navbar navbar-default navbar-static-top ">
    <div class="container">
        <div class="navbar-header">
            <button class="navbar-toggle collapsed" type="button" data-toggle="collapse" data-target="#bs-navbar"
                aria-controls="bs-navbar" aria-expanded="false">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="/">问卷调查</a>
        </div>
        <nav id="bs-navbar" class="navbar-collapse collapse" aria-expanded="false" style="height: 1px;">

            <ul class="nav navbar-nav">
                <li {{if .IsPaperNew}}class="active" {{end}}><a href="/paper/new">我要发布</a></li>
                <li {{if .IsPaperList}}class="active" {{end}}><a href="/paper/list">问卷列表</a></li>
            </ul>
            <ul class="nav navbar-nav navbar-right">
                {{if .IsLogin}}
                {{if eq .user.UserType  0}}
                <li><a href="#">后台管理</a></li>
                {{end}}
                <li>
                    <div class="dropdown">

                        <button class="btn btn-link " href="#" type="button" id="dropdownMenu1" data-toggle="dropdown"
                            aria-haspopup="true" aria-expanded="true">
                            <img src="/member/headimg" class="img-circle headimg" alt="喵喵喵~" width="35px">
                            <span class="nick">{{.user.NickName}}</span>
                            <span class="caret"></span>
                        </button>
                        <ul class="dropdown-menu" aria-labelledby="dropdownMenu1">
                            <li><a href="/member/info">个人中心</a></li>
                            <li><a href="/member/publish">我的发布</a></li>
                            <li><a href="/member/do">我的参与</a></li>
                            <li role="separator" class="divider"></li>
                            <li><a href="/member/logout">退出</a></li>
                        </ul>
                    </div>
                </li>
                {{else}}
                <li><a href="/register">注册</a></li>
                <li><a href="/login">登录</a></li>
                {{end}}
            </ul>
        </nav>
    </div>
</nav>

<!--{{end}}-->