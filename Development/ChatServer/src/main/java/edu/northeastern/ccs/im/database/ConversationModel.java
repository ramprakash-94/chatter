package edu.northeastern.ccs.im.database;

import edu.northeastern.ccs.im.ChatLogger;

import java.util.List;
import java.util.Map;


/**
 * The type Conversation model.
 */
public class ConversationModel {
    /**
     * The Mysql con.
     */
    DataCon conn;

    /**
     * Instantiates a new Conversation db.
     *
     * @param dataConnection the data connection
     */
    public ConversationModel(DataCon dataConnection){
        conn = dataConnection.getInstance();
    }

    /**
     * Create conversation for user int.
     *
     * @param userid1 the userid 1
     * @param userid2 the userid 2
     * @return the int (1 if conversation is created, -1 otherwise)
     */
    public int createConversationForUser(int userid1,int userid2){
        int conversationId = getUserUserConversation(userid1,userid2);
        if(conversationId<0){
            String createConvQuery = "SELECT user_user_conversation("+userid1+","+userid2+") as conversations_id";
            List<Map<String, Object>> r = conn.sqlGet(createConvQuery);
            if(!r.isEmpty()){
                conversationId = (int) r.get(0).get("conversations_id");
            }else {
                conversationId = -1;
            }
        }
        return conversationId;
    }

    /**
     *
     * @param id1
     * @param id2
     * @return int id of the conversation
     */
    private int getUserUserConversation(int id1,int id2) {
        String query = "select conversations_id from users_converses_users where (users_id="+id1+" and users_id1="+id2+") or ( users_id="+id2+" and users_id1="+id1+")";

        List<Map<String, Object>> rs = conn.sqlGet(query);
        if(!rs.isEmpty()){
            return (int) rs.get(0).get("Conversations_id");
        }else {
            return -1;
        }
    }


    /**
     * Create thread for conversation int.
     *
     * @param conversationId the conversation id
     * @return the int (1 if thread is created, -1 o.w.)
     */
    public int createThreadForConversation(int conversationId){
        String query = "INSERT INTO thread(conversations_id) VALUES ("+conversationId+")";
        int r = conn.sqlcreate(query);
        if(r>0){
            return conn.getLastInsertedID();
        }
        return -1;
    }


    /**
     * Create thread for conversation by thread id int.
     *
     * @param threadId       the thread id
     * @param conversationId the conversation id
     * @return the int
     */
    public int createThreadForConversationByThreadID(int threadId, int conversationId){
    String query = "INSERT INTO thread(id,conversations_id) VALUES ("+threadId+","+conversationId+")";
    int r = conn.sqlcreate(query);
    if(r>0){
      return conn.getLastInsertedID();
    }
    return -1;
  }


    /**
     * Get threads for conversation list.
     *
     * @param conversationId the conversation id
     * @return the list of threads
     */
    public List<Map<String,Object>> getThreadsForConversation(int conversationId){
        String query = "SELECT * FROM thread WHERE conversations_id='" + conversationId + "';";
        return conn.sqlGet(query);
    }

    /**
     * Create message for thread int.
     *
     * @param threadId the thread id
     * @param senderId the sender id
     * @param text     the text
     * @return the int
     */
    public int createMessageForThread(int threadId, int senderId, String text){
        String query = "INSERT INTO message(sender_id,thread_id,text) VALUES ("+senderId+","+threadId+",'"+text+"');";
        int r = conn.sqlcreate(query);
        if(r>0){
            return conn.getLastInsertedID();
        }
        return -1;
    }

    /**
     * Add message to thread int.
     *
     * @param messageID the message id
     * @param threadId  the thread id
     * @return the int
     */
    public int addMessageToThread(int messageID, int threadId){
      String query = "UPDATE message SET thread_id = "+threadId+" WHERE id="+messageID+";";
      int r = conn.sqlcreate(query);
      if(r>0){
        return conn.getLastInsertedID();
      }
      return -1;
    }

    /**
     * Get messages for conversation list.
     *
     * @param conversationId the conversation id
     * @return the list of messages inside a conversation
     */
    public List<Map<String,Object>> getMessagesForConversation(int conversationId){
            String query = "CALL message_in_conversation("+conversationId+");";
        return conn.sqlGet(query);
    }

    /**
     * Get conversations list
     *
     * @return the list of conversations
     */
    public List<Map<String, Object>> getConversations(){
        String sql = "SELECT * FROM conversations";
        return conn.sqlGet(sql);
    }

    /**
     * Get users in a conversation.
     *
     * @param conversationID the conversation id
     * @return list of users in the conversation
     */
    public List<Map<String, Object>> getUsersInConversation(int conversationID){
      String sql = "SELECT * FROM users as u JOIN users_converses_users as uu on u.id = uu.Users_id or u.id = Users_id1 JOIN conversations as c on c.id = uu.Conversations_id WHERE c.id = "+conversationID+" group by u.id;";
      return conn.sqlGet(sql);
    }


    /**
     * Get messages in a thread.
     *
     * @param threadID the thread id
     * @return list of messages in the thread
     */
    public List<Map<String, Object>> getMessagesInThread(int threadID){
    String sql = "select * from message JOIN users on message.sender_id = users.id where thread_id = '"+threadID + "';";
    return conn.sqlGet(sql);
  }

    /**
     * retrieves a list of conversations with the given id
     *
     * @param id the id of the conversation(s) being searched for
     * @return the list of conversations with that id
     */
    public List<Map<String, Object>> getConversationsById(int id){
        String sql = "SELECT * FROM conversations where id='"+id+"' AND deleted=0";
        return conn.sqlGet(sql);
    }

    /**
     * Get conversations list.
     *
     * @param userId the user id
     * @return the list
     */
    public List<Map<String, Object>> getConversations(int userId){
        String sql = "SELECT c.id as id, c.created_on,\n" +
                "\t\t\t\tCASE WHEN uu.users_id != "+userId+" THEN uu.users_id1 ELSE uu.users_id END as destination_id,\n" +
                "\t\t\t\tCASE WHEN uu.users_id != "+userId+" THEN u1.username ELSE u2.username END as destination_username,\n" +
                "\t\t\t\tCASE WHEN uu.users_id != "+userId+" THEN u1.first_name ELSE u2.first_name END as destination_firstname,\n" +
                "\t\t\t\tCASE WHEN uu.users_id != "+userId+" THEN u1.last_name ELSE u2.last_name END as destination_lastname\n" +
                "\n" +
                "FROM conversations as c JOIN users_converses_users as uu on c.id = uu.Conversations_id \n" +
                "\t\t\tJOIN users as u1 on users_id = u1.id \n" +
                "            JOIN users as u2 on users_id1 = u2.id\n" +
                "WHERE uu.users_id="+userId+" OR uu.users_id1="+userId+";";

        ChatLogger.info(sql);
        return conn.sqlGet(sql);
    }

    /**
     * Get thread list.
     *
     * @param threadId the thread id
     * @return the list
     */
    public List<Map<String,Object>> getThread(int threadId){
        String sql = "SELECT * FROM thread WHERE id=" +threadId+";";
        ChatLogger.info(sql);
        return conn.sqlGet(sql);
    }

    /**
     * Get last inserted conversation id
     *
     * @return last inserted conversation id
     */
    public int getLastInsertedID() {
        return conn.getLastInsertedID();
    }

    public List<Map<String, Object>> getGroupConversations(int userId) {
        String sql = "SELECT Groups_id as groupid, u.id as user_id, first_name,last_name, conversation_id, name as destination_name, username, c.created_on\n" +
                "FROM groups_has_users as gu JOIN \n" +
                "\t\t\tgroups as g on gu.Groups_id = g.id JOIN \n" +
                "            conversations as c on g.conversation_id = c.id JOIN\n" +
                "            users as u on u.id=gu.Users_id\n" +
                "where Users_id ="+userId+";";

        return conn.sqlGet(sql);
    }

    public List<Map<String, Object>> getConversationGroup(int conversationId) {
        String sql = "SELECT * FROM groups WHERE conversation_id="+conversationId+";";
        return conn.sqlGet(sql);
    }
}
