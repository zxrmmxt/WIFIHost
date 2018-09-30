package com.zhf.wifidemo.ui;

import android.os.Message;

import com.zhf.wifidemo.wifi.MainActivity;

/**
 * Wifi��������
 * @author ZHF
 *
 */
public class WFSearchProcess implements Runnable {
	
	public MainActivity context;
	public WFSearchProcess(MainActivity context) {
		this.context = context;
	}

	public boolean running = false;
	private long startTime = 0L;
	private Thread thread  = null;
			
	@Override
	public void run() {
		while(true) {
			//�Ƿ�
			if(!running) return;
			if(System.currentTimeMillis() - startTime >= 30000L) {
				//���ͣ�������ʱ����Ϣ
				Message msg = context.mHandler.obtainMessage(context.m_nWifiSearchTimeOut);
				context.mHandler.sendMessage(msg);
			}
			try {
				Thread.sleep(10L);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void start() {
		try {
			thread = new Thread(this);
			running = true;
			startTime = System.currentTimeMillis();
			thread.start(); //�����߳�
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public void stop() {
		try {
			running = false;
			thread = null;
			startTime = 0L;
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
