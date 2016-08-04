package com.example.jiangrui.myapplication;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    BaseDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        View view = getLayoutInflater().inflate(R.layout.dialog_input_amount, null);
        EditText amountEdit = (EditText) view.findViewById(R.id.dialog_et_amount);
        amountEdit.setText("123456789");
        dialog = new BaseDialog.Builder(this).setTitle("标题").setMessage("ffffffffffffffffffffffffffffffffffffffffffffffffff")
                .setPositiveButton( new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                }).setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialogInterface) {
                        Toast.makeText(MainActivity.this, "cancel", Toast.LENGTH_SHORT).show();
                    }
                }).setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {
                        Toast.makeText(MainActivity.this, "dismiss", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeBtnShow(false)
                .setView(view)
                .create();
        dialog.show();
    }

}
