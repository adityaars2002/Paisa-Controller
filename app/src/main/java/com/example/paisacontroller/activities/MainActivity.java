package com.example.paisacontroller.activities;

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

import com.example.paisacontroller.Budget;
import com.example.paisacontroller.Expenses;
import com.example.paisacontroller.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    TextView txtDetails, txtBalance;
    Button btnSignOut, btnViewBudget, btnViewExpenses, btnSave;
    FirebaseAuth auth;
    FirebaseUser user;
    EditText edtTxtExpense, edtTxtAmount;
    int total;
    int budget;

    ArrayList<String> names = new ArrayList<>();
    ArrayList<Integer> amounts = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        auth = FirebaseAuth.getInstance();
        btnSignOut = findViewById(R.id.btnSignOut);
        txtDetails = findViewById(R.id.txtDetails);
        user = auth.getCurrentUser();
        if (user == null) {
            Intent intent = new Intent(MainActivity.this, Login.class);
            startActivity(intent);
            finish();
        } else {
            txtDetails.setText(user.getEmail());
        }
        btnSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(MainActivity.this, Login.class);
                startActivity(intent);
                finish();
            }
        });

        btnViewBudget = findViewById(R.id.btnViewBudget);
        btnViewExpenses = findViewById(R.id.btnViewExpenses);
        btnSave = findViewById(R.id.btnSave);
        edtTxtExpense = findViewById(R.id.edtTxtExpense);
        edtTxtAmount = findViewById(R.id.edtTxtAmount);
        txtBalance = findViewById(R.id.txtBalance);


        btnViewBudget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Budget.class);
                startActivity(intent);
                finish();
            }
        });

        btnSave.setOnClickListener(view -> {
            String name = edtTxtExpense.getText().toString();
            String amountText = edtTxtAmount.getText().toString();

            if (!name.isEmpty() && !amountText.isEmpty()) {
                int amount = Integer.parseInt(amountText);
                names.add(name);
                amounts.add(amount);
                Toast.makeText(MainActivity.this, "Added!", Toast.LENGTH_SHORT).show();
                edtTxtExpense.setText("");
                edtTxtAmount.setText("");
            } else {
                Toast.makeText(MainActivity.this, "Enter both values!", Toast.LENGTH_SHORT).show();
            }
        });

        btnViewExpenses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, Expenses.class);
                startActivity(intent);
                finish();
                if (!names.isEmpty() && !amounts.isEmpty()) {
                    intent.putStringArrayListExtra("names", names);
                    intent.putIntegerArrayListExtra("amounts", amounts);
                    startActivity(intent);
                } else {
                    Toast.makeText(MainActivity.this, "No data to display!", Toast.LENGTH_SHORT).show();
                }

            }


        });

        if (getIntent().hasExtra("budget")) {
            int budget = getIntent().getIntExtra("budget", 0);
            txtBalance.setText("Your Budget: ₹" + budget);


        }
    }
}