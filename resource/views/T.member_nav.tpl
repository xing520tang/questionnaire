{{define "memberNav"}}

<div class="container">
    <ul class="nav nav-pills ">
        <li role="presentation" {{if .MemberInfo}} class="active" {{end}}><a href="/member/info">我的信息</a></li>
        <li role="presentation" {{if .MemberPublish }} class="active" {{end}}><a href="/member/publish">我的发布</a></li>
        <li role="presentation" {{if .MemberDo}} class="active" {{end}}><a href="/member/do">我的参与</a></li>
    </ul>
</div>


{{end}}