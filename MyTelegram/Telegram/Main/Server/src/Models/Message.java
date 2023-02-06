package Models;

import Controller.*;

import java.io.*;
import java.time.*;
import java.util.*;

public class Message {

    private static int MESSAGE_NUMBER = 0;
    private static ArrayList<Message> MESSAGES = new ArrayList<>();
    private int messageId;
    private String text;
    private int senderId;
    private Chatroom chatroom;
    private DateTime dateTimeSent;
    private ArrayList<Reaction> reactionArrayList;
    private ArrayList<Message> replies;
    private boolean isReplied;
    private int repliedMessageId;

    public Message(String text, int senderId, Chatroom chatroom) {
        this.text = text;
        this.senderId = senderId;
        this.chatroom = chatroom;
        this.dateTimeSent = new DateTime(ZonedDateTime.now().getYear(), ZonedDateTime.now().getMonthValue(), ZonedDateTime.now().getDayOfMonth(), ZonedDateTime.now().getHour(), ZonedDateTime.now().getMinute(), ZonedDateTime.now().getSecond());
        this.reactionArrayList = new ArrayList<Reaction>();
        this.replies = new ArrayList<Message>();
        MESSAGE_NUMBER++;
        this.messageId = 1000000 + MESSAGE_NUMBER;
        MESSAGES.add(this);
        this.isReplied = false;
        this.repliedMessageId = -1;
    }

    public Message(String text, User sender, Chatroom chatroom) {
        this.text = text;
        this.senderId = sender.getId();
        this.chatroom = chatroom;
        this.dateTimeSent = new DateTime(ZonedDateTime.now().getYear(), ZonedDateTime.now().getMonthValue(), ZonedDateTime.now().getDayOfMonth(), ZonedDateTime.now().getHour(), ZonedDateTime.now().getMinute(), ZonedDateTime.now().getSecond());
        this.reactionArrayList = new ArrayList<Reaction>();
        this.replies = new ArrayList<Message>();
        MESSAGE_NUMBER++;
        this.messageId = 1000000 + MESSAGE_NUMBER;
        MESSAGES.add(this);
        this.isReplied = false;
        this.repliedMessageId = -1;
    }

    public Message (String text, Chatroom chatroom) {
        this.text = text;
        this.senderId = 20230000; //define a default id for server
        this.chatroom= chatroom;
        this.dateTimeSent = new DateTime(ZonedDateTime.now().getYear(), ZonedDateTime.now().getMonthValue(), ZonedDateTime.now().getDayOfMonth(), ZonedDateTime.now().getHour(), ZonedDateTime.now().getMinute(), ZonedDateTime.now().getSecond());
        MESSAGE_NUMBER++;
        this.messageId = 1000000 + MESSAGE_NUMBER;
        MESSAGES.add(this);
        this.isReplied = false;
        this.repliedMessageId = -1;
    }

    public Message (String text, int senderId, Chatroom chatroom, boolean isReplied, int repliedMessageId) {
        this.text = text;
        this.senderId = senderId;
        this.chatroom = chatroom;
        this.dateTimeSent = new DateTime(ZonedDateTime.now().getYear(), ZonedDateTime.now().getMonthValue(), ZonedDateTime.now().getDayOfMonth(), ZonedDateTime.now().getHour(), ZonedDateTime.now().getMinute(), ZonedDateTime.now().getSecond());
        this.reactionArrayList = new ArrayList<Reaction>();
        this.replies = new ArrayList<Message>();
        MESSAGE_NUMBER++;
        this.messageId = 1000000 + MESSAGE_NUMBER;
        MESSAGES.add(this);
        this.isReplied = true;
        this.repliedMessageId = repliedMessageId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getSenderId() {
        return senderId;
    }

    public void setSenderId(int senderId) {
        this.senderId = senderId;
    }

    public Chatroom getChatroom() {
        return chatroom;
    }

    public void setChatroom(Chatroom chatroom) {
        this.chatroom = chatroom;
    }

    public DateTime getDateTimeSent() {
        return dateTimeSent;
    }

    public void setDateTimeSent(DateTime dateTimeSent) {
        this.dateTimeSent = dateTimeSent;
    }

    public ArrayList<Reaction> getReactionArrayList() {
        return reactionArrayList;
    }

    public void setReactionArrayList(ArrayList<Reaction> reactionArrayList) {
        this.reactionArrayList = reactionArrayList;
    }

    public ArrayList<Message> getReplies() {
        return replies;
    }

    public void setReplies(ArrayList<Message> replies) {
        this.replies = replies;
    }

    public void addReaction (Reaction reaction) {
        this.reactionArrayList.add(reaction);
    }

    public boolean isReplied() {
        return isReplied;
    }

    public int getRepliedMessageId() {
        return repliedMessageId;
    }

    public static boolean deleteMessageR (int userId, Message message) {
        try {
            if (message.getSenderId() == userId){
                if (message.getChatroom().getMessageArrayList().contains(message)){
                    message.getChatroom().getMessageArrayList().remove(message);
                }
            }
            return true;
        } catch (NullPointerException nullPointerException){
            nullPointerException.printStackTrace();
            return false;
        }
    }

    public static boolean forwardMessageR (int userId, Message messageToForward, Chatroom toChatroom) {
        User user = returnUser(userId);
        try {
            if (messageToForward.getChatroom().getMessageArrayList().contains(messageToForward)){
                if (user.getChathome().getChatroomArrayList().contains(toChatroom)){
                    toChatroom.addMessage(messageToForward);
                }
            }
            return true;
        } catch (NullPointerException nullPointerException){
            nullPointerException.printStackTrace();
            return false;
        }
    }

    public static boolean reactOnMessageR (Message message, Reaction reaction, Chatroom chatroom) {
        try {
            if (chatroom.getMessageArrayList().contains(message)){
                message.addReaction(reaction);
            }
            return true;
        } catch (NullPointerException nullPointerException){
            nullPointerException.printStackTrace();
            return false;
        }
    }

    public static boolean replyOnMessageR (int userId, Message messageToReplyOn, Message replyMessage) {
        try {
            Chatroom chatroom = messageToReplyOn.getChatroom();
            if (chatroom.getMessageArrayList().contains(messageToReplyOn) && chatroom.getAccessedMembersId().contains(userId)){
                messageToReplyOn.getReplies().add(replyMessage);
                chatroom.getMessageArrayList().add(replyMessage);
            }
            return true;
        } catch (NullPointerException nullPointerException){
            nullPointerException.printStackTrace();
            return false;
        }
    }

    public static boolean saveMessageR (User user, Message message) {
        try {
            if (user.getChathome().getChatroomArrayList().contains(message.getChatroom())){
                user.getSavedMessages().add(message);
            }
            return true;
        } catch (NullPointerException nullPointerException){
            nullPointerException.printStackTrace();
            return false;
        }
    }

    public static boolean sendMessageR (Message message){
        try {
            if (message.getChatroom().getAccessedMembersId().contains(message.getSenderId())){
                message.getChatroom().getMessageArrayList().add(message);
            }
            return true;
        } catch (NullPointerException nullPointerException){
            nullPointerException.printStackTrace();
            return false;
        }
    }

    public boolean editMessageR (int userId, String newMessage, Message messageToEdit, Chatroom chatroom) {
        try {
            if (chatroom.getMessageArrayList().contains(messageToEdit)){
                if (messageToEdit.getSenderId() == userId){
                    messageToEdit.setText(newMessage);
                }
            }
            return true;
        } catch (NullPointerException nullPointerException){
            nullPointerException.printStackTrace();
            return false;
        }
    }

    public static int getMessageNumber() {
        return MESSAGE_NUMBER;
    }

    public static ArrayList<Message> getMESSAGES() {
        return MESSAGES;
    }

    public int getMessageId() {
        return messageId;
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }

    private static User returnUser (int id) {
        for (User user : User.getUSERS()) {
            if (user.getId() == id){
                return user;
            }
        }
        return null;
    }

    public void showMessage (DataOutputStream outputStream) throws IOException {
        User user = returnUser(this.senderId);
        if (this.isReplied) {
            RequestHandler.connectionV(user.getUsername() + ": " + this.getText() + "          "
                    + this.getDateTimeSent().toString() + "   " + "replied on: " + this.getRepliedMessageId());
        } else {
            RequestHandler.connectionV(user.getUsername() + ": " + this.getText() + "          "
                    + this.getDateTimeSent().toString());
        }
    }

    public static Message returnMessage (int id) {
        for (Message mes : MESSAGES) {
            if (mes.getMessageId() == id) {
                return mes;
            }
        }
        return null;
    }
}
