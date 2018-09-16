 
package com.giaynhap.chatwebserver.core;

import com.giaynhap.chatwebserver.object.ChatMessage;
import com.giaynhap.chatwebserver.model.Messages;

import com.giaynhap.chatwebserver.model.Sessions;
import com.giaynhap.chatwebserver.object.User;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonValue;
import javax.websocket.Session;

/**
 *
 * @author GiayNhap
 */
public class sSystem {

    LinkedBlockingQueue<ChatMessage> lstRecord;
    private static sSystem system;

    public static sSystem instance() {
        if (system == null) {
            system = new sSystem();
        }
        return system;
    }

    public sSystem() {
        lstRecord = new LinkedBlockingQueue<ChatMessage>();
        this.system = this;

    }

    public void add(ChatMessage msg) {
        try {
            lstRecord.add(msg);

            execute();
        } catch (IOException ex) {
            Logger.getLogger(sSystem.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void execute() throws IOException {
        List<ChatMessage> lst = new ArrayList<ChatMessage>();
        lstRecord.drainTo(lst);
        for (ChatMessage msg : lst) {

            String session = msg.getToUser();
            User user = Sessions.instance().get(session);
            if (user != null) {
                if ( msg.getUser()!=0|| msg.getType()!=1 )
                user.setSession(msg.getSession());
            }
                
            if (msg.getType() == 2) {
                if (user == null || !user.isAdmin()) {
                    msg.getSession().getAsyncRemote().sendText("{\"type\":-2}");
                    continue;
                }

                JsonArray array = createJsonArray(Messages.instance().get(msg.getMessage()));
                System.out.println("------ GET THREAD " );
                msg.getSession().getAsyncRemote().sendText(array.toString());

            }
            if (msg.getType() == 1) {

                if (user == null) {
                    msg.getSession().getAsyncRemote().sendText("{\"type\":-2}");
                    continue;
                }

                ArrayList lstMsg = Messages.instance().get(user.getThreadKey());
                if (lstMsg == null) {
                    lstMsg = new ArrayList();
                }
                JsonObject value = Json.createObjectBuilder()
                        .add("user", msg.getUser())
                        .add("date", msg.getStrDate())
                        .add("threat", msg.getToUser())
                        .add("timestamp", msg.getTimeSpam())
                        .add("message", msg.getMessage())
                        .add("type", msg.getType())
                        
                        .build();
                lstMsg.add(value);
                user.setLast(msg.getTimeSpam());
                Messages.instance().add(user.getThreadKey(), lstMsg);
                HashMap<String, User> list = Sessions.instance().list();
                 
                user.getSession().getAsyncRemote().sendText(value.toString());
                
                  for ( Map.Entry<String,User> val : list.entrySet())
                  {
                            if (val.getValue().isAdmin())
                            {
                             val.getValue().getSession().getAsyncRemote().sendText(value.toString());
                              val.getValue().getSession().getAsyncRemote().sendText(getListSession(Sessions.instance().list()).toString());
                
                            }
                  }
            
             
                  
            } else if (msg.getType() == 0) {

                String name = msg.getMessage();

                if (user == null) {
                    user = new User();
                    user.setThreadKey(session);
                    user.setName(name);
                    user.setIsAdmin(false);
                    user.setSession(msg.getSession());
                    user.getSession().getAsyncRemote().sendText("{\"status\":1}");
                } else {

                    JsonArray array = createJsonArray(Messages.instance().get(user.getThreadKey()));

                    System.out.println(" ======== Send user: " + array.toString() + "  ============= ");

                    user.getSession().getAsyncRemote().sendText(array.toString());

                }
                System.out.println(" ======== User: " + user.getName() + " login ============= ");
                Sessions.instance().add(session, user);
            } else if (msg.getType() == -1) {

                if (!msg.getMessage().equals("giaynhap")) {
                    return;
                }

                if (user == null) {
                    user = new User();
                   
                    user.setName("Admin");
                
                   

                    System.out.println(" ======== Admin login ============= ");

                    //  
                }
                 user.setThreadKey(session);
                 user.setSession(msg.getSession());
                 user.setIsAdmin(true);
                user.getSession().getAsyncRemote().sendText(getListSession(Sessions.instance().list()).toString());
                Sessions.instance().add(session, user);

            }
            else if(msg.getType() == -2)
            {
                
                System.out.println("user exit");
                Sessions.instance().delete(session);
                Messages.instance().get(session).clear();
                HashMap<String, User> list = Sessions.instance().list();
            
                  for ( Map.Entry<String,User> val : list.entrySet())
                             if (val.getValue().isAdmin())
                              val.getValue().getSession().getAsyncRemote().sendText(getListSession(Sessions.instance().list()).toString());

            
            }

        }

    }

    public JsonArray getListSession(HashMap<String, User> list) {
         
        JsonArrayBuilder jsonArray = Json.createArrayBuilder();
        for (Map.Entry m : list.entrySet()) {
            User user = (User) m.getValue();
            if (user.isAdmin()) {
                continue;
            }
            ArrayList arr = Messages.instance().get(user.getThreadKey());
            if (arr.size()<1) continue;
            JsonObject obj = (JsonObject) arr.get(arr.size() - 1);
            JsonObject value = Json.createObjectBuilder()
                    .add("name", user.getName())
                    .add("threadkey", user.getThreadKey())
                    .add("msg", obj.toString())
                    .add("last", user.getLast())
                    .build();
            jsonArray.add(value);
        }
        
        JsonArray json= jsonArray.build();
       
          
         return json;
    }

    public JsonArray createJsonArray(List<JsonObject> list) {
        JsonArrayBuilder jsonArray = Json.createArrayBuilder();
        for (JsonObject msg : list) {
            jsonArray.add(msg);
        }
        return jsonArray.build();
    }
     
}
