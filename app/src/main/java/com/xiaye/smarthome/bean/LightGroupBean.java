package com.xiaye.smarthome.bean;

/**
 * Created by ChenSir on 2015/8/19 0019.
 */
public class LightGroupBean {


    /**
     *     群组号(即所有开关的群组号)
     * 	名称
     * 	位置
     * 	硬件类型
     * 	地址
     * 	端口
     * 	备注
     */
    private int groupId;
    private String groupName;
    private String groupLocation;
    private int groupAddress;
    private int groupPort;
    private int groupState;
    private String remarks;


    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getGroupLocation() {
        return groupLocation;
    }

    public void setGroupLocation(String groupLocation) {
        this.groupLocation = groupLocation;
    }

    public int getGroupAddress() {
        return groupAddress;
    }

    public void setGroupAddress(int groupAddress) {
        this.groupAddress = groupAddress;
    }

    public int getGroupPort() {
        return groupPort;
    }

    public void setGroupPort(int groupPort) {
        this.groupPort = groupPort;
    }

    public int getGroupState() {
        return groupState;
    }

    public void setGroupState(int groupState) {
        this.groupState = groupState;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }
}
