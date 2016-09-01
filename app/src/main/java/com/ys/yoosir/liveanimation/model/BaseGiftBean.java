package com.ys.yoosir.liveanimation.model;

/** 礼物信息
 * Created by ys on 2016/8/30 0030.
 */
public class BaseGiftBean {

    //用户头像
    private String senderAvatar;

    //用户名称
    private String senderName;

    //用户Id
    private String senderId;

    //礼物类型
    private int giftType;

    //礼物数量
    private int giftCounts;

    //礼物添加时间
    private long addTime;

    public String getSenderAvatar() {
        return senderAvatar;
    }

    public void setSenderAvatar(String senderAvatar) {
        this.senderAvatar = senderAvatar;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public int getGiftType() {
        return giftType;
    }

    public void setGiftType(int giftType) {
        this.giftType = giftType;
    }

    public int getGiftCounts() {
        return giftCounts;
    }

    public void setGiftCounts(int giftCounts) {
        this.giftCounts = giftCounts;
    }

    public void addGiftCounts(int giftCounts){
        this.giftCounts += giftCounts;
    }

    public long getAddTime() {
        return addTime;
    }

    public void setAddTime(long addTime) {
        this.addTime = addTime;
    }

    public BaseGiftBean(String senderAvatar, String senderName, String senderId, int giftType, int giftCounts, long addTime) {
        this.senderAvatar = senderAvatar;
        this.senderName = senderName;
        this.senderId = senderId;
        this.giftType = giftType;
        this.giftCounts = giftCounts;
        this.addTime = addTime;
    }
}
