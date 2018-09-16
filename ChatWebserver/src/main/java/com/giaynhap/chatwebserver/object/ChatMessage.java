package com.giaynhap.chatwebserver.object;

import javax.websocket.Session;

/**
 *
 * @author GiayNhap
 */
public class ChatMessage {
	

	private String message;
        private int type ;
        private int user ;
        private String toUser;
        private String strDate;
        private long timeSpam;
        
    public String getStrDate() {
        return strDate;
    }

    public void setStrDate(String strDate) {
        this.strDate = strDate;
    }

    public long getTimeSpam() {
        return timeSpam;
    }

    public void setTimeSpam(long timeSpam) {
        this.timeSpam = timeSpam;
    }
 private Session session;

        public Session getSession() {
            return session;
        }

        public void setSession(Session session) {
            this.session = session;
        }
    public String getToUser() {
        return toUser;
    }

    public void setToUser(String toUser) {
        this.toUser = toUser;
    }
        public int getUser() {
            return user;
        }

        public void setUser(int user) {
            this.user = user;
        }
 
        public void setType(int type) {
		this.type = type;
	}
        
	public String getMessage() {
		return message;
	}

 
	public void setMessage(String message) {
		this.message = message;
	}
        
         public int getType( ) {
		return this.type  ;
	}
     
}