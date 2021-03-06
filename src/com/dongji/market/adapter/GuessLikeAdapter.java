package com.dongji.market.adapter;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.dongji.market.R;
import com.dongji.market.activity.ApkDetailActivity;
import com.dongji.market.cache.FileService;
import com.dongji.market.pojo.ApkItem;

/**
 * 猜你喜欢gridview适配器
 * 
 * @author yvon
 * 
 */
public class GuessLikeAdapter extends BaseAdapter {

	private Activity context;
	private List<ApkItem> data;
	private Bitmap defaultBitmap_icon;

	public GuessLikeAdapter() {
		super();
	}

	public GuessLikeAdapter(Activity context, List<ApkItem> data, Bitmap defaultBitmap_icon) {
		super();
		this.context = context;
		this.data = data;
		this.defaultBitmap_icon = defaultBitmap_icon;
	}

	@Override
	public int getCount() {
		return data == null ? 0 : data.size();
	}

	@Override
	public Object getItem(int position) {
		return data.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = LayoutInflater.from(context).inflate(R.layout.item_guess_gridview, null);
			holder.mIconImage = (ImageView) convertView.findViewById(R.id.app_icon);
			holder.mTextView = (TextView) convertView.findViewById(R.id.app_name);
			holder.ivdongji_guess = (ImageView) convertView.findViewById(R.id.ivdongji_guess);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		if (data.get(position).heavy > 0) {// 是否为推荐应用
			holder.ivdongji_guess.setVisibility(View.VISIBLE);
		} else {
			holder.ivdongji_guess.setVisibility(View.GONE);
		}

		try {
			FileService.getBitmap(data.get(position).appIconUrl, holder.mIconImage, defaultBitmap_icon, 0);
		} catch (OutOfMemoryError e) {
			if (defaultBitmap_icon != null && !defaultBitmap_icon.isRecycled()) {
				defaultBitmap_icon.recycle();
			}
		}

		holder.mTextView.setText(data.get(position).appName);
		convertView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				Bundle bundle = new Bundle();
				bundle.putParcelable("apkItem", data.get(position));
				intent.putExtras(bundle);
				intent.setClass(context, ApkDetailActivity.class);
				context.startActivity(intent);
				if (!context.isFinishing() && context.getClass().equals(ApkDetailActivity.class)) {
					context.finish();
				}
			}
		});
		return convertView;
	}

	private static class ViewHolder {
		ImageView mIconImage;
		ImageView ivdongji_guess;
		TextView mTextView;
	}

	public void setData(List<ApkItem> data) {
		this.data = data;
		notifyDataSetChanged();
	}

}
