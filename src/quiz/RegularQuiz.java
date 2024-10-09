package quiz;
import question.Question;
import student.Student;
import java.util.*;


public final class RegularQuiz extends QuizFactory {

    public RegularQuiz(List<Question> questionPool) {
        super(questionPool);
    }


    protected Quiz createQuizInstance(List<Question> selectedQuestions) {
        return new RegularQuiz(selectedQuestions);
    }


    protected Quiz createRevisionQuizInstance(List<Question> revisionQuestions, Student student) {
        throw new UnsupportedOperationException("RegularQuiz cannot generate a revision quiz.");
    }


    public double takeQuiz(Student student, List<Question> questions, List<String> answers) {
        int correctAnswers = 0;
        for (int i = 0; i < questions.size(); i++) {
            if (questions.get(i).checkAnswer(answers.get(i))) {
                correctAnswers++;
            }
        }
        recordQuizAttempt(student, questions);  // Registrar el intento
        return (double) correctAnswers / questions.size();
    }

    public double takeRevisionQuiz(Student student, List<Question> questions, List<String> answers) {
        throw new UnsupportedOperationException("RegularQuiz cannot take revision quizzes.");
    }
}
