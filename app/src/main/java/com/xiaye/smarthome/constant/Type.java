package com.xiaye.smarthome.constant;

/**
 * @author Android组-ChenSir
 * @version 1.0
 * @InterfaceName: Type
 * @Description: 总控消息类型及接口type参数
 * @date 2014-11-26 下午3:19:52
 */
public interface Type {

    String USER_NAME = "username";
    String PASSWORD_KEY = "password";
    String SERVERIP_KEY = "serverip";

    // 类别表中的类别类型
    int SORT_TYPE_SOCKET = 1;// 插座及控制面板
    int SORT_TYPE_HOMEAPP = 2;// 智能家电
    int SORT_TYPE_COOKAPP = 3; // 烹调器具
    int SORT_TYPE_SECURITY = 4; // 安防设备
    int SORT_TYPE_OTHER = 5; // 其它

    // 类别表中的类别ID 原始数据 主键
    int SORT_SOCKET = 1; // 插座
    int SORT_KEYPANEL = 2; // 按键面板
    int SORT_KEYLIGHT = 3; // 灯光开关
    int SORT_HOMEAPP = 4; // 智能家电
    int SORT_COOKAPP = 5; // 烹调器具
    int SORT_SECURITY = 6; // 安防设备
    int SORT_OTHER = 7;// 其它

    // 文件类型
    int COOKING_RECORD_TYPE = 0x30; // 烹调记录

    int IF_M_INIT = 0x50000000;// 总控初始化
    int IF_M_CLOSE = 0x50000001;// 总控关闭
    int IF_MSG = 0x50000002;// 发送至界面的消息，消息类型未知
    int IF_USER_M = 0x5000000A;// 总控界面
    int IF_USER_S = 0x5000000B;// 手机界面
    int IF_USER_P = 0x5000000C;// PC界面

    // Control
    int PROTO_FILE_TODEVICE = 0x10000008;// 下载文件到设备
    int PROTO_FILE_DEVICEUP = 0x1000000B;// 请求设备上传文件到总控
    int PROTO_FUN_POWON = 0x10000002; // 智能电器开机控制指令
    int PROTO_FUN_POWOFF = 0x10000004; // 智能电器关机控制指令
    int PROTO_FUN_MODIFY_CONFIG = 0x10000006;// 对设备进行配置
    int DRIVER_DRIVER_MODIFY_SETTING = 0x2000000A; // 自定义控制命令
    int SYSTM_KEEPLIVE_TODEVICE = 0x4000000B; // 界面向设备的保活
    int PROTO_FUN_DEVICE_QUERY = 0x10000011; // 对设备内部状态信息、数据的查询
    int PROTO_FUN_UNREGISTER_DEVICE = 0x10000022; // 设备注销


    //逻辑操作
    int IF_FULL_USERID = 0x50000008; // 注册ID 数已满
    int SWEBKEEPLIVEOUTTIME = 0x50220009;// web连接断开，保活超时

    int SYSTM_FUN_REGISTER_LOAD = 0x40000036; // 设备注册，正在下载界面插件
    int SYSTM_FUN_REGISTER_NOLOAD = 0x40000037; // 设备注册，无法下载界面插件
    int SYSTM_FUN_REPLY_REGISTER = 0x40000026; // 智能电器注册


    int SYSTM_FUN_NOTIFY_UNREGISTER = 0; // 智能电器注销
    int SYSTM_FUN_CONTROL_NOTIFY = 0x40000020; // 总控通知
    int SYSTM_FUN_DEVICE_OFFLINE = 0x40000021; // 智能电器下线
    int SYSTM_FUN_DEVICE_ONLINE = 0x40000022; // 智能电器上线
    int IF_FAILCONNECT = 0x50000004; // 与总控断开连接
    int IF_CONNECTAGAIN = 0x50000005; // 与总控重新连接
    int IF_INVALID_USERID = 0x50000007; // 无效的界面id，或id 已被占用
    int SYSTM_INTERFACE_LOAD_OK = 0x40000030; // 界面插件程序下载成功
    int SYSTM_INTERFACE_UPDATE_OK = 0x40000032; // 界面插件程序更新成功
    int SYSTM_INTERFACE_LOAD_FAIL = 0x40000031; // 界面插件程序下载失败
    int SYSTM_INTERFACE_UPDATE_FAIL = 0x40000033; // 界面插件程序更新失败

    int SYN_C_SYNOK = 0x50230001;// 手机总控文件同步成功
    int PROTO_FILE_TODEVICE_OK = 0x10000009;// 下载文件到设备成功
    int PROTO_FILE_TODEVICE_FAIL = 0x1000000A;// 下载文件到设备失败
    int PROTO_FILE_DEVICEUP_OK = 0x1000000C; /* 请求设备上传文件到总控成功 */
    int PROTO_FILE_DEVICEUP_FAIL = 0x1000000D;

    int DRIVER_DRIVER_REPLY_SETTING = 0x2000000B;// 设备状态变化报告（自定义）
    int SYSTM_DEVICE_MSG = 0x40000034;// 设备状态变化报告（通用）
    int SYSTM_NOTFIND_DRIVER = 0x40000028;// 找不到设备数据文件解析驱动
    int SYSTM_DRIVER_DECAP_ERROR = 0x40000029;// 设备数据文件解析出错

    // 数据库操作
    int SELECT_PROCESS3 = 0x80201030;// 查询
    // ”菜谱记录表“中的菜谱名称，菜谱ID，菜谱简介，制作介绍，荤素，颜色，干湿，味道
    // 根据设备序列号、适用人数、菜系

    int SELECT_PROCESS4 = 0x80201032;// 查询烹调记录表（查询烹调记录ID，菜谱Id，时长，节点数，使用人数，设备条形码，记录号，数据文件存储路径，设备虚拟地址根据菜谱Id，适用人数）

    int SELECT_MEMBER1 = 0x80201031;// 查询成员信息表（查询姓名，称谓）
    int SELECT_MACHINE6 = 0x80201020; // 查询设备信息表(查询设备条形码、设备别名、设备类别、备注根据设备虚拟地址)
    int SELECT_MACHINE1 = 0x8020100e; // 查询设备信息表（查询设备别名、当前状态、设备虚拟地址、设备条形码根据类别ID智能家电）
    int SELECT_MACHINE5 = 0x8020101c; // 查询设备信息表(设备虚拟地址、设备别名根据设备条形码)
    int MENU_DETAIL4 = 0x80201026;// 查询烹调记录表（查询所有）
    int SELECT_MANAGE = 0x80201025;// 查询设备条形码管理表（查询设备别名根据设备条形码）
    int EDITOR_MENU4 = 0x80201027;// 查询菜谱表（查询菜谱名根据烹调记录表的菜谱Id外连烹调记录表）
    int SELECT_MACHINE7 = 0x80201028;// 查询设备信息表（查询所有根据设备虚拟地址）
    int UPDATE_MACHINE2 = 0x80103007;// 更新设备信息表（更新设备别名、类别、注册方式、备注根据设备虚拟地址）
    int SELECT_MACHINE2 = 0x80201013;// 查询设备信息表（查询所有）
    int MENU_DETAIL2 = 0x80201021; // 查询烹调记录表(查询烹调记录ID，菜谱Id，时长，节点数，使用人数，设备条形码，记录号，数据文件存储路径根据菜谱Id)
    int SELECT_MENU1 = 0x80201029; // 查询菜谱表（查询菜谱Id根据菜谱名）
    int SELECT_MENU2 = 0x8020102a; // 查询菜谱表（查询所有菜谱名）
    int SELECT_PROCESS1 = 0x8020102b;// 查询烹调记录表（查询菜谱Id根据烹调记录Id）
    int SELECT_MENU3 = 0x8020102c;// 查询菜谱表（查询所有根据菜谱Id）
    int SELECT_MACHINE_TYPE = 0x80201038; // 查询设备信息表、类别表（查询设备信息表所有，类别表所有根据设备虚拟地址）
    int ELECT_PROCESS2 = 0x8020102d; // 查询烹调记录表（查询所有根据烹调记录Id）
    int SELECT_TIMING2 = 0x8020102e; // 查询记录步骤表（查询所有根据烹调记录Id）
    int MENU_DETAIL3 = 0x80201022; // 查询菜谱原料加工表(查询所有根据烹调记录ID)
    int SELECT_TIMING1 = 0x8020101e; // 查询记录步骤表（查询节点编号、提示信息根据烹调记录Id）

    int UPDATE_PROCESS2 = 0x8010300c; // 更新烹调记录表（更新菜谱Id根据烹调记录Id）
    int UPDATE_PROCESS = 0x8010300a; // //更新烹调记录表（更新菜谱Id、适用人数、备注根据烹调记录Id）
    int UPDATE_PROCESS1 = 0x8010300b; // 更新烹调记录表（更新适用人数、备注根据烹调记录Id）
    int UPDATE_MENU1 = 0x80103009;// 更新菜谱表（更新菜谱名称根据菜谱Id）
    int UPDATE_MENU = 0x80103003; // 更新菜谱表（更新所有根据菜谱Id）
    int UPDATE_TIMING2 = 0x80103002;// 更新记录步骤表（更新提示信息根据烹调记录Id和节点编号）
    int UPDATE_MATERIAL1 = 0x8010300d; // 更新菜谱原料制作表（更新材料名称、材料类别、材料数量、加工方法、加工序号根据烹调记录Id）
    int EDIT_MENU = 0x80102004; // 插入菜谱表（插入所有）
    int EDIT_MATERIAL = 0x80102005;// 插入菜谱原料制作表（插入所有）

    int INSERT_MEMBER_INFORMATION = 0x80102014; // 插入成员信息表所有

    int UPDATE_GROUP = 0x80103011;//按键面板注册时用户更新位置信息

    //灯光部分操作
    int SYSTM_FUN_REGISTER_GROUP = 0x40000038;//通知新建面板
    int SELECT_GROUP3 = 0x80201051;//查询群组列表所有
    int SELECT_GROUP4 = 0x80201052;//查询群组列表（群组ID，名称，状态根据位置）
    int SELECT_GROUPMEMBER4 = 0x80201053;//查询群成员列表所有（根据群组ID）
    int SELECT_GROUPMEMBER5 = 0x80201054;//查询设备别名，虚拟地址，地址，端口（包括加入群组和没加入群组）
    int SELECT_GROUPMEMBER6 = 0x80201055;//查询群组id，地址，端口（加入群组的灯光）
    int SELECT_GROUP5 = 0x80201056;//查询用户创建的群组

    int PROTO_FUN_NEW_GROUP = 0x10000029; // 新增群组
    int PROTO_FUN_GROUPIN = 0x10000024;  // 将设备加入到群组
    int PROTO_FUN_GROUPOUT = 0x10000026; //将设备移除群组
    int PROTO_FUN_DELETE_GROUP = 0x10000028; //删除群组
    int GROUP_DEVICE_POWON = 0x10000032;//向群组发送“增加亮度”命令
    int GROUP_DEVICE_POWOFF = 0x10000033;//向群组发送“降低亮度”命令

    // 网络通信模块客户端消息
    int LOGINSERVER_OK = 0x50210001; // 登录服务器成功
    int LOGINSERVER_FAILED = 0x50210002;// 登录服务器失败
    int LOGINSERVER_PWDERR = 0x50210003;// 登录服务器失败，密码错误
    int LOGINSERVER_TIMEOUT = 0x50210004;// 登录服务器失败，超时
    int LOGINSERVER_FULL = 0x50210005;// 登录服务器失败，连接已满
    int LOGINSERVER_REPEAT = 0x50210006; // 登录服务器失败，重复登录
    int SERVER_BREAK = 0x50210007; // 与服务器断开连接

    int LOGINTERML_OK = 0x50210011; // 登录总控成功
    int LOGINTERML_FAILED = 0x50210012;// 登录总控失败
    int LOGINTERML_PWDERR = 0x50210013; // 登录总控失败，密码错误
    int LOGINTERML_TIMEOUT = 0x50210014; // 登录总控失败，超时
    int LOGINTERML_FULL = 0x50210015; // 登录总控失败，连接已满
    int LOGINTERML_REPEAT = 0x50210016; // 登录总控失败，重复登录
    int TERML_BREAK = 0x50210017; // 与服务器断开连接
    int CLIENT_OFFLINE = 0x50220001; // 客户端下线
    int CLIENT_ONLINE = 0x50220002; // 客户端上线
    int SERACH_OK = 0x50220011; // 局域网下成功搜索到总控


    int SELECT_TYPE3 = 0x80201039; // //查询类别表所有
    int SELECT_MACHINE_TYPE2 = 0x80201040; // 查询设备信息表、类别表(查询设备条形码、设备别名、备注、类别类型、类别名称根据设备虚拟地址)
    int UPDATE_MACHINE4 = 0x80103010; // 更新设备信息表（更新设备别名、类别、备注根据设备虚拟地址）
}
