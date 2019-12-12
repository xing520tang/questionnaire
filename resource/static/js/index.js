$(function(){
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
});