<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!doctype html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>JBlog</title>
<Link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/jblog.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/jquery/jquery-1.9.0.js"></script>
</head>
<body>
	<div id="container">
		<c:import url='/WEB-INF/views/blog/includes/header.jsp' />	
		<div id="wrapper">
			<div id="content" class="full-screen">
				<c:import url='/WEB-INF/views/blog/includes/admin-menu.jsp'>
					<c:param name="menu" value="category" />
				</c:import>
				<!-- DB List 띄어주기 -->
				<div id="categoryList">
		      	</div>
      	
      			<h4 class="n-c">새로운 카테고리 추가</h4>
		      	<form id="categoryForm" name="categoryForm" method="post">
			      	<table id="admin-cat-add">
			      		<tr>
			      			<td class="t">카테고리명</td>
			      			<td><input type="text" name="name" id="name"></td>
			      		</tr>
			      		<tr>
			      			<td class="t">설명</td>
			      			<td><input type="text" name="description" id="description"></td>
			      		</tr>
			      		<tr>
			      			<td class="s">&nbsp;</td>
			      			<td><input type="button" onclick="fn_category_add()" value="카테고리 추가"></td>
			      		</tr>      		      		
			      	</table> 
		      	</form>
			</div>
		</div>
		<c:import url="/WEB-INF/views/blog/includes/footer.jsp"/>
	</div>
<script type="text/javascript">
var authUser = '${authUser.id}';
var context_path = '${pageContext.request.contextPath}';

// 댓글 등록
function fn_category_add() {
	$.ajax({
		type:"post",
		url: context_path + "/" + authUser + "/category/add",
		data: $("#categoryForm").serialize(),
		success : function (data) {
			if(data=="success")
			{
				getCategoryList();
				$("#name").val("");
				$("#description").val("");
			}
		},
		error: function (request, status, error) {
			alert(status);
		}
	});
}

// 초기 페이지 로딩시 불러오기
$(function () {
	getCategoryList();
});

// 댓글 불러오기

function getCategoryList() {

	$.ajax({
		type:'GET',
		url : context_path + "/" + authUser + "/category/get",
		dataType: "json",
		data: $("#categoryForm").serialize(),
		contentType: "application/x-www-form-urlencoded; charset=UTF-8", 
		success : function (data) {
			var category = JSON.parse(data)
			var html = "";
			html += "<table class='admin-cat'>";
			html += "<tr>";
  			html += "<th>번호</th>"
  			html += "<th>카테고리명</th>"
  			html += "<th>포스트 수</th>"
  			html += "<th>설명</th>"
  			html += "<th>삭제</th>"
  			html += "</tr>";
			if(category.length > 0){
				for (var i = 0; i < category.length; i++) {
					html += "<tr>";
					html += "<td>"+ (i+1) +"</td>";
					html += "<td>"+ category[i].name + "</td>";
					html += "<td>"+ "포스팅개수" + "</td>";
					html += "<td>"+ category[i].description + "</td>";
					html += '<td><img src="/jblog2/assets/images/delete.jpg"></td>';
					html += "</tr>";						
				}
			}else{
				html += "<tr>";
				html += "<td>카테고리가 없습니다.</td>";
				html += "</tr>";		
			}
			html += "</table>";
		    $("#categoryList").html(html);
		},
		error: function (request, status, error) {
			console.log("status", status);
		}
	});
}
</script>
</body>
</html>