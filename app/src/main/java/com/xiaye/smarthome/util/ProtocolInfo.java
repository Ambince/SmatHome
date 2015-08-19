package com.xiaye.smarthome.util;


public interface ProtocolInfo {
	public static final int SETQUERYCTRLTYPE = 1;
	public static final int SETBARCODETYPE = 2;
	public static final int SETQUERYNOTETYPE = 3;
	public static final int SETQUERYSUMTYPE = 4;
	public static final int GETOPERATETYPE = 10;
	public static final int GETSTATUSINFTYPE = 11;
	public static final int GETBASEINFTYPE = 12;
	public static final int GETDEVICEBRKTYPE = 13;
	public static final int GETNOTESTEPTYPE = 14;
	public static final int GETNOTEINFTYPE = 15;
	public static final int GETCTRLBACKTYPE = 16;
	
	
	public static final int SETCOOKREMINDTYPE = 9;	//提醒执行智能烹调
	public static final int GETSTATEINFOTYPE = 5;	//获取状态信息界面
	


//	public static final int SETQUERYCTRLLEN = 1; // 还有参数，需要加上1-2字节，由viewctl中的定义决定
//	public static final int SETBARCODELEN = 13;
//	public static final int SETQUERYNOTELEN = 2;
//	public static final int SETQUERYSUMLEN = 1;
//	public static final int GETOPERATELEN = 1;
//	public static final int GETSTATUSINFLEN = 2; // 状态码1字节，参数1至N字节，由viewctl中的定义决定
//	public static final int GETBASEINFLEN = 17; // 生产日期6字节，程序版本3字节，虚拟地址2字节，注册时间6字节，设备名称N字节，还需要详细定义
//	public static final int GETDEVICEBRKLEN = 1;
//	public static final int GETNOTESTEPLEN = 6; // 记录号1，下一步骤号1，时间长度2，剩余时间2，还需要详细定义
//	public static final int GETNOTEINFLEN = 4; // 记录号1，总步骤数1，总时间2，还需要详细定义
}
