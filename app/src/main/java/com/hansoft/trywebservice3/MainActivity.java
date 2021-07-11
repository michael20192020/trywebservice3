package com.hansoft.trywebservice3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

//my web service
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button querybutton,insertbutton,updatebutton,deletebutton;
    private static final String nameSpace = "https://www.hansoft.com.au/querydata";
    private static final String url = "https://www.hansoft.com.au/querydata.asmx";
    private static final String firstmethod = "queryabcdata";
    private static final String secondmethod = "InsertData";
    private static final String thirdmethod = "UpdateData";
    private static final String fourthmethod = "DeleteData";
    private static final String firstsoapAction = "https://www.hansoft.com.au/querydata/queryabcdata";
    private static final String secondsoapAction = "https://www.hansoft.com.au/querydata/InsertData";
    private static final String thirdsoapAction = "https://www.hansoft.com.au/querydata/UpdateData";
    private static final String fourthsoapAction = "https://www.hansoft.com.au/querydata/DeleteData";
    private String result;
    private TextView txt_result;
    private EditText nameEditText,passwordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bindViews();
    }

    private void bindViews() {


        querybutton = (Button) findViewById(R.id.querybutton);
        insertbutton = (Button) findViewById(R.id.insertbutton);
        updatebutton = (Button) findViewById(R.id.updatebutton);
        deletebutton = (Button) findViewById(R.id.deletebutton);
        txt_result = (TextView) findViewById(R.id.resulttextview);
        nameEditText = (EditText) findViewById(R.id.nameedittext);
        passwordEditText = (EditText) findViewById(R.id.passwordedittext);
        querybutton.setOnClickListener(this);
        insertbutton.setOnClickListener(this);
        updatebutton.setOnClickListener(this);
        deletebutton.setOnClickListener(this);
    }


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0x001:
                    txt_result.setText(result);
                    Toast.makeText(MainActivity.this, "get query web service successfully", Toast.LENGTH_SHORT).show();
                    break;
                case 0x002:
                    txt_result.setText(result);
                    Toast.makeText(MainActivity.this, "get insert web service successfully", Toast.LENGTH_SHORT).show();
                    break;
                case 0x003:
                    txt_result.setText(result);
                    Toast.makeText(MainActivity.this, "get update web service successfully", Toast.LENGTH_SHORT).show();
                    break;
                case 0x004:
                    txt_result.setText(result);
                    Toast.makeText(MainActivity.this, "get delete web service successfully", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;

            }

        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.querybutton:
                new Thread() {
                    @Override
                    public void run() {
                        getquerywebservice();
                    }
                }.start();
                break;
            case R.id.insertbutton:
                new Thread() {
                    @Override
                    public void run() {
                        getinsertwebservice();
                    }
                }.start();
                break;
            case R.id.updatebutton:
                new Thread() {
                    @Override
                    public void run() {
                        getupdatewebservice();
                    }
                }.start();
                break;
            case R.id.deletebutton:
                new Thread() {
                    @Override
                    public void run() {
                        getdeletewebservice();
                    }
                }.start();
                break;
            default:
                break;
        }
    }

    public void getquerywebservice() {
        result = "";
        SoapObject soapObject = new SoapObject(nameSpace, firstmethod);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);

        envelope.bodyOut = soapObject;

        envelope.dotNet = true;

        envelope.setOutputSoapObject(soapObject);

        HttpTransportSE httpTransportSE = new HttpTransportSE(url);

        Log.d("aaa", "getquerywebservice: prepare start query web service");
        try {

            httpTransportSE.call(firstsoapAction, envelope);
            Log.d("aaa", "getquerywebservice: invoke query Web Service successfully");
        } catch (Exception e) {
            // e.printStackTrace();
            Log.d("aaa", "getquerywebservice: invoke query Web Service failed " + e.getLocalizedMessage());
        }


        SoapObject object = (SoapObject) envelope.bodyIn;

        Log.d("aaa", "getquerywebservice: receive query web service response");
        result = object.getProperty(0).toString();
        handler.sendEmptyMessage(0x001);
        Log.d("aaa", "getquerywebservice: finish query web service");
    }


    public void getinsertwebservice() {
        result = "";
        SoapObject soapObject = new SoapObject(nameSpace, secondmethod);
        soapObject.addProperty("name", nameEditText.getText().toString());
        soapObject.addProperty("password", passwordEditText.getText().toString());

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);

        envelope.bodyOut = soapObject;

        envelope.dotNet = true;

        envelope.setOutputSoapObject(soapObject);

        HttpTransportSE httpTransportSE = new HttpTransportSE(url);

        Log.d("aaa", "getinsertwebservice: prepare start insert web service");
        try {

            httpTransportSE.call(secondsoapAction, envelope);
            Log.d("aaa", "getinsertwebservice: invoke insert Web Service successfully");
        } catch (Exception e) {
            // e.printStackTrace();
            Log.d("aaa", "getinsertwebservice: invoke insert Web Service failed " + e.getLocalizedMessage());
        }


        SoapObject object = (SoapObject) envelope.bodyIn;

        Log.d("aaa", "getinsertwebservice: receive insert web service response");
        int count = 0;
        count = Integer.parseInt(object.getProperty(0).toString());
        if (count > 0) {
            result = "insert data successfully";
        }
        else
        {
            result = "fail to insert data";
        }

        handler.sendEmptyMessage(0x002);
        Log.d("aaa", "getinsertwebservice: finish insert web service");
    }

    public void getupdatewebservice() {
        result = "";
        SoapObject soapObject = new SoapObject(nameSpace, thirdmethod);
        soapObject.addProperty("name", nameEditText.getText().toString());
        soapObject.addProperty("password", passwordEditText.getText().toString());

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);

        envelope.bodyOut = soapObject;

        envelope.dotNet = true;

        envelope.setOutputSoapObject(soapObject);

        HttpTransportSE httpTransportSE = new HttpTransportSE(url);

        Log.d("aaa", "getupdatewebservice: prepare start update web service");
        try {

            httpTransportSE.call(thirdsoapAction, envelope);
            Log.d("aaa", "getupdatewebservice: invoke update Web Service successfully");
        } catch (Exception e) {
            // e.printStackTrace();
            Log.d("aaa", "getupdatewebservice: invoke update Web Service failed " + e.getLocalizedMessage());
        }


        SoapObject object = (SoapObject) envelope.bodyIn;

        Log.d("aaa", "getupdatewebservice: receive update web service response");
        int count = 0;
        count = Integer.parseInt(object.getProperty(0).toString());
        if (count > 0) {
            result = "update data successfully";
        }
        else
        {
            result = "fail to update data";
        }

        handler.sendEmptyMessage(0x003);
        Log.d("aaa", "getupdatewebservice: finish update web service");
    }


    public void getdeletewebservice() {
        result = "";
        SoapObject soapObject = new SoapObject(nameSpace, fourthmethod);
        soapObject.addProperty("name", nameEditText.getText().toString());
        soapObject.addProperty("password", passwordEditText.getText().toString());

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);

        envelope.bodyOut = soapObject;

        envelope.dotNet = true;

        envelope.setOutputSoapObject(soapObject);

        HttpTransportSE httpTransportSE = new HttpTransportSE(url);

        Log.d("aaa", "getdeletewebservice: prepare start delete web service");
        try {

            httpTransportSE.call(fourthsoapAction, envelope);
            Log.d("aaa", "getdeletewebservice: invoke delete Web Service successfully");
        } catch (Exception e) {
            // e.printStackTrace();
            Log.d("aaa", "getdeletewebservice: invoke delete Web Service failed " + e.getLocalizedMessage());
        }


        SoapObject object = (SoapObject) envelope.bodyIn;

        Log.d("aaa", "getdeletewebservice: receive delete web service response");
        int count = 0;
        count = Integer.parseInt(object.getProperty(0).toString());
        if (count > 0) {
            result = "delete data successfully";
        }
        else
        {
            result = "fail to delete data";
        }

        handler.sendEmptyMessage(0x004);
        Log.d("aaa", "getdeletewebservice: finish delete web service");
    }

}