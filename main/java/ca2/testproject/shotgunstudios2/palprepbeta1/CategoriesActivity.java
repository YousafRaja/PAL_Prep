package ca2.testproject.shotgunstudios2.palprepbeta1;

import android.content.Intent;
import android.os.Bundle;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;

import java.util.ArrayList;

import ca2.testproject.shotgunstudios.palprepbeta1.R;

/**CategoriesActivity
 *- creates Categories page
 *- returns a list of allowed categories
 *- remembers the status of the checkboxes before they are closed
 */
public class CategoriesActivity extends ActionBarActivity {
    private Toolbar toolbar;
    boolean []activities; //restores the status of the checkboxes after activity is destroyed
    private static category[] categoriesCopy = MainActivity.getCategories();
    private static ArrayList<String> allowedCategoriesList = new ArrayList<String>();

    public static ArrayList<String> getAllowedCategoriesList (){
        allowedCategoriesList.clear();
        for (int i=0; i<categoriesCopy.length;i++){
            if (categoriesCopy[i].isActive()){
                allowedCategoriesList.add(categoriesCopy[i].getShortName());
            }
        }
        return  allowedCategoriesList;
    }

    LinearLayout container;
    CheckBox []cBox;
    int counter = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_appbar);

        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        NavigationDrawerFragment drawerFragment= (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer ,(DrawerLayout) findViewById(R.id.drawer_layout), toolbar );
        container = (LinearLayout) findViewById(R.id.categoriesLayoutInScrollView);

        fillInCategories(categoriesCopy.length, savedInstanceState);
        activities = new boolean[categoriesCopy.length];

    }

    public void fillInCategories (int numberOfCategories, Bundle savedInstanceState){
        cBox = new CheckBox[numberOfCategories];

        for (counter =0; counter<numberOfCategories; counter++){
            cBox[counter] = new CheckBox(this);
            cBox[counter] = (CheckBox)getLayoutInflater().inflate(R.layout.catstyle, null);//sets the style which is predefined in catstyle
            cBox[counter].setId(counter);
            cBox[counter].setPadding(5, 20, 5, 20);
            cBox[counter].setText(categoriesCopy[counter].getLongName());

            if (savedInstanceState == null) {
                cBox[counter].setChecked(true);
            } else {
                cBox[counter].setChecked(activities[counter]);
            }
            //have to add the click listeners here as soon as we add the categories since it'll be hard/impossible to get access to them later
            cBox[counter].setOnClickListener(new View.OnClickListener() {
                                        public void onClick(View v) {
                                        if (cBox[v.getId()].isChecked()) {
                                            categoriesCopy[v.getId()].setActive(true);
                                        } else {
                                            categoriesCopy[v.getId()].setActive(false);
                                        }
                                        }
                                    });


                    container.addView(cBox[counter]);

        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_more, menu);
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
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Save the user's current game state

        for (int i=0; i<categoriesCopy.length; i++){
            activities[i] = categoriesCopy[i].isActive();
        }
        savedInstanceState.putBooleanArray("activitiesRecover", activities);

        // Always call the superclass so it can save the view hierarchy state
        super.onSaveInstanceState(savedInstanceState);
    }

    public void onRestoreInstanceState(Bundle savedInstanceState) {
        // Always call the superclass so it can restore the view hierarchy
        super.onRestoreInstanceState(savedInstanceState);
        activities = savedInstanceState.getBooleanArray("activitiesRecover");
    }
}
