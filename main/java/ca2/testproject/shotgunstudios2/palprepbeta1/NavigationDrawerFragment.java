package ca2.testproject.shotgunstudios2.palprepbeta1;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import ca2.testproject.shotgunstudios.palprepbeta1.R;

//import android.app.Fragment;


/**NavigationDrawerFragment
 *- creates the navigation drawer
 */
public class NavigationDrawerFragment extends Fragment {
    Button btnMyProgress;
    Button btnPracticeTests;
    Button btnQuestionBank;
    Button btnHandBook;
    Button btnMore;
    Button btnPracReview;

    public static final String PREF_FILE_NAME = "testpref";
    public static final String KEY_USER_LEARNED_DRAWER = "user_learned_drawer";

    private ActionBarDrawerToggle mDrawerToggle;

    private DrawerLayout mDrawerLayout;

    private boolean mUserLearnedDrawer; //when the drawer is opened (inside onDrawerOpened), we'll check if it's the very first time
    private boolean mFromSavedInstanceState;

    private View containerView;

    public NavigationDrawerFragment() {
        // Required empty public constructor
    }







    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUserLearnedDrawer = Boolean.valueOf(readFromPreferences(getActivity(), KEY_USER_LEARNED_DRAWER, "false"));
        if (savedInstanceState != null) {
            mFromSavedInstanceState = true;
        }




    }



    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_navigation_drawer, container, false);

       btnMyProgress = (Button) view.findViewById(R.id.btnMyProgress);
        btnMyProgress.setOnClickListener(new View.OnClickListener() //directly instantiated the interface and defined the method right where it's used
        {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(getActivity(), FindInstructorActivity.class);
                mDrawerLayout.closeDrawer(containerView);
                getActivity().startActivity(myIntent);
                //do what you want to do when the button is clicked
            }
        });


        btnPracticeTests = (Button) view.findViewById(R.id.btnPracticeTests);
        btnPracticeTests.setOnClickListener(new View.OnClickListener() //directly instantiated the interface and defined the method right where it's used
        {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(getActivity(), PracticeTestActivity.class);

                //mDrawerToggle.setDrawerIndicatorEnabled(true);
                mDrawerLayout.closeDrawer(containerView);
                getActivity().startActivity(myIntent);
                //do what you want to do when the button is clicked
            }
        });


        btnQuestionBank = (Button) view.findViewById(R.id.btnQuestionBank);
        btnQuestionBank.setOnClickListener(new View.OnClickListener() //directly instantiated the interface and defined the method right where it's used
        {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(getActivity(), QuestionBankActivity.class);

                //mDrawerToggle.setDrawerIndicatorEnabled(false);
                mDrawerLayout.closeDrawer(containerView);
                getActivity().startActivity(myIntent);
                //do what you want to do when the button is clicked
            }
        });



        btnHandBook = (Button) view.findViewById(R.id.btnHandbook);
        btnHandBook.setOnClickListener(new View.OnClickListener() //directly instantiated the interface and defined the method right where it's used
        {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(getActivity(), CategoriesActivity.class);

                //mDrawerToggle.setDrawerIndicatorEnabled(false);
                mDrawerLayout.closeDrawer(containerView);
                getActivity().startActivity(myIntent);
                //do what you want to do when the button is clicked
            }
        });
/*
        btnPracReview = (Button) view.findViewById(R.id.btnPracStart);
        btnPracReview.setOnClickListener(new View.OnClickListener() //directly instantiated the interface and defined the method right where it's used
        {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(getActivity(), PracticalReviewActivity.class);

                //mDrawerToggle.setDrawerIndicatorEnabled(false);
                mDrawerLayout.closeDrawer(containerView);
                getActivity().startActivity(myIntent);
                //do what you want to do when the button is clicked
            }
        });
*/

        btnMore = (Button) view.findViewById(R.id.btnMore);
        btnMore.setOnClickListener(new View.OnClickListener() //directly instantiated the interface and defined the method right where it's used
        {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("message/rfc822");
                i.putExtra(Intent.EXTRA_EMAIL, new String[]{"waynestudent38@gmail.com"});
                i.putExtra(Intent.EXTRA_SUBJECT, "User Feedback");
                i.putExtra(Intent.EXTRA_TEXT   , "(Enter any questions, comments or suggestions pertaining to the app here)");
                try {
                    startActivity(Intent.createChooser(i, "Send mail..."));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(getActivity(), "There are no email clients installed.", Toast.LENGTH_SHORT).show();
                }
                //do what you want to do when the button is clicked
            }
        });

        return view;
    }


    public void setUp(int fragmentId, DrawerLayout drawerLayout, final Toolbar toolbar) {
        containerView = getActivity().findViewById(fragmentId);
        mDrawerLayout = drawerLayout;//stores the object being passed from the main activity
        mDrawerToggle = new ActionBarDrawerToggle(getActivity(), drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close) {

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                if (!mUserLearnedDrawer) {
                    mUserLearnedDrawer = true;
                    saveToPreferences(getActivity(), KEY_USER_LEARNED_DRAWER, mUserLearnedDrawer + "");
                }
                getActivity().invalidateOptionsMenu();//this will make your activity draw the action bar again

            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                if (slideOffset < 0.6) {
                    toolbar.setAlpha(1 - slideOffset);
                }
            }

        };
        if (!mUserLearnedDrawer && !mFromSavedInstanceState) {
            mDrawerLayout.openDrawer(containerView);
        }

        mDrawerLayout.setDrawerListener(mDrawerToggle);

        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });

    }

    public static void saveToPreferences(Context context, String preferenceName, String preferenceValue) {

        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(preferenceName, preferenceValue);

        editor.apply();
    }

    public static String readFromPreferences(Context context, String preferenceName, String defaultValue) {

        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);

        return sharedPreferences.getString(preferenceName, defaultValue);

    }

}
