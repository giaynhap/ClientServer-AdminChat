
function reset()
{
 $('.person').unbind();
$('.person').mousedown(function() {
	
    if ($(this).hasClass('.active')) {
        return false;
    } else {
		$('.chat').html("");
		
        var findChat = $(this).attr('data-chat');
		$('.chat').attr('data-chat',findChat);
        var personName = $(this).find('.name').text();
        $('.right .top .name').html(personName);
        $('.chat').removeClass('active-chat');
        $('.left .person').removeClass('active');
        $(this).addClass('active');
        $('.chat').addClass('active-chat');
		server.getThreat(findChat);
 
    }
});
}


var server = new ChatServer();
var isClickLogin = false;
var loginName = null;
server.onOpen = (function(){

    
  if (server.isLogin)
  {
    server.makecontinue();
    $(".container2").css({"display":"block"})
   $(".inbox-login").css({"display":"none"});
  }
  else
  $(".inbox-login").css({"display":"block"});
})

server.onMessage = function(data){
  message = data.data;
  console.log(message);
  if (isClickLogin)
  {
     server.setName(loginName);

     $(".container2").css({"display":"block"})
     $(".inbox-login").css({"display":"none"});
    
  }
  json = JSON.parse(message);
  
  if( Object.prototype.toString.call( json ) === '[object Array]' ) {
    for (var k in json)
    {
      type = json[k].type;
      message = json[k].message;
      user = json[k].user;
   
         var str = ' <div class="bubble '+((user==1)?"you":"me")+'"> '+message+' </div>'
         $(".inbox-chat-elm").append(str);

    }
  }
  else{
    type = json.type;
    message = json.message;
    user = json.user;
    
    switch(type)
    {
    case 1:

         var str = ' <div class="bubble '+((user==1)?"you":"me")+'"> '+message+' </div>'
         $(".inbox-chat-elm").append(str);
           $('.inbox-chat-elm').animate({scrollTop: $('.inbox-chat-elm').prop('scrollHeight')});
      break;
    case -1:
      
    case -2:
      server.logout();
      break;
    
    }
  }
  
}
server.onClose = function(){
 
}

server.init('ws://localhost:24024/chat');

$(document).ready(function(){
 $("#inbox-btn-login").click( function()
 {
 
  isClickLogin = true;
  loginName = $("#inbox-input-name").val();
  if (loginName.length<2) return;
 
  server.login(loginName);
 });
 $(".inbox-btn-send").click(function()
 {
 send_message();
 });
});

$("#inbox-input-chat").on('keyup', function (e) {
    if (e.keyCode == 13) {
       send_message();
    }
});


function send_message()
{
 if ($("#inbox-input-chat").val().length<1) return;
   server.sendMessage($("#inbox-input-chat").val());
  $("#inbox-input-chat").val("");
}