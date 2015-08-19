package com.jni.info;

public class InfoDealIF {
	
	static{
		System.loadLibrary("smart");
	}
	
	public native int init(String para);

	public native int registe(String name, int id, int type, CallMsg callmsg);

	public native int unregiste(int id, int type, String para);

	public native int config(int id, int type, String para);

	public native String inquire(int id, int type, String para);

	public native int control(int id, int type, byte[] input, OutPut output);

	public native int close();
	
	//总控id  user:总控别名
	public native int u_loginTerml(int id,String user);

	public static class CallMsg {
		
		public static Flag fl;
		public static byte[] parag;
		public int type;

		private static int msgResult;

		public CallMsg(Flag flag) {
			msgResult = 0;
			fl = flag;
		}

		public static int messageCallback(int id, int type, byte[] para) {
			// 处理字符串para中的消息
			parag = para;
		/*	System.out.println("msg: " + " id= " + id + " type= " + type
					+ " para= ");*/
			fl.setFlag(1);
			fl.setType(type);
			return 0;
		}

		public static int getMsgResult() {
			return msgResult;
		}

		public byte[] getPara() {
			return parag;
		}

		public int getType() {
			return type;
		}
	}

	public static class OutPut {
		byte[] joutput;
		boolean isOut;

		public OutPut() {
			this.joutput = new byte[1024];
			this.isOut = false;
		}

		public int setOutput(byte[] output) {
			System.out.println("set output");
			if (output == null) {
				System.out.println("output is null");
				this.isOut = false;
			} else {
				this.joutput = output.clone();
				int len = output.length;
				System.out.println("output is not null,len= " + len);
				this.isOut = true;
			}

			return 0;
		}

		public boolean isOutture() {
			return isOut;
		}

		public byte[] getOutput() {
			return this.joutput;

		}
	}
}
