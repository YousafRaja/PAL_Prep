package ca2.testproject.shotgunstudios2.palprepbeta1;

/**prompt
 *- Encapsulates all the details associated with each questions
 */
public class prompt {
    String pictureName;

    String questionDescription;
    String choiceDescription1;
    String choiceDescription2;
    String choiceDescription3;
    String choiceDescription4;
    String categoryIdentifier;
    String explanation;

    int  answer ;
    int index;
    String type;
    int selectedAnswer=0;

    boolean answered=false;

    String questionStatus ="";

    public void resetPrompt (){
        selectedAnswer=0;
        answered=false;
        questionStatus="";
    }

    public String getCategoryIdentifier (String shortName){
        return categoryIdentifier;
    }

    public void setCategoryIdentifier (String shortName){
        categoryIdentifier = shortName;
    }


    public int getSelectedAnswer (){ return selectedAnswer; }

    public void setSelectedAnswer(int answer){ selectedAnswer= answer; }


    public String getQuestionStatus (){ return questionStatus; }

    public void setQuestionStatus (String status){ questionStatus = status; }

    public String getQuestion (){
        return questionDescription;
    }

    public String getChoice1 (){
        return choiceDescription1;
    }

    public String getChoice2 (){
        return choiceDescription2;
    }

    public String getChoice3 (){
        return choiceDescription3;
    }

    public String getChoice4 (){
        return choiceDescription4;
    }
    public int getAnswer (){
        return answer;
    }

    public String getType (){
        return type;
    }

    public boolean getAnswered (){
        return answered;
    }

    public void setAnswered (boolean value){
        answered=value;
    }

}


