package com.zhf.wifidemo.wifi;

import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.zhf.wifidemo.R;
import com.zhf.wifidemo.wifi.utils.WifiAdmin;

import java.util.List;

/**
 * �����б�������
 * @author ZHF
 *
 */
public class WTAdapter extends BaseAdapter {
	private LayoutInflater mInflater;
	private List<ScanResult> mList;  //ɨ�赽���������б�
	private MainActivity mContext;

	public WTAdapter(MainActivity context, List<ScanResult> list) {
		// TODO Auto-generated constructor stub
		this.mContext = context;
		this.mList = list;
		this.mInflater = LayoutInflater.from(context);
	}

	//�¼ӵ�һ��������������������
	public void setData(List<ScanResult> list) {
		this.mList = list;
		notifyDataSetChanged();
	}
	
	@Override
	public int getCount() {
		return mList.size();
	}

	@Override
	public Object getItem(int position) {
		return mList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		//��ȡ������ɨ����
		final ScanResult localScanResult = mList.get(position);
		//��ȡwifi��
//		final WifiAdmin wifiAdmin = WifiAdmin.getInstance(mContext);
		final WifiAdmin wifiAdmin = mContext.m_wiFiAdmin;
		final ViewHolder viewHolder;
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.wtitem, null);
			//���ز���ģ��ؼ�
			viewHolder.textVName = ((TextView) convertView.findViewById(R.id.name_text_wtitem));
			viewHolder.textConnect = ((TextView) convertView.findViewById(R.id.connect_text_wtitem));
			viewHolder.linearLConnectOk = ((LinearLayout) convertView.findViewById(R.id.connect_ok_layout_wtitem));
			viewHolder.progressBConnecting = ((ProgressBar) convertView.findViewById(R.id.connecting_progressBar_wtitem));
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		//������Ӵ����¼�
		viewHolder.textConnect.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//����wifi����
//				WifiConfiguration localWifiConfiguration = wifiAdmin.createWifiInfo(localScanResult.SSID, MainActivity.WIFI_AP_PASSWORD, 3,"wt");
//				//��ӵ�����
//				wifiAdmin.addNetworkAndConnect(localWifiConfiguration);
				wifiAdmin.Wificonnect(localScanResult.SSID, MainActivity.WIFI_AP_PASSWORD);
				//"�������"��ʧ����ʾ��������
				viewHolder.textConnect.setVisibility(View.GONE);
				viewHolder.progressBConnecting.setVisibility(View.VISIBLE);
				viewHolder.linearLConnectOk.setVisibility(View.GONE);
				//�����3.5s������Ϣ
				mContext.mHandler.sendEmptyMessageDelayed(mContext.m_nWTConnected, 3500L);
			}
		});
		// ����Ͽ������¼�
		viewHolder.linearLConnectOk.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//�Ͽ�ָ��wifi�ȵ�
				wifiAdmin.disconnectWifi(wifiAdmin.getWifiInfo().getNetworkId());
				//"�Ͽ�����"��ʧ����������ʾ
				viewHolder.textConnect.setVisibility(View.GONE);
				viewHolder.progressBConnecting.setVisibility(View.VISIBLE);
				viewHolder.linearLConnectOk.setVisibility(View.GONE);
				//�����3.5s������Ϣ
				mContext.mHandler.sendEmptyMessageDelayed(mContext.m_nWTConnected, 3500L);
			}
		});

		//��ʼ������
		viewHolder.textConnect.setVisibility(View.GONE);
		viewHolder.progressBConnecting.setVisibility(View.GONE);
		viewHolder.linearLConnectOk.setVisibility(View.GONE);
		viewHolder.textVName.setText(localScanResult.SSID); //��ʾ�ȵ�����
		
		// �����ӵ�wifi��Ϣ
		WifiInfo localWifiInfo = wifiAdmin.getWifiInfo();
		if (localWifiInfo != null) {
			try {//��������
				String ssid = localWifiInfo.getSSID();
				System.out.println("��ǰ���ӵ�wifi------------------��"+ssid);
				System.out.println("ɨ�赽��wifi-------------------��"+localScanResult.SSID);
				if ((localWifiInfo.getSSID() != null)&& (ssid.equals(localScanResult.SSID)||(ssid.equals("\""+localScanResult.SSID+"\"")))){
					viewHolder.linearLConnectOk.setVisibility(View.VISIBLE);
					return convertView;
				}
			} catch (NullPointerException e) {
				e.printStackTrace();
				return convertView;
			}
			viewHolder.textConnect.setVisibility(View.VISIBLE);
		}
		return convertView;
	}

	public final class ViewHolder {
		public LinearLayout linearLConnectOk;
		public ProgressBar progressBConnecting;
		public TextView textConnect;
		public TextView textVName;

		public ViewHolder() {
		}
	}
}
