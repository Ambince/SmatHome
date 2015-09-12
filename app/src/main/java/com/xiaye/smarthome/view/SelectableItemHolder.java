package com.xiaye.smarthome.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.unnamed.b.atv.model.TreeNode;
import com.xiaye.smarthome.R;
import com.xiaye.smarthome.bean.CookMenuBean;
import com.xiaye.smarthome.main.SmartHomeApplication;

/**
 * Created by Bogdan Melnychuk on 2/15/15.
 */
public class SelectableItemHolder extends TreeNode.BaseNodeViewHolder<String> {

    public CheckBox nodeSelector;
    private TextView tvValue;
    private Context mcontext;
    private String mean;
    private String color;
    private String isDry;
    private String isMeat;
    private String isFish;
    private String isBird;
    private String isMushroom;
    private String isProducts;
    private String isVegetable;
    private CookMenuBean menuBean;



    public SelectableItemHolder(Context context) {
        super(context);
        this.mcontext = context;
    }

    @Override
    public View createNodeView(final TreeNode node, String value) {
        final LayoutInflater inflater = LayoutInflater.from(context);
        final View view = inflater.inflate(R.layout.layout_selectable_item, null, false);

        tvValue = (TextView) view.findViewById(R.id.node_value);
        tvValue.setText(value);
        nodeSelector = (CheckBox) view.findViewById(R.id.node_selector);
        nodeSelector.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                IconTreeItemHolder.IconTreeItem micontree = (IconTreeItemHolder.IconTreeItem) node.getParent().getValue();
                String mtext = micontree.text;
                for (int i = 0; i < node.getParent().size(); i++) {
                    if (!node.equals(node.getParent().getChildren().get(i))) {
                        tView.deselectById(node.getParent().getId(), i);
                    } else {
                        node.setSelected(isChecked);
                        if (node.isSelected()) {
                            nodeSelector.setChecked(isChecked);
                            changedate(mtext, node.getValue().toString());
                            Toast.makeText(mcontext, mtext + node.getValue() + "被选中了", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });

        if (node.isLastChild()) {
            view.findViewById(R.id.bot_line).setVisibility(View.INVISIBLE);
        }
        return view;
    }

    private void changedate(String parent, String child) {
        if (parent.equals("菜系")) {
            SmartHomeApplication.menuBean.setTheCuisine(child);
        } else if (parent.equals("荤素")) {
            if (child.equals("荤")) {
                SmartHomeApplication.menuBean.setMeat(true);
            } else if (child.equals("素")) {
                SmartHomeApplication.menuBean.setMeat(false);
            }
        } else if (parent.equals("干湿")) {
            if (child.equals("干菜")) {
                SmartHomeApplication.menuBean.setDry(true);
            } else if (child.equals("湿")) {
                SmartHomeApplication.menuBean.setDry(false);
            }

        } else if (parent.equals("颜色")) {
            SmartHomeApplication.menuBean.setColor(child);

        } else if (parent.equals("菜类")) {
            if (child.equals("是")) {
                SmartHomeApplication.menuBean.setFood(true);
            } else if (child.equals("否")) {
                SmartHomeApplication.menuBean.setFood(false);
            }
        } else if (parent.equals("鱼类")) {
            if (child.equals("是")) {
                SmartHomeApplication.menuBean.setFish(true);
            } else if (child.equals("否")) {
                SmartHomeApplication.menuBean.setFish(false);
            }

        } else if (parent.equals("禽类")) {
            if (child.equals("是")) {
                SmartHomeApplication.menuBean.setBirds(true);
            } else if (child.equals("否")) {
                SmartHomeApplication.menuBean.setBirds(false);
            }

        } else if (parent.equals("菌类")) {
            if (child.equals("是")) {
                SmartHomeApplication.menuBean.setMushroom(true);
            } else if (child.equals("否")) {
                SmartHomeApplication.menuBean.setMushroom(false);
            }
        } else if (parent.equals("制品类")) {
            if (child.equals("是")) {
                SmartHomeApplication.menuBean.setProductsClass(true);
            } else if (child.equals("否")) {
                SmartHomeApplication.menuBean.setProductsClass(false);
            }
        } else {

        }

    }


    @Override
    public void toggleSelectionMode(boolean editModeEnabled) {
        nodeSelector.setVisibility(editModeEnabled ? View.VISIBLE : View.GONE);
        nodeSelector.setChecked(mNode.isSelected());
    }

}
