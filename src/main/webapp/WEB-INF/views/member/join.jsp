<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="/resources/css/member/join.css">
<script src="https://code.jquery.com/jquery-3.4.1.js" integrity="sha256-WpOohJOqMqqyKL9FccASB9O0KwACQJpFTUBLTYOVvVU=" crossorigin="anonymous"></script>
</head>
<body>
	<div class="wrapper">
		<form id="join_form" method="POST">
			<div class="wrap">
				<div class="subjecet">
					<span>회원가입</span>
				</div>
				<div class="id_wrap">
					<div class="id_name">아이디</div>
					<div class="id_input_box">
						<input class="id_input" id="memberId" name="memberId">
					</div>
					<div class="idCheck_button">
						<span>ID 중복확인</span>
					</div>
					<span class="id_input_re_1">사용 가능한 아이디입니다.</span> 
					<span class="id_input_re_2">아이디가 이미 존재합니다.</span>
				</div>
				<div class="pw_wrap">
					<div class="pw_name">비밀번호</div>
					<div class="pw_input_box">
						<input class="pw_input" id="memberPw" name="memberPw">
					</div>
				</div>
				<div class="pwck_wrap">
					<div class="pwck_name">비밀번호 확인</div>
					<div class="pwck_input_box">
						<input class="pwck_input" id="memberPwCheck" name="memberPwCheck">
					</div>
				</div>
				<div class="user_wrap">
					<div class="user_name">이름</div>
					<div class="user_input_box">
						<input class="user_input" id="memberName" name="memberName">
					</div>
				</div>
				<div class="mail_wrap">
					<div class="mail_name">이메일</div>
					<div class="mail_input_box">
						<input class="mail_input" id="memberMail" name="memberMail">
					</div>
					<div class="mail_check_wrap">
						<div class="mail_check_input_box">
							<input class="mail_check_input">
						</div>
						<div class="mail_check_button">
							<span>인증번호 전송</span>
						</div>
						<div class="clearfix"></div>
					</div>
				</div>
				<div class="address_wrap">
					<div class="address_name">주소</div>
					<div class="address_input_1_wrap">
						<div class="address_input_1_box">
							<input class="address_input_1" id="memberAddr1" name="memberAddr1">
						</div>
						<div class="address_button">
							<span>주소 찾기</span>
						</div>
						<div class="clearfix"></div>
					</div>
					<div class="address_input_2_wrap">
						<div class="address_input_2_box">
							<input class="address_input_2" id="memberAddr2" name="memberAddr2">
						</div>
					</div>
					<div class="address_input_3_wrap">
						<div class="address_input_3_box">
							<input class="address_input_3" id="memberAddr3" name="memberAddr3">
						</div>
					</div>
				</div>
				<div class="join_button_wrap">
					<input type="button" class="join_button" value="가입하기">
				</div>
			</div>
		</form>
	</div>
<script type="text/javascript">
	$(document).ready(function() {
		// 회원가입 버튼.
		$(".join_button").click(function() {
			$("#join_form").attr("action", "/member/join");
			$("#join_form").submit();
		});
	});
	
	// 아이디 중복 검사.
	$(".idCheck_button").click(function() {
		var memberId = $(".id_input").val();
		var data = {memberId : memberId}
		console.log("memberId : " + memberId);
		
		$.ajax({
			type : "post",
			url : "/member/memberIdChk",
			data : data,	
			success : function(result) {
				if (result != "fail") {
					$(".id_input_re_1").css("display", "inline-block");
					$(".id_input_re_2").css("display", "none");
				}else {
					$(".id_input_re_2").css("display", "inline-block");
					$(".id_input_re_1").css("display", "none");
				}
			}
		});
	});
</script>
</body>
</html>