package com.mpower.test;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.mpower.test.utils.Helper;

import org.odk.collect.android.R;
import org.odk.collect.android.activities.FormChooserList;
import org.odk.collect.android.activities.MainMenuActivity;
import org.odk.collect.android.application.Collect;
import org.odk.collect.android.dao.InstancesDao;
import org.odk.collect.android.provider.InstanceProviderAPI;
import org.odk.collect.android.utilities.FileUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.text.DateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.button2)
    Button btnNew;
    @BindView(R.id.button3)
    Button btnParse;
    @BindView(R.id.button4)
    Button btnFill;


    String xmlFile="<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
            "<h:html xmlns=\"http://www.w3.org/2002/xforms\" xmlns:ev=\"http://www.w3.org/2001/xml-events\" xmlns:h=\"http://www.w3.org/1999/xhtml\" xmlns:jr=\"http://openrosa.org/javarosa\" xmlns:orx=\"http://openrosa.org/xforms\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\">\n" +
            "  <h:head>\n" +
            "    <h:title>2.23 Provide Supportive Care to Sick Newborn</h:title>\n" +
            "    <model>\n" +
            "      <instance>\n" +
            "        <session_23_chap_2_en id=\"twentythree_session_second_chapter_en\">\n" +
            "          <formhub>\n" +
            "            <uuid/>\n" +
            "          </formhub>\n" +
            "          <chapter1/>\n" +
            "          <s23_q1/>\n" +
            "          <s23_q2/>\n" +
            "          <s23_q3/>\n" +
            "          <scoreEng/>\n" +
            "          <score/>\n" +
            "          <start/>\n" +
            "          <end/>\n" +
            "          <username/>\n" +
            "          <meta>\n" +
            "            <instanceID/>\n" +
            "          </meta>\n" +
            "        </session_23_chap_2_en>\n" +
            "      </instance>\n" +
            "      <bind nodeset=\"/session_23_chap_2_en/chapter1\" type=\"select1\"/>\n" +
            "      <bind constraint=\".='t'\" jr:constraintMsg=\"উত্তর ভুল\" nodeset=\"/session_23_chap_2_en/s23_q1\" required=\"true()\" type=\"select1\"/>\n" +
            "      <bind constraint=\".='t'\" jr:constraintMsg=\"উত্তর ভুল\" nodeset=\"/session_23_chap_2_en/s23_q2\" required=\"true()\" type=\"select1\"/>\n" +
            "      <bind constraint=\".='t'\" jr:constraintMsg=\"উত্তর ভুল\" nodeset=\"/session_23_chap_2_en/s23_q3\" required=\"true()\" type=\"select1\"/>\n" +
            "      <bind calculate=\"if( /session_23_chap_2_en/s23_q1  = 't',1,0) + if( /session_23_chap_2_en/s23_q2  = 't',1,0) + if( /session_23_chap_2_en/s23_q3  = 't',1,0)\" nodeset=\"/session_23_chap_2_en/scoreEng\" type=\"string\"/>\n" +
            "      <bind nodeset=\"/session_23_chap_2_en/score\" readonly=\"true()\" type=\"string\"/>\n" +
            "      <bind jr:preload=\"timestamp\" jr:preloadParams=\"start\" nodeset=\"/session_23_chap_2_en/start\" type=\"dateTime\"/>\n" +
            "      <bind jr:preload=\"timestamp\" jr:preloadParams=\"end\" nodeset=\"/session_23_chap_2_en/end\" type=\"dateTime\"/>\n" +
            "      <bind jr:preload=\"property\" jr:preloadParams=\"username\" nodeset=\"/session_23_chap_2_en/username\" type=\"string\"/>\n" +
            "      <bind calculate=\"concat('uuid:', uuid())\" nodeset=\"/session_23_chap_2_en/meta/instanceID\" readonly=\"true()\" type=\"string\"/>\n" +
            "      <bind calculate=\"'cb4bbc25cc2f496e9625471f120380a7'\" nodeset=\"/session_23_chap_2_en/formhub/uuid\" type=\"string\"/>\n" +
            "    </model>\n" +
            "  </h:head>\n" +
            "  <h:body>\n" +
            "    <select1 appearance=\"html\" ref=\"/session_23_chap_2_en/chapter1\">\n" +
            "      <label>Chapter 2</label>\n" +
            "      <item>\n" +
            "        <label>session_twentythree.html</label>\n" +
            "        <value>h</value>\n" +
            "      </item>\n" +
            "    </select1>\n" +
            "    <select1 appearance=\"button\" ref=\"/session_23_chap_2_en/s23_q1\">\n" +
            "      <label>1. What are the indication for managing a baby with IV fluid?</label>\n" +
            "      <item>\n" +
            "        <label>Temperature is 35.5–36.5°C (95.9–97.7°F)</label>\n" +
            "        <value>ff</value>\n" +
            "      </item>\n" +
            "      <item>\n" +
            "        <label>Unconscious, Lethergic</label>\n" +
            "        <value>t</value>\n" +
            "      </item>\n" +
            "      <item>\n" +
            "        <label>Central cyanosis</label>\n" +
            "        <value>f</value>\n" +
            "      </item>\n" +
            "    </select1>\n" +
            "    <select1 appearance=\"button\" ref=\"/session_23_chap_2_en/s23_q2\">\n" +
            "      <label>2.Which statement is correct?</label>\n" +
            "      <item>\n" +
            "        <label>First 2 days of life give 10% glucose IV if the baby need IV fluid</label>\n" +
            "        <value>t</value>\n" +
            "      </item>\n" +
            "      <item>\n" +
            "        <label>Suitable alternative IV fluid after first 2 days are half normal saline &amp; 6% dexrose</label>\n" +
            "        <value>f</value>\n" +
            "      </item>\n" +
            "      <item>\n" +
            "        <label>Do not exceed 80 ml/kg/day IV fluid when the baby get IV fluid</label>\n" +
            "        <value>ff</value>\n" +
            "      </item>\n" +
            "    </select1>\n" +
            "    <select1 appearance=\"button\" ref=\"/session_23_chap_2_en/s23_q3\">\n" +
            "      <label>3. Oxygen therapy with a pulse oximeter can be discontinued if:</label>\n" +
            "      <item>\n" +
            "        <label>Baby can maintain saturation &gt;85% in room air</label>\n" +
            "        <value>f</value>\n" +
            "      </item>\n" +
            "      <item>\n" +
            "        <label>Baby can maintain saturation &gt;90% in room air</label>\n" +
            "        <value>t</value>\n" +
            "      </item>\n" +
            "      <item>\n" +
            "        <label>Baby can maintain saturation &gt;95% in room air</label>\n" +
            "        <value>ff</value>\n" +
            "      </item>\n" +
            "    </select1>\n" +
            "    <input ref=\"/session_23_chap_2_en/score\">\n" +
            "      <label>Your Score\n" +
            "\n" +
            "<output value=\" /session_23_chap_2_en/scoreEng \"/>/3</label>\n" +
            "    </input>\n" +
            "  </h:body>\n" +
            "</h:html>";

    String xml2="<?xml version='1.0' ?>" +
            "<session_23_chap_2_en id=\"twentythree_session_second_chapter_en\">" +
            "<formhub>" +
            "<uuid>cb4bbc25cc2f496e9625471f120380a7</uuid>" +
            "</formhub>" +
            "<chapter1>h</chapter1><s23_q1>t</s23_q1><s23_q2>t</s23_q2><s23_q3>t</s23_q3><scoreEng>3</scoreEng><score /><start>2017-06-22T13:29:56.763+06</start><end>2017-06-22T13:31:19.409+06</end><username /><meta><instanceID>uuid:114b351b-0b90-4265-ad1d-33203a24b635</instanceID></meta></session_23_chap_2_en>";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_test_layout);
        ButterKnife.bind(this);

        InstancesDao instancesDao=new InstancesDao();
       /* try {
            InputStream inputStream = getAssets().open("file.xml");
        } catch (IOException e) {
            e.printStackTrace();
        }
*/
        String fileName="test.xml";
        File file=new File(Collect.TEST_SERVER_DIR+File.separator+fileName);
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (file.exists()){
            try {
                FileOutputStream fileOutputStream=new FileOutputStream(file);
                //fileOutputStream=openFileOutput(fileName,Context.MODE_PRIVATE);
                fileOutputStream.write(xml2.getBytes());
                fileOutputStream.close();
               /*
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(openFileOutput(file.getName(), Context.MODE_PRIVATE));
                outputStreamWriter.write(xmlFile);
                outputStreamWriter.close();*/
            }
            catch (IOException e) {
                Toast.makeText(this,"File Write failed",Toast.LENGTH_LONG).show();
                Log.d("Exception", "File write failed: " + e.toString());
            }
        }else if (!file.exists()){
            Toast.makeText(this,"File not avilable",Toast.LENGTH_LONG).show();
        }

        File instanceFile=new File(Collect.INSTANCES_PATH+File.separator+fileName);
        try {
            instanceFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (instanceFile.exists()){
            try {
                FileOutputStream fileOutputStream=new FileOutputStream(instanceFile);
                //fileOutputStream=openFileOutput(fileName,Context.MODE_PRIVATE);
                fileOutputStream.write(xml2.getBytes());
                fileOutputStream.close();
               /*
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(openFileOutput(file.getName(), Context.MODE_PRIVATE));
                outputStreamWriter.write(xmlFile);
                outputStreamWriter.close();*/
            }
            catch (IOException e) {
                Toast.makeText(this,"File Write failed",Toast.LENGTH_LONG).show();
                Log.d("Exception", "File write failed: " + e.toString());
            }
        }else if (!instanceFile.exists()){
            Toast.makeText(this,"File not avilable",Toast.LENGTH_LONG).show();
        }


        File formFile=new File(Collect.FORMS_PATH+File.separator+fileName);
        try {
            formFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String path=Environment.getExternalStorageDirectory().getAbsolutePath()+File.separator+"himel";
        Helper.copyFileOrDirectory(file.getAbsolutePath(),path);

        String fname="My Bloody Valentine - Sometimes.mp3";
        String songPath=Collect.TEST_SERVER_DIR+File.separator+fname;
        Helper.copyFileOrDirectory(songPath,path);

        new File(songPath).delete();

        String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());

        ContentValues contentValues=new ContentValues();
        contentValues.put(InstanceProviderAPI.InstanceColumns.DISPLAY_NAME,"test_xml");
        contentValues.put(InstanceProviderAPI.InstanceColumns.INSTANCE_FILE_PATH,file.getAbsolutePath());
        contentValues.put(InstanceProviderAPI.InstanceColumns.JR_FORM_ID,"twentythree_session_second_chapter_en");
        contentValues.put(InstanceProviderAPI.InstanceColumns.STATUS,InstanceProviderAPI.STATUS_COMPLETE);
        contentValues.put(InstanceProviderAPI.InstanceColumns.CAN_EDIT_WHEN_COMPLETE,Boolean.toString(true));
        contentValues.put(InstanceProviderAPI.InstanceColumns.LAST_STATUS_CHANGE_DATE,currentDateTimeString);
        contentValues.put(InstanceProviderAPI.InstanceColumns.DISPLAY_SUBTEXT,"test_xml");

        instancesDao.saveInstance(contentValues);

        ContentValues contentValues2=new ContentValues();
        contentValues2.put(InstanceProviderAPI.InstanceColumns.DISPLAY_NAME,"test_xml");
        contentValues2.put(InstanceProviderAPI.InstanceColumns.INSTANCE_FILE_PATH,instanceFile.getAbsolutePath());
        contentValues2.put(InstanceProviderAPI.InstanceColumns.JR_FORM_ID,"twentythree_session_second_chapter_en");
        contentValues2.put(InstanceProviderAPI.InstanceColumns.STATUS,InstanceProviderAPI.STATUS_COMPLETE);
        contentValues2.put(InstanceProviderAPI.InstanceColumns.CAN_EDIT_WHEN_COMPLETE,Boolean.toString(true));
        contentValues2.put(InstanceProviderAPI.InstanceColumns.LAST_STATUS_CHANGE_DATE,currentDateTimeString);
        contentValues2.put(InstanceProviderAPI.InstanceColumns.DISPLAY_SUBTEXT,"test_xml");

        instancesDao.saveInstance(contentValues2);

        btnNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Collect.getInstance().getActivityLogger().logAction(this,"FillingBlankForm","click");
                Intent intent=new Intent(getApplicationContext(), MainMenuActivity.class);
                startActivity(intent);
            }
        });
    }
}
