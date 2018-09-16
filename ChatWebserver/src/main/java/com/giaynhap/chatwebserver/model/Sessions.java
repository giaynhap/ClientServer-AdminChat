 
package com.giaynhap.chatwebserver.model;

import com.giaynhap.chatwebserver.object.User;
import java.util.HashMap;
import javax.websocket.Session;

/**
 *
 * @author GiayNhap
 */
public class Sessions {
     private   HashMap<String,User> lstName = new HashMap<String,User >();
	 private static Sessions session ;   
	 public static Sessions instance()
	 {
		  if (session == null)  new Sessions();
		  return session ;
	 }
         
	 public Sessions()
	 {
		 session = this;
	 }
         public void delete(String session)
         {
              lstName.remove(session);
         }
 	public void add(String Sessionkey,User session)
 	{
 		lstName.put(Sessionkey, session);
               
 	 
 	}
 	public User get(String SessionKey)
 	{
 		return lstName.get(SessionKey);
 	}
        public HashMap <String,User> list()
        {
            return lstName;
        }

  
}
