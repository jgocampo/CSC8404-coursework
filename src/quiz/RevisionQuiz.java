package quiz;

import question.Question;
import student.Student;
import java.util.List;

/**
 * This class represents a revision quiz that is generated for students to practice
 * with questions they have either not seen before or answered incorrectly. This tye of quiz
 * does not affect the final verdict.
 * It extends the abstract class QuizFactory to handle the specific behavior of revision quizzes.
 */

public final class RevisionQuiz extends QuizFactory {

    /**
     * Constructor for creating a RevisionQuiz with a pool of questions and a reference to the student.
     * The student is important for tracking the seenQuestions.
     *
     * @param questionPool The pool of questions from which the revision quiz is created.
     * @param student The student taking the revision quiz.
     */
    public RevisionQuiz(List<Question> questionPool, Student student) {
        super(questionPool);

    }

    /**
     * This method cannot be used in the context of a RevisionQuiz, as revision quizzes
     * cannot generate regular quizzes. It will always throw an exception.
     *
     * @param selectedQuestions The list of questions for the quiz.
     * @throws UnsupportedOperationException when invoked.
     */

    @Override
    protected Quiz createQuizInstance(List<Question> selectedQuestions) {
        throw new UnsupportedOperationException("RevisionQuiz cannot generate a regular quiz.");
    }

    /**
     * Creates a new instance of RevisionQuiz using the selected questions and the given student.
     * This allows the quiz system to generate quizzes based on what the student has or hasn't answered correctly.
     *
     * @param revisionQuestions The list of revision questions.
     * @param student The student who is taking the revision quiz.
     * @return A new instance of RevisionQuiz with the specified questions.
     */

    @Override
    protected Quiz createRevisionQuizInstance(List<Question> revisionQuestions, Student student) {
        return new RevisionQuiz(revisionQuestions, student);
    }

    /**
     * Allows the student to take the revision quiz by checking the answers and calculating the score.
     * After the quiz is taken, it tracks the questions seen and updates the student's statistics.
     *
     * @param student The student taking the quiz.
     * @param questions The list of questions in the quiz.
     * @param answers The student answers for the quiz.
     * @return The score the student achieved.
     * @throws IllegalStateException if the student has already exceeded the allowed number of revision attempts.
     */

    @Override
    public double takeQuiz(Student student, List<Question> questions, List<String> answers) {

        if (!student.getStatistics().canTakeRevisionQuiz()) {
            throw new IllegalStateException("Cannot take more revision quizzes. Final verdict: " + student.getStatistics().getVerdict());
        }

        int correctAnswers = 0;
        for (int i = 0; i < questions.size(); i++) {
            if (questions.get(i).checkAnswer(answers.get(i))) {
                correctAnswers++;
            }
        }

        double score = (double) correctAnswers / questions.size();

        recordSeenQuestions(student, questions);

        student.getStatistics().recordRevisionQuizScore(score);

        return score;
    }

}
