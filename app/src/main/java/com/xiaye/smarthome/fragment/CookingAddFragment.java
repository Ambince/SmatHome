package com.xiaye.smarthome.fragment;

import android.app.FragmentTransaction;
import org.json.JSONException;
import org.json.JSONObject;

import com.jni.info.InfoDealIF;
import com.xiaye.smarthome.R;
import com.xiaye.smarthome.bean.CookingRecordBean;
import com.xiaye.smarthome.constant.Type;
import com.xiaye.smarthome.main.MainActivity;
import com.xiaye.smarthome.util.JsonParse;
import com.xiaye.smarthome.util.ParseJson;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author Android组-ChenSir/ChengBin
 * @version 1.0
 * @ClassName: CookingAddFragment
 * @Description: 烹调记录
 * @date 2014-11-26 下午5:50:52
 */
public class CookingAddFragment extends Fragment implements OnClickListener {

    public static String TAG = CookingAddFragment.class.getSimpleName();

    private TextView mMenuId_txt;
    private TextView mTimes_txt;
    private TextView mNodes_txt;
    private TextView mMachineShapeCode_txt;
    private TextView mRcdNo_txt;
    private TextView mFilePath_txt;

    private EditText mRemark_edt;
    private EditText mUseNum_edt;

    private Button mNextStep;
    private Button mBack;

    /*
     * 烹调记录表每一项参数
     */
    private String menuId;
    private int usenumber;
    private String remark;

    String data_reveive;
    int foodProcessingId;
    InfoDealIF info;
    JsonParse jsonParse;
    CookingRecordBean cRcdBean = null;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        menuId = getArguments().getString("menuId","");
        foodProcessingId = getArguments().getInt("foodProcessingId");
        Log.i("CookingAddFragment", "menuId = " + menuId
                + "   foodProcessingId = " + foodProcessingId);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cooking_add, null);
        initView(view);
        bindData();

        return view;
    }

    public void initView(View view) {

        mMenuId_txt = (TextView) view.findViewById(R.id.add_menuId_txt);
        mTimes_txt = (TextView) view.findViewById(R.id.add_time_txt);
        mNodes_txt = (TextView) view.findViewById(R.id.add_nodes_txt);
        mMachineShapeCode_txt = (TextView) view
                .findViewById(R.id.add_dev_code_txt);
        mRcdNo_txt = (TextView) view.findViewById(R.id.add_record_txt);
        mFilePath_txt = (TextView) view.findViewById(R.id.add_file_path_txt);

        mRemark_edt = (EditText) view.findViewById(R.id.add_remark_edt);
        mUseNum_edt = (EditText) view.findViewById(R.id.add_use_edt);

        mNextStep = (Button) view.findViewById(R.id.btn_next);
        mBack = (Button) view.findViewById(R.id.btn_back);
        mBack.setOnClickListener(this);
        mNextStep.setOnClickListener(this);
    }

    /**
     * @Description:查询数据库 绑定数据
     * @author ChenSir
     * @version 1.0
     * @date 2014-12-6 下午2:39:51
     */
    public void bindData() {
        info = new InfoDealIF();
        jsonParse = new JsonParse();

        mMenuId_txt.setText(menuId);

        // 根据烹调记录ID查询数据库
        data_reveive = info.inquire(MainActivity.interfaceId,
                Type.ELECT_PROCESS2,
                jsonParse.pagingJsonParse(0, 0, foodProcessingId));
        Log.e("Cooking Add", "data_reveive = " + data_reveive);
        if (data_reveive != null) {

            try {
                cRcdBean = ParseJson.parseCbDetailBean(data_reveive);
            } catch (Exception e) {
                Toast.makeText(getActivity(), "解析出错！", Toast.LENGTH_SHORT)
                        .show();
                e.printStackTrace();
            }

            mTimes_txt.setText(cRcdBean.getTimes() + "");
            mNodes_txt.setText(cRcdBean.getNodes() + "");
            mMachineShapeCode_txt.setText(cRcdBean.getMachineShapeCode());
            mRcdNo_txt.setText(cRcdBean.getRecord() + "");
            mFilePath_txt.setText(cRcdBean.getDatafilestoragepath());
            mUseNum_edt.setText(cRcdBean.getUsenumber() + "");
            mRemark_edt.setText(cRcdBean.getRemark());
        }

    }

    @Override
    public void onClick(View view) {

        int id = view.getId();

        if (id == R.id.btn_next) {
            // 更新烹调记录表
            String updateString = changeObjToJson(getDataOnView());
            if (usenumber != 0) {

                if (updateString != null) {

                    if ((info.control(MainActivity.interfaceId,
                            Type.UPDATE_PROCESS, updateString.getBytes(), null)) == 0) {
                        Toast.makeText(getActivity(), "更新成功!", Toast.LENGTH_SHORT)
                                .show();

                        RecordStepEditFragment fg = new RecordStepEditFragment();
                        Bundle bundle = new Bundle();
                        bundle.putInt("foodProcessingId", foodProcessingId);
                        bundle.putString("menuId", menuId);
                        bundle.putInt("totalNodes",
                                Integer.parseInt((mNodes_txt.getText().toString())));
                        fg.setArguments(bundle);

                        FragmentTransaction transaction = getActivity()
                                .getFragmentManager().beginTransaction();
                        transaction.replace(R.id.xiaye_fragment, fg);
                        transaction.addToBackStack(TAG);
                        transaction.commit();
                    } else {
                        Toast.makeText(getActivity(), "更新失败!", Toast.LENGTH_SHORT)
                                .show();
                    }
                }
            } else {
                Toast.makeText(getActivity(), "适用人数不能为0！", Toast.LENGTH_LONG)
                        .show();
            }
        } else if (id == R.id.btn_back) {
//            CookMenuEditFragment fg = new CookMenuEditFragment();
//            Bundle bundle = new Bundle();
//            bundle.putInt("foodProcessingId", foodProcessingId);
//            fg.setArguments(bundle);
//            getActivity().getFragmentManager().beginTransaction()
//                    .replace(R.id.xiaye_fragment, fg).commit();
            getActivity().getFragmentManager().popBackStackImmediate();
        } else {

        }

    }

    public CookingRecordBean getDataOnView() {

        if (mUseNum_edt.getText() != null) {

            usenumber = Integer.parseInt(mUseNum_edt.getText().toString()
                    .trim());

            remark = mRemark_edt.getText().toString().trim();

            CookingRecordBean bean = new CookingRecordBean(foodProcessingId,
                    menuId, remark, usenumber);

            return bean;
        }
        return null;

    }

    public String changeObjToJson(CookingRecordBean obj) {
        if (obj != null) {

            CookingRecordBean cb = (CookingRecordBean) obj;
            JSONObject jsonToDB = new JSONObject();
            try {
                jsonToDB.put("foodProcessingId", cb.getFoodProcessingId());
                jsonToDB.put("menuId", cb.getMenuId());
                jsonToDB.put("usenumber", cb.getUsenumber());
                jsonToDB.put("remark", cb.getRemark());
            } catch (JSONException e) {

                e.printStackTrace();
            }
            return jsonToDB.toString();
        }
        return null;

    }

}
