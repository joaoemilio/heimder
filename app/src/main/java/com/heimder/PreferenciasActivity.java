package com.heimder;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ToggleButton;

import com.heimder.alarm.SampleAlarmReceiver;
import com.heimder.dao.ConfigDAO;
import com.heimder.domain.Empresa;
import com.heimder.service.EmpresaService;

import heimder.com.heimder.R;

public class PreferenciasActivity extends HeimderAppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferencias);

        ToggleButton toggle = (ToggleButton) findViewById(R.id.toggleButton);
        toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Heimder.getInstance().setRunning(true);
                    SampleAlarmReceiver alarmReceiver = new SampleAlarmReceiver();
                    alarmReceiver.setAlarm(Heimder.getInstance().getContext());
                } else {
                    Heimder.getInstance().setRunning(false);
                    SampleAlarmReceiver alarmReceiver = new SampleAlarmReceiver();
                    alarmReceiver.cancelAlarm(Heimder.getInstance().getContext());
                }
            }
        });

        final EditText intervalEditText = (EditText)findViewById(R.id.intervaloxxxEditText);
        Button salvarButton = (Button)findViewById(R.id.buttonSalvarPreferencias);
        salvarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer intervalo = Integer.parseInt(intervalEditText.getText().toString());
                ConfigDAO dao = new ConfigDAO();
                dao.setIntervalo(intervalo);
                finish();
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();  // Always call the superclass method first

        ToggleButton toggle = (ToggleButton) findViewById(R.id.toggleButton);
        toggle.setChecked(Heimder.getInstance().isRunning());

        EditText intervalEditText = (EditText)findViewById(R.id.intervaloxxxEditText);
        intervalEditText.setText(Heimder.getInstance().getInterval().toString());
    }
}
