<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Title</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
</head>
<body>
    <label class="col-sm-2 control-label">请选择要上传的图片:</label>
        <div class="col-sm-10">
        <input type="hidden" name="img"  id="photoUrl"/>
        <input type="file" name="logoFile" id="logoFile" onchange="setImg(this);"><br>
        <span><img id="photourlShow" src="" width="500" height="500"/></span><br>
        <span><input id="show" style="width: 500px"></span>
        </div>
</body>
</html>
<script src="/js/jquery.min.js"></script>
<script type="text/javascript">

    function setImg(obj){
        var f=$(obj).val();
        if(f == null || f ==undefined || f == ''){
            return false;
        }
        if(!/\.(?:png|jpg|bmp|gif|PNG|JPG|BMP|GIF)$/.test(f))
        {
            alert("类型必须是图片(.png|jpg|bmp|gif|PNG|JPG|BMP|GIF)");
            $(obj).val('');
            return false;
        }
        var data = new FormData();
        console.log(data);
        $.each($(obj)[0].files,function(i,file){
            data.append('file', file);
        });
        console.log(data);
        $.ajax({
            type: "POST",
            url: "/fileManager`/upload.action",
            data: data,
            cache: false,
            contentType: false,    //不可缺
            processData: false,    //不可缺
            dataType:"json",
            success: function(ret) {
                console.log(ret);
                if(ret.code==10000){
                    $("#photoUrl").val(ret.data.url);//将地址存储好
                    $("#photourlShow").attr("src",ret.data.url);//显示图片
                    $("#show").attr("value",ret.data.url);//显示图片
                    alert(ret.msg);
                }else{
                    alertError(ret.msg);
                    $("#url").val("");
                    $(obj).val('');
                }
            },
            error: function(XMLHttpRequest, textStatus, errorThrown) {
                alert("上传失败，请检查网络后重试");
            }
        });
    }
</script>
</html>