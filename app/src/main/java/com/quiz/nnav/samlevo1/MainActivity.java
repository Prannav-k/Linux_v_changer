package com.quiz.nnav.samlevo1;

import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.EditText;
import android.view.View;

import android.os.Bundle;


import android.support.v7.app.AppCompatActivity;


import android.widget.Toast;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.logging.Handler;


public class MainActivity extends AppCompatActivity {




    SeekBar seekBar;
    TextView textView;

    private void initializeVariables(){
        seekBar=(SeekBar)findViewById(R.id.seekbar);
        textView=(TextView)findViewById(R.id.tview);
    }

    public void send(int p) {
        String z=String.valueOf(p);
        final String f=z;
        Thread t = new Thread() {
            public void run() {
                try {
                    Socket s = new Socket("192.168.43.222",7073);
                    DataOutputStream dos = new DataOutputStream(s.getOutputStream());
                    dos.writeUTF(f);
                    dos.flush();
                    dos.close();
                    s.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        t.start();
        //Toast.makeText(this, "Volume changed", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeVariables();

        textView.setText("Volume level: " + seekBar.getProgress() + "  /  " + seekBar.getMax());

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progress = 0;

            @Override
            public void onProgressChanged(SeekBar seekBar, int progresValue, boolean fromUser) {
                progress = progresValue;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                textView.setText("Volume level: " + progress + "  /  " + seekBar.getMax());
                send(progress);


                Toast.makeText(getApplicationContext(), "Volume Changed", Toast.LENGTH_SHORT).show();

            }



        });


    }
}






// Toast.makeText(getApplicationContext(), "Stopped tracking seekbar", Toast.LENGTH_SHORT).show();