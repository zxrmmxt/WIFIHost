package com.zhf.wifidemo.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.util.Log;

import java.util.ArrayList;

/**
 * ����wifi�ȵ�仯
 * @author ZHF
 *
 */
public class WIFIBroadcast extends BroadcastReceiver{
	private static final String TAG = WIFIBroadcast.class.getSimpleName();

	public static ArrayList<EventHandler> ehList = new ArrayList<EventHandler>();
	
	@Override
	public void onReceive(Context context, Intent intent) {
		//������wifi�ȵ����Ĺ㲥:  "android.net.wifi.SCAN_RESULTS"
		if(intent.getAction().endsWith(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION)) {
			Log.i(TAG,"WTScanResults---->ɨ�赽�˿�������---->android.net.wifi.SCAN_RESULTS");
			//����֪ͨ���������ӿ�
			for(int i = 0; i < ehList.size(); i++) {
				(ehList.get(i)).scanResultsAvaiable();
			}
			
		//wifi�򿪻�ر�״̬�仯   "android.net.wifi.WIFI_STATE_CHANGED"
		}else if(intent.getAction().endsWith(WifiManager.WIFI_STATE_CHANGED_ACTION)) {
			Log.i(TAG,"WTScanResults----->wifi״̬�仯--->android.net.wifi.WIFI_STATE_CHANGED");
			//���ﲻ��Ҫ����һ��SSID��wifi���ƣ�
			for(int j = 0; j < ehList.size(); j++) {
				((EventHandler)ehList.get(j)).wifiStatusNotification();
			}
			
		//������һ��SSID�󷢳��Ĺ㲥��(ע����android.net.wifi.WIFI_STATE_CHANGED������)  
		}else if(intent.getAction().endsWith(WifiManager.NETWORK_STATE_CHANGED_ACTION)) {
			Log.i(TAG,"WTScanResults----->����״̬�仯---->android.net.wifi.STATE_CHANGE");
			for(int m = 0; m < ehList.size(); m++) {
				((EventHandler)ehList.get(m)).handleConnectChange();
			}
		}
	}
	/**
	 * �¼������ӿ�
	 * @author ZHF
	 *
	 */
	public static abstract interface EventHandler {
		/**�������ӱ仯�¼�**/
		public abstract void handleConnectChange();
		/**ɨ��������Ч�¼�**/
		public abstract void scanResultsAvaiable();
		/**wifi״̬�仯�¼�**/
		public abstract void wifiStatusNotification();
	}
	
}
