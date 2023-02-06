package Models;

import java.util.*;

public class ChatHome {

    private int ownerId;
    private ArrayList<Chatroom> chatroomArrayList;

    public ChatHome(int ownerId) {
        this.ownerId = ownerId;
        this.chatroomArrayList = new ArrayList<>();
    }

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    public ArrayList<Chatroom> getChatroomArrayList() {
        return chatroomArrayList;
    }

    public void setChatroomArrayList(ArrayList<Chatroom> chatroomArrayList) {
        this.chatroomArrayList = chatroomArrayList;
    }

    public void addChatroom(Chatroom chatroom) {
        this.chatroomArrayList.add(chatroom);
    }

}
