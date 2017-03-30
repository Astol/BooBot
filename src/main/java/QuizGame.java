import java.time.LocalDateTime;
import java.util.Hashtable;
import java.util.Random;

/**
 * Created by Alexander on 2017-03-29.
 */
public class QuizGame {
    private Random random = new Random();

    private QuizQuestion question;
    private LocalDateTime timeLimit;
    private Hashtable<String, String> solution = new Hashtable<>();
    private boolean answered;

    private boolean gameCreated = false;

    private static final String[] options = {"A", "B", "C", "D"};

    public void createGame(QuizQuestion question, LocalDateTime creationTime) {
        this.question = question;
        gameCreated = true;
        timeLimit = creationTime.plusSeconds(12);
        answered = false;
        scrambleAnswers(question.getCorrect_answer(), question.getIncorrect_answers());
    }

    private void scrambleAnswers(String correct, String[] incorrect) {
        int randomInt = random.ints(0, 3).limit(1).findFirst().getAsInt();
        solution.clear();
        solution.put(options[randomInt], correct);

        int skipped = 10;
        for (int i = 0; i < incorrect.length; i++) {
            if (!solution.containsKey(options[i])) {
                solution.put(options[i], incorrect[i]);
            } else { skipped = i; }

            if( skipped != 10 && i == (incorrect.length -1)) {
                solution.put(options[incorrect.length], incorrect[skipped]);
            }
        }
    }

    public boolean isActive(LocalDateTime time) {
        if(gameCreated && time.isBefore(timeLimit)){
            return true;
        } else {
            return false;
        }
    }

    public boolean correctGuess(String guess) {
        if (question.getType().equals("boolean")) {
            if(question.getCorrect_answer().equalsIgnoreCase(guess) && !answered) {
                return true;
            } else { return false; }
        }
        else {
            if (solution.get(guess).equalsIgnoreCase(question.getCorrect_answer()) && !answered) {
                return true;
            } else {
                return false;
            }
        }
    }

    public String answer() {
        return question.getCorrect_answer();
    }

    public String questionEnding() {
        String questionStr;

        if(question.getType().equals("multiple")){
            questionStr =     "**!!a** " + solution.get("A") + "\n"
                            + "**!!b** " + solution.get("B") + "\n"
                            + "**!!c** " + solution.get("C") + "\n"
                            + "**!!d** " + solution.get("D") + "\n";
        } else {
            questionStr = "**!!true** or **!!false?**";
        }
        return questionStr;
    }
}
