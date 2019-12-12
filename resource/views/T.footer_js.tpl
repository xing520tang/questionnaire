{{define "footerJS"}}

<!-- jQuery -->
<script src="/static/vendor/jquery/jquery.min.js"></script>
<!-- Bootstrap JavaScript -->
<script src="/static/vendor/bootstrap/js/bootstrap.min.js"></script>

<script src="/static/vendor/toastr/js/toastr.min.js"></script>
{{template "message" .}}
{{end}}