import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import static org.apache.commons.lang3.StringEscapeUtils.unescapeHtml4;

/**
 * Created by Alexander on 2017-03-29.
 */
public class QuizQuestion {
    private String category;
    private String type;
    private String difficulty;
    private String question;
    private String correct_answer;
    private String[] incorrect_answers;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public String getQuestion() { return question; }

    public void setQuestion(String question) { this.question = question; }

    public String getCorrect_answer() {
        return correct_answer;
    }

    public void setCorrect_answer(String correct_answer) {
        this.correct_answer = correct_answer;
    }

    public String[] getIncorrect_answers() {
        return incorrect_answers;
    }

    public void setIncorrect_answers(String[] incorrect_answers) {
        this.incorrect_answers = incorrect_answers;
    }


    public static QuizQuestion generateQuestion() throws JsonSyntaxException {
        String json = HTTPHandler.getHTTPJSON("https://opentdb.com/api.php?amount=1");
        json = editString(json);

        Gson gson = new Gson();

        return gson.fromJson(json, QuizQuestion.class);
    }

    public String getFormatted() {
        String questionStr = "__**Quiz Category:**__ " + category + " \n\n"
                            + unescapeHtml4(question) + " \n\n";
        return questionStr;
    }

    private static String editString(String str) {
        str = str.replaceAll("^([^\\[]*)\\[","");
        str = str.substring(0, str.length()-2);

        return str;
    }

    @Override
    public String toString(){
        return "Question ['category': " + category + "\n'type': " + type + "\n'difficulty': " + difficulty
                + "\n'question': " + question + "'\ncorrect_answer': " + correct_answer + "\n'incorrect_answers': " + incorrect_answers + "]";
    }
}
