<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %> 


<html>
<head>

<script src="https://code.jquery.com/jquery-3.3.1.js"></script>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
<meta name="description" content="">
<meta name="author" content="">
<link rel="icon" href="./resources/docs/favicon.ico">
<link rel="stylesheet" type="text/css" href="css/groupstyle.css">
<link rel="stylesheet" type="text/css" href="css/mypagenav-style.css">
<link rel="stylesheet" type="text/css" href="css/bottom-style.css">

<link rel="stylesheet" type="text/css" href="css/main-card-style.css">
<link rel="stylesheet" type="text/css" href="css/main-calender-style.css">
<link rel="stylesheet" type="text/css" href="css/main-carousel-style.css">

<script src="//code.jquery.com/jquery-1.11.1.min.js"></script>

<title>meet now</title>

<link href="./resources/docs/dist/css/bootstrap.min.css"
	rel="stylesheet">
<link href="./resources/docs/examples/jumbotron/jumbotron.css"
	rel="stylesheet">

<!-- Animated  -->
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/animate.css/3.5.2/animate.min.css">

<title>Meet now</title>

<script src="./resources/docs/assets/js/ie-emulation-modes-warning.js"></script>

<!-- 합쳐지고 최소화된 최신 CSS -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">

<!-- 부가적인 테마 -->
<link rel="stylesheet"
   href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap-theme.min.css">

<!-- 합쳐지고 최소화된 최신 자바스크립트 -->
<script
   src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>

<!-- fontawesome -->
<link rel="stylesheet"
   href="https://use.fontawesome.com/releases/v5.1.0/css/all.css"
   integrity="sha384-lKuwvrZot6UHsBSfcMvOkWwlCMgc0TaWr+30HWe3a4ltaBwTZhyTEggF5tJv8tbt"
   crossorigin="anonymous">


</head>



<script>
   $(document).ready(function() {
       $('#media').carousel({
         pause: true,
         interval: false,
       });

      $("#login").click(function() {
         $(location).attr("href", "LoginController.co");

      });
      $("#logout").click(function() {

         $(location).attr("href", "LogoutController.co");
      });

   });
</script>
<STYLE>

/* 나눔고딕 */
@import url(http://fonts.googleapis.com/earlyaccess/nanumgothic.css); 

/* 제주고딕 */
@import url(http://fonts.googleapis.com/earlyaccess/jejugothic.css);
body {
/* 	font-family: 'Jeju Gothic', serif; */
	font-family: 'Nanum Gothic', serif;
}
</STYLE>


<body>
   <header>
      <%@ include file="include/nav/mypageNav.jsp"%>
      <link rel="stylesheet" type="text/css" href="css/groupstyle.css">
   </header>

   <div class="jumbotron" style="background-color: #b548d0">
      <div class="container">

			<h1 id="" style="color: white;">무엇을 좋아하시나요?</h1>
			<p style="color: white;">MeetNow로 한 걸음 더 나아가기</p>
			<p>
				<a class="btn btn-primary btn-lg" href="#" role="button">가입하기&raquo;</a>
			</p>
		</div>
	</div>


	<div class="container">
		<h2 style="color: #5e5568;">
			<b>주변의 인기 Meet Up</b>
		</h2>
		<div class='row'>
			<div class='col-md-12'>
				<div class="carousel slide media-carousel" id="media">
					<div class="carousel-inner">
						<div class="item  active">
							<div class="row">
								<div class="col-md-4">
									<a class="thumbnail" href="#"><img alt=""
										src="./image/manse.jpeg"></a>
								</div>
								<div class="col-md-4">
									<a class="thumbnail" href="#"><img alt=""
										src="./image/manse.jpeg"></a>
								</div>
								<div class="col-md-4">
									<a class="thumbnail" href="#"><img alt=""
										src="http://placehold.it/150x150"></a>
								</div>
							</div>
						</div>
						<div class="item">
							<div class="row">
								<div class="col-md-4">
									<a class="thumbnail" href="#"><img alt=""
										src="http://placehold.it/150x150"></a>
								</div>
								<div class="col-md-4">
									<a class="thumbnail" href="#"><img alt=""
										src="http://placehold.it/150x150"></a>
								</div>
								<div class="col-md-4">
									<a class="thumbnail" href="#"><img alt=""
										src="http://placehold.it/150x150"></a>
								</div>
							</div>
						</div>
						<div class="item">
							<div class="row">
								<div class="col-md-4">
									<a class="thumbnail" href="#"><img alt=""
										src="http://placehold.it/150x150"></a>
								</div>
								<div class="col-md-4">
									<a class="thumbnail" href="#"><img alt=""
										src="http://placehold.it/150x150"></a>
								</div>
								<div class="col-md-4">
									<a class="thumbnail" href="#"><img alt=""
										src="http://placehold.it/150x150"></a>
								</div>
							</div>
						</div>
					</div>
					<a data-slide="prev" href="#media" class="left carousel-control">‹</a>
					<a data-slide="next" href="#media" class="right carousel-control">›</a>
				</div>
			</div>
		</div>
	</div>

<div class="container">
  <h2 style="color:#5e5568;"><b>주변의 인기 Meet Up</b></h2></br>
    <div class='row'>
    <div class='col-md-12'>
      <div class="carousel slide media-carousel" id="media">
        <div class="carousel-inner">
          
          <c:forEach var="items" items="${result}" varStatus="status" >
          
          	   <c:choose>
      				<c:when test="${status.count==1}">
      					<div class="item  active">
            			<div class="row">
      				</c:when>
      				<c:when test="${status.count!=1 && status.count%3==1}">
      					<div class="item">
            			<div class="row">
      				</c:when>
      			</c:choose>
      			
            <div class="col-md-4">
               <div class="card">
                  <time class="icon">
                  <strong><fmt:formatDate value="${items.meeting_start_time}" pattern="M"/>월</strong>
                    <span><fmt:formatDate value="${items.meeting_start_time}" pattern="dd"/></span>
                  </time>
                  <a class="img-card" href="meeting.meet?seq=${items.meeting_seq}"><img src="files/${items.meeting_picture}"> </a>
                  <div class="card-content">
                     <div class="" style="color:#8b96a8;" >
                        <fmt:formatDate value="${items.meeting_start_time}" pattern="M"/>월
                        <fmt:formatDate value="${items.meeting_start_time}" pattern="dd"/>일 
                        <fmt:formatDate value="${items.meeting_start_time}" pattern="E"/>요일 
                        <fmt:formatDate value="${items.meeting_start_time}" pattern="a"/> 
                        <fmt:formatDate value="${items.meeting_start_time}" pattern="hh"/>시
                        <fmt:formatDate value="${items.meeting_start_time}" pattern="mm"/>분 
                     </div>
                     <br>
                     <h2 class="card-title">
                        <p><a href="meeting.meet?seq=${items.meeting_seq}"> ${items.meeting_title} </a></p>
                     </h2>
                     <p class="img-member">
                        <img style="width: 60px; height: 60px; border-radius: 50%; outline: none; margin : 0 10px 20px 0;" class="pull-left" src="./image/manse.jpeg" alt="Focus Lab">
                     </p>
                     <div class="">주최자 : ${items.group_leader}</div>
                     <div class="">주최그룹 : ${items.group_name}</div>
                     
                  </div>
               </div>
            </div>
         
      		</c:forEach>
          
        </div>
        <a data-slide="prev" href="#media" class="left carousel-control">‹</a>
        <a data-slide="next" href="#media" class="right carousel-control">›</a>
      </div>                          
    </div>
 




   <div class="container">
      <h2 class="interest-title" style="color:#5e5568;"><b>분류별 탐색</b></h2></br>
      <div class="col">
         <div class="col-md-3">
            <div class="interest-card">
                 <a class="interest-img" href="http://www.fostrap.com/"><img src="./image/야외활동.JPG"></a> <br />
               <p class="interest-name">야외활동</p>
            </div>
         </div>
         <div class="col-md-3">
            <div class="interest-card">
                 <a class="interest-img" href="http://www.fostrap.com/"><img src="./image/tech.jpg"></a> <br />
               <p class="interest-name">기술</p>
            </div>
         </div>
         <div class="col-md-3">
            <div class="interest-card">
                 <a class="interest-img" href="http://www.fostrap.com/"><img src="./image/Tulips.jpg"></a> <br />
               <p class="interest-name">가족</p>
            </div>
         </div>
         <div class="col-md-3">
            <div class="interest-card">
                 <a class="interest-img" href="http://www.fostrap.com/"><img src="./image/Tulips.jpg"></a> <br />
               <p class="interest-name">건강과 웰빙</p>
            </div>
         </div>
      </div>
      <div class="col">
         <div class="col-md-3">
            <div class="interest-card">
                 <a class="interest-img" href="http://www.fostrap.com/"><img src="./image/Tulips.jpg"></a> <br />
               <p class="interest-name">스포츠 / 피트니스</p>
            </div>
         </div>
         <div class="col-md-3">
            <div class="interest-card">
                 <a class="interest-img" href="http://www.fostrap.com/"><img src="./image/Tulips.jpg"></a> <br />
               <p class="interest-name">학습</p>
            </div>
         </div>
         <div class="col-md-3">
            <div class="interest-card">
                 <a class="interest-img" href="http://www.fostrap.com/"><img src="./image/Tulips.jpg"></a> <br />
               <p class="interest-name">사진촬영</p>
            </div>
         </div>
         <div class="col-md-3">
            <div class="interest-card">
                 <a class="interest-img" href="http://www.fostrap.com/"><img src="./image/Tulips.jpg"></a> <br />
               <p class="interest-name">음식</p>
            </div>
         </div>
      </div>
      <div class="col">
         <div class="col-md-3">
            <div class="interest-card">
                 <a class="interest-img" href="http://www.fostrap.com/"><img src="./image/Tulips.jpg"></a> <br />
               <p class="interest-name">글쓰기</p>
            </div>
         </div>
         <div class="col-md-3">
            <div class="interest-card">
                 <a class="interest-img" href="http://www.fostrap.com/"><img src="./image/Tulips.jpg"></a> <br />
               <p class="interest-name">언어와 문화</p>
            </div>
         </div>
         <div class="col-md-3">
            <div class="interest-card">
                 <a class="interest-img" href="http://www.fostrap.com/"><img src="./image/Tulips.jpg"></a> <br />
               <p class="interest-name">음악</p>
            </div>
         </div>
         <div class="col-md-3">
            <div class="interest-card">
                 <a class="interest-img" href="http://www.fostrap.com/"><img src="./image/Tulips.jpg"></a> <br />
               <p class="interest-name">영화</p>
            </div>
         </div>
         <div class="col-md-3">
            <div class="interest-card">
                 <a class="interest-img" href="http://www.fostrap.com/"><img src="./image/Tulips.jpg"></a> <br />
               <p class="interest-name">공상과학 / 게임</p>
            </div>
         </div>
         <div class="col-md-3">
            <div class="interest-card">
                 <a class="interest-img" href="http://www.fostrap.com/"><img src="./image/Tulips.jpg"></a> <br />
               <p class="interest-name">예술</p>
            </div>
         </div>
         <div class="col-md-3">
            <div class="interest-card">
                 <a class="interest-img" href="http://www.fostrap.com/"><img src="./image/Tulips.jpg"></a> <br />
               <p class="interest-name">북클럽</p>
            </div>
         </div>
         <div class="col-md-3">
            <div class="interest-card">
                 <a class="interest-img" href="http://www.fostrap.com/"><img src="./image/Tulips.jpg"></a> <br />
               <p class="interest-name">춤</p>
            </div>
         </div>
         <div class="col-md-3">
            <div class="interest-card">
                 <a class="interest-img" href="http://www.fostrap.com/"><img src="./image/Tulips.jpg"></a> <br />
               <p class="interest-name">반려동물</p>
            </div>
         </div>
         <div class="col-md-3">
            <div class="interest-card">
                 <a class="interest-img" href="http://www.fostrap.com/"><img src="./image/Tulips.jpg"></a> <br />
               <p class="interest-name">취미와 공예</p>
            </div>
         </div>
         <div class="col-md-3">
            <div class="interest-card">
                 <a class="interest-img" href="http://www.fostrap.com/"><img src="./image/Tulips.jpg"></a> <br />
               <p class="interest-name">패션과 스타일</p>
            </div>
         </div>
         <div class="col-md-3">
            <div class="interest-card">
                 <a class="interest-img" href="http://www.fostrap.com/"><img src="./image/Tulips.jpg"></a> <br />
               <p class="interest-name">비지니스</p>
            </div>
         </div>
         
      </div>
      
   </div> 

      

		<div class="col">
			<div class="col-xs-12 col-sm-4">
				<div class="card">
					<a class="img-card" href="http://www.fostrap.com/"> <img
						src="./image/Tulips.jpg" />
					</a> <br />
					<div class="card-content">
						<h4 class="card-title">
							<a href="http://www.fostrap.com/"> Your title card </a>
						</h4>
						<div class="">Your description card!!</div>
					</div>
					<div class="card-read-more">
						<a class="btn btn-link btn-block" href="http://www.fostrap.com/">
							Read More </a>
					</div>
				</div>
			</div>
		</div>


      <%@ include file="include/bottom.jsp"%>

   
   
</body>

</html>
