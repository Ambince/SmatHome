package com.xiaye.smarthome.util;

import android.util.Log;

/**
 * 
 * @ClassName: DataUtil
 * @Description: 数据解析
 * @author Android组-ChengBin
 * @version 1.0
 * @date 2014-10-13 下午4:12:17
 * 
 */
public class DataUtil {

	public static int flag = 0;
	/**
	 * 
	 * @Description: 获取运算结果
	 * 
	 * @author ChengBin
	 * @version 1.0
	 * @date 2014-10-13 下午4:24:46
	 * @param operOne
	 *            第一个操作数
	 * @param operTwo
	 *            第二个操作数
	 * @param oper
	 *            操作运算符
	 * @return 返回类型
	 */
	public static int getResult(int operOne, int operTwo, int oper) {
		int result = operOne;

		switch (oper) {
		case Operation.NONE:
			break;
		case Operation.ADD:
			result += operTwo;
			break;
		case Operation.SUB:
			result -= operTwo;
			break;
		case Operation.AND:
			result &= operTwo;
			break;
		case Operation.MOD:
			result %= operTwo;
			break;
		case Operation.MUL:
			result *= operTwo;
			break;
		case Operation.DIV:
			result /= oper;
			break;
		default:

			break;
		}

		return result;
	}
	
	
	 public static Object getSecondResult(Object []data, int oper, int num)
	{
		Object result = 0;
		Object obj = data[0];
		
		switch (oper) {
		case 0:
			result = ((int[])obj)[num];
			break;
		case 1:  // 分割字符串  （1|自动/2|手动） 
			result = ((String)obj).split("/");
			break;
		case 2:	//取2-8位，右移两位
			byte b = Byte.parseByte(obj+"");
			result = (b & num) >> 1;
		default:
			break;
		}
		
		return result;
	}
	
	public static String getStringByCode(String[] words, byte code) {
		for (int i = 0; i < words.length; i++) {
			String[] word = words[i].toString().split("\\|");
			if ((code+"").equals(word[0].trim())) {
				return word[1];
			}
		}
		
		return null;
	}
	
	

	/**
	 * 
	 * @Description: 获取运算结果
	 * 
	 * @author ChengBin
	 * @version 1.0
	 * @date 2014-10-13 下午4:24:46
	 * @param operOne
	 *            第一个操作数
	 * @param operTwo
	 *            第二个操作数
	 * @param oper
	 *            操作运算符
	 * @return 返回类型
	 */
	public static int getResult(int[] operOne, int operTwo, int oper) {
		int result = 0;

		switch (oper) {
		case Operation.LEN:
			result = operOne[operTwo];
			break;
		default:

			break;
		}

		return result;
	}
	
	
	/**
	 * 
	 * @ClassName: Operation
	 * @Description: 操作运算符 0，没运算；1、-；2、+；3、&；4、*；5、/；6，%；
	 * @author Android组-ChengBin
	 * @version 1.0
	 * @date 2014-10-13 下午4:10:55
	 * 
	 */
	private final class Operation {

		public static final int NONE = 0; // 无操作
		public static final int SUB = 1; // 减
		public static final int ADD = 2; // 加
		public static final int AND = 3; // 与
		public static final int MUL = 4; // 乘
		public static final int DIV = 5; // 除
		public static final int MOD = 6; // 求余
		public static final int LEN = 7; // ，取数组中位数
	}
	
	
	public final static String getLineInfo() {
		StackTraceElement ste = new Throwable().getStackTrace()[1];
		return ste.getFileName() + ": Line " + ste.getLineNumber();
	}
}
