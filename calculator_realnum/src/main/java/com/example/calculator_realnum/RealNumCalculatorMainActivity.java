package com.example.calculator_realnum;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class RealNumCalculatorMainActivity extends AppCompatActivity {

    boolean isFirstInput = true;

    ScrollView scrollView;
    TextView resultExpressionTextView;
    TextView resultTextView;

    ImageButton allClearButton;
    ImageButton clearEntryButton;
    ImageButton backspaceButton;
    ImageButton decimalButton;

    Button[] numberButton = new Button[10];
    ImageButton[] operatorButton = new ImageButton[5];

    Calculator_RealNum calculator = new Calculator_RealNum();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_real_num_calculator_main);
        scrollView = findViewById(R.id.scroll_view);
        resultExpressionTextView = findViewById(R.id.result_operator_text_view);
        resultTextView = findViewById(R.id.result_text_view);

        allClearButton = findViewById(R.id.all_clear_button);
        clearEntryButton = findViewById(R.id.clear_entry_button);
        backspaceButton = findViewById(R.id.backspace_button);
        decimalButton = findViewById(R.id.decimal_button);

        for (int i = 0; i < numberButton.length; i++) {
            numberButton[i] = findViewById(R.id.num_button_0 + i);
        }

        for (int i = 0; i < operatorButton.length; i++) {
            operatorButton[i] = findViewById(R.id.operator_button_0 + i);
        }

        for (Button button : numberButton) {
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    numberButtonClick(v);
                }
            });
        }

        for (ImageButton imageButton : operatorButton) {
            imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    operatorButtonClick(v);
                }
            });
        }

        allClearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                allClearButtonClick(v);
            }
        });

        clearEntryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearEntryButtonClick(v);
            }
        });

        backspaceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backspaceButtonClick(v);
            }
        });

        decimalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                decimalButtonClick(v);
            }
        });
    }

    private void decimalButtonClick(View v) {
        if (isFirstInput) {
            resultTextView.setTextColor(0xFF000000);
            resultTextView.setText("0.");
            isFirstInput = false;
        } else {
            if (resultTextView.getText().toString().contains(".")) {
                Toast.makeText(getApplicationContext(), "이미 소숫점이 존재합니다.", Toast.LENGTH_SHORT).show();
            } else {
                resultTextView.append(".");
            }
        }

    }

    private void backspaceButtonClick(View v) {
        if (isFirstInput && !calculator.getExpressionString().equals("")) {
            Toast.makeText(getApplicationContext(), "결과값은 지울 수 없습니다.", Toast.LENGTH_SHORT).show();
        } else {
            if (resultTextView.getText().toString().length() > 1) {
                String getResultString = resultTextView.getText().toString().replace(",", "");
                String subString = getResultString.substring(0, getResultString.length() - 1);
                String decimalString = calculator.getDecimalString(subString);
                resultTextView.setText(decimalString);
            } else {
                clearText();
            }
        }
    }

    private void clearEntryButtonClick(View v) {
        clearText();
    }

    private void allClearButtonClick(View v) {
        calculator.setAllClear();
        resultExpressionTextView.setText(calculator.getExpressionString());
        clearText();
    }

    private void clearText() {
        isFirstInput = true;
        resultTextView.setTextColor(0xFF666666);
        resultTextView.setText(calculator.getClearInputText());
    }

    private void operatorButtonClick(View v) {
        String getResultString = resultTextView.getText().toString();
        String operator = v.getTag().toString();
        String getResult = calculator.getResult(isFirstInput, getResultString, operator);
        resultTextView.setText(getResult);
        resultExpressionTextView.setText(calculator.getExpressionString());
        isFirstInput = true;
    }

    private void numberButtonClick(View v) {
        if (isFirstInput) {
            resultTextView.setTextColor(0xFF000000);
            resultTextView.setText(v.getTag().toString());
            isFirstInput = false;
        } else {
            String getResultText = resultTextView.getText().toString().replace(",",""); // 12,000 -> 12000
            getResultText = getResultText + v.getTag().toString();
            String getDecimalString = calculator.getDecimalString(getResultText);
            resultTextView.setText(getDecimalString);
            }
    }
}
