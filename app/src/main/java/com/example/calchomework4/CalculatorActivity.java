package com.example.calchomework4;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class CalculatorActivity extends AppCompatActivity {

    private TextView resultField;
    private EditText numberField;
    private TextView operationField;
    private Double operand = null;
    private String lastOperation = "=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);

        String appName = getResources().getString(R.string.app_name);
        Toast.makeText(this, appName, Toast.LENGTH_SHORT).show();

        resultField = findViewById(R.id.text_view);
        numberField = findViewById(R.id.edit_text);
        operationField = findViewById(R.id.operation_field);
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString("OPERATION", lastOperation);
        if (operand != null) {
            outState.putDouble("OPERAND", operand);
            super.onSaveInstanceState(outState);
        }
    }


    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        lastOperation = savedInstanceState.getString(getString(R.string.OPERATION));
        operand = savedInstanceState.getDouble(getString(R.string.OPERAND));
        resultField.setText(operand.toString());
        operationField.setText(lastOperation);
    }


    public void onNumberClick(View view) {

        Button button = (Button) view;
        numberField.append(button.getText());

        if (lastOperation.equals("=") && operand != null) {
            operand = null;
        }
    }


    public void onOperationClick(View view) {

        Button button = (Button) view;
        String op = button.getText().toString();
        String number = numberField.getText().toString();

        if (number.length() > 0) {
            number = number.replace(',', '.');
            try {
                performOperation(Double.valueOf(number), op);
            } catch (NumberFormatException ex) {
                numberField.setText("");
            }
        }
        lastOperation = op;
        operationField.setText(lastOperation);
    }

    private void performOperation(Double number, String operation) {


        if (operand == null) {
            operand = number;
        } else {
            if (lastOperation.equals("=")) {
                lastOperation = operation;
            }
            switch (lastOperation) {
                case "=":
                    operand = number;
                    break;
                case "/":
                    if (number == 0) {
                        operand = 0.0;
                    } else {
                        operand /= number;
                    }
                    break;
                case "*":
                    operand *= number;
                    break;
                case "+":
                    operand += number;
                    break;
                case "-":
                    operand -= number;
                    break;
            }
        }
        resultField.setText(operand.toString().replace('.', ','));
        numberField.setText("");
    }
}