package edu.northeastern.ccs.im.database;

import edu.northeastern.ccs.im.Message;
import edu.northeastern.ccs.im.group.Group;

/**
 * The type Model factory.
 */
public class ModelFactory {

    private static MysqlCon mysqlCon;
    private static DataCon conn = mysqlCon.getInstance();

    /**
     * Get user model user model.
     *
     * @return the user model
     */
    public static UserModel getUserModel(){
        return new UserModel(conn);
    }

    /**
     * Get conversation model conversation model.
     *
     * @return the conversation model
     */
    public static ConversationModel getConversationModel(){
        return new ConversationModel(conn);
    }

    /**
     * Get group model group model.
     *
     * @return the group model
     */
    public static GroupModel getGroupModel(){
        return new GroupModel(conn);
    }

    /**
     * Get message model message model.
     *
     * @return the message model
     */
    public static MessageModel getMessageModel(){
        return new MessageModel(conn);
    }
}
