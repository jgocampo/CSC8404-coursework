package quiz;

import question.Question;
import student.Student;
import statistics.Statistics;
import java.util.List;

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
        return new RevisionQuiz(revisionQuestions);  // Devuelve una nueva instancia de quiz de revisión con las preguntas seleccionadas
    }


//    public double takeQuiz(Student student, List<Question> questions, List<String> answers) {
//        // Verificar si el estudiante es elegible para tomar un quiz de revisión
//        Statistics stats = student.getStatistics();
//        if (!stats.canTakeRevisionQuiz()) {
//            System.out.println("Student cannot take more revision quizzes. Final verdict: " + stats.getVerdict());
//            return 0.0;  // No puede tomar el quiz de revisión
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
//        stats.recordRevisionQuizScore(score);
//
//        // Retornar el puntaje
//        return score;
//    }

    @Override
    public double takeQuiz(Student student, List<Question> questions, List<String> answers) {
        // Verificar si el estudiante es elegible para tomar un quiz de revisión
        Statistics stats = student.getStatistics();
        if (!stats.canTakeRevisionQuiz()) {
            System.out.println("Student cannot take more revision quizzes. Final verdict: " + stats.getVerdict());
            return 0.0;  // No puede tomar el quiz de revisión
        }

        // Asegurarse de que la lista de respuestas tenga el mismo tamaño que las preguntas
        if (questions.size() != answers.size()) {
            throw new IllegalArgumentException("Mismatch between number of questions and answers.");
        }

        // Calcular el puntaje
        int correctAnswers = 0;
        for (int i = 0; i < questions.size(); i++) {
            if (questions.get(i).checkAnswer(answers.get(i))) {
                correctAnswers++;
            }
        }
        double score = (double) correctAnswers / questions.size();

        // Registrar el puntaje en las estadísticas
        stats.recordRevisionQuizScore(score);

        // Retornar el puntaje
        return score;
    }

}

