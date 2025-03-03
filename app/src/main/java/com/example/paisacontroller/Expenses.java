package com.example.paisacontroller;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.paisacontroller.activities.MainActivity;

import java.util.ArrayList;

public class Expenses extends AppCompatActivity {

    ListView listView;
    TextView txtTotal;
    ArrayList<String> names;
    ArrayList<Integer> amounts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_expenses);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        listView = findViewById(R.id.listView);
        txtTotal = findViewById(R.id.txtTotal);

        names = getIntent().getStringArrayListExtra("names");
        amounts = getIntent().getIntegerArrayListExtra("amounts");

        if (names == null) names = new ArrayList<>();
        if (amounts == null) amounts = new ArrayList<>();

        ArrayList<String> displayList = new ArrayList<>();
        int total = 0;

        for (int i = 0; i < names.size(); i++) {
            displayList.add(names.get(i) + " - ₹" + amounts.get(i));
            total += amounts.get(i);
        }

        txtTotal.setText("Total Expense: ₹" + total);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, displayList);
        listView.setAdapter(adapter);


    }
}