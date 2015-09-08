package com.xiaye.smarthome.fragment;

import android.app.FragmentTransaction;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.jni.info.InfoDealIF;
import com.xiaye.smarthome.R;
import com.xiaye.smarthome.bean.MaterialBean;
import com.xiaye.smarthome.constant.Type;
import com.xiaye.smarthome.main.MainActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MaterialEditFragment extends Fragment implements OnClickListener {

    public static String TAG = MaterialEditFragment.class.getSimpleName();

    private EditText materialName_edt;
    private Spinner typeId_spn;
    private EditText materialNumber_edt;
    private EditText materialProcessingMethod_edt;
    private EditText materialProcessingNumber_edt;

    private Button back_btn;
    private Button save_btn;
    private Button complete_btn;
    private Button last_btn;

    private InfoDealIF info;
    private String[] types;

    int foodProcessingId = 0;
    int totalNodes = 0;
    String menuId = "";//用于返回上一步传参

    int count = 0;

    // private String data_receive;

    private HashMap<Integer, MaterialBean> materialBeans;

    public MaterialEditFragment() {
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        foodProcessingId = getArguments().getInt("foodProcessingId");
        totalNodes = getArguments().getInt("totalNodes");
        menuId = getArguments().getString("menuId");
        Log.e("MaterialEditFragment", foodProcessingId + "  foodProcessingId");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.materialtable, null);
        initView(view);
        info = new InfoDealIF();
        materialBeans = new HashMap<>();
        return view;
    }

    public void initView(View view) {

        materialName_edt = (EditText) view
                .findViewById(R.id.materialtable_materialname_txt);
        materialNumber_edt = (EditText) view
                .findViewById(R.id.materialtable_materinumber_txt);
        materialProcessingMethod_edt = (EditText) view
                .findViewById(R.id.materialtable_cookmothod_txt);
        materialProcessingNumber_edt = (EditText) view
                .findViewById(R.id.materialtable_cooknumber_txt);

        typeId_spn = (Spinner) view
                .findViewById(R.id.materialtable_materitype_spn);

        types = new String[]{"原料", "辅料", "配料"};
        typeId_spn.setAdapter(new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, types));

        back_btn = (Button) view.findViewById(R.id.materialtable_back_btn);
        save_btn = (Button) view.findViewById(R.id.materialtable_save_btn);
        complete_btn = (Button) view.findViewById(R.id.materialtable_ok_btn);
        last_btn = (Button) view.findViewById(R.id.materialtable_last_btn);

        back_btn.setOnClickListener(this);
        save_btn.setOnClickListener(this);
        complete_btn.setOnClickListener(this);
        last_btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        int id = v.getId();

        switch (id) {
            case R.id.materialtable_back_btn:
                //返回
                getActivity().getFragmentManager().popBackStackImmediate();
                break;

            case R.id.materialtable_save_btn:
                // 保存并编辑下一节点
                insertDataToDB();
                clearView();
                break;

            case R.id.materialtable_last_btn:
                showLastNodeView();
                break;

            case R.id.materialtable_ok_btn:
                // 下一步
                FragmentTransaction transaction = getActivity()
                        .getFragmentManager().beginTransaction();
                transaction.replace(R.id.xiaye_fragment, new CookingEditFragment());
                transaction.addToBackStack(TAG);
                transaction.commit();
                break;

            default:
                break;
        }
    }

    /**
     * @Description:清空数据
     * @author ChenSir
     * @version 1.0
     * @date 2014-12-7 下午3:16:32
     */
    private void clearView() {
        materialName_edt.setText("");
        materialNumber_edt.setText("");
        materialProcessingMethod_edt.setText("");
        materialProcessingNumber_edt.setText("");
    }

    /**
     * @Description:插入表
     * @author ChenSir
     * @version 1.0
     * @date 2014-12-7 下午3:16:53
     */
    private void insertDataToDB() {
        MaterialBean material = getDataFromView();
        // 插入操作(插入新记录到菜原料制作表)
        if (material != null
                && (info.control(MainActivity.interfaceId, Type.EDIT_MATERIAL,
                changeObjToJson(material).getBytes(), null)) == 0) {
            materialBeans.put(count, material);
            count++;
            Toast.makeText(getActivity(), "更新成功！", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getActivity(), "更新失败！", Toast.LENGTH_SHORT).show();
        }

    }

    public MaterialBean getDataFromView() {

        MaterialBean material = new MaterialBean();

        material.setFoodProcessingId(foodProcessingId);

        material.setMaterialName(materialName_edt.getText().toString());

        material.setMaterialNumber(materialNumber_edt.getText().toString());

        material.setMaterialProcessingMethod(materialProcessingMethod_edt
                .getText().toString());

        String type = typeId_spn.getSelectedItem().toString();
        Log.i(TAG, "type = " + type);

        // "原料", "辅料", "配料"
        if (type.equals("原料")) {
            material.setTypeId(1);
        } else if (type.equals("辅料")) {
            material.setTypeId(2);
        } else {
            material.setTypeId(3);
        }
        if (!materialProcessingNumber_edt.getText().toString().equals("")) {
            material.setMaterialProcessingNumber(Integer
                    .parseInt(materialProcessingNumber_edt.getText().toString()));
            return material;
        } else {
            Toast.makeText(getActivity(), "请输入加工序号！！！", Toast.LENGTH_LONG)
                    .show();
        }
        return null;
    }

    public String changeObjToJson(MaterialBean material) {
        JSONObject jsonToDB = new JSONObject();
        try {
            jsonToDB.put("foodProcessingId", material.getFoodProcessingId());
            jsonToDB.put("materialName", material.getMaterialName());
            jsonToDB.put("typeId", material.getTypeId());
            jsonToDB.put("materialNumber", material.getMaterialNumber());
            jsonToDB.put("materialProcessingMethod",
                    material.getMaterialProcessingMethod());
            jsonToDB.put("materialProcessingNumber",
                    material.getMaterialProcessingNumber());
            return jsonToDB.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    /*
        显示上一节点
     */
    public void showLastNodeView() {
        count--;
        if (count >= 0) {
            materialName_edt.setText(materialBeans.get(count).getMaterialName());
            materialNumber_edt.setText(materialBeans.get(count).getMaterialNumber());
            materialProcessingMethod_edt.setText(materialBeans.get(count).getMaterialProcessingMethod());
            materialProcessingNumber_edt.setText(materialBeans.get(count).getMaterialProcessingNumber() + "");

            int typeId = materialBeans.get(count).getTypeId();
            Log.i(TAG, "typeId = " + typeId);
            if (typeId == 1) {
                typeId_spn.setSelection(0);
            } else if (typeId == 2) {
                typeId_spn.setSelection(1);
            } else {
                typeId_spn.setSelection(2);
            }
        } else {
            Toast.makeText(getActivity().getApplicationContext(), "已是第1步！", Toast.LENGTH_LONG).show();

        }

    }
}
