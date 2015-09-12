package com.xiaye.smarthome.bean;

/**
 * Created by ChenSir on 2015/8/19 0019.
 */
public class LightGroupMemberBean {

    /**
     * 硬件类型
     * 地址
     * 端口
     * 备注
     */

    private int device_Vaddrs;// 设备虚拟地址
    private int dv_addrs;// 设备地址

    private int groupId;
    private String group_Hardware_Type;
    private int groupAddress;
    private int groupPort;
    private String remarks;
    private String name;

    public LightGroupMemberBean() {
    }


    public String getGroup_Hardware_Type() {
        return group_Hardware_Type;
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

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public int getDevice_Vaddrs() {
        return device_Vaddrs;
    }

    public void setDevice_Vaddrs(int device_Vaddrs) {
        this.device_Vaddrs = device_Vaddrs;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDv_addrs() {
        return dv_addrs;
    }

    public void setDv_addrs(int dv_addrs) {
        this.dv_addrs = dv_addrs;
    }
}
