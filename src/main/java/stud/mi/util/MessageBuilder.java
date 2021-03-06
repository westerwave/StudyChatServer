package stud.mi.util;

import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import stud.mi.message.Message;
import stud.mi.message.MessageType;
import stud.mi.server.ChatServer;
import stud.mi.server.channel.Channel;
import stud.mi.server.user.RemoteUser;

public final class MessageBuilder
{
    private static final String CHANNEL_NAME = "channelName";
    private static final String USER_ID = "userID";
    private static final String SUCCESS = "success";
    private static final String VERSION = "version";
    private static final String TYPE = "type";
    private static final String CONTENT = "content";
    private static final String MESSAGE = "message";
    private static final String USER_NAME = "userName";
    private static final String CHANNEL_USER_NAMES = "channelUserNames";
    private static final String CHANNEL_NAMES = "channelNames";
    private static final String CHANNEL_HISTORY = "channelHistory";

    private MessageBuilder()
    {
    }

    public static Message buildChannelHistoryMessage(final JsonArray msgArray, final Channel channel)
    {
        final Message msg = buildMessageBase(MessageType.CHANNEL_HISTORY);
        msg.getContent().add(CHANNEL_HISTORY, msgArray);
        msg.getContent().addProperty(CHANNEL_NAME, channel.getName());
        return msg;
    }

    public static Message buildUserChangeMessage(final List<RemoteUser> users, final Channel channel)
    {
        final Message msg = buildMessageBase(MessageType.CHANNEL_USER_CHANGE);
        final JsonArray userNameArray = new JsonArray();
        for (final RemoteUser user : users)
        {
            userNameArray.add(user.getName());
        }
        msg.getContent().add(CHANNEL_USER_NAMES, userNameArray);
        msg.getContent().addProperty(CHANNEL_NAME, channel.getName());
        return msg;
    }

    public static Message buildChannelChangeMessage(final List<Channel> channels)
    {
        final Message msg = buildMessageBase(MessageType.CHANNEL_CHANGE);
        final JsonArray channelNameArray = new JsonArray();
        for (final Channel channel : channels)
        {
            channelNameArray.add(channel.getName());
        }
        msg.getContent().add(CHANNEL_NAMES, channelNameArray);
        return msg;
    }

    public static Message buildAckUserJoinChannel(final Long userID, final Channel channel)
    {
        final Message msg = buildMessageBase(MessageType.ACK_CHANNEL_JOIN);
        msg.getContent().addProperty(USER_ID, userID);
        msg.getContent().addProperty(CHANNEL_NAME, channel.getName());
        return msg;
    }

    public static Message buildSendUserID(final Long userID)
    {
        final Message msg = buildMessageBase(MessageType.USER_JOIN);
        msg.getContent().addProperty(USER_ID, userID);
        return msg;
    }

    public static Message buildMessagePropagateAnswer(final String message, final String senderName)
    {
        final Message msg = buildMessageBase(MessageType.CHANNEL_MESSAGE);
        msg.getContent().addProperty(MESSAGE, message);
        msg.getContent().addProperty(USER_NAME, senderName);
        return msg;
    }

    public static Message buildChannelJoinAnswer(final String channelName, final boolean success)
    {
        final Message msg = buildMessageBase(MessageType.CHANNEL_JOIN);
        msg.getContent().addProperty(CHANNEL_NAME, channelName);
        msg.getContent().addProperty(SUCCESS, success);
        return msg;
    }

    public static Message buildChannelExitAnswer(final String channelName, final boolean success)
    {
        final Message msg = buildMessageBase(MessageType.CHANNEL_EXIT);
        msg.getContent().addProperty(CHANNEL_NAME, channelName);
        msg.getContent().addProperty(SUCCESS, success);
        return msg;
    }

    public static Message buildSendMessageAnswer(final String channelName, final boolean success)
    {
        final Message msg = buildMessageBase(MessageType.CHANNEL_MESSAGE_REPLY);
        msg.getContent().addProperty(CHANNEL_NAME, channelName);
        msg.getContent().addProperty(SUCCESS, success);
        return msg;
    }

    public static Message buildMessageBase(final String type)
    {
        final JsonObject jo = new JsonObject();
        jo.addProperty(VERSION, ChatServer.PROTOCOL_VERSION);
        jo.addProperty(TYPE, type);
        jo.add(CONTENT, new JsonObject());
        return new Message(jo);
    }
}
