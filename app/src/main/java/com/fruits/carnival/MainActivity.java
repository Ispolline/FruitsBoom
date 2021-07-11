package com.fruits.carnival;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import uk.co.samuelwall.materialtaptargetprompt.MaterialTapTargetPrompt;

public class MainActivity extends AppCompatActivity {

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









        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}