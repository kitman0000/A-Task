
function checkToken(){
	var token = localStorage.getItem("token");
	var params = new URLSearchParams();
	params.append("token",token);
	axios
		.post("/user/checkToken",params)
		.then(res=>{
			var response=res.data;
			if(response=="TOKEN_CHECK_FAILED"){
				window.location.href="login.html";
			}
		})
}

function getTheme(){
	var token = localStorage.getItem("token");
	var params = new URLSearchParams();
	params.append("token",token);
	axios
		.post("/user/getTheme",params)
		.then(res=>{
			var response=res.data;
			this.ind=response
			$("#cssLink").attr("href","css/"+response);
			return response;
		})

}