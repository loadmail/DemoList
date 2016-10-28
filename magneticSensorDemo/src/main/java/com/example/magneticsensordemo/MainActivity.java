package com.example.magneticsensordemo;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 功能：采集加速度传感器，磁场传感器和陀螺仪传感器的数据，得到传感器从外界采集数据的时间间隔
 * @author ZSY
 *
 */
public class MainActivity extends Activity implements SensorEventListener {
	private SensorManager sensorManager;
	private Sensor magneticSensor;
	private TextView showTextView;
	private Sensor accelerometerSensor;
	private Sensor gyroscopeSensor;
	// 将纳秒转化为秒
	private static final float NS2S = 1.0f / 1000000000.0f;
	private float timestamp;
	private float angle[] = new float[3];

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);


		sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		magneticSensor = sensorManager
				.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
		accelerometerSensor = sensorManager
				.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		gyroscopeSensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
		if (gyroscopeSensor == null) {
			Toast.makeText(MainActivity.this,"手机不支持陀螺仪",Toast.LENGTH_SHORT);
		}

		//注册陀螺仪传感器，并设定传感器向应用中输出的时间间隔类型是SensorManager.SENSOR_DELAY_GAME(20000微秒)
		//SensorManager.SENSOR_DELAY_FASTEST(0微秒)：最快。最低延迟，一般不是特别敏感的处理不推荐使用，该模式可能在成手机电力大量消耗，由于传递的为原始数据，诉法不处理好会影响游戏逻辑和UI的性能
		//SensorManager.SENSOR_DELAY_GAME(20000微秒)：游戏。游戏延迟，一般绝大多数的实时性较高的游戏都是用该级别
		//SensorManager.SENSOR_DELAY_NORMAL(200000微秒):普通。标准延时，对于一般的益智类或EASY级别的游戏可以使用，但过低的采样率可能对一些赛车类游戏有跳帧现象
		//SensorManager.SENSOR_DELAY_UI(60000微秒):用户界面。一般对于屏幕方向自动旋转使用，相对节省电能和逻辑处理，一般游戏开发中不使用
		sensorManager.registerListener(this, gyroscopeSensor,
				SensorManager.SENSOR_DELAY_GAME);
		 sensorManager.registerListener(this, magneticSensor,
		 SensorManager.SENSOR_DELAY_GAME);
		sensorManager.registerListener(this, accelerometerSensor,
				SensorManager.SENSOR_DELAY_GAME);

	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	//坐标轴都是手机从左侧到右侧的水平方向为x轴正向，从手机下部到上部为y轴正向，垂直于手机屏幕向上为z轴正向
	@Override
	public void onSensorChanged(SensorEvent event) {
boolean hasGYROSCOPE = false;
		while (!hasGYROSCOPE && event.sensor.getType() == Sensor.TYPE_GYROSCOPE) {
			hasGYROSCOPE = true;
		}

		if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
			// x,y,z分别存储坐标轴x,y,z上的加速度
			float x = event.values[0];
			float y = event.values[1];
			float z = event.values[2];
			// 根据三个方向上的加速度值得到总的加速度值a
			float a = (float) Math.sqrt(x * x + y * y + z * z);
			System.out.println("a---------->" + a);
			// 传感器从外界采集数据的时间间隔为10000微秒
			System.out.println("magneticSensor.getMinDelay()-------->"
					+ magneticSensor.getMinDelay());
			// 加速度传感器的最大量程
			System.out.println("event.sensor.getMaximumRange()-------->"
					+ event.sensor.getMaximumRange());

			 Log.e("TAG","x------------->" + x);
			 Log.e("TAG", "y------------>" + y);
			 Log.e("TAG", "z----------->" + z);

		} else if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
			// 三个坐标轴方向上的电磁强度，单位是微特拉斯(micro-Tesla)，用uT表示，也可以是高斯(Gauss),1Tesla=10000Gauss
			float x = event.values[0];
			float y = event.values[1];
			float z = event.values[2];
			// 手机的磁场感应器从外部采集数据的时间间隔是10000微秒
			System.out.println("magneticSensor.getMinDelay()-------->"
					+ magneticSensor.getMinDelay());
			// 磁场感应器的最大量程
			System.out.println("event.sensor.getMaximumRange()----------->"
					+ event.sensor.getMaximumRange());
			System.out.println("x------------->" + x);
			System.out.println("y------------->" + y);
			System.out.println("z------------->" + z);
		} else if (event.sensor.getType() == Sensor.TYPE_GYROSCOPE) {
			//从 x、y、z 轴的正向位置观看处于原始方位的设备，如果设备逆时针旋转，将会收到正值；否则，为负值
			 if(timestamp != 0){ 
			// 得到两次检测到手机旋转的时间差（纳秒），并将其转化为秒
			 final float dT = (event.timestamp - timestamp) * NS2S;
			// 将手机在各个轴上的旋转角度相加，即可得到当前位置相对于初始位置的旋转弧度
			 angle[0] += event.values[0] * dT;
			 angle[1] += event.values[1] * dT;
			 angle[2] += event.values[2] * dT;
			// 将弧度转化为角度
			 float anglex = (float) Math.toDegrees(angle[0]);
			 float angley = (float) Math.toDegrees(angle[1]);
			 float anglez = (float) Math.toDegrees(angle[2]);




				 Log.e("执行","anglex------------>" + anglex);
				 Log.e("执行","angley------------>" + angley);
				 Log.e("执行","anglez------------>" + anglez);
				 Log.e("执行","gyroscopeSensor.getMinDelay()----------->" +
						 gyroscopeSensor.getMinDelay());

			 }
			 //将当前时间赋值给timestamp
			 timestamp = event.timestamp;

		}

	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		sensorManager.unregisterListener(this);
	}
}
