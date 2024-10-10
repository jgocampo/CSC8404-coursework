package quiz;

import question.Question;
import student.Student;
import statistics.Statistics;
import java.util.List;

public final class RegularQuiz extends QuizFactory {

    public RegularQuiz(List<Question> questionPool) {
        super(questionPool);
    }

    @Override
    protected Quiz createQuizInstance(List<Question> selectedQuestions) {
        return new RegularQuiz(selectedQuestions);  // Devuelve una nueva instancia de quiz regular con las preguntas seleccionadas
    }

    @Override
    protected Quiz createRevisionQuizInstance(List<Question> revisionQuestions, Student student) {
        throw new UnsupportedOperationException("RegularQuiz cannot generate a revision quiz.");
    }

    @Override
//    public double takeQuiz(Student student, List<Question> questions, List<String> answers) {
//        // Verificar si el estudiante es elegible para tomar un quiz regular
//        Statistics stats = student.getStatistics();
//        if (!stats.canTakeRegularQuiz()) {
//            System.out.println("Student cannot take more regular quizzes. Final verdict: " + stats.getVerdict());
//            return 0.0;  // No puede tomar el quiz
//        }
//
//        // Calcular el puntaje
//        int correctAnswers = 0;
//        for (int i = 0; i < questions.size(); i++) {
//            if (questions.get(i).checkAnswer(answers.get(i))) {
//                correctAnswers++;
//            }
//        }
//        double score = (double) correctAnswers / questions.size();
//
//        // Registrar el puntaje en las estadísticas
//        stats.recordRegularQuizScore(score);
//
//        // Retornar el puntaje
//        return score;
//    }

    public double takeQuiz(Student student, List<Question> questions, List<String> answers) {
        // Verificar si el estudiante tiene un veredicto
        Statistics stats = student.getStatistics();
        String verdict = stats.getVerdict();

        // Si el veredicto es PASS o FAIL, lanzar una IllegalStateException
        if ("PASS".equals(verdict) || "FAIL".equals(verdict)) {
            throw new IllegalStateException("Student cannot take more regular quizzes. Final verdict: " + verdict);
        }

        // Implementación normal para tomar el quiz
        int correctAnswers = 0;
        for (int i = 0; i < questions.size(); i++) {
            if (questions.get(i).checkAnswer(answers.get(i))) {
                correctAnswers++;
            }
        }

        // Calcular la puntuación
        return (double) correctAnswers / questions.size();
    }
}
