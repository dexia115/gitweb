<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
	request.setAttribute("base", basePath);
%>
<script type="text/javascript">
			try {
				var scripts = [ null, null ];
				$('.page-content-area').ace_ajax('loadScripts', scripts,
						function() {
						});
			} catch (e) {
			}
			
			
		</script>


<h1>Hello ${sessionUser.userName}</h1>

<input type="text" id="username" />
<input type="text" id="content" />
<input type="button" onclick="sendMsg()" value="发送"/>
<br><br>
<input type="button" onclick="disconnection()" value="离开"/>

<br><br>
<input type="button" onclick="connection()" value="建立连接"/>

<script>
function connection(){
	initWebSocket(user);
}

function disconnection(){
	leaveSystem(user);
}

function sendMsg(){
	var content = $("#content").val();
	var curUser = $("#username").val();
	send(curUser,content);
}
		jQuery(document).ready(function() {
			
			var unique_id = $.gritter.add({
                // (string | mandatory) the heading of the notification
                title: 'Meet Metronic!',
                // (string | mandatory) the text inside the notification
                text: 'Metronic is a brand new Responsive Admin Dashboard Template you have always been looking for!',
                // (string | optional) the image to display on the left
                image: 'media/img/avatar1.jpg',
                // (bool | optional) if you want it to fade out on its own or just sit there
                sticky: true,
                // (int | optional) the time you want it to be alive for before fading out
                time: '',
                // (string | optional) the class name you want to apply to that specific message
                class_name: 'my-sticky-class'
            });
			
			setTimeout(function () {
                $.gritter.remove(unique_id, {
                    fade: true,
                    speed: 'slow'
                });
            }, 3000);
			
			var cc = $("#header_inbox_bar i").attr("class");
			$("#header_inbox_bar i").removeClass("icon-animated-vertical");
			setTimeout(function(){
				$("#header_inbox_bar i").addClass("icon-animated-vertical");
			},10000);
			
			/* var number = $('#header_inbox_bar .badge').text();
            number = parseInt(number);
            number = number + 2;
            $('#header_inbox_bar .badge').text(number);
            $('#header_inbox_bar').pulsate({
                color: "#dd5131",
                repeat: 20
            }); */
		});
		</script>
