	var ChatServer = function()
					{
						this.ws = null;
						this.session = null;
						 
						this.isLogin  = false;
						this.name  = null;
					}
	ChatServer.prototype = {
			
			init:function(url)
			{
				this.getSession();
				this.ws = new WebSocket(url);
				this.ws.onmessage =  this.onMessage;
				this.ws.onclose = this.onClose;
				this.ws.onopen = this.onOpen;
				
			},
			setName:function(name)
			{
				this.name = name;
				console.log("cokie"+name)
				this.setCookie("uname",name, 30);

			}
			,
			onMessage:function(evt)
			{
				 
				
			},
			onClose:function()
			{
				 
			},
			onOpen:function()
			{
				 
				
			}
			,
			 
			send:function(str)
			{
			
				this.ws.send(JSON.stringify(str));
				
			},
			
			makecontinue:function()
			{
				this.login(this.name);
			},
			logout:function()
			{
				this.setCookie("uname", "", 30);
				this.isLogin = false;
				location.reload();

				var message = {"user":0,"message":message,"session":this.session,"type":-2};

				this.send(message);
			},
			login:function (name, adminPassword)
			{
				var message = {"user":0,"message":name,"session":this.session,"type":0};
				if (adminPassword!=null) 
				{
					message.type =-1;
					message.message = adminPassword;
				}	
				 this.send(message);
			},
			sendTo:function(session,message)
			{
				var message = {"user":0,"message":message,"session":session,"type":1};
				this.send(message);
			},
			sendMessage:function(message)
			{
				var message = {"user":1,"message":message,"session":this.session,"type":1};
				this.send(message);
			},
			getThreat:function(session)
			{
				var message = {"user":0,"message":session,"session":this.session,"type":2};
				this.send(message);
			}
			,
			getSession:function()
			{
				this.session = this.getCookie("realtimesession");
				this.name = this.getCookie("uname");
				console.log(this.session);
				if (this.session==null)
				{
					this.session = this.makeid();
					this.setCookie("realtimesession", this.session, 30);
					this.isLogin= false;
				}else {
					if (this.name !=null && this.name.length>2)
						 
					this.isLogin= true;
				}
			},
			setCookie:function(cname, cvalue, exdays)
			{
				 var d = new Date();
				d.setTime(d.getTime() + (exdays*24*60*60*1000));
				var expires = "expires="+ d.toUTCString();
				document.cookie = cname + "=" + cvalue + ";path=/";
			},
			
					
			getCookie:function(cname) {
				var name = cname + "=";
				var ca = document.cookie.split(';');
				for(var i = 0; i < ca.length; i++) {
					var c = ca[i];
					while (c.charAt(0) == ' ') {
						c = c.substring(1);
					}
					if (c.indexOf(name) == 0) {
						return c.substring(name.length, c.length);
					}
				}
				return null;
			},
			makeid:function () {
			  var text = "";
			  var possible = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

			  for (var i = 0; i < 20; i++)
				text += possible.charAt(Math.floor(Math.random() * possible.length));

			  return text;
			}
			
			
		
	}