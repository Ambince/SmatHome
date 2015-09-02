package com.xiaye.smarthome.fragment;

import java.util.List;

import android.app.FragmentTransaction;
import org.json.JSONException;
import org.json.JSONObject;

import com.jni.info.InfoDealIF;
import com.xiaye.smarthome.R;
import com.xiaye.smarthome.bean.CookingRcdStepBean;
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
 * @author Android组-ChenSir
 * @version 1.0
 * @ClassName: RecordStepEditFragment
 * @Description: 编辑烹调记录步骤
 * @date 2014-12-7 上午10:50:26
 */
public class RecordStepEditFragment extends Fragment implements OnClickListener {

    public static String TAG = RecordStepEditFragment.class.getSimpleName();

    TextView sTotal_txt;
    TextView currentNo_txt;

    TextView nodeNo_txt;
    TextView nodeTime_txt;
    EditText tips_edt;

    Button nextStep_btn;
    Button back_btn;
    Button nextNode_btn;
    Button lastNode_btn;

    String nodenumber;
    String timenode;
    String tips;

    int foodProcessingId = 0;
    int totalNodes = 0;
    String menuId = "";//用于返回上一步传参

    List<CookingRcdStepBean> rStepList = null;
    String receive = null;
    InfoDealIF info = null;
    JsonParse jsonParse = null;

    int sNum = 0;// 用于步骤计数

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        foodProcessingId = getArguments().getInt("foodProcessingId");
        totalNodes = getArguments().getInt("totalNodes");
        menuId = getArguments().getString("menuId");

        Log.i("RecordStep", "foodProcessingId = " + foodProcessingId
                + "  totalNodes = " + totalNodes);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recordstepinfo, null);
        initView(view);
        bindData();

        return view;
    }

    public void initView(View view) {

        sTotal_txt = (TextView) view
                .findViewById(R.id.recordstepinfo_totalstep_txt);
        currentNo_txt = (TextView) view
                .findViewById(R.id.recordstepinfo_currentstep_txt);
        nodeNo_txt = (TextView) view
                .findViewById(R.id.recordstepinfo_nodenum_txt);
        nodeTime_txt = (TextView) view
                .findViewById(R.id.recordstepinfo_nodetime_txt);

        tips_edt = (EditText) view.findViewById(R.id.recordstepinfo_tips_edt);

        nextStep_btn = (Button) view.findViewById(R.id.recordstepinfo_next_btn);
        back_btn = (Button) view.findViewById(R.id.recordstepinfo_back_btn);
        nextNode_btn = (Button) view.findViewById(R.id.recordstepinfo_save_btn);
        lastNode_btn = (Button) view.findViewById(R.id.recordstepinfo_last_btn);

        nextStep_btn.setOnClickListener(this);
        back_btn.setOnClickListener(this);
        nextNode_btn.setOnClickListener(this);
        lastNode_btn.setOnClickListener(this);
    }

    public void bindData() {

        info = new InfoDealIF();
        JsonParse jsonParse = new JsonParse();
        // 查询数据
        receive = info.inquire(MainActivity.interfaceId, Type.SELECT_TIMING2,
                jsonParse.pagingJsonParse(0, 0, foodProcessingId));
        Log.e("RecordStep", "receive = " + receive);
        if (receive != null) {

            rStepList = ParseJson.parseCookingRcdStepBean(receive);
            if (rStepList != null && rStepList.size() != 0) {
                // 绑定数据
                sTotal_txt.setText(totalNodes + "");
                updateView();

            } else {
                Toast.makeText(getActivity(), "解析出错！", Toast.LENGTH_SHORT)
                        .show();
            }

        } else {
            Toast.makeText(getActivity(), "未获取到数据！", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onClick(View view) {

        int stepCount = rStepList.size();
        int id = view.getId();

        switch (id) {

            case R.id.recordstepinfo_next_btn:
                // 下一步
                MaterialEditFragment mFg = new MaterialEditFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("foodProcessingId", foodProcessingId);
                bundle.putString("menuId", menuId);
                bundle.putInt("totalNodes", totalNodes);
                mFg.setArguments(bundle);
                FragmentTransaction transaction = getActivity()
                        .getFragmentManager().beginTransaction();
                transaction.replace(R.id.xiaye_fragment, mFg);
                transaction.addToBackStack(TAG);
                transaction.commit();
                break;

            case R.id.recordstepinfo_last_btn:
                // 返回上一节点
                sNum = sNum - 1;
                if (sNum >= 0) {
                    updateView();
                } else {
                    Toast.makeText(getActivity(), "已是第0步！", Toast.LENGTH_LONG).show();
                }
                break;

            case R.id.recordstepinfo_save_btn:
                if (sNum < stepCount) {
                    tips = tips_edt.getText().toString();
				    CookingRcdStepBean cRcdStepbean = new CookingRcdStepBean();
                    cRcdStepbean.setNodenumber(sNum);
                    Log.e(TAG, "sNum = " + sNum);
                    cRcdStepbean.setFoodProcessingId(foodProcessingId);
                    cRcdStepbean.setTips(tips);
                    rStepList.set(sNum, cRcdStepbean);
                    // 更新数据库
                    if ((info.control(MainActivity.interfaceId,
                            Type.UPDATE_TIMING2, changeObjToJson(cRcdStepbean)
                                    .getBytes(), null)) == 0) {
                        Toast.makeText(getActivity(), "更新成功!", Toast.LENGTH_SHORT)
                                .show();
                        sNum = sNum + 1;
                        updateView();
                    } else {
                        Toast.makeText(getActivity(), "更新失败!请重试！",
                                Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getActivity(), "没有更多步骤！请点击'下一步'编辑下一项！",
                            Toast.LENGTH_LONG).show();
                }
                break;

            case R.id.recordstepinfo_back_btn:
                //返回上一步
                getActivity().getFragmentManager().popBackStackImmediate();
                break;
            default:
                break;

        }

    }

    private String changeObjToJson(CookingRcdStepBean cRcdStepbean) {
        JSONObject jsonToDB = new JSONObject();
        try {
            jsonToDB.put("foodProcessingId", cRcdStepbean.getFoodProcessingId());
            jsonToDB.put("tips", cRcdStepbean.getTips());
            jsonToDB.put("nodenumber", cRcdStepbean.getNodenumber());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonToDB.toString();
    }

    public void updateView() {

        if (sNum < rStepList.size()) {
            Log.e(TAG, "rStepList.get(sNum) = " + rStepList.get(sNum));
            currentNo_txt.setText(rStepList.get(sNum).getNodenumber() + "");
            nodeNo_txt.setText(rStepList.get(sNum).getNodenumber() + "");
            nodeTime_txt.setText(rStepList.get(sNum).getTimeOfNode() + "");
            tips_edt.setText(rStepList.get(sNum).getTips());
        }
    }
}
