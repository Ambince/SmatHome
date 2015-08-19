package com.xiaye.smarthome.view;

import android.content.Context;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.xiaye.smarthome.R;
import com.xiaye.smarthome.constant.UI_Constant;
import com.xiaye.smarthome.util.MobilePhoneDeviceInfo;

/**
 * 
 * @ClassName: CategoryView
 * @Description: 自定义菜单
 * @author Android组-ChengBin
 * @version 1.0
 * @date 2014-11-23 下午2:23:35
 * 
 */
public class CategoryView extends LinearLayout implements
		OnCheckedChangeListener {
	private LayoutInflater inflater;
	private OnClickCategoryListener mListener;

	public CategoryView(Context context) {
		this(context, null);
	}

	public CategoryView(Context context, AttributeSet attrs) {
		super(context, attrs);
		inflater = LayoutInflater.from(context);
	}

	/**
	 * 
	 * @Description: 添加菜单数组
	 * 
	 * @author ChengBin
	 * @version 1.0
	 * @date 2014-11-23 下午2:30:58
	 * @param category
	 *            List<String>
	 */
	public void addCategory(String[] category, String categoryName,
			int orientation) {

		if (category.length > 0 && !isCategoryExist(categoryName)) {

			// 从右到左显示（默认为从左到右显示，所以先需要将控件中 的子控件移除，添加最后一个控件）
			int count = this.getChildCount(); // 获取子控件个数
			View[] views = new View[count]; // 将子控件保存于数组views

			int i = 0;
			for (i = 0; i < count; i++) { // 取出每个子控件存于数组views
				views[i] = this.getChildAt(i);
			}
			this.removeAllViews(); // 移除控件中的所有子控件

			View view = inflater.inflate(R.layout.view_category, null);
			this.addView(view);

			for (i = 0; i < count; i++) {
				this.addView(views[i]);
			}

			RadioGroup radioGroup = (RadioGroup) view
					.findViewById(R.id.container);
			radioGroup.setTag(categoryName);
			if (orientation == UI_Constant.CATEGORY_VERTICAL) {
				radioGroup.setOrientation(RadioGroup.VERTICAL);
			} else {
				radioGroup.setOrientation(RadioGroup.HORIZONTAL);
			}

			RadioButton bt = null;
			for (String str : category) {
				bt = newRadioButton(str, orientation); // 实例化新的RadioButton
				if (bt != null) {
					radioGroup.addView(bt);
				}
			}

			radioGroup.setOnCheckedChangeListener(this);
		}
	}

	/**
	 * 
	 * @Description: 根据category名称移除view
	 * 
	 * @author ChengBin
	 * @version 1.0
	 * @date 2014-11-23 下午9:54:21
	 * @param category
	 */
	public void delCategoryByName(String category) {
		for (int i = 0; i < this.getChildCount(); i++) {
			View view = this.getChildAt(i);
			if (view != null) {
				View container = view.findViewById(R.id.container);
				if (container != null) {
					if (category.equals(container.getTag())) {
						this.removeView(view);
					}
				}
			}
		}
	}

	/**
	 * 
	 * @Description: 如果菜单栏已经存在
	 * 
	 * @author ChengBin
	 * @version 1.0
	 * @date 2014-11-23 下午9:31:27
	 * @param category
	 * @return
	 */
	public boolean isCategoryExist(String category) {
		for (int i = 0; i < this.getChildCount(); i++) {
			View view = this.getChildAt(i).findViewById(R.id.container);
			if (view != null) {
				if (category.equals(view.getTag().toString())) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 
	 * @Description: 创建RadioButton
	 * 
	 * @author ChengBin
	 * @version 1.0
	 * @date 2014-11-23 下午2:25:13
	 * @param text
	 * @param orientation
	 * @return
	 */
	private RadioButton newRadioButton(String text, int orientation) {
		RadioButton button = new RadioButton(getContext());
		RadioGroup.LayoutParams params = null;
		if (orientation == UI_Constant.CATEGORY_HORIZONTAL) {
			params = new RadioGroup.LayoutParams(
					RadioGroup.LayoutParams.WRAP_CONTENT,
					RadioGroup.LayoutParams.WRAP_CONTENT);

		} else {
			float width = MobilePhoneDeviceInfo.width;
			float height = MobilePhoneDeviceInfo.height;
			height = height / 5 - 37;
			params = new RadioGroup.LayoutParams(RadioGroup.LayoutParams.WRAP_CONTENT, (int)height);

		}

		// 设置内外边距
		params.leftMargin = 5;
		params.rightMargin = 5;
		params.topMargin = 5;
		params.bottomMargin = 5;
		params.weight = 1;
		button.setLayoutParams(params);
		button.setPadding(6, 6, 6, 6);

		button.setBackgroundResource(R.drawable.selector_category_bg);
		button.setButtonDrawable(android.R.color.transparent);
		button.setTextColor(getResources().getColorStateList(R.color.white));
		button.setGravity(Gravity.CENTER);
		button.setTextSize(16);
		Uri uri = Uri.parse(text);
		button.setText(uri.getAuthority());
		button.setTag(uri.getScheme());

		return button;
	}

	/**
	 * Description: 重写onCheckedChanged事件
	 * 
	 * @param group
	 * @param checkedId
	 * @see android.widget.RadioGroup.OnCheckedChangeListener#onCheckedChanged(android.widget.RadioGroup,
	 *      int)
	 */
	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		if (mListener != null) {
			mListener.click(group, checkedId);
		}
	}

	/**
	 * 
	 * @Description: 设置监听方法
	 * 
	 * @author ChengBin
	 * @version 1.0
	 * @date 2014-11-23 下午2:25:41
	 * @param listener
	 */
	public void setOnClickCategoryListener(OnClickCategoryListener listener) {
		mListener = listener;
	}

	/** 回掉接口 */
	public interface OnClickCategoryListener {
		/** 点击事件发生 */
		public void click(RadioGroup group, int checkedId);
	}
}
