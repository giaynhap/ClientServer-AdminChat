 
package com.giaynhap.chatwebserver.model;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.websocket.Session;

/**
 *
 * @author GiayNhap
 */
  
public class Messages {
	 private   HashMap<String,ArrayList> lstMessages = new HashMap<String,ArrayList >();
	 private static Messages message ;
        
	 public static Messages instance()
	 {
		  if (message == null)  new Messages();
		  return message ;
	 }
	 public Messages()
	 {
		  message = this;
	 }
 	public void add(String Sessionkey,ArrayList message)
 	{
 		lstMessages.put(Sessionkey, message);
 	 
 	}
 	public ArrayList get(String SessionKey)
 	{
 		return lstMessages.get(SessionKey);
 	}
	  
}
