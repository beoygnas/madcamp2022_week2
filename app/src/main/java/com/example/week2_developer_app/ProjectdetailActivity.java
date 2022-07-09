package com.example.week2_developer_app;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.week2_developer_app.databinding.ActivityDetailprojectBinding;

import java.text.SimpleDateFormat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProjectdetailActivity extends AppCompatActivity {

    private ImageButton btn_back;
    private ImageButton btn_call;
    private ImageButton btn_delete;
    private String name;
    private String email;

    ActivityDetailprojectBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailprojectBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();

        name = getIntent().getStringExtra("name");
        email = getIntent().getStringExtra("email");

        binding.projId.setText(Integer.toString(intent.getIntExtra("proj_id" , 0)));
        binding.writer.setText(intent.getStringExtra("writer"));
        binding.writerEmail.setText(intent.getStringExtra("writer_email"));
        binding.title.setText(intent.getStringExtra("title"));
        binding.content.setText(intent.getStringExtra("content"));
        binding.field.setText(intent.getStringExtra("field"));
        binding.level.setText(Integer.toString(intent.getIntExtra("level", 0)));
        binding.headcount.setText(Integer.toString(intent.getIntExtra("headcount", 0)));
        binding.language.setText(intent.getStringExtra("language"));
        binding.time.setText(intent.getStringExtra("time"));
        binding.regdata.setText(intent.getStringExtra("regdata"));

        if(email.equals(intent.getStringExtra("writer_email")))
            binding.btnDelete.setVisibility(View.VISIBLE);


        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        binding.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ProjectApi projectApi = RetrofitClient.getClient().create(ProjectApi.class);



            }
        });
    }
}
