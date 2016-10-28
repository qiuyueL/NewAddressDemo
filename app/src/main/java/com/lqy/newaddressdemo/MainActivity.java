package com.lqy.newaddressdemo;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.lqy.newaddressdemo.databinding.ActivityMainBinding;
import com.lqy.newaddressdemo.model.AddressBean;
import com.lqy.newaddressdemo.popup.CommonPopupWindow;
import com.lqy.newaddressdemo.popup.PopupViewHolder;

import org.apache.http.util.EncodingUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

/**
 * 获取地址正式页面
 * Created by LQY on 2016/10/19.
 */

public class MainActivity extends AppCompatActivity {

	private static String TAG = "MainActivity";
	private ActivityMainBinding binding;
	private AddressBean bean;

	private ArrayAdapter<String> mProvinceAdapter = null;  //省级适配器
	private ArrayAdapter<String> mCityAdapter = null;    //地级适配器
	private ArrayAdapter<String> mAreaAdapter = null;    //县级适配器
	private String[] mProvinceDatas;//省份
	private String[] mCityDatas;//城市
	private String[] mAreaDatas;//地区
	//存储省对应的市
	private Map<String, String[]> mCitiesMap = new HashMap<String,String[]>();
	//存储市对应的区县
	private Map<String, String[]> mAreaesMap = new HashMap<String,String[]>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//DataBinding绑定布局
		binding = DataBindingUtil.setContentView(this,R.layout.activity_main);
		bean = new AddressBean();
		//解析Json获得数据
		CityJsonData(initJson());
		//弹出列表点击事件
		setPopupListener();
	}
	private void setPopupListener() {
		binding.provinceEdit.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				ProvincePopup();
			}
		});
		binding.cityEdit.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				CityPopup();
			}
		});
		binding.areaEdit.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				AreaPopup();
			}
		});
	}

	//弹出省列表
	private void ProvincePopup() {
		mProvinceAdapter = new ArrayAdapter<String>(this,R.layout.popuwindow_lv_item,mProvinceDatas);
		CommonPopupWindow mProvinceDialogue = new CommonPopupWindow(this, R.layout.posting_listview) {
			@Override
			protected void showWindow(PopupViewHolder popupView) {
				popupView.setArrayAdapter(R.id.posting_list, mProvinceAdapter);
				popupView.setOnItemClickListener(R.id.posting_list, new AdapterView.OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
						//将省的全部数据写入实体类中
						bean.setProvince(mProvinceDatas[position]);
						binding.setAddress(bean);
						//关闭列表
						dismiss();
						//根据选出的省更新市级的数据
						updateCity(mProvinceDatas[position]);
					}
				});
			}
		};
		//根据控件显示列表出现的位置
		mProvinceDialogue.listPopup(binding.provinceEdit);
	}

	//弹出城市列表
	private void CityPopup() {
		CommonPopupWindow mCityDialogue = new CommonPopupWindow(this, R.layout.posting_listview) {
			@Override
			protected void showWindow(PopupViewHolder popupView) {
				popupView.setArrayAdapter(R.id.posting_list, mCityAdapter);
				popupView.setOnItemClickListener(R.id.posting_list, new AdapterView.OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
						//将全部市级数据写入实体类
						bean.setCity(mCityDatas[position]);
						binding.setAddress(bean);
						dismiss();
						//根据选出的城市更新区域的数据
						updateArea(mCityDatas[position]);
					}
				});
			}
		};
		//根据控件显示列表出现的位置
		mCityDialogue.listPopup(binding.cityEdit);
	}

	//弹出区域列表
	private void AreaPopup() {
		CommonPopupWindow mAreaDialogue = new CommonPopupWindow(this, R.layout.posting_listview) {
			@Override
			protected void showWindow(PopupViewHolder popupView) {
				popupView.setArrayAdapter(R.id.posting_list, mAreaAdapter);
				popupView.setOnItemClickListener(R.id.posting_list, new AdapterView.OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
						//将全部的区域数据写入实体类
						bean.setArea(mAreaDatas[position]);
						binding.setAddress(bean);
						dismiss();
					}
				});
			}
		};
		//根据控件显示列表出现的位置
		mAreaDialogue.listPopup(binding.areaEdit);
	}

	private void updateCity(String pro){
		mCityDatas = mCitiesMap.get(pro);
		for (int i = 0;i < mCityDatas.length;i++){
			mCityAdapter = new ArrayAdapter<String>(this,R.layout.popuwindow_lv_item,mCityDatas);
			mCityAdapter.notifyDataSetChanged();
		}
	}

	private void updateArea(String city){
		mAreaDatas = mAreaesMap.get(city);
		if (mAreaDatas != null){
			mAreaAdapter = new ArrayAdapter<String>(this,R.layout.popuwindow_lv_item,mAreaDatas);
			mAreaAdapter.notifyDataSetChanged();
		}
	}

	//读取Json文件
	private String initJson(){
		String json = "";
		try {
			InputStream stream = getResources().getAssets().open("city.json");
			Reader reader = new InputStreamReader(stream);
			BufferedReader bufferedReader = new BufferedReader(reader);
			int length = stream.available();
			byte[] buffer = new byte[length];
			stream.read(buffer);
			json= EncodingUtils.getString(buffer,"gb2312");
			bufferedReader.close();
			reader.close();
			stream.close();
		}catch (IOException e){
			e.printStackTrace();
		}
		return json;
	}

	private void CityJsonData(String cityJson){
		Log.v(TAG ,"返回的Json：" + cityJson);
		if (!TextUtils.isEmpty(cityJson)){
			try {
				JSONObject object = new JSONObject(cityJson);
				JSONArray array = object.getJSONArray("citylist");
				//获取省份数量
				mProvinceDatas = new String[array.length()];
				//循环遍历
				for (int i = 0;i < array.length();i++){
					//循环遍历省份，将省份保存在mProvinceDatas中
					JSONObject mProvinceObject = array.getJSONObject(i);
					if (mProvinceObject.has("p")){
						mProvinceDatas[i] =mProvinceObject.getString("p");
					} else {
						mProvinceDatas[i] ="unknown province";
					}
					//遍历循环城市
					JSONArray cityArray;
					try {
						//循环遍历省对应的城市
						cityArray = mProvinceObject.getJSONArray("c");
					} catch (Exception e){
						e.printStackTrace();
						continue;
					}
					mCityDatas = new String[cityArray.length()];
					for (int j = 0;j <cityArray.length();j++){
						//循环遍历城市，将城市保存在mCityDatas中
						JSONObject mCityObject = cityArray.getJSONObject(j);
						if (mCityObject.has("n")) {
							mCityDatas[j] = mCityObject.getString("n");
						} else {
							mCityDatas[j] = "unknown city";
						}
						//遍历循环地区
						JSONArray areaArray;
						try {
							// 判断是否含有第三级区域划分，若没有，则跳出本次循环，重新执行循环遍历城市
							areaArray = mCityObject.getJSONArray("a");
						} catch (Exception e){
							e.printStackTrace();
							continue;
						}
						// 执行下面过程则说明存在第三级区域
						mAreaDatas = new String[areaArray.length()];
						for (int a=0;a < areaArray.length();a++){
							// 循环遍历第三级区域，并将区域保存在mAreaDatas[]中
							JSONObject areaObject = areaArray.getJSONObject(a);
							if (areaObject.has("s")){
								mAreaDatas[a] = areaObject.getString("s");
							} else {
								mAreaDatas[a] = "unknown area";
							}
						}
						// 存储市对应的所有第三级区域
						mAreaesMap.put(mCityDatas[j], mAreaDatas);
					}
					// 存储省份对应的所有市
					mCitiesMap.put(mProvinceDatas[i], mCityDatas);
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}

		}
	}
}
