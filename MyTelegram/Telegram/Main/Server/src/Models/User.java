package Models;

import Controller.*;

import javax.swing.*;
import javax.xml.crypto.*;
import java.io.*;
import java.time.*;
import java.util.*;

public class User {

    private static int USER_NUM;
    private static final ArrayList<Integer> USERS_ID = new ArrayList<>();
    private static final ArrayList<User> USERS = new ArrayList<>();
    private static final ArrayList<Username> USERNAMES = new ArrayList<>();
    private static final ArrayList<PhoneNumber> PHONE_NUMBERS = new ArrayList<>();
    private int id;
    private String firstName;
    private String lastName;
    private Username username;
    private Password password;
    private DateTime birthDate;
    private Email email;
    private PhoneNumber phoneNumber;
    private String bio;
    private boolean isActive;
    private ArrayList<Integer> blockedUsersId;
    private ArrayList<Chatroom> groups;
    private ArrayList<Chatroom> PV;
    private ArrayList<Chatroom> channels;
    private ChatHome chathome;
    private ArrayList<Message> savedMessages;
    private ArrayList<PhoneNumber> contacts;
    private ArrayList<Integer> friendsId;
    private boolean disabled;
    private boolean deleted;

    private boolean isLoggedIn;

    public User(String firstName, String lastName,DateTime birthDate, String bio, Password password, PhoneNumber phoneNumber) {
        USER_NUM++;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.bio = bio;
        this.id = generateId();///
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.isActive = true;
        this.blockedUsersId = new ArrayList<>();
        this.groups = new ArrayList<>();
        this.PV = new ArrayList<>();
        this.channels = new ArrayList<>();
        this.chathome = new ChatHome(id);
        this.savedMessages = new ArrayList<>();
        this.contacts = new ArrayList<>();
        this.friendsId = new ArrayList<>();
        this.disabled = false;
        this.deleted = false;
        this.isLoggedIn = true;
//        USERS.add(this);
//        USERS_ID.add(this.id);
//        USERNAMES.add(this.username);
//        PHONE_NUMBERS.add(this.phoneNumber);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Username getUsername() {
        return username;
    }

    public void setUsername(Username username) {
        this.username = username;
    }

    public Password getPassword() {
        return password;
    }

    public void setPassword(Password password) {
        this.password = password;
    }

    public DateTime getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(DateTime birthDate) {
        this.birthDate = birthDate;
    }

    public Email getEmail() {
        return email;
    }

    public void setEmail(Email email) {
        this.email = email;
    }

    public PhoneNumber getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(PhoneNumber phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public ArrayList<Integer> getBlockedUsersId() {
        return blockedUsersId;
    }

    public void setBlockedUsersId(ArrayList<Integer> blockedUsersId) {
        this.blockedUsersId = blockedUsersId;
    }

    public ArrayList<Chatroom> getGroups() {
        return groups;
    }

    public void setGroups(ArrayList<Chatroom> groups) {
        this.groups = groups;
    }

    public ArrayList<Chatroom> getPV() {
        return PV;
    }

    public void setPV(ArrayList<Chatroom> PV) {
        this.PV = PV;
    }

    public ArrayList<Chatroom> getChannels() {
        return channels;
    }

    public void setChannels(ArrayList<Chatroom> channels) {
        this.channels = channels;
    }

    public ChatHome getChathome() {
        return chathome;
    }

    public void setChathome(ChatHome chathome) {
        this.chathome = chathome;
    }

    public ArrayList<Message> getSavedMessages() {
        return savedMessages;
    }

    public void setSavedMessages(ArrayList<Message> savedMessages) {
        this.savedMessages = savedMessages;
    }

    public ArrayList<PhoneNumber> getContacts() {
        return contacts;
    }

    public void setContacts(ArrayList<PhoneNumber> contacts) {
        this.contacts = contacts;
    }

    public ArrayList<Integer> getFriendsId() {
        return friendsId;
    }

    public void setFriendsId(ArrayList<Integer> friends) {
        this.friendsId = friends;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    private int generateId() {
        return ZonedDateTime.now().getYear()*10000 + USER_NUM;
    }

    public void addToChannels (Chatroom chatroom) {
        this.channels.add(chatroom);
    }

    public void addToGroups (Chatroom chatroom) {
        this.groups.add(chatroom);
    }

    public void addToPVs (Chatroom chatroom) {
        this.PV.add(chatroom);
    }

    public void addToBlockedUsers (User user) {
        this.blockedUsersId.add(user.getId());
    }

    public void addToBlockedUsers (int userId) {
        this.blockedUsersId.add(userId);
    }

    public void addToSavedMessages (Message message) {
        this.savedMessages.add(message);
    }

    public void addToFriends (User user) {
        this.friendsId.add(user.getId());
    }

    public void addToFriends (int friendId) {
        this.friendsId.add(friendId);
    }

    public void addToContact (PhoneNumber phoneNumber) {
        this.contacts.add(phoneNumber);
    }

    public static ArrayList<User> getUSERS () {
        return USERS;
    }

    public static ArrayList<Integer> getUsersId () {
        return USERS_ID;
    }

    public static ArrayList<Username> getUSERNAMES () {
        return USERNAMES;
    }

    public static boolean hasUser(User user) {
        for(int i = 0 ; i < USERS.size() ; i ++){
            if( USERS.get( i ).equals( user ) )return true ;
        }
        return false ;
    }

    public static boolean addUser(User user) {
        if(hasUser(user))return false;
        USERS.add( user ) ;
        return true;
    }

    public static boolean hasUsername(Username username) {
        for(int i = 0 ; i < USERNAMES.size() ; i ++){
            if( USERNAMES.get( i ).equals( username ) )return true ;
        }
        return false ;
    }

    public static boolean addUsername(Username username) {
        if(hasUsername(username))return false;
        USERNAMES.add( username ) ;
        return true;
    }

    public static boolean hasPhoneNumber(PhoneNumber phoneNumber) {
        for(int i = 0 ; i < PHONE_NUMBERS.size() ; i ++){
            if( PHONE_NUMBERS.get( i ).equals( phoneNumber ) )return true ;
        }
        return false ;
    }

    public static boolean addPhoneNumber(PhoneNumber phoneNumber) {
        if(hasPhoneNumber(phoneNumber))return false;
        PHONE_NUMBERS.add( phoneNumber ) ;
        return true;
    }

    public static ArrayList<PhoneNumber> getPhoneNumbers() {
        return PHONE_NUMBERS;
    }

    public boolean isLoggedIn() {
        return isLoggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        isLoggedIn = loggedIn;
    }

    public boolean addToContactR (int userId, PhoneNumber phoneNumber) {
        User user = returnUser(userId);
        try {
            for (User u: User.getUSERS()) {
                if (u.phoneNumber.equals(phoneNumber)){
                    user.addToContact(phoneNumber);
                }
                return true;
            }
            return false;
        } catch (NullPointerException nullPointerException) {
            nullPointerException.printStackTrace();
            return false;
        }
    }

    public boolean removeFromContactR (int userId, PhoneNumber phoneNumber) {
        User user = returnUser(userId);
        try {
            for (User u: User.getUSERS()) {
                if (u.phoneNumber.equals(phoneNumber)){
                    user.getContacts().remove(phoneNumber);
                }
                return true;
            }
            return false;
        } catch (NullPointerException nullPointerException) {
            nullPointerException.printStackTrace();
            return false;
        }
    }

    public boolean blockUserR (int userId, int userToBlock) {
        User user = returnUser(userId);
        try {
            if (User.getUsersId().contains(userToBlock)) {
                if (!user.getBlockedUsersId().contains(userToBlock)) {
                    user.getBlockedUsersId().add(userToBlock);
                    return true;
                }
            }
            return false;
        } catch (NullPointerException nullPointerException){
            nullPointerException.printStackTrace();
            return false;
        }
    }

    public boolean unBlockUserR (int userId, int idToUnBlock) {
        User user = returnUser(userId);
        try {
            if (User.getUsersId().contains(idToUnBlock)) {
                if (user.getBlockedUsersId().contains(idToUnBlock)) {
                    user.getBlockedUsersId().remove(idToUnBlock);
                    return true;
                }
            }
            return false;
        } catch (NullPointerException nullPointerException){
            nullPointerException.printStackTrace();
            return false;
        }
    }

    public void showUser (DataOutputStream outputStream) throws IOException{
        RequestHandler.connectionV(this.firstName + " " + this.lastName);
        RequestHandler.connectionV(this.username.getText());
        RequestHandler.connectionV(this.bio);
        RequestHandler.connectionV(this.phoneNumber.toString());
    }

    public void showUserC (DataOutputStream outputStream) throws IOException {
        showUser(outputStream);
        RequestHandler.connectionV(this.email.getText());
        RequestHandler.connectionV(this.birthDate.toString());
        RequestHandler.connectionV(this.password.getText());
        RequestHandler.connectionV("Blocked users:\n");
        for (int id: this.blockedUsersId) {
            RequestHandler.connectionV(returnUser(id).getUsername().getText() + "\n");
        }
        RequestHandler.connectionV("Groups:\n");
        for (Chatroom ch: this.groups) {
            RequestHandler.connectionV(ch.getName() + "\n");
        }
        RequestHandler.connectionV("Channels:\n");
        for (Chatroom ch: this.channels) {
            RequestHandler.connectionV(ch.getName() + "\n");
        }
        RequestHandler.connectionV("PVs:\n");
        for (Chatroom ch: this.PV) {
            RequestHandler.connectionV(ch.getName() + "\n");
        }
        RequestHandler.connectionV("Contacts:\n");
        for (PhoneNumber ph: this.contacts) {
            ph.showPhoneNumber(outputStream);
        }
    }

    public void addToChathome(Chatroom chatroom) {
        this.chathome.addChatroom(chatroom);
    }

    private User returnUser (int id) {
        for (User user : User.getUSERS()) {
            if (user.getId() == id){
                return user;
            }
        }
        return null;
    }

    public static void initialize(){
        User.USERNAMES.add(new Username(-1, "server"));
        User.USERS_ID.add(-1);
        User.PHONE_NUMBERS.add(new PhoneNumber("98", "9351960731"));
        User.USERS.add(new User("ali", "ghaem", new DateTime(1380, 9, 20), "say my name", new Password("aghd"), new PhoneNumber("98", "9301960731")));
    }
    // a code must be added later
}
