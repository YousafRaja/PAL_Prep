package ca2.testproject.shotgunstudios2.palprepbeta1;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.Hashtable;

import ca2.testproject.shotgunstudios.palprepbeta1.R;

/**QuestionBankActivity
 *- creates the question bank page
 */
public class PracticalReviewActivity extends ActionBarActivity {

    private Toolbar toolbar;
    TextView answerfield;
    TextView questionfield;
    TextView locationfield;
    RadioButton rbutton1;
    RadioButton rbutton2;
    RadioButton rbutton3;
    RadioButton rbutton4;
    RadioGroup rgroup;
    ImageView shownPicture;
    Drawable drawable;
    //promptBank PromptBank = MainActivity.getBank();

    //get the hashtable we created in main activity
    Hashtable<Integer, prompt> htPrompt = MainActivity.getTable("Prac");

    //index for the current question
    int currentQuestion=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practicalreview_appbar);

        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        NavigationDrawerFragment drawerFragment= (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer ,(DrawerLayout) findViewById(R.id.drawer_layout), toolbar );

        answerfield = (TextView)findViewById(R.id.answer);

        questionfield = (TextView) findViewById (R.id.question);
        questionfield.setText(    htPrompt.get( (currentQuestion)).getQuestion()     );

        rbutton1 = (RadioButton) findViewById(R.id.radioButton1);
        rbutton2 = (RadioButton) findViewById(R.id.radioButton2);
        rbutton3 = (RadioButton) findViewById(R.id.radioButton3);
        rbutton4 = (RadioButton) findViewById(R.id.radioButton4);
        rgroup = (RadioGroup) findViewById(R.id.radio_group1);

        shownPicture = (ImageView) findViewById(R.id.displayedPic);

        //shownPicture = (ImageView) findViewById(R.id.displayedPic);
        //drawable = getResources().getDrawable(R.drawable.bg_categories320x480);


        int drawableResourceId = this.getResources().getIdentifier("pq1", "drawable", this.getPackageName());





        locationfield = (TextView) findViewById (R.id.location);
        int test = 123;
        String test2 = String.valueOf(test);
        locationfield.setText(String.valueOf(currentQuestion+1)+"/"+htPrompt.size());
        //answerfield.setText("TEST");
        refreshFields();

    }

    public void updatePicture (prompt question){
        String picname = question.pictureName;
        int drawableResourceId = this.getResources().getIdentifier(picname, "drawable", this.getPackageName());
        shownPicture.setImageResource(drawableResourceId);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_questionbank, menu);

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


    public void refreshFields (){

        locationfield.setText(String.valueOf(currentQuestion+1)+"/"+htPrompt.size());

        updatePicture(htPrompt.get( (currentQuestion)));



        questionfield.setText(    htPrompt.get( (currentQuestion)).getQuestion()     );
        rbutton1.setText(        htPrompt.get( (currentQuestion)).getChoice1()       );
        rbutton2.setText(        htPrompt.get( (currentQuestion)).getChoice2()       );
        if  (htPrompt.get( (currentQuestion)).getType().equals("MC") ){
            rbutton3.setVisibility(View.VISIBLE);
            rbutton4.setVisibility(View.VISIBLE);
            rbutton3.setText(        htPrompt.get( (currentQuestion)).getChoice3()       );
            rbutton4.setText(        htPrompt.get( (currentQuestion)).getChoice4()       );
        } else if (htPrompt.get ( (currentQuestion)).getType().equals("TF") ){
            rbutton3.setVisibility(View.GONE);
            rbutton4.setVisibility(View.GONE);
        }

        //reset all settings to defaults
        answerfield.setText("");

        rgroup.clearCheck();
    }

    public void onClickNext (View view){

        if (currentQuestion<htPrompt.size()-1) {
            currentQuestion++;

        }
        refreshFields();

    }


    public void onClickPrev (View view){
        if (currentQuestion>0) {
            currentQuestion--;
        }
        refreshFields();
    }


    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        int choice=0;


        // Check which radio button was clicked

        switch(view.getId()) {
            case R.id.radioButton1:
                if (checked)
                   // answerfield.setText("Incorrect");
                choice=1;
                    break;
            case R.id.radioButton2:
                if (checked)
                    //answerfield.setText("Incorrect");
                choice=2;
                    break;
            case R.id.radioButton3:
                if (checked)
                    //answerfield.setText("Incorrect");
                choice=3;
                break;
            case R.id.radioButton4:
                if (checked)
                    //answerfield.setText("Correct");
                choice =4;
                break;
        }

        if (choice == htPrompt.get ( (currentQuestion)).getAnswer()){

            answerfield.setTextColor(getResources().getColor(R.color.rightColor));
            answerfield.setText("Correct");

        } else {
            answerfield.setTextColor(getResources().getColor(R.color.wrongColor));
            answerfield.setText("Incorrect");
        }

    }



}
