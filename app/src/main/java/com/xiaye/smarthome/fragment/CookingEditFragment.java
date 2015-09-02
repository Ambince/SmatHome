package com.xiaye.smarthome.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.jni.info.InfoDealIF;
import com.unnamed.b.atv.model.TreeNode;
import com.unnamed.b.atv.view.AndroidTreeView;
import com.xiaye.smarthome.R;
import com.xiaye.smarthome.bean.CookMenuBean;
import com.xiaye.smarthome.constant.Type;
import com.xiaye.smarthome.main.MainActivity;
import com.xiaye.smarthome.main.SmartHomeApplication;
import com.xiaye.smarthome.view.IconTreeItemHolder;
import com.xiaye.smarthome.view.SelectableHeaderHolder;
import com.xiaye.smarthome.view.SelectableItemHolder;

import org.json.JSONObject;

/**
 * @author Android组-ChenSir/ChengBin
 * @version 1.0
 * @ClassName: Fragment_cooking_edit
 * @Description: 菜谱编辑
 * @date 2014-11-26 下午5:40:41
 */
public class CookingEditFragment extends Fragment implements OnClickListener {

    public static String TAG = CookingEditFragment.class.getSimpleName();

    InfoDealIF info = null;
    /*
    TreeView
     */
    private AndroidTreeView tView;
    private boolean selectionModeEnabled = true;
    private int i = 0;//父结点索引
    private int j = 0;//子结点索引

    private Button msave, mback;//分类保存，返回按钮
    // 存放数据
    private String theCuisineString;
    private String colorString;
    private boolean isDry;
    private boolean isMeat;
    private boolean isFish;
    private boolean isVegetable;
    private boolean isBird;
    private boolean isMushroom;
    private boolean isProductsClass;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cooking_edit_treeview, null, false);
        ViewGroup containerView = (ViewGroup) view.findViewById(R.id.container_treeview);
        TreeNode root = TreeNode.root();

        initView(view);
        setListener();

        TreeNode folder1 = new TreeNode(new IconTreeItemHolder.IconTreeItem(R.string.ic_folder, "菜系")).setViewHolder(new SelectableHeaderHolder(getActivity()));
        TreeNode folder2 = new TreeNode(new IconTreeItemHolder.IconTreeItem(R.string.ic_folder, "荤素")).setViewHolder(new SelectableHeaderHolder(getActivity()));
        TreeNode folder3 = new TreeNode(new IconTreeItemHolder.IconTreeItem(R.string.ic_folder, "干湿")).setViewHolder(new SelectableHeaderHolder(getActivity()));
        TreeNode folder4 = new TreeNode(new IconTreeItemHolder.IconTreeItem(R.string.ic_folder, "颜色")).setViewHolder(new SelectableHeaderHolder(getActivity()));
        TreeNode folder5 = new TreeNode(new IconTreeItemHolder.IconTreeItem(R.string.ic_folder, "菜类")).setViewHolder(new SelectableHeaderHolder(getActivity()));
        TreeNode folder6 = new TreeNode(new IconTreeItemHolder.IconTreeItem(R.string.ic_folder, "鱼类")).setViewHolder(new SelectableHeaderHolder(getActivity()));
        TreeNode folder7 = new TreeNode(new IconTreeItemHolder.IconTreeItem(R.string.ic_folder, "禽类")).setViewHolder(new SelectableHeaderHolder(getActivity()));
        TreeNode folder8 = new TreeNode(new IconTreeItemHolder.IconTreeItem(R.string.ic_folder, "菌类")).setViewHolder(new SelectableHeaderHolder(getActivity()));
        TreeNode folder9 = new TreeNode(new IconTreeItemHolder.IconTreeItem(R.string.ic_folder, "制品类")).setViewHolder(new SelectableHeaderHolder(getActivity()));
        TreeNode menu1 = new TreeNode("鲁菜").setViewHolder(new SelectableItemHolder(getActivity()));
        TreeNode menu2 = new TreeNode("川菜").setViewHolder(new SelectableItemHolder(getActivity()));
        TreeNode menu3 = new TreeNode("闽菜").setViewHolder(new SelectableItemHolder(getActivity()));
        TreeNode menu4 = new TreeNode("浙江菜").setViewHolder(new SelectableItemHolder(getActivity()));
        TreeNode menu5 = new TreeNode("黔菜").setViewHolder(new SelectableItemHolder(getActivity()));
        TreeNode menu6 = new TreeNode("微菜").setViewHolder(new SelectableItemHolder(getActivity()));
        TreeNode menu7 = new TreeNode("东北菜").setViewHolder(new SelectableItemHolder(getActivity()));
        TreeNode menu8 = new TreeNode("港台菜").setViewHolder(new SelectableItemHolder(getActivity()));
        folder1.addChildren(menu1, menu2, menu3, menu4, menu5, menu6, menu7, menu8);

        TreeNode menu11 = new TreeNode("荤").setViewHolder(new SelectableItemHolder(getActivity()));
        TreeNode menu21 = new TreeNode("素").setViewHolder(new SelectableItemHolder(getActivity()));
        folder2.addChildren(menu11, menu21);

        TreeNode menu12 = new TreeNode("干菜").setViewHolder(new SelectableItemHolder(getActivity()));
        TreeNode menu22 = new TreeNode("湿").setViewHolder(new SelectableItemHolder(getActivity()));
        TreeNode menu32 = new TreeNode("汤").setViewHolder(new SelectableItemHolder(getActivity()));
        folder3.addChildren(menu12, menu22, menu32);

        TreeNode menu13 = new TreeNode("红").setViewHolder(new SelectableItemHolder(getActivity()));
        TreeNode menu23 = new TreeNode("橙").setViewHolder(new SelectableItemHolder(getActivity()));
        TreeNode menu33 = new TreeNode("黄").setViewHolder(new SelectableItemHolder(getActivity()));
        TreeNode menu43 = new TreeNode("绿").setViewHolder(new SelectableItemHolder(getActivity()));
        TreeNode menu53 = new TreeNode("蓝").setViewHolder(new SelectableItemHolder(getActivity()));
        folder4.addChildren(menu13, menu23, menu33, menu43, menu53);

        TreeNode menu14 = new TreeNode("是").setViewHolder(new SelectableItemHolder(getActivity()));
        TreeNode menu24 = new TreeNode("否").setViewHolder(new SelectableItemHolder(getActivity()));
        folder5.addChildren(menu14, menu24);//菜类

        TreeNode menu15 = new TreeNode("是").setViewHolder(new SelectableItemHolder(getActivity()));
        TreeNode menu25 = new TreeNode("否").setViewHolder(new SelectableItemHolder(getActivity()));
        folder6.addChildren(menu15, menu25);//鱼类

        TreeNode menu16 = new TreeNode("是").setViewHolder(new SelectableItemHolder(getActivity()));
        TreeNode menu26 = new TreeNode("否").setViewHolder(new SelectableItemHolder(getActivity()));
        folder7.addChildren(menu16, menu26);//禽类

        TreeNode menu17 = new TreeNode("是").setViewHolder(new SelectableItemHolder(getActivity()));
        TreeNode menu27 = new TreeNode("否").setViewHolder(new SelectableItemHolder(getActivity()));
        folder8.addChildren(menu17, menu27);//菌类

        TreeNode menu18 = new TreeNode("是").setViewHolder(new SelectableItemHolder(getActivity()));
        TreeNode menu28 = new TreeNode("否").setViewHolder(new SelectableItemHolder(getActivity()));
        folder9.addChildren(menu18, menu28);//制品类

        root.addChildren(folder1, folder2, folder3, folder4, folder5, folder6, folder7, folder8, folder9);

        tView = new AndroidTreeView(getActivity(), root);
        tView.setSelectionModeEnabled(selectionModeEnabled, j, i);
        tView.setDefaultAnimation(true);
        containerView.addView(tView.getView());
        if (savedInstanceState != null) {
            String state = savedInstanceState.getString("tState");
            if (!TextUtils.isEmpty(state)) {
                tView.restoreState(state);
            }
        }
        return view;
    }

    /**
     * @param view
     * @Description:初始化控件
     * @author ChenSir
     * @version 1.0
     * @date 2014-12-6 上午11:22:31
     */
    public void initView(View view) {
        mback = (Button) view.findViewById(R.id.btn_tv_back);
        msave = (Button) view.findViewById(R.id.btn_tv_save);

    }


    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.btn_tv_save) {
            // 分类保存
            info = new InfoDealIF();

            CookMenuBean menu = SmartHomeApplication.menuBean;
            // 设置菜谱ID
            menu.setMenuId(SmartHomeApplication.appMap.get("menuId").toString());
            // 菜谱名称
            menu.setMenuName(SmartHomeApplication.appMap.get("menuName").toString());
            // 菜谱介绍
            menu.setSummarize(SmartHomeApplication.appMap.get("summarize")
                    .toString());
            // 制作介绍
            menu.setIntroduceMakeMethod(SmartHomeApplication.appMap.get(
                    "makingMethod").toString());

            String insertData = changeBeanToJsonString(menu);
//
            if (insertData != null) {
                int flag = info.control(MainActivity.interfaceId,
                        Type.EDIT_MENU, insertData.getBytes(), null);
                if (flag == 0) {
                    Toast.makeText(getActivity(), "保存成功！", Toast.LENGTH_LONG)
                            .show();
                    getActivity().getFragmentManager().beginTransaction()
                            .replace(R.id.xiaye_fragment, new CoverFragment())
                            .commit();
                } else {
                    Log.e("CookingEditFragment", "flag = " + flag);
                    Toast.makeText(getActivity(), "保存失败！", Toast.LENGTH_SHORT)
                            .show();
                }
            }

        } else {
            // 返回
            getActivity().getFragmentManager().popBackStackImmediate();
        }
    }

    /**
     * @Description:设置监听
     * @author ChenSir
     * @version 1.0
     * @date 2014-12-6 下午1:22:51
     */
    public void setListener() {
        msave.setOnClickListener(this);
        mback.setOnClickListener(this);
    }

    /**
     * @param
     * @return
     * @description:将新建菜谱表数据转化成Json格式字符串
     * @author ChenSir
     * @version 1.0
     * @date 2014-12-6 下午1:37:47
     */
    public String changeBeanToJsonString(CookMenuBean cookMenu) {

        JSONObject stoneObject = new JSONObject();

        String menuId = cookMenu.getMenuId();
        String menuName = cookMenu.getMenuName();
        String theCuisine = cookMenu.getTheCuisine();
        boolean dry = cookMenu.isDry();
        boolean meat = cookMenu.isMeat();
        boolean fish = cookMenu.isFish();
        boolean birds = cookMenu.isBirds();
        boolean food = cookMenu.isFood();
        boolean mushroom = cookMenu.isMushroom();
        boolean productsClass = cookMenu.isProductsClass();
        String color = cookMenu.getColor();
        String summarize = cookMenu.getSummarize();
        String introduceMakeMethod = cookMenu.getIntroduceMakeMethod();

        try {
            stoneObject.put("menuId", menuId);
            stoneObject.put("menuName", menuName);
            stoneObject.put("theCuisine", theCuisine);
            stoneObject.put("dry", dry);
            stoneObject.put("meat", meat);
            stoneObject.put("fish", fish);
            stoneObject.put("birds", birds);
            stoneObject.put("food", food);
            stoneObject.put("mushroom", mushroom);
            stoneObject.put("productsClass", productsClass);
            stoneObject.put("color", color);
            stoneObject.put("summarize", summarize);
            stoneObject.put("introduceMakeMethod", introduceMakeMethod);
            return stoneObject.toString();

        } catch (Exception e) {
            Toast.makeText(getActivity(), "保存记录出错！", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
        return null;
    }

}
