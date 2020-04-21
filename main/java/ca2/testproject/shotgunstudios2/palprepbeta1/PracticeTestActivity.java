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
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Random;

import ca2.testproject.shotgunstudios.palprepbeta1.R;

/**PracticeTestActivity
 *- creates the practice test page
 */
public class PracticeTestActivity extends ActionBarActivity {

    TextView explanationField;
    private Toolbar toolbar;
    category[] categoriesCopy = MainActivity.getCategories();
    category[] allowedCategories;

    private static ArrayList<String> allowedCategoriesList;

    TextView answerfield;
    TextView questionfield;
    TextView locationfield;
    RadioButton rbutton1;
    RadioButton rbutton2;
    RadioButton rbutton3;
    RadioButton rbutton4;
    int rDefault;
    Drawable rDefault2;
    RadioGroup rgroup;
    Button nextButton;
    //static Random rand = new Random();
    //promptBank PromptBank = MainActivity.getBank();

    //get the hashtable we created in main activity
    Hashtable<Integer, prompt> htPrompt = MainActivity.getTable("Reg");
    private static  Hashtable<Integer, prompt> htPromptFiltered = new Hashtable<Integer, prompt>();
    prompt holder = new prompt();


    //index for the current question
    int currentQuestion=0;

    int practiceTestSize = 20 ;

    private static ArrayList<prompt> practiceList = new ArrayList<prompt>();

    public static ArrayList<prompt> getList (){
        return  practiceList;
    }

    Random rand = new Random();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_practicetest_appbar);

        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        NavigationDrawerFragment drawerFragment= (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer ,(DrawerLayout) findViewById(R.id.drawer_layout), toolbar );




        //Prepare questions
        int randomNum = 0;
        int keyCounter =0;//used to produce a linearly incremented set of keys, this is important later on when we generate a random number to access the keys, if we know the keys are within a certain range, it makes it easier
        allowedCategoriesList = CategoriesActivity.getAllowedCategoriesList();//declared locally instead of globally because it needs to be called every time we make a new test since the allowed categories could have changed, unlike the original question bank which won't change
        htPromptFiltered.clear();
        //will create a hashTable containing a new question bank with only the allowed questions
        Enumeration keys = htPrompt.keys();
        while( keys.hasMoreElements() )
        {
            int aKey = (int) keys.nextElement();
            prompt aValue = htPrompt.get(aKey);
            holder = aValue;
            if (allowedCategoriesList.contains(aValue.categoryIdentifier)){
                htPromptFiltered.put(keyCounter, aValue);
                keyCounter++;
            }
        }
        if (practiceList.size()>0){
            practiceList.clear();
        }


        //Prepare the UI
        explanationField = (TextView) findViewById(R.id.explanationTextView);
        answerfield = (TextView) findViewById(R.id.answer);

        questionfield = (TextView) findViewById(R.id.question);

        rbutton1 = (RadioButton) findViewById(R.id.radioButton1);
        rbutton2 = (RadioButton) findViewById(R.id.radioButton2);
        rbutton3 = (RadioButton) findViewById(R.id.radioButton3);
        rbutton4 = (RadioButton) findViewById(R.id.radioButton4);
        rgroup = (RadioGroup) findViewById(R.id.radio_group1);
        locationfield = (TextView) findViewById(R.id.location);
        nextButton = (Button) findViewById(R.id.btnNext);



        //Launch activity and last minute error check
        if (htPromptFiltered.size() >= practiceTestSize) { //check to make sure we have enough questions for the test
            for (int i = 0; i < practiceTestSize; i++) {
                randomNum = rand.nextInt((htPromptFiltered.size())) ;//next int is normally exclusive of the top value so that's why we add 1, but we don't want the top value of size
                if (!practiceList.contains(htPromptFiltered.get(randomNum)) && htPromptFiltered.get(randomNum)!=null ) {//checking to make sure we haven't already added the randomnly selected prompt and check for potential error
                    practiceList.add((htPromptFiltered.get(randomNum)));
                    (htPromptFiltered.get(randomNum)).resetPrompt();//we need to remove the previously selected answers from the prompt
                } else {
                    i--; //if the list already contains the prompt, we need not do anything except have the for loop go back one step and try again
                }
            }
            Collections.shuffle(practiceList);//shuffle the questions so they don't appear in the same order as in the question bank
            refreshFields(false); //undo any highlights

        } else {
            refreshFields(true);
        }




    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        getMenuInflater().inflate(R.menu.menu_practicetest, menu);
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


    public void checkSelectedAnswer(){
        if (practiceList.get ( (currentQuestion)).getSelectedAnswer()==1){
            rbutton1.setChecked(true);
        } else if ((practiceList.get ( (currentQuestion)).getSelectedAnswer()==2)){

            rbutton2.setChecked(true);
        } else if ((practiceList.get ( (currentQuestion)).getSelectedAnswer()==3)){

            rbutton3.setChecked(true);
        } else if ((practiceList.get ( (currentQuestion)).getSelectedAnswer()==4)) {

            rbutton4.setChecked(true);
        }


    }





    public void highlightAnswers(){


        if (practiceList.get ( (currentQuestion)).getSelectedAnswer()==1){
            rbutton1.setBackgroundColor(getResources().getColor(R.color.wrongColor));
        } else if ((practiceList.get ( (currentQuestion)).getSelectedAnswer()==2)){
            rbutton2.setBackgroundColor(getResources().getColor(R.color.wrongColor));
        } else if ((practiceList.get ( (currentQuestion)).getSelectedAnswer()==3)){
            rbutton3.setBackgroundColor(getResources().getColor(R.color.wrongColor));
        } else if ((practiceList.get ( (currentQuestion)).getSelectedAnswer()==4)) {
            rbutton4.setBackgroundColor(getResources().getColor(R.color.wrongColor));
        }


        if (practiceList.get ( (currentQuestion)).getAnswer()==1){
            rbutton1.setBackgroundColor(getResources().getColor(R.color.rightColor));
        } else if ((practiceList.get ( (currentQuestion)).getAnswer()==2)){
            rbutton2.setBackgroundColor(getResources().getColor(R.color.rightColor));
        } else if ((practiceList.get ( (currentQuestion)).getAnswer()==3)){
            rbutton3.setBackgroundColor(getResources().getColor(R.color.rightColor));
        } else if ((practiceList.get ( (currentQuestion)).getAnswer()==4)) {
            rbutton4.setBackgroundColor(getResources().getColor(R.color.rightColor));
        }



    }

    public void removeHighLights(){
        rbutton1.setBackgroundColor(getResources().getColor(R.color.abc_background_cache_hint_selector_material_dark));
        rbutton2.setBackgroundColor(getResources().getColor(R.color.abc_background_cache_hint_selector_material_dark));
        rbutton3.setBackgroundColor(getResources().getColor(R.color.abc_background_cache_hint_selector_material_dark));
        rbutton4.setBackgroundColor(getResources().getColor(R.color.abc_background_cache_hint_selector_material_dark));
    }

    public void updateAnswerField(){
        if ( practiceList.get ( (currentQuestion)).getSelectedAnswer() == practiceList.get ( (currentQuestion)).getAnswer()){
            answerfield.setTextColor(getResources().getColor(R.color.rightColor));
            practiceList.get ( (currentQuestion)).setQuestionStatus("Correct");
            answerfield.setText(practiceList.get ( (currentQuestion)).getQuestionStatus());

        } else {
            answerfield.setTextColor(getResources().getColor(R.color.wrongColor));
            practiceList.get ( (currentQuestion)).setQuestionStatus("Incorrect");
            answerfield.setText(practiceList.get ( (currentQuestion)).getQuestionStatus());
        }

        explanationField.setText(      practiceList.get ( (currentQuestion)).explanation     );

    }


    //updates location, question, radio buttons and resets answer field
    public void refreshFields (boolean error){
        rgroup.clearCheck();
        removeHighLights();
        answerfield.setText("");
        explanationField.setText("");
if (error == false) {
    if (practiceList.get((currentQuestion)).getAnswered() == false) {//UNASWERED QUESTION
        rbutton1.setEnabled(true);
        rbutton2.setEnabled(true);
        rbutton3.setEnabled(true);
        rbutton4.setEnabled(true);

    } else { //ANSWERED QUESTION
        rbutton1.setEnabled(false);
        rbutton2.setEnabled(false);
        rbutton3.setEnabled(false);
        rbutton4.setEnabled(false);
        highlightAnswers();
        checkSelectedAnswer();
        updateAnswerField();
    }

    locationfield.setText(String.valueOf(currentQuestion + 1) + "/" + practiceList.size());
    questionfield.setText(practiceList.get((currentQuestion)).getQuestion());
    rbutton1.setText(practiceList.get((currentQuestion)).getChoice1());
    rbutton2.setText(practiceList.get((currentQuestion)).getChoice2());
    if (practiceList.get((currentQuestion)).getType().equals("MC")) {
        rbutton3.setVisibility(View.VISIBLE);
        rbutton4.setVisibility(View.VISIBLE);
        rbutton3.setText(practiceList.get((currentQuestion)).getChoice3());
        rbutton4.setText(practiceList.get((currentQuestion)).getChoice4());
    } else if (practiceList.get((currentQuestion)).getType().equals("TF")) {
        rbutton3.setVisibility(View.GONE);
        rbutton4.setVisibility(View.GONE);
    }
    nextButton.setVisibility(View.VISIBLE);
} else {
    locationfield.setText("");
    questionfield.setText(R.string.Error1);
    rbutton1.setVisibility(View.GONE);
    rbutton2.setVisibility(View.GONE);
    rbutton3.setVisibility(View.GONE);
    rbutton4.setVisibility(View.GONE);
    nextButton.setVisibility(View.GONE);
}
        //reset all settings to defaults


    }

    public void onClickNext (View view){

        if (currentQuestion<practiceList.size()-1) {
            currentQuestion++;
        } else {
            startActivity(new Intent(this, ScoreActivity.class));
            this.finish();
        }
        refreshFields(false);


    }


    public void onClickPrev (View view){
        if (currentQuestion>0) {
            currentQuestion--;
        }
        refreshFields(false);
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

        practiceList.get( (currentQuestion)).setAnswered(true);




        practiceList.get ( (currentQuestion)).setSelectedAnswer(choice);




        highlightAnswers();

        rbutton1.setEnabled(false);
        rbutton2.setEnabled(false);
        rbutton3.setEnabled(false);
        rbutton4.setEnabled(false);

        updateAnswerField();
    }
}
