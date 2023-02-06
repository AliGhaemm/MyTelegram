package Controller;

import Models.*;
import java.io.*;
import java.net.*;
import java.util.*;

public class RequestHandler {

    private MainController mainController;
    private Socket socket;
    private static DataInputStream inputStream;
    private static DataOutputStream outputStream;
    private int id;
    private boolean mustDie;


    public RequestHandler (MainController mainController, Socket socket) throws IOException{
        this.mainController = mainController;
        this.socket = socket;
        this.inputStream = new DataInputStream(socket.getInputStream());
        this.outputStream = new DataOutputStream(socket.getOutputStream());
        this.id = -1;
        this.mustDie = false;
    }

    public MainController getMainController() {
        return mainController;
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public DataInputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(DataInputStream inputStream) {
        this.inputStream = inputStream;
    }

    public DataOutputStream getOutputStream() {
        return outputStream;
    }

    public void setOutputStream(DataOutputStream outputStream) {
        this.outputStream = outputStream;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private String[] interpretRequest (Request request) {
        String[] s = new String[2];
        s[0] = request.getRequest().split(":")[0].trim();
        s[1] = "";
        String[] s1 = request.getRequest().split(":")[1].trim().split(" ");
        for (String value : s1) {
            s[1] += value;
        }
        return s;
    }

    private String[] interpret (String str) {
        String[] s = new String[2];
        s[0] = str.split(":")[0].trim();
        s[1] = "";
        String[] s1 = str.split(":")[1].trim().split(" ");
        for (String val : s1) {
            s[1] += val;
        }
        return s;
    }

//    private Event requestDeclare (Request request) {
//
//        String[] messageEvent = {"deletemessage", "forwardmessage", "reactonmessage", "replyonmessage", "savemessage", "sendmessage"};
//        String[] messageEvent2 = {"delete", "forward", "react", "reply", "save", "send"};
//        String[] seeEvent = {"chatroom", "contacts", "messagereactions", "messagereplies", "myinfo", "sender", "userinfo"};
//        String[] otherEvent = {"addtocontact", "blockuser", "removefromcontact", "reportuser", "searchforuser", "unblock"};
//        String[] otherEvent2 = {"addcontact", "block", "removecontact", "report", "search", "unblock"};
//    }

    public void requestHandling (Request request) throws IOException {
        String[] interpretedRequest = interpretRequest(request);
        switch (interpretedRequest[0]) {
            case "-r", "-request" -> otherEventHandling(interpretedRequest[1], request.getRequestOwner());
            case "-e", "-edit" -> editEventHandling(interpretedRequest[1], request.getRequestOwner());
            case "-c" -> chatroomEventHandling(interpretedRequest[1], request.getRequestOwner());
            case "-m", "-message" -> messagingEventHandling(interpretedRequest[1], request.getRequestOwner());
            case "-s", "-see" -> seeEventHandling(interpretedRequest[1], request.getRequestOwner());
            case "-u" -> serverEvent(interpretedRequest[1]);
        }
    }

    private void otherEventHandling (String order, int id) {

    }

    private void editEventHandling (String order, int id) throws IOException {
        User user = returnUser(id);
        switch (order) {
            case "editbio":
            case "bio":
                String newBio = connectionS("Insert new bio text:");
                user.setBio(newBio);
                connectionV("Bio updated");
                break;
            case "editbirthdate":
            case "birthdate":
                int year = connectionI("Insert year:");
                int month = connectionI("Insert month:");
                int day = connectionI("Insert day:");
                user.setBirthDate(new DateTime(year, month, day));
                connectionV("Birth Date updated successfully");
                break;
            case "editchatroomname":
            case "chatroomname":
                boolean isDone = false;
                String chatroomName = connectionS("Insert name of the chatroom:");
                for (Chatroom chatroom: user.getChathome().getChatroomArrayList()) {
                    if (chatroom.getName().equals(chatroomName)){
                        String newName = connectionS("Insert new name:");
                        isDone = chatroom.editChatroomNameR(id, newName, chatroom);
                    }
                }
                if (isDone) {
                    connectionV("Name updated successfully");
                } else {
                    connectionV("Name did not updated due to a reason");
                }
                break;
            case "editdescription":
            case "description":
                isDone = false;
                chatroomName = connectionS("Insert chatroom name:");
                for (Chatroom chatroom: user.getChathome().getChatroomArrayList()) {
                    if (chatroom.getName().equals(chatroomName)){
                        String newName = connectionS("Insert new name:");
                        isDone = chatroom.editDescriptionR(id, newName, chatroom);
                    }
                }
                if (isDone) {
                    connectionV("Description updated successfully");
                } else {
                    connectionV("Description did not updated due to a reason");
                }
                break;
            case "editemail":
            case "email":
                String newEmail = connectionS("Insert new email:");
                user.setEmail(new Email(user, newEmail));
                connectionV("Email Updated successfully");
                break;
            case "editmessage":
            case "message":

                break;
            case "editname":
            case "name":
                String fName = connectionS("Insert first name:");
                String lName = connectionS("Insert last name:");
                user.setFirstName(fName);
                user.setLastName(lName);
                connectionV("Name updated successfully");
                break;
            case "editpassword":
            case "password":
                String newPass = connectionS("Insert new password:");
                user.setPassword(new Password(newPass));
                connectionV("Password updated successfully");
                break;
            case "editusername":
            case "username":
                String newUsername = connectionS("Insert username:");
                user.setUsername(new Username(user, newUsername));
                connectionV("Username updated successfully");
                break;
    }
    }

    private void chatroomEventHandling (String order, int id) throws IOException {
        User user = returnUser(id);
        boolean isDone = false;
        switch (order) {
            case "addadmin":
                String chatroomName = connectionS("Insert chatroom name:");
                String username = connectionS("Insert username:");
                for (Chatroom chatroom: user.getChathome().getChatroomArrayList()) {
                    if (chatroom.getName().equals(chatroomName)){
                        try {
                            isDone = chatroom.addAdminR(id, returnId(username), chatroom);
                        } catch (NullPointerException e) {
                            e.printStackTrace();
                        }
                    }
                }
                if (isDone) {
                    connectionV("Admin added successfully.");
                } else {
                    connectionV("Admin did not added due to a reason.");
                }
                break;
            case "addmember":
                chatroomName = connectionS("Insert chatroom name:");
                username = connectionS("Insert username:");
                for (Chatroom chatroom: user.getChathome().getChatroomArrayList()) {
                    if (chatroom.getName().equals(chatroomName)){
                        try {
                            isDone = chatroom.addMemberR(id, returnId(username), chatroom);
                        } catch (NullPointerException e) {
                            e.printStackTrace();
                        }
                    }
                }
                if (isDone) {
                    connectionV("Member added successfully.");
                } else {
                    connectionV("Member did not added due to a reason.");
                }
                break;
            case "createchatroom":
                String chatroomStatus = connectionS("Insert chatroom status:");
                switch (chatroomStatus.toLowerCase()) {
                    case "pv":
                        username = connectionS("Insert username:");
                        for (Username usr: User.getUSERNAMES()) {
                            if (usr.getText().equals(username)){
                                Chatroom chatroom = new Chatroom(id, returnId(username));
                                user.addToChathome(chatroom);
                                user.getPV().add(chatroom);
                                User user1 = returnUser(returnId(username));
                                user1.addToChathome(chatroom);
                                user1.getPV().add(chatroom);
                            }
                        }
                        break;
                    case "channel":
                        chatroomName = connectionS("Insert chatroom name:");
                        Chatroom chatroom1 = new Chatroom(id, chatroomName, ChatroomStatus.CHANNEL);
                        user.getChathome().getChatroomArrayList().add(chatroom1);
                        user.getChannels().add(chatroom1);
                        break;
                    case "group":
                        chatroomName = connectionS("Insert chatroom name:");
                        Chatroom chatroom2 = new Chatroom(id, chatroomName, ChatroomStatus.GROUP);
                        user.getChathome().getChatroomArrayList().add(chatroom2);
                        user.getChannels().add(chatroom2);
                        break;
                }
                break;
            case "leftchatroom":
                chatroomName = connectionS("Insert chatroom name:");
                for (Chatroom ch: user.getChathome().getChatroomArrayList()) {
                    if (ch.getName().equals(chatroomName)){
                        isDone = ch.leftChatroomR(id, ch);
                    }
                }
                if (isDone) {
                    connectionV("You left the chatroom.");
                } else {
                    connectionV("leaving did not happen due to a reason");
                }
                break;
            case "removeadmin":
                chatroomName = connectionS("Insert chatroom name:");
                username = connectionS("Insert username:");
                for (Chatroom ch: user.getChathome().getChatroomArrayList()) {
                    if (ch.getName().equals(chatroomName)){
                        isDone = ch.removeAdminR(id, returnId(username), ch);
                    }
                }
                if (isDone) {
                    connectionV("Admin removed successfully.");
                } else  {
                    connectionV("Admin did not removed due to a reason");
                }
                break;
            case "removemember":
                chatroomName = connectionS("Insert chatroom name:");
                username = connectionS("Insert username:");
                for (Chatroom ch: user.getChathome().getChatroomArrayList()) {
                    if (ch.getName().equals(chatroomName)){
                        isDone = ch.removeMemberR(id, returnId(username), ch);
                    }
                }
                if (isDone) {
                    connectionV("User removed successfully.");
                } else  {
                    connectionV("User did not removed due to a reason");
                }
                break;
        }
    }

    private void messagingEventHandling (String order, int id) throws IOException {
        User user = returnUser(id);
        boolean isDone = false;
        switch (order) {
            case "deletemessage":
            case "delete":
                int messageId = connectionI("Insert message id:");
                Message message = Message.returnMessage(messageId);
                isDone = Message.deleteMessageR(id, message);
                if (isDone) {
                    connectionV("Message deleted successfully");
                } else {
                    connectionV("Message did not delete");
                }
                break;
            case "forwardmessage":
            case "forward":
                messageId = connectionI("Insert message id:");
                message = Message.returnMessage(messageId);
                String chatroomName = connectionS("Insert chatroom name:");
                for (Chatroom ch: user.getChathome().getChatroomArrayList()) {
                    if (ch.getName().equals(chatroomName)){
                        isDone = Message.forwardMessageR(id, message, ch);
                    }
                }
                if (isDone) {
                    connectionV("Message forwarded successfully");
                } else {
                    connectionV("Message did not forward");
                }
                break;
            case "reactonmessage":
            case "react":
                outputStream.writeUTF("     Insert message id:");
                messageId = inputStream.readInt();

                break;
            case "replyonmessage":
            case "reply":
                chatroomName = connectionS("Insert chatroom name:");
                messageId = connectionI("Insert message id:");
                String repliedMes = connectionS("Write your message");
                for (Chatroom ch: user.getChathome().getChatroomArrayList()) {
                    if (ch.getName().equals(chatroomName)){
                        isDone = Message.replyOnMessageR(id, Message.returnMessage(messageId) ,new Message(repliedMes, id, ch, true, messageId));
                    }
                }
                break;
            case "savemessage":
            case "save":
                chatroomName = connectionS("Insert chatroom name:");
                messageId = connectionI("Insert message id:");
                isDone = Message.saveMessageR(user, Message.returnMessage(messageId));
                if (isDone) {
                    connectionV("Message saved successfully");
                } else {
                    connectionV("Message did not save");
                }
                break;
            case "sendmessage":
            case "send":
                chatroomName = connectionS("Insert chatroom name:");
                String mes = connectionS("Write your message");
                for (Chatroom ch: user.getChathome().getChatroomArrayList()) {
                    if (ch.getName().equals(chatroomName)) {
                        isDone = Message.sendMessageR(new Message(mes, id, ch));
                    }
                }
                if (isDone) {
                    connectionV("Message sent successfully");
                } else {
                    connectionV("Message did not send due to a reason");
                }
                break;
        }

    }

    private void seeEventHandling (String order, int id) throws IOException{
        User user = returnUser(id);
        switch (order) {
            case "chatroom":
            case "chatroominfo":
                String chatroomName = connectionS("Insert chatroom name:");
                for (Chatroom ch: user.getChathome().getChatroomArrayList()) {
                    if (ch.getName().equals(chatroomName)){
                        ch.showChatroom(outputStream);
                    }
                }
                break;
            case "chatroommessage":
            case "chatroommessages":
                chatroomName = connectionS("Insert chatroom name:");
                for (Chatroom ch: user.getChathome().getChatroomArrayList()) {
                    if (ch.getName().equals(chatroomName)){
                        ch.showChatroomM(outputStream);
                    }
                }
            case "contacts":
            case "contact":
            case "mycontact":
            case "mycontacts":
                for (PhoneNumber ph : user.getContacts()) {
                    ph.showPhoneNumber(outputStream);
                }
                break;
            case "me":
            case "myinfo":
            case "myinformation":
                user.showUser(outputStream);
                break;
            case "user":
            case "account":
                String name = connectionS("insert username:");
                Objects.requireNonNull(returnUser(returnId(name))).showUser(outputStream);
                break;
            case "mefully":
            case "mefull":
            case "mecompletely":
            case "mecomplete":
            case "mecomp":
            case "meall":
                user.showUserC(outputStream);
                break;

        }
    }

    private int serverEvent (String order) throws IOException {
        switch (order) {
            case "login" -> {
                String username = connectionS("Username:");
                String pass = connectionS("Password");
                for (Username u : User.getUSERNAMES()) {
                    if (u.getText().equals(username)) {
                        User user = returnUser(returnId(username));
                        if (user.getPassword().getText().equals(pass)) {
                            return user.getId();
                        } else return -1;
                    }
                }
                return -1;
            }
            case "signin" -> {
                String fname = connectionS("Firstname:");
                System.out.println(fname);
                String lname = connectionS("Lastname");
                System.out.println(lname);
                String uname = null;
                boolean isValid = true;
                while (isValid) {
                    uname = connectionS("Username:");
                    for (Username u : User.getUSERNAMES()) {
                        if (u.getText().equals(uname)) {
                            isValid = false;
                            break;
                        }
                    }
                    isValid = !isValid;
                }
                System.out.println(uname);
                String pass = null;
                while (true) {
                    pass = connectionS("Password");
                    if (pass.length() >= 8) {
                        break;
                    } else {
                        connectionV("Password must be 8 chars at least! try again");
                    }
                }
                System.out.println(pass);
                int year = connectionI("Year of birth:");
                System.out.println(year);
                int month = connectionI("Month of birth");
                System.out.println(month);
                int day = connectionI("Day of birth");
                System.out.println(day);
                String email = connectionS("Email:");
                System.out.println(email);
                int countryCode = 0;
                int mainPart = 0;
                while (true) {
                    while (true) {
                        countryCode = connectionI("Country code for phone number:");
                        if (countryCode/10000 < 10) {
                            break;
                        } else {
                            connectionV("Country code must be 5 chars at least! try again");
                        }
                    }
                    while (true) {
                        mainPart = connectionI("Main part for phone number:");
                        if (mainPart/1000000 < 10) {
                            break;
                        } else {
                            connectionV("Main part must be 7 chars at least! try again");
                        }
                    }
                    if (User.getPhoneNumbers().contains(new PhoneNumber(String.valueOf(countryCode), String.valueOf(mainPart)))) {
                        connectionV("Phone number exists! try another!");
                    } else {
                        break;
                    }
                }
                String bio = connectionS("Bio:");
                System.out.println(bio);
                User user = new User(fname, lname, new DateTime(year, month, day), bio, new Password(pass), new PhoneNumber(String.valueOf(countryCode), String.valueOf(mainPart)));
                user.setUsername(new Username(user.getId(), uname));
                user.setEmail(new Email(email, user.getId()));
                User.getUSERS().add(user);
                User.getUsersId().add(user.getId());
                User.getUSERNAMES().add(new Username(user.getId(), uname));
                User.getPhoneNumbers().add(new PhoneNumber(String.valueOf(countryCode), String.valueOf(mainPart)));
                return user.getId();
            }
        }
        return 0;
    }

    public void start() throws IOException {
        User user0 = new User("ali", "ghaem", new DateTime(1380, 9, 20), "say my name", new Password("aghd2091380"), new PhoneNumber("98935", "1960731"));
        user0.setUsername(new Username(user0.getId(), "user0"));
        User user1 = new User("mahdi", "ghaem", new DateTime(1382, 10, 8), "helloooo", new Password("aasgsdgsadf"), new PhoneNumber("98930", "8640597"));
        user1.setUsername(new Username(user1.getId(), "user1"));
        User user2 = new User("reza", "ghaem", new DateTime(1351, 6, 20), "my name is ...", new Password("sagsbethw4g"), new PhoneNumber("98935", "7810459"));
        user2.setUsername(new Username(user2.getId(), "user2"));
        User user3 = new User("kobra", "nikandish", new DateTime(1359, 3, 13), "how are you", new Password("hrjsdfbggs"), new PhoneNumber("98935", "7863254"));
        user3.setUsername(new Username(user3.getId(), "user3"));
        User user4 = new User("hossein", "khandani", new DateTime(1378, 5, 2), "dear...", new Password("aoignlaksdng"), new PhoneNumber("98935", "410235"));
        user4.setUsername(new Username(user4.getId(), "user4"));
        User user5 = new User("hasan", "talebi", new DateTime(1379, 6, 4), "Only god", new Password("aslgldsbglbsg"), new PhoneNumber("98935", "7895236"));
        user5.setUsername(new Username(user5.getId(), "user5"));
        User.getUSERS().add(user0); User.getUSERS().add(user1); User.getUSERS().add(user2); User.getUSERS().add(user3); User.getUSERS().add(user4); User.getUSERS().add(user5);
        User.getUsersId().add(user0.getId()); User.getUsersId().add(user1.getId()); User.getUsersId().add(user2.getId()); User.getUsersId().add(user3.getId()); User.getUsersId().add(user4.getId()); User.getUsersId().add(user5.getId());
        User.getUSERNAMES().add(user0.getUsername());
        User.getUSERNAMES().add(user1.getUsername());
        User.getUSERNAMES().add(user2.getUsername());
        User.getUSERNAMES().add(user3.getUsername());
        User.getUSERNAMES().add(user4.getUsername());
        User.getUSERNAMES().add(user5.getUsername());
        User.getPhoneNumbers().add(user0.getPhoneNumber());
        User.getPhoneNumbers().add(user1.getPhoneNumber());
        User.getPhoneNumbers().add(user2.getPhoneNumber());
        User.getPhoneNumbers().add(user3.getPhoneNumber());
        User.getPhoneNumbers().add(user4.getPhoneNumber());
        User.getPhoneNumbers().add(user5.getPhoneNumber());
        user0.setEmail(new Email("alighaem@gmail.com", user0.getId()));
        user1.setEmail(new Email("alighaem@gmail.com", user1.getId()));
        user2.setEmail(new Email("alighaem@gmail.com", user2.getId()));
        user3.setEmail(new Email("alighaem@gmail.com", user3.getId()));
        user4.setEmail(new Email("alighaem@gmail.com", user4.getId()));
        user5.setEmail(new Email("alighaem@gmail.com", user5.getId()));
//        user0.getChathome().addChatroom(new Chatroom(user0.getId(), "server", ChatroomStatus.GROUP));
//        user0.getChathome().addChatroom(new Chatroom(user0.getId(), "server", ChatroomStatus.CHANNEL));
//        user0.getChathome().addChatroom(new Chatroom(user0.getId(), "server", ChatroomStatus.PV));
//        user0.addToChathome(new Chatroom(user0.getId(), "server", ChatroomStatus.GROUP));

        System.out.println(User.getUSERNAMES().size());
        while (true) {
            int id = 0;
            outputStream.writeInt(-1);
            String inp = inputStream.readUTF();
            System.out.println("request achieved!");
            if (interpret(inp)[0].equals("-u")) {
                System.out.println("server event called");
                id = serverEvent(interpret(inp)[1]);
                if (id == -1){
                    connectionV("User did not exists.");
                    continue;
                }
                else {
                    connectionV("You signed in successfully! now you are in your account.");
                }
            }
            else continue;
            while (!mustDie) {
                outputStream.writeInt(-1);
                String input = inputStream.readUTF();
                if (!interpret(input)[1].equals("logout")){
                    requestHandling(new Request(id, input));
                } else {
                    break;
                }
            }
        }
    }

    private static String connectionS (String whatToWrite) throws IOException {
        outputStream.writeInt(1);
        outputStream.writeUTF(whatToWrite);
        return inputStream.readUTF();
    }

    private static Integer connectionI (String whatToWrite) throws IOException {
        outputStream.writeInt(1);
        outputStream.writeUTF(whatToWrite);
        return inputStream.readInt();
    }

    public static void connectionV (String whatToWrite) throws IOException {
        outputStream.writeInt(0);
        outputStream.writeUTF(whatToWrite);
    }

    private User returnUser (int id) {
        for (User user : User.getUSERS()) {
            if (user.getId() == id){
                return user;
            }
        }
        return null;
    }

    private Integer returnId (String username) {
        for (Username usr: User.getUSERNAMES()) {
            if (usr.getText().equals(username)) {
                return usr.getUserId();
            }
        }
        return null;
    }

}
