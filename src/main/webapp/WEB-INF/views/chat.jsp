<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
<meta charset="UTF-8">
<title>Chating</title>
</head>

<script type="text/javascript">
	var ws;

	function wsOpen() {
		ws = new WebSocket("ws://" + location.host + "/chating");
		wsEvt();
	}

	function wsEvt() {
		ws.onopen = function(data) {
			//소켓이 열리면 초기화 세팅하기
		}

		ws.onmessage = function(data) {
			var msg = data.data;
			if (msg != null && msg.trim() != '') {
				$("#chating").append("<p>" + msg + "</p>");
			}
		}

		document.addEventListener("keypress", function(e) {
			if (e.keyCode == 13) { //enter press
				send();
			}
		});
	}

	function chatName() {
		var userName = $("#userName").val();
		if (userName == null || userName.trim() == "") {
			alert("사용자 이름을 입력해주세요.");
			$("#userName").focus();
		} else {
			wsOpen();
			$("#yourName").hide();
			$("#yourMsg").show();
			$("#userId").text(userName);
		}
	}

	function send() {
		var uN = $("#userName").val();
		var msg = $("#chatting").val();
		ws.send(uN + " : " + msg);
		$('#chatting').val("");
	}
</script>
<body>

	<div class="container w-50">
		<h1>채팅</h1>
		<div id="chating" class="bg-dark text-white overflow-auto"  style = "height:500px"></div>
		<div id = "yourName" class="input-group mb-3">
			<input type="text" class="form-control" name="userName" id="userName" placeholder="닉네임을 입력하세요">
			<div class="input-group-append">
				<button type="button" id="startBtn"
							class="btn btn-outline-dark" onclick="chatName()">닉네임 등록</button>
			</div>
		</div>
		<div id = "yourMsg" class="input-group mb-3" style = "display:none">
			<input type="text" class="form-control" name="chatting" id="chatting" placeholder="보내실 메시지를 입력하세요.">
			<div class="input-group-append">
				<button type="button" id="startBtn"
							class="btn btn-outline-dark" onclick="send()">보내기</button>
			</div>
		</div>
	</div>
</body>
</html>