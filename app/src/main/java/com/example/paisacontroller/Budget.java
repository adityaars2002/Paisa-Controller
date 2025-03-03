package com.example.paisacontroller;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.paisacontroller.activities.MainActivity;

public class Budget extends AppCompatActivity {

    EditText edtTxtSalary, edtTxtNecessity, edtTxtRepayment, edtTxtInvestment;
    Button btnCalculate;
    TextView txtBudget;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_budget);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        edtTxtSalary = findViewById(R.id.edtTxtSalary);
        edtTxtNecessity = findViewById(R.id.edtTxtNecessity);
        edtTxtRepayment = findViewById(R.id.edtTxtRepayment);
        edtTxtInvestment = findViewById(R.id.edtTxtInvestment);
        btnCalculate = findViewById(R.id.btnCalculate);
        txtBudget = findViewById(R.id.txtBudget);

        btnCalculate.setOnClickListener(view -> calculateBudget());
    }

    private void calculateBudget() {
        try {

            int salary = Integer.parseInt(edtTxtSalary.getText().toString().trim());
            int necessity = Integer.parseInt(edtTxtNecessity.getText().toString().trim());
            int debt = Integer.parseInt(edtTxtRepayment.getText().toString().trim());
            int investment = Integer.parseInt(edtTxtInvestment.getText().toString().trim());
            int remainingBudget = salary - (necessity + debt + investment);

            Intent intent = new Intent(Budget.this, MainActivity.class);
            intent.putExtra("budget", remainingBudget);
            startActivity(intent);
            finish();


            txtBudget.setText("Your Budget: ₹" + remainingBudget);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Please enter valid numbers!", Toast.LENGTH_SHORT).show();
        }

    }
}