var websocket = null;

//初始话WebSocket
function initWebSocket(user) {
	if (window.WebSocket) {
		websocket = new WebSocket(encodeURI('ws://' + wimadress));

		websocket.onopen = function() {
			websocket.send('FHadminqq313596790' + user);//用户加入
		}
		websocket.onerror = function() {
		}
		websocket.onclose = function() {
			//alert(websocket)
		}
		//消息接收
		websocket.onmessage = function(response) {
			var message = response.data;
			//接收用户发送的消息
			alert(message);
		}
	}
}
//发送消息
function send(toUser,content) {
	var content = "fhadmin886"+toUser+"fhfhadmin888" + content;
	if (websocket != null) {
		websocket.send(content);
	} else {
		alert('您已经掉线，无法发送消息!');
	}
}

function leaveSystem(user){
	if(websocket != null){
		websocket.send('LeaveFHadminqq313596790'+user);
		websocket.close();
	}	
}