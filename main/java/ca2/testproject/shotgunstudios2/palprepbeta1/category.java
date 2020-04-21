package ca2.testproject.shotgunstudios2.palprepbeta1;

/**Category
 *- Encapsulates info related to each category
 */
public class category  {
    String longName = "a";
    String shortName = "b";
    boolean active = true;

    public String getLongName() {
        return longName;
    }

    public String getShortName() {
        return shortName;
    }

    public Boolean isActive() {
        if (active == true) {
            return true;
        } else {
            return false;
        }
    }


    public void setLongName(String name) {
        longName = name;
    }

    public void setShortName(String name) {
        shortName = name;
    }

    public void setActive(boolean status) {
        active = status;
    }




}







