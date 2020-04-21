package ca2.testproject.shotgunstudios2.palprepbeta1;

import android.content.Intent;
import android.os.Bundle;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import ca2.testproject.shotgunstudios.palprepbeta1.R;

/**FindInstructorActivity
 *- creates the Find Instructor page
 */
public class FindInstructorActivity extends ActionBarActivity implements View.OnClickListener {

    private Toolbar toolbar;
    Button b1;
    Button b2;
    Button b3;
    Button b4;
    Button b5;
    Button b6;
    Button b7;
    Button b8;
    Button b9;
    Button b10;
    Button b11;
    Button b12;
    Button b13;



    private static  String instructorField="";

    public static String getiField (){
        return  instructorField;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myprogress_appbar);


        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        NavigationDrawerFragment drawerFragment= (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer ,(DrawerLayout) findViewById(R.id.drawer_layout), toolbar );

        b1= (Button) findViewById(R.id.btnAB);
        b2= (Button) findViewById(R.id.btnBC);
        b3= (Button) findViewById(R.id.btnMB);
        b4= (Button) findViewById(R.id.btnNB);
        b5= (Button) findViewById(R.id.btnNL);
        b6= (Button) findViewById(R.id.btnNS);
        b7= (Button) findViewById(R.id.btnNU);
        b8= (Button) findViewById(R.id.btnNT);
        b9= (Button) findViewById(R.id.btnON);
        b10= (Button) findViewById(R.id.btnPEI);
        b11= (Button) findViewById(R.id.btnQB);
        b12= (Button) findViewById(R.id.btnSK);
        b13= (Button) findViewById(R.id.btnYK);
//find your button id defined in your xml.

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_myprogress, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        if(id==R.id.navigate){
            startActivity(new Intent(this, MainActivity.class));
        }

        if(id==android.R.id.home){
            //NavUtils.navigateUpFromSameTask(this);

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {

        switch(v.getId())  //get the id of the view clicked. (in this case button)
        {
            case R.id.btnAB : // if its button1
                instructorField= getResources().getString(R.string.provInfoAB);
                break;
            case R.id.btnBC : // if its button1
                instructorField=getResources().getString(R.string.provInfoBC);
                break;
            case R.id.btnMB : // if its button1
                instructorField=getResources().getString(R.string.provInfoMB);
                break;
            case R.id.btnNB : // if its button1
                instructorField=getResources().getString(R.string.provInfoNB);
                break;
            case R.id.btnNL : // if its button1
                instructorField=getResources().getString(R.string.provInfoNFL);
                break;
            case R.id.btnNS : // if its button1
                instructorField=getResources().getString(R.string.provInfoNS);
                break;
            case R.id.btnNU : // if its button1
                instructorField=getResources().getString(R.string.provInfoNU);
                break;
            case R.id.btnNT : // if its button1
                instructorField=getResources().getString(R.string.provInfoNT);
                break;
            case R.id.btnON : // if its button1
                instructorField=getResources().getString(R.string.provInfoON);
                break;
            case R.id.btnPEI : // if its button1
                instructorField=getResources().getString(R.string.provInfoPEI);
                break;
            case R.id.btnQB : // if its button1
                instructorField=getResources().getString(R.string.provInfoQB);
                break;
            case R.id.btnSK : // if its button1
                instructorField=getResources().getString(R.string.provInfoSK);
                break;
            case R.id.btnYK : // if its button1
                instructorField=getResources().getString(R.string.provInfoYK);
                break;
        }

        startActivity(new Intent(this, InstructorActivity.class));

    }

}
