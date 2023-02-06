package Models;

import Controller.*;
import com.sun.jdi.event.*;

import java.io.*;
import java.time.*;
import java.util.*;
import java.util.concurrent.*;

public class Chatroom {

    private String name;
    private String description;
    private ChatroomStatus chatroomStatus;
    private ArrayList<Integer> membersId;
    private int ownerId;
    private DateTime dateOfCreation;
    private ArrayList<Integer> accessedMembersId;
    private ArrayList<Integer> admins;
    private ArrayList<Message> messageArrayList;
    private ArrayList<Chatroom> groupsInCommon;
    private ArrayList<Chatroom> channelsInCommon;
    private boolean isPV;

    public Chatroom () {}

    public Chatroom(int ownerId, String name, ChatroomStatus chatroomStatus) {
        this.name = name;
        this.chatroomStatus = chatroomStatus;
        this.ownerId = ownerId;
        this.accessedMembersId = new ArrayList<Integer>();
        accessedMembersId.add(ownerId);
        this.membersId = new ArrayList<Integer>();
        membersId.add(ownerId);
        this.admins = new ArrayList<Integer>();
        admins.add(ownerId);
        this.dateOfCreation = new DateTime(ZonedDateTime.now().getYear(), ZonedDateTime.now().getMonthValue(), ZonedDateTime.now().getDayOfMonth());
        this.messageArrayList = new ArrayList<Message>();
        messageArrayList.add(new Message("Welcome", this));//later
        this.isPV = false;
        this.groupsInCommon = new ArrayList<Chatroom>();
        this.channelsInCommon = new ArrayList<Chatroom>();
        this.description = "nothing yet";
    }

    public Chatroom (User user, String name, ChatroomStatus chatroomStatus) {
        this.name = name;
        this.chatroomStatus = chatroomStatus;
        this.ownerId = user.getId();
        this.accessedMembersId = new ArrayList<Integer>();
        accessedMembersId.add(ownerId);
        this.membersId = new ArrayList<Integer>();
        membersId.add(ownerId);
        this.admins = new ArrayList<Integer>();
        admins.add(ownerId);
        this.dateOfCreation = new DateTime(ZonedDateTime.now().getYear(), ZonedDateTime.now().getMonthValue(), ZonedDateTime.now().getDayOfMonth());;
        this.messageArrayList = new ArrayList<Message>();
        messageArrayList.add(new Message("Welcome", this));//later
        this.isPV = false;
        this.description = "nothing yet";
    }

    public Chatroom (int ownerId, int friendId) {
        this.ownerId = ownerId;
        this.name = "my Pv with" + friendId;
        this.chatroomStatus = ChatroomStatus.PV;
        this.accessedMembersId = new ArrayList<Integer>();
        accessedMembersId.add(ownerId);
        accessedMembersId.add(friendId);
        this.membersId = new ArrayList<Integer>();
        membersId.add(ownerId);
        membersId.add(friendId);
        this.admins = new ArrayList<Integer>();
        admins.add(ownerId);
        admins.add(friendId);
        this.isPV = true;
        this.dateOfCreation = new DateTime(ZonedDateTime.now().getYear(), ZonedDateTime.now().getMonthValue(), ZonedDateTime.now().getDayOfMonth());;
        this.messageArrayList = new ArrayList<Message>();
        messageArrayList.add(new Message("Welcome Both", this));//later
        this.groupsInCommon = new ArrayList<Chatroom>();
        this.channelsInCommon = new ArrayList<Chatroom>();
        this.description = "its a PV";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ChatroomStatus getChatroomStatus() {
        return chatroomStatus;
    }

    public void setChatroomStatus(ChatroomStatus chatroomStatus) {
        this.chatroomStatus = chatroomStatus;
    }

    public ArrayList<Integer> getMembersId() {
        return membersId;
    }

    public void setMembersId(ArrayList<Integer> membersId) {
        this.membersId = membersId;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    public DateTime getDateOfCreation() {
        return dateOfCreation;
    }

    public void setDateOfCreation(DateTime dateOfCreation) {
        this.dateOfCreation = dateOfCreation;
    }

    public ArrayList<Integer> getAccessedMembersId() {
        return accessedMembersId;
    }

    public void setAccessedMembersId(ArrayList<Integer> accessedMembersId) {
        this.accessedMembersId = accessedMembersId;
    }

    public boolean getIsPV () {
        return this.isPV;
    }

    public void setIsPV (boolean isPV) {
        this.isPV = isPV;
    }

    public ArrayList<Message> getMessageArrayList() {
        return messageArrayList;
    }

    public void setMessageArrayList(ArrayList<Message> messageArrayList) {
        this.messageArrayList = messageArrayList;
    }

    public ArrayList<Chatroom> getGroupsInCommon() {
        return groupsInCommon;
    }

    public void setGroupsInCommon(ArrayList<Chatroom> groupsInCommon) {
        this.groupsInCommon = groupsInCommon;
    }

    public ArrayList<Chatroom> getChannelsInCommon() {
        return channelsInCommon;
    }

    public void setChannelsInCommon(ArrayList<Chatroom> channelsInCommon) {
        this.channelsInCommon = channelsInCommon;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription (String description) {
        this.description = description;
    }

    public void setAdmins (ArrayList<Integer> admins) {
        this.admins = admins;
    }

    public ArrayList<Integer> getAdmins() {
        return this.admins;
    }
    public void addMember (int id) {
        this.membersId.add(id);
    }

    public void addAccessedMember (int id) {
        this.accessedMembersId.add(id);
    }

    public void addMessage (Message message) {
        this.messageArrayList.add(message);
    }

    public void addAdmin (int adminId) {
        this.admins.add(adminId);
    }

    public boolean addAdminR (int userId, int userIdToAdd, Chatroom chatroom) {
        try {
            if (chatroom.getOwnerId() == userId) {
                chatroom.addAdmin(userIdToAdd);
            }
            return true;
        } catch (NullPointerException nullPointerException) {
            nullPointerException.printStackTrace();
            return false;
        }
    }

    public boolean addMemberR (int userId, int userIdToAdd, Chatroom chatroom) {
        User user = returnUser(userId);
        try {
            if (user.getChathome().getChatroomArrayList().contains(chatroom) && chatroom.getAdmins().contains(user.getId())) {
                chatroom.addMember(userIdToAdd);
                for (User tempUser: User.getUSERS()) {
                    if (tempUser.getId() == userIdToAdd) {
                        tempUser.getChathome().addChatroom(chatroom);
                        if (chatroom.getChatroomStatus() == ChatroomStatus.CHANNEL) {
                            tempUser.getChannels().add(chatroom);
                        } else if (chatroom.getChatroomStatus() == ChatroomStatus.GROUP) {
                            tempUser.getGroups().add(chatroom);
                            chatroom.getAccessedMembersId().add(tempUser.getId());
                        }
                        break;
                    }
                }
            }
            return true;
        } catch (NullPointerException nullPointerException) {
            nullPointerException.printStackTrace();
            return false;
        }
    }

    public boolean leftChatroomR (int userToLeft, Chatroom chatroom) {
        User user = returnUser(userToLeft);
        try {
            if (chatroom.getOwnerId() == userToLeft) {
                chatroom.getMembersId().remove(userToLeft);
                user.getChathome().getChatroomArrayList().remove(chatroom);
                if (chatroom.getChatroomStatus() == ChatroomStatus.CHANNEL) {
                    user.getChannels().remove(chatroom);
                    return true;
                } else if (chatroom.getChatroomStatus() == ChatroomStatus.GROUP) {
                    user.getGroups().remove(chatroom);
                    return true;
                }
                if (chatroom.getAccessedMembersId().contains(userToLeft)){
                    chatroom.getAccessedMembersId().remove(userToLeft);
                    return true;
                }
                if (chatroom.getAdmins().contains(userToLeft)){
                    chatroom.getAdmins().remove(userToLeft);
                    return true;
                }
            }
            return false;
        } catch (NullPointerException nullPointerException) {
            nullPointerException.printStackTrace();
            return false;
        }
    }

    public boolean removeAdminR (int userId, int userToRemove, Chatroom chatroom) {
        try {
            if (chatroom.getOwnerId() == userId) {
                chatroom.getAdmins().remove(userToRemove);
            }
            return true;
        } catch (NullPointerException nullPointerException) {
            nullPointerException.printStackTrace();
            return false;
        }
    }

    public boolean removeMemberR (int userId, int userToRemove, Chatroom chatroom){
        try {
            if (chatroom.getAdmins().contains(userId) && !chatroom.getAdmins().contains(userToRemove) && chatroom.getOwnerId() != userId){
                chatroom.getMembersId().remove(userToRemove);
                for (User tempUser: User.getUSERS()) {
                    if (tempUser.getId() == userToRemove) {
                        tempUser.getChathome().getChatroomArrayList().remove(chatroom);
                        if (chatroom.getChatroomStatus() == ChatroomStatus.CHANNEL) {
                            tempUser.getChannels().remove(chatroom);
                        } else {
                            tempUser.getGroups().remove(chatroom);
                        }
                    }
                }
            }
            if (chatroom.getOwnerId() == userId) {
                chatroom.getMembersId().remove(userToRemove);
                for (User tempUser: User.getUSERS()) {
                    if (tempUser.getId() == userToRemove) {
                        tempUser.getChathome().getChatroomArrayList().remove(chatroom);
                        if (chatroom.getChatroomStatus() == ChatroomStatus.CHANNEL) {
                            tempUser.getChannels().remove(chatroom);
                        } else {
                            tempUser.getGroups().remove(chatroom);
                        }
                    }
                }
            }
            return true;
        } catch (NullPointerException nullPointerException){
            nullPointerException.printStackTrace();
            return false;
        }
    }
    public boolean editChatroomNameR (int userId, String name, Chatroom chatroom) {
        try {
            if (chatroom.getAdmins().contains(userId)) {
                chatroom.setName(name);
            }
            return true;
        } catch (NullPointerException nullPointerException) {
            nullPointerException.printStackTrace();
            return false;
        }
    }

    public boolean editDescriptionR (int userId, String newDescription, Chatroom chatroom) {
        try {
            if (chatroom.getAdmins().contains(userId)){
                chatroom.setDescription(newDescription);
            }
            return true;
        } catch (NullPointerException nullPointerException){
            nullPointerException.printStackTrace();
            return false;
        }
    }

    public void showChatroom (DataOutputStream outputStream) throws IOException {
        RequestHandler.connectionV("Name: " + this.name);
        RequestHandler.connectionV("Chatroom status: " + this.chatroomStatus);
        RequestHandler.connectionV("owner: " + returnUser(this.ownerId).getUsername());
        RequestHandler.connectionV("Date of creation: " + this.dateOfCreation);
        RequestHandler.connectionV("Description: " + this.description);
        RequestHandler.connectionV("Admins:");
        for (int id: this.admins) {
            RequestHandler.connectionV(returnUser(id).getUsername().getText());
        }
        outputStream.writeUTF("Members:");
        for (int id: this.membersId) {
            RequestHandler.connectionV(returnUser(id).getUsername().getText());
        }
    }

    public void showChatroomM (DataOutputStream outputStream) throws IOException {
        for (Message m: this.getMessageArrayList()) {
            m.showMessage(outputStream);
        }
    }

    private User returnUser (int id) {
        for (User user : User.getUSERS()) {
            if (user.getId() == id){
                return user;
            }
        }
        return null;
    }
}
