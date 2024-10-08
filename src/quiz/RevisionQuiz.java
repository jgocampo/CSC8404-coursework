package quiz;
import question.Question;
import student.Student;
import java.util.*;


public final class RevisionQuiz extends QuizFactory {

    public RevisionQuiz(List<Question> questionPool) {
        super(questionPool);
    }

    @Override
    protected Quiz createQuizInstance(List<Question> selectedQuestions) {
        throw new UnsupportedOperationException("RevisionQuiz cannot generate a regular quiz.");
    }

    @Override
    protected Quiz createRevisionQuizInstance(List<Question> revisionQuestions, Student student) {
        return new RevisionQuiz(revisionQuestions);  // Devuelve un quiz de revisión con preguntas filtradas
    }

    @Override
    public double takeQuiz(Student student, List<Question> questions, List<String> answers) {
        throw new UnsupportedOperationException("RevisionQuiz cannot take regular quizzes.");
    }

    @Override
    public double takeRevisionQuiz(Student student, List<Question> questions, List<String> answers) {
        int correctAnswers = 0;
        for (int i = 0; i < questions.size(); i++) {
            if (questions.get(i).checkAnswer(answers.get(i))) {
                correctAnswers++;
            }
        }
        recordQuizAttempt(student, questions);  // Registrar el intento de revisión
        return (double) correctAnswers / questions.size();
    }
}
