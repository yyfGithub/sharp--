/**
 * 提交
 */
$("#credit_save").click(function () {

        $.ajax({
            cache : true,
            type : "POST",
            url : "/student/student/updateCreditInfo",
            data : $('#signupForm2').serialize(),// 你的formid
            async : false,
            error : function(request) {
                parent.layer.alert("Connection error");
            },
            success : function(data) {
                if (data.code == 0) {
                    parent.layer.msg("操作成功");
                    parent.reLoad();
                    var index = parent.layer.getFrameIndex(window.name); // 获取窗口索引
                    parent.layer.close(index);

                } else {
                    parent.layer.alert(data.msg)
                }

            }
        });

});
