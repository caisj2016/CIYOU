/**
 * ajax请求后台删除admin
 * @param id adminId
 */
function deleteAdmin(id) {

	$.ajax({
		type: 'POST',
		url: 'deleteAdmin',
		cache: false,
		dataType:'json',
		data: {
			adminId: id
		},
		success: function (data) {
			if(data.stateCode == "403"){
				showInfo(data.message);
				window.location.href = "/403";
			}else {
				showInfo(data.message);
			}
		},
		error: function (jqXHR, textStatus, errorThrown) {
			showInfo("提交失败，请重试");
		}
	});

}


$('#modal_info').on('hide.bs.modal',function() {//提示模糊框隐藏时候触发
       		 location.reload();  	//刷新当前页面
 });



function showInfo(msg) {
    $("#div_info").text(msg);
    $("#modal_info").modal('show');
}


