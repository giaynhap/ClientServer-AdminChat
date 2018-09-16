
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
        var listThreat = {};
        server.onOpen = (function(){
				server.login("Admin","giaynhap");
        })
        server.onMessage = function(data){
		
            console.log(data.data);
             
            json =JSON.parse(data.data);
            if( Object.prototype.toString.call( json ) === '[object Array]' ) {
                if (json.length<1) return;
                var first = json[0];
 
                if ( first.hasOwnProperty("threadkey") )
                {
					
                     json.sort(compare);
                     $(".people").html("");
                    for (var k in json)
                    {
                        msg = JSON.parse(json[k].msg);
                        var date = new Date(msg.timestamp);
                        str= ' <li class="person" data-chat="'+json[k].threadkey+'">\
                            <img src="user_male2-512.png" alt="" />\
                            <span class="name">'+json[k].name+'</span>\
                            <span class="time">'+date.toTimeString().split(' ')[0]+'</span>\
                            <span class="preview">'+msg.message+'</span>\
                        </li>'
                        
                        $(".people").append(str);
                        reset();
                    }
                }
                else
                {
                    for (var k in json)
                    {
                        var str = ' <div class="bubble '+((json[k].user==0)?"you":"me")+'"> '+json[k].message+' </div>'
                        $(".chat").append(str);

                    }
                     $('.chat').animate({scrollTop: $('.chat').prop('scrollHeight')});
                
                }
            }
            else
            {
                if (json.type==1)
                {
                    if (json.threat==$('.chat').attr('data-chat')){
                        var str = ' <div class="bubble '+((json.user==0)?"you":"me")+'"> '+json.message+' </div>'
                            $(".chat").append(str);
                    }
                    $('.chat').animate({scrollTop: $('.chat').prop('scrollHeight')});
                }
            }
        }
        server.init('ws://localhost:24024/chat');
        
        
         $(".write-link.send").click(function(){
           

           send_message();
         })

         function compare(a,b) {
            if (a.last > b.last)
            return -1;
            if (a.last < b.last)
            return 1;
            return 0;
        }

$("#inbox_input").on('keyup', function (e) {
    if (e.keyCode == 13) {
       send_message();
    }
});


function send_message()
{
var session = $('.chat').attr('data-chat'); if (session==null) return;
if ($("#inbox_input").val().length<1) return;
server.sendTo(session, $("#inbox_input").val());
$("#inbox_input").val("");
}

 