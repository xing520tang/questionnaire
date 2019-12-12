{{define "message"}}
<script>
    toastr.options = {
        "closeButton": true,
        "debug": false,
        "newestOnTop": false,
        "progressBar": false,
        "positionClass": "toast-top-center",
        "preventDuplicates": true,
        "onclick": null,
        "showDuration": "300",
        "hideDuration": "1000",
        "timeOut": "2000",
        "extendedTimeOut": "1000",
        "showEasing": "swing",
        "hideEasing": "linear",
        "showMethod": "fadeIn",
        "hideMethod": "fadeOut"
    }
</script>
{{ if .flash.notice}}
<script>toastr.success({{ .flash.notice }});</script>
{{ end }}
{{ if .flash.warning}}
<script>toastr.warning({{.flash.warning }});</script>
{{ end }}
{{ if .flash.error}}
<script>toastr.error({{.flash.error }});</script>
{{ end }}
{{end}}