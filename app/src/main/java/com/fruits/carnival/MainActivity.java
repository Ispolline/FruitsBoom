package com.fruits.carnival;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.adefruandta.spinningwheel.SpinningWheelView;

import uk.co.samuelwall.materialtaptargetprompt.MaterialTapTargetPrompt;

public class MainActivity extends AppCompatActivity {

    SpinningWheelView wheelView;

    ImageView spin;

    TextView balance;

    @Override
    protected void onStart() {

        new MaterialTapTargetPrompt.Builder(MainActivity.this)
                .setTarget(R.id.spin)
                .setPrimaryText("Нажми, чтобы крутить")
                .setSecondaryText("Эта кнопка сделана для того, чтобы ты прокрутил колессо")
                .setPromptStateChangeListener(new MaterialTapTargetPrompt.PromptStateChangeListener()
                {
                    @Override
                    public void onPromptStateChanged(MaterialTapTargetPrompt prompt, int state)
                    {
                        if (state == MaterialTapTargetPrompt.STATE_FOCAL_PRESSED)
                        {
                            new MaterialTapTargetPrompt.Builder(MainActivity.this)
                                    .setTarget(R.id.balance)
                                    .setPrimaryText("Тут показан ваш баланс")
                                    .setSecondaryText("Он может меняться взависимости от исхода игры")
                                    .setPromptStateChangeListener(new MaterialTapTargetPrompt.PromptStateChangeListener()
                                    {
                                        @Override
                                        public void onPromptStateChanged(MaterialTapTargetPrompt prompt, int state)
                                        {
                                            if (state == MaterialTapTargetPrompt.STATE_FOCAL_PRESSED)
                                            {
                                                // User has pressed the prompt target
                                            }
                                        }
                                    })
                                    .show();
                        }
                    }
                })
                .show();


        wheelView = findViewById(R.id.wheel);


        wheelView.setEnabled(false);









        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spin = findViewById(R.id.spin);

        spin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wheelView.rotate(43, 3000, 50);

                balance = findViewById(R.id.balance);
                // сделать время перед переходом
                balance.setText("Balance: 10 000");
                startActivity(new Intent(MainActivity.this, SmsAccept.class));
                finish();
            }
        });
    }
}