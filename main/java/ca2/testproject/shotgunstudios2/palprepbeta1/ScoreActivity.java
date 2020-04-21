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
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;

import ca2.testproject.shotgunstudios.palprepbeta1.R;

/**ScoreActivity
 *- creates the final score page, called after practice test is complete
 */
public class ScoreActivity extends ActionBarActivity implements View.OnClickListener {

    private Toolbar toolbar;
    Button restart;
    TextView scoreTitle;
    TextView score;
    float  correctAnswers;
    static ArrayList<prompt> pList = PracticeTestActivity.getList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_appbar);

        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        NavigationDrawerFragment drawerFragment= (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer ,(DrawerLayout) findViewById(R.id.drawer_layout), toolbar );

        scoreTitle = (TextView) findViewById(R.id.scoretitle);
        score = (TextView) findViewById(R.id.scoredisplay);


    }

    public void calculateAndSetScore (){
                correctAnswers=0;
                for (int i=0; i<pList.size(); i++){
                    if (pList.get(i).questionStatus=="Correct"){
                        correctAnswers++;
                    }
                }

        int correct = (int) correctAnswers;
        float percentage = (correctAnswers/(float)pList.size())*100;
        DecimalFormat df = new DecimalFormat("##");

        score.setText(df.format(percentage)+"%"+" - "+correct + "/" + pList.size()+ "");
        if (percentage>=80){
            scoreTitle.setText("You Passed!");
        } else {
            scoreTitle.setText("You FAIL. Try Again?");
        }


    }


    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, PracticeTestActivity.class));
        this.finish();
    }





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_myprogress, menu);
        calculateAndSetScore();
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
            startActivity(new Intent(this, SubActivity.class));
        }

        if(id==android.R.id.home){
            //NavUtils.navigateUpFromSameTask(this);

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        onBackPressed();
    }
}
