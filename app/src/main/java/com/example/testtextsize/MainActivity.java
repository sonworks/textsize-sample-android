package com.example.testtextsize;

import java.net.ContentHandler;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.Menu;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	private final String TAG = "MainActivity";
	
	private Toast toast = null;
	
    private float scaledDensity = 0f;
    private int statusBarHeight = 0;
    private int displayWidth = 0;
    private int displayHeight = 0;
    private boolean isTabletDevice = false;
    double screenInches = 0.0;
    int densityDpi = 0;
    	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		getDisplayInfo();
		
		float size = 15.f;
		
        // setTextSize(TypedValue.COMPLEX_UNIT_PT, 20) = 20pt
        // setTextSize(TypedValue.COMPLEX_UNIT_PX, 20) = 20px
        // setTextSize(TypedValue.COMPLEX_UNIT_SP, 20) = 20sp
        // setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20) = 20dip// device independent pixel
        // setTextSize(TypedValue.COMPLEX_UNIT_MM, 20) = 20mm // Milli meter
        // setTextSize(TypedValue.COMPLEX_UNIT_IN, 20) = 20in // inch
		
		// ScrollView
        ScrollView scrView = new ScrollView(this);
        
        LinearLayout linear;
        linear = new LinearLayout(this);
        linear.setOrientation(LinearLayout.VERTICAL);
        

        float sizeCale = size/scaledDensity;
        logD("sizeCale:" + sizeCale, null);
        StringBuilder sb = new StringBuilder();
        sb.append("densityDpi" + densityDpi).append("\n");
        sb.append("size(" + size).append(")");
        sb.append("/scaledDensity(" + scaledDensity).append("%)").append("\n");
        sb.append("=sizeCale(" + sizeCale).append(")").append("\n");
        sb.append("-----------------------------").append("\n");
        TextView strDisplayInfo = new TextView(this);
        strDisplayInfo.setText(sb);
        linear.addView(strDisplayInfo);
        
        //scale
        float pixelsToSpSize = 14.f;//pixelsToSp(this, size);
        TextView scaleText1 = new TextView(this);
        scaleText1.setText("スケール文字列(" + pixelsToSpSize + "sp):");
        scaleText1.setTextSize(TypedValue.COMPLEX_UNIT_SP, pixelsToSpSize);
        linear.addView(scaleText1);
        TextView scaleText2 = new TextView(this);
        scaleText2.setText("↑↑↑(" + scaleText1.getTextSize() + "px):");
        scaleText2.setTextSize(TypedValue.COMPLEX_UNIT_PX, scaleText1.getTextSize());
        linear.addView(scaleText2);
        
        
        // Default
        TextView outputText1 = new TextView(this);
        outputText1.setText("テスト文字列(default):" + outputText1.getTextSize() + "px");
        linear.addView(outputText1);
        
        TextView outputText2 = new TextView(this);
        outputText2.setTextSize(TypedValue.COMPLEX_UNIT_PX, size); 
        outputText2.setText("テスト文字列(" + size + "px):" + outputText2.getTextSize() + "px");
        linear.addView(outputText2);

        TextView outputText3 = new TextView(this);
        outputText3.setTextSize(TypedValue.COMPLEX_UNIT_PT, size);
        outputText3.setText("⇒テスト文字列(" + size + "pt):" + outputText3.getTextSize() + "px");
        linear.addView(outputText3);
        
        TextView outputText4 = new TextView(this);
        outputText4.setTextSize(TypedValue.COMPLEX_UNIT_PT, sizeCale);
        outputText4.setText("⇒テスト文字列(" + sizeCale + "pt):" + outputText4.getTextSize() + "px");
        linear.addView(outputText4);
        
        // font bold & italic
        TextView outputText5 = new TextView(this);
        outputText5.setTextSize(TypedValue.COMPLEX_UNIT_MM, size);
        outputText5.setText("テスト文字列(15mm):" + outputText5.getTextSize() + "px");
        linear.addView(outputText5);
        
        scrView.addView(linear);
        
        setContentView(scrView);
		
        //カメラ有無確認
        String checkCameraHardware = "";
        if(this.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
        	checkCameraHardware += "FEATURE_CAMERA";
        }
        if(this.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FRONT)) {
        	checkCameraHardware += "/FEATURE_CAMERA_FRONT";
        }
        toastShow(checkCameraHardware, Toast.LENGTH_LONG);
        
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	
	private float pixelsToSp(Context context, Float px) {
		float scaledensity = context.getResources().getDisplayMetrics().scaledDensity;
		return px/scaledensity;
	}
	
    private void getDisplayInfo() {
		final WindowManager windowManager = (WindowManager)getSystemService(Context.WINDOW_SERVICE);
		final Display display = windowManager.getDefaultDisplay();
		final DisplayMetrics displayMetrics = new DisplayMetrics();
	    display.getMetrics(displayMetrics);
		
		//Scale Density
        scaledDensity = displayMetrics.scaledDensity;
        logD("scaledDensity= " + scaledDensity, null);
		
        densityDpi = displayMetrics.densityDpi;
        logD("densityDpi= " + densityDpi, null);
        
        
        
        //端末の高さ情報(onCreateで取得)
        final int LOW_DPI_STATUS_BAR_HEIGHT = 19;
        final int MEDIUM_DPI_STATUS_BAR_HEIGHT = 25;
        final int HIGH_DPI_STATUS_BAR_HEIGHT = 38;
        switch (displayMetrics.densityDpi) {
        case DisplayMetrics.DENSITY_HIGH:
            statusBarHeight = HIGH_DPI_STATUS_BAR_HEIGHT * (int)scaledDensity;
            break;
        case DisplayMetrics.DENSITY_MEDIUM:
            statusBarHeight = MEDIUM_DPI_STATUS_BAR_HEIGHT * (int)scaledDensity;
            break;
        case DisplayMetrics.DENSITY_LOW:
            statusBarHeight = LOW_DPI_STATUS_BAR_HEIGHT * (int)scaledDensity;
            break;
        default:
            statusBarHeight = MEDIUM_DPI_STATUS_BAR_HEIGHT * (int)scaledDensity;
        }
        logD("statusBarHeight:" + statusBarHeight, null);
        
        //Display Size
        displayWidth = display.getWidth();
        displayHeight = display.getHeight(); 
        logD("displayWidth:" + displayWidth + "/displayHeight:" + displayHeight, null);
        
        //Display Inches Size
        double xInches = Math.pow(displayMetrics.widthPixels/displayMetrics.xdpi, 2);
        double yInches = Math.pow(displayMetrics.heightPixels/displayMetrics.ydpi, 2);
        screenInches = Math.sqrt(xInches + yInches);
        logD("screenInches:" + screenInches, null);
        if(screenInches >= 7) {
        	isTabletDevice = true;
        }
    }	

    private void toastShow(String msg, int duration) {
        if(toast != null) {
            toast.cancel();
            toast = null;
        }
        toast = Toast.makeText(getApplicationContext(), msg, duration);
        toast.show();
    }
    
    private void logD(String msg, Exception exception) {
    	if(exception == null) {
    		Log.d(TAG, "### " + msg);
    	} else {
    		Log.d(TAG, "### " + msg, exception);
    	}
    }    
}
