package com.example.ai_news_summary;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class SummarySettingsActivity extends AppCompatActivity {

    private SeekBar sbFontSize, sbSpeed;
    private Switch swAutoRead, swOfflineCache, swWifiOnly;
    private TextView tvPreview;
    private Button btnSave;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary_settings);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        sharedPreferences = getSharedPreferences("settings", MODE_PRIVATE);

        initViews();
        loadSettings();
        setupListeners();
    }

    private void initViews() {
        sbFontSize = findViewById(R.id.sb_font_size);
        sbSpeed = findViewById(R.id.sb_speed);
        swAutoRead = findViewById(R.id.sw_auto_read);
        swOfflineCache = findViewById(R.id.sw_offline_cache);
        swWifiOnly = findViewById(R.id.sw_wifi_only);
        tvPreview = findViewById(R.id.tv_preview);
        btnSave = findViewById(R.id.btn_save);
    }

    private void loadSettings() {
        int fontSize = sharedPreferences.getInt("font_size", 14);
        int speed = sharedPreferences.getInt("speed", 50);
        boolean autoRead = sharedPreferences.getBoolean("auto_read", false);
        boolean offlineCache = sharedPreferences.getBoolean("offline_cache", false);
        boolean wifiOnly = sharedPreferences.getBoolean("wifi_only", false);

        sbFontSize.setProgress(fontSize - 10);
        sbSpeed.setProgress(speed);
        swAutoRead.setChecked(autoRead);
        swOfflineCache.setChecked(offlineCache);
        swWifiOnly.setChecked(wifiOnly);

        updatePreviewText(fontSize);
    }

    private void setupListeners() {
        sbFontSize.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int fontSize = 10 + progress;
                updatePreviewText(fontSize);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        btnSave.setOnClickListener(v -> saveSettings());
    }

    private void updatePreviewText(int fontSize) {
        tvPreview.setTextSize(fontSize);
    }

    private void saveSettings() {
        int fontSize = 10 + sbFontSize.getProgress();
        int speed = sbSpeed.getProgress();
        boolean autoRead = swAutoRead.isChecked();
        boolean offlineCache = swOfflineCache.isChecked();
        boolean wifiOnly = swWifiOnly.isChecked();

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("font_size", fontSize);
        editor.putInt("speed", speed);
        editor.putBoolean("auto_read", autoRead);
        editor.putBoolean("offline_cache", offlineCache);
        editor.putBoolean("wifi_only", wifiOnly);
        editor.apply();

        Toast.makeText(this, "设置已保存", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}