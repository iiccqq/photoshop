<!DOCTYPE html>
<html>
<head lang="en">
<title>Spring Boot Demo - FreeMarker</title>
</head>
<body>

        <#list list as item> 
          <img style="width:200px;height:100px" src="${item!}"/>
             <input type="button" value="删除" onclick="del(${item_index})" />
        </#list>
   <br>
   <form id="f1" action="uploadSrc" method="post" enctype="multipart/form-data">
   <input type="file" name="fileSrc"/>
   <input type="button" value="上传源图" onclick="uploadSrc()" />
   </form>
<hr/>
<form id="f" action="preview" method="post" enctype="multipart/form-data">

样例图序号:<input name="demoNo" id="demoNo" type="number" value="1"/>
<input name="action" id="action" type="hidden" value="0"/>
<input name="type" id="type" type="hidden" value="0"/>
<hr/>
   叠加图1：
  <img src="${addPic1}" />
x:<input type="number" id="x1" name="x1" value="550" />像素
y:<input type="number" id="y1" name="y1"  value="610" />像素
<input type="file" name="file1"/>
<input type="button" value="上传" onclick="upload1()" />

<input type="button" value="预览" onclick="preview1()" />
<input type="button" value="生成" onclick="deal1()" />
<img src="">
<br/><hr/>
叠加图2：
 <img src="${addPic2}" />
x:<input type="number" id="x2" name="x2"  value="750" />像素
y:<input type="number" id="y2" name="y2"  value="450" />像素

<input type="file" name="file2"/>
<input type="button" value="上传" onclick="upload2()" />

<input type="button" value="预览" onclick="preview2()" />
<input type="button" value="生成" onclick="deal2()" />
<br/>
<hr/>
<form>
</body>
</html>
<script>
function setXY(){
localStorage.x1=document.getElementById("x1").value;
localStorage.y1=document.getElementById("y1").value;
localStorage.x2=document.getElementById("x2").value;
localStorage.y21=document.getElementById("y2").value;
}

function getXY(){
if(localStorage.x1!=null)
document.getElementById("x1").value= localStorage.x1;
if(localStorage.y1!=null)
localStorage.y1=document.getElementById("y1").value;
if(localStorage.x2!=null)
localStorage.x2=document.getElementById("x2").value;
if(localStorage.y2!=null)
localStorage.y2=document.getElementById("y2").value;
}
function uploadSrc (){
f1.submit();
}
function del(index){

window.location.href="del?index=" + index;
}
function upload1 (){
setXY();
f.action='upload';
document.getElementById("type").value = 0;
f.submit();
}
function preview1 (){
setXY();
f.action="preview";
document.getElementById("type").value = 0;
f.submit();
}
function deal1 (){
setXY();
f.action="deal";
document.getElementById("type").value = 0;
f.submit();
}
function upload2 (){
setXY();
f.action="upload";
document.getElementById("type").value = 1;
f.submit();
}
function preview2 (){
setXY();
f.action="preview";
document.getElementById("type").value = 1;
f.submit();
}
function deal2 (){
setXY();
f.action="deal";
document.getElementById("type").value = 1;
f.submit();
}
getXY();
</script>