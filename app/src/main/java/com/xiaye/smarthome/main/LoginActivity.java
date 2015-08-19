package com.xiaye.smarthome.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.xiaye.smarthome.R;
import com.xiaye.smarthome.constant.Type;
import com.xiaye.smarthome.util.GetDataFromSharedPreference;
import com.xiaye.smarthome.util.JsonParse;

/*
 * 
 * @ClassName: LoginActivity
 * @Description: 智能家居登陆界面
 * @author  Android组-ChenSir
 * @version 1.0
 * @date 2014-11-26 下午1:46:05
 *
 */
public class LoginActivity extends Activity {
	// UI控件声明
	private EditText interfaceID_edt;
	private EditText password_edt;
	private EditText serverIP_edt;
	private Button mainEntry_btn;

	private String passwordStr;
	private String ipStr;
	private String userName;// 用户名
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.login_activity);
		// 获取控件ID
		getView();

		if (isLogin()) {

			passwordStr = GetDataFromSharedPreference.getPassword(
					Type.PASSWORD_KEY, getApplication());

			ipStr = GetDataFromSharedPreference.getServerIP(Type.SERVERIP_KEY,
					getApplication());

			userName = GetDataFromSharedPreference.getUserName(
					Type.USER_NAME, getApplication());

			interfaceID_edt.setText(userName);
			password_edt.setText(passwordStr);
			serverIP_edt.setText(ipStr);
		}

		// 按钮监听事件
		mainEntry_btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				try {
					userName = interfaceID_edt.getText()
							.toString().trim();

					passwordStr = password_edt.getText().toString().trim();
					
					ipStr = serverIP_edt.getText().toString().trim();

					// 保存登录信息
					GetDataFromSharedPreference.setUserName(userName,
							getApplicationContext());
					GetDataFromSharedPreference.setPassword(passwordStr,
							getApplicationContext());

					GetDataFromSharedPreference.setServerIP(ipStr,
							getApplicationContext());

					if (userName != "") {
						Intent intent = new Intent();
						// 初始化总控所需JSON信息
						Log.i("login", "passwordStr = " + passwordStr);
						String initInfoJson = JsonParse.initInfoJsonParse(
								userName, passwordStr, ipStr);
						Log.i("login", "initInfoJson = " + initInfoJson);
						intent.putExtra("initInfoJson", initInfoJson);
						intent.setClass(LoginActivity.this, MainActivity.class);
						startActivity(intent);
						finish();
					}
				} catch (Exception e) {
					Toast.makeText(getApplicationContext(), "请输入用户名！！！",
							Toast.LENGTH_LONG).show();
				}

			}
		});
	}

	public void getView() {
		interfaceID_edt = (EditText) findViewById(R.id.input_ui_id_edt);
		password_edt = (EditText) findViewById(R.id.input_password_edt);
		serverIP_edt = (EditText) findViewById(R.id.input_server_ip_edt);
		mainEntry_btn = (Button) findViewById(R.id.login_btn);
	}

	private boolean isLogin() {

		String pwd = GetDataFromSharedPreference.getPassword(Type.PASSWORD_KEY,
				getApplication());

		if (pwd == null) {

			return false;

		} else {

			return true;
		}
	}
}
