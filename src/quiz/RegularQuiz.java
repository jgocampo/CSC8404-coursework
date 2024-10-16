package quiz;

import question.Question;
import student.Student;
import statistics.Statistics;
import java.util.List;

/**
 * This class represents a regular quiz that students can take.
 * It extends the QuizFactory and implements the behavior for taking
 * regular quizzes, tracking scores, and updating the student's statistics.
 */

public final class RegularQuiz extends QuizFactory {

    /**
     * Constructor for RegularQuiz. It initializes the quiz with a pool of questions.
     *
     * @param questionPool The list of questions to use in the quiz.
     */

    public RegularQuiz(List<Question> questionPool) {
        super(questionPool);
    }

    /**
     * Creates a new instance of a RegularQuiz with a given list of questions.
     *
     * @param selectedQuestions The list of selected questions for the quiz.
     * @return A new instance of RegularQuiz with the selected questions.
     */
    @Override
    protected Quiz createQuizInstance(List<Question> selectedQuestions) {
        return new RegularQuiz(selectedQuestions);
    }

    /**
     * This method is not supported in RegularQuiz because revision quizzes are not generated here
     * and handles the exceptions it could generate.
     * @param revisionQuestions The list of revision questions.
     * @param student The student who takes the revision quiz.
     * @throws UnsupportedOperationException if this method is called.
     */

    @Override
    protected Quiz createRevisionQuizInstance(List<Question> revisionQuestions, Student student) {
        throw new UnsupportedOperationException("RegularQuiz cannot generate a revision quiz.");
    }

    /**
     * Takes the quiz by comparing the students answers with the correct answers and updates the statistics.
     *
     * @param student The student taking the quiz.
     * @param questions The list of questions in the quiz.
     * @param answers The student's answers to the questions.
     * @return The score the student achieved in the quiz (as a percentage).
     * @throws IllegalStateException if the student is not eligible to take more regular quizzes.
     */

    @Override
    public double takeQuiz(Student student, List<Question> questions, List<String> answers) {
        Statistics stats = student.getStatistics();

        if (!stats.canTakeRegularQuiz()) {
            throw new IllegalStateException("Student cannot take more regular quizzes. Final verdict: " + stats.getVerdict());
        }

        int correctAnswers = 0;
        for (int i = 0; i < questions.size(); i++) {
            if (questions.get(i).checkAnswer(answers.get(i))) {
                correctAnswers++;
            }
        }

        double score = (double) correctAnswers / questions.size();

        recordSeenQuestions(student, questions);

        student.getStatistics().recordRegularQuizScore(score);

        return score;
    }

}
