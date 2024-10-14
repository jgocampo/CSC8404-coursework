package question;

/**
 * This is the interface Question.
 * This expose the methods of the contract that questions will implement.
 */

public interface Question {


    String getQuestionFormulation();

    boolean checkAnswer(String answer);
}

