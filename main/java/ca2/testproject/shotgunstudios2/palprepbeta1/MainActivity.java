package ca2.testproject.shotgunstudios2.palprepbeta1;

import android.content.Intent;
import android.os.Bundle;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarActivity;
import androidx.appcompat.widget.Toolbar;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Hashtable;

import ca2.testproject.shotgunstudios.palprepbeta1.R;


/**MainActivity
 *- creates the Main/home page
 *- reads from file and creates the original question bank which can be accessed by other classes
 */
//MainActivity class: Loads the homepage, setups the google analytics tracker, reads from the textfile
public class MainActivity extends ActionBarActivity implements View.OnClickListener, constants {

    TextView linkNR;
    TextView linkR;

    private static String[][] categories;

    private static category [] cats;

    private Toolbar toolbar;

    private static  Hashtable<Integer, prompt> hashTablePrompt = new Hashtable<Integer, prompt>();

    private static  Hashtable<Integer, prompt> hashTablePracPrompt = new Hashtable<Integer, prompt>();

    public static Hashtable<Integer, prompt> getTable (String type){

        if (type == "Reg") {
            return hashTablePrompt;
        } else if (type == "Prac"){
            return  hashTablePracPrompt;
        }
        return null;
    }

    public static category[] getCategories (){

        return cats;
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //get tracker reference
        Tracker tracker = ((GunTestCanada) getApplication()).getTracker(GunTestCanada.TrackerName.GLOBAL_TRACKER);
        tracker.send (new HitBuilders.EventBuilder().setCategory("Experiment").setAction("Starting app").build());

        setContentView(R.layout.activity_main_appbar);

        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

       getSupportActionBar().setHomeButtonEnabled(true);
       getSupportActionBar().setDisplayShowHomeEnabled(true);

        NavigationDrawerFragment drawerFragment= (NavigationDrawerFragment)//this seems to attach the home button onto the app bar
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), toolbar);


        //reads from file and builds the prompts and categories
        buildPrompts();

        linkNR = (TextView) findViewById(R.id.NRmanual);
        linkNR.setClickable(true);
        linkNR.setMovementMethod(LinkMovementMethod.getInstance());
        String text = "<a href='http://www.gov.pe.ca/photos/original/CFSCmanualVer2.pdf'>Download Non-Restricted Manual</a>";
        linkNR.setText(Html.fromHtml(text));

        linkR = (TextView) findViewById(R.id.RManual);
        linkR.setClickable(true);
        linkR.setMovementMethod(LinkMovementMethod.getInstance());

        String text2 = "<a href='http://www.gov.pe.ca/photos/original/CRFSCmanualV2.pdf'>Download Restricted Manual</a>";
        linkR.setText(Html.fromHtml(text2));


    }

    public void fillInPrompts (String[] fileList,  String table){
        for (int i=0; i<fileList.length; i++) {
            prompt p = new prompt();
            p.categoryIdentifier= fileList[ i].split(BREAK2)[0];
            p.questionDescription= fileList[ i].split(BREAK2)[1];
            p.type = fileList[ i].split(BREAK2)[2];
            p.choiceDescription1=fileList[i].split(BREAK2)[3];
            p.choiceDescription2=fileList[i].split(BREAK2)[4];
            p.choiceDescription3=fileList[i].split(BREAK2)[5];
            p.choiceDescription4=fileList[i].split(BREAK2)[6];
            p.answer= Integer.parseInt(fileList[i].split(BREAK2)[7]);
            p.explanation = fileList[i].split(BREAK2)[8];
            p.index=i;
            if (table == "Prac"){
                p.pictureName= fileList[i].split(BREAK2)[8];
                hashTablePracPrompt.put(i, p);
            } else if (table == "Reg") {
                //add the prompt object to the hash table
                hashTablePrompt.put(i, p);
            } else if (table == "game"){

            }
        }
    }

    public  void buildPrompts (){

        String filecontents = readerit();
        String[] fileSection = filecontents.split(BREAK3);
        String[] fileCategories = fileSection[0].split(BREAK1);//first half of fileSection will contain categories info broken up by BREAK1


        cats = new category[fileCategories.length]; //now that we've read the file, we can initialize the categories array
        for (int i=0; i<fileCategories.length; i++){
            cats[i] = new category();
            cats[i].setLongName( fileCategories[i].split(BREAK2)[0]);//long form
            cats[i].setShortName( fileCategories[i].split(BREAK2)[1]);//short form
        }



        //split the file by each section which contains a prompt (question, choices, answer_
        String[] filePrompts = fileSection[1].split(BREAK1);//second half will contain questions
        //go through each section and fill in the prompt object

        fillInPrompts(filePrompts, "Reg");

        String[] filePracPrompts = fileSection[2].split(BREAK1);//prompts for practical

        fillInPrompts(filePracPrompts, "Prac");

        String test = "placeholder";


    }

    public String readerit(){
        BufferedReader reader = null;
        String s ="";
        try {
            reader = new BufferedReader(
                    new InputStreamReader(getAssets().open("filename.txt"), "UTF-8"));


            // do reading, usually loop until end of file reading
            String mLine = reader.readLine();
            s=s+mLine;
            while (mLine != null) {
                //process line

                mLine =  reader.readLine();
                if (mLine!=null){
                    s=s+mLine;
                }
            }
        } catch (IOException e) {
            System.out.println(e.getCause());
            //log the exception
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    //log the exception
                }
            }
        }
        return s;
    }





    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        GoogleAnalytics.getInstance(this).reportActivityStart(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        GoogleAnalytics.getInstance(this).reportActivityStop(this);
        super.onStop();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Toast.makeText(this, "Hey you just hit " + item.getTitle(), Toast.LENGTH_SHORT).show();
            return true;
        }
        if(id==R.id.navigate){
            startActivity(new Intent(this, MainActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }

    public void doSomething (View v){

        if (v.getId()==R.id.button1){
        } else if (v.getId()==R.id.button2){
        }
    }

    @Override
    public void onClick(View v) {
        //4) what should happen when its clicked

    }
}
