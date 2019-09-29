package com.decimals.stuhub;

/**
 * Created by Shubham on 08-04-2018.
 */

public class chat {
    public String userName, userMsg, msgTime;

    public chat() {

    }

    public chat(String userName, String userMsg, String msgTime) {
        this.userName = userName;
        this.userMsg = userMsg;
        this.msgTime = msgTime;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserMsg() {
        return userMsg;
    }

    public void setUserMsg(String userMsg) {
        this.userMsg = userMsg;
    }

    public String getMsgTime() {
        return msgTime;
    }

}
