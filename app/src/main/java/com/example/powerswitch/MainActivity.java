package com.example.powerswitch;

import android.accessibilityservice.AccessibilityService;
import android.app.Activity;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View;
public class MainActivity extends Activity {

    private DevicePolicyManager dpm;
    private ComponentName adminComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
		View root = findViewById(android.R.id.content);
		int statusBarHeight = 0;
		int resId = getResources().getIdentifier("status_bar_height", "dimen", "android");
		if (resId > 0) {
    		statusBarHeight = getResources().getDimensionPixelSize(resId);
		}
		root.setPadding(0, statusBarHeight, 0, 0);
        dpm = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);
        adminComponent = new ComponentName(this, AdminReceiver.class);

        findViewById(R.id.btnShutdown).setOnClickListener(v -> {
            PowerAccessibilityService svc = PowerAccessibilityService.instance;
            if (svc == null) {
                Toast.makeText(this, "AccessibilityService Is Inactive, Please Turn On the Item Of The Software.", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS));
                return;
            }
            svc.performGlobalAction(AccessibilityService.GLOBAL_ACTION_POWER_DIALOG);
        });

        findViewById(R.id.btnSleep).setOnClickListener(v -> {
            if (!dpm.isAdminActive(adminComponent)) {
                Toast.makeText(this, "Please Signing In DeviceManager First.", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
                intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, adminComponent);
                startActivity(intent);
                return;
            }
            dpm.lockNow();
        });
        findViewById(R.id.btnTaskm).setOnClickListener(v -> {
            PowerAccessibilityService svc = PowerAccessibilityService.instance;
            if (svc == null) {
                Toast.makeText(this, "Accessibility Is Not Active, Please Turn On the Item of The Software First", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS));
                return;
            }
            svc.performGlobalAction(AccessibilityService.GLOBAL_ACTION_RECENTS);
        });

        findViewById(R.id.btnSleep2).setOnClickListener(v -> {
            PowerAccessibilityService svc = PowerAccessibilityService.instance;
            if (svc == null) {
                Toast.makeText(this, "Accessibility Is Not Active, Please Turn On the Item of The Software First", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS));
                return;
            }
            svc.performGlobalAction(AccessibilityService.GLOBAL_ACTION_LOCK_SCREEN);
        });


        updateStatus();
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateStatus();
    }

    private void updateStatus() {
        TextView tv = findViewById(R.id.tvStatus);
        String acc = PowerAccessibilityService.instance != null ? "已开启" : "未开启";
        String adm = dpm.isAdminActive(adminComponent) ? "已激活" : "未激活";
        tv.setText("无障碍：" + acc + "\n设备管理员：" + adm);
    }
}
