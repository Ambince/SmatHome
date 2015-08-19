package com.xiaye.smarthome.view;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.unnamed.b.atv.model.TreeNode;
import com.xiaye.smarthome.R;

/**
 * Created by Bogdan Melnychuk on 2/15/15.
 */
public class SelectableItemHolder extends TreeNode.BaseNodeViewHolder<String> {
    public CheckBox nodeSelector;
    private TextView tvValue;
    private Context mcontext;

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
                        Log.i("123124124", node.getParent().getChildren().get(i).toString());
                        tView.deselectById(node.getParent().getId(), i);


                    } else {
                        node.setSelected(isChecked);
                        if (node.isSelected()) {
                            nodeSelector.setChecked(isChecked);
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


    @Override
    public void toggleSelectionMode(boolean editModeEnabled) {
        nodeSelector.setVisibility(editModeEnabled ? View.VISIBLE : View.GONE);
        nodeSelector.setChecked(mNode.isSelected());
    }
}
