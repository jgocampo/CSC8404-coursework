package quiz;

import question.Question;
import student.Student;
import java.util.*;

public abstract class QuizFactory implements Quiz {
    protected final List<Question> questionPool;  // Pool de preguntas disponibles para los quizzes
    protected final Map<Student, List<Question>> studentHistory;  // Historial de preguntas respondidas por el estudiante

    public QuizFactory(List<Question> questionPool) {
        if (questionPool == null || questionPool.isEmpty()) {
            throw new IllegalArgumentException("The question pool cannot be empty.");
        }
        this.questionPool = new ArrayList<>(questionPool);  // Inicializa el pool de preguntas
        this.studentHistory = new HashMap<>();  // Inicializa el historial de estudiantes
    }

    // Genera un quiz regular seleccionando preguntas aleatorias
    @Override
    public Quiz generateQuiz(int numberOfQuestions) {
        if (numberOfQuestions < 1 || numberOfQuestions > questionPool.size()) {
            throw new IllegalArgumentException("Invalid number of questions.");
        }
        // Barajar las preguntas y seleccionar el número solicitado
        Collections.shuffle(questionPool);
        List<Question> selectedQuestions = new ArrayList<>(questionPool.subList(0, numberOfQuestions));

        return createQuizInstance(selectedQuestions);  // Devuelve una instancia de quiz regular
    }

    // Genera un quiz de revisión basado en preguntas no respondidas correctamente
    @Override
    public Quiz revise(Student student, int numberOfQuestions) {
        if (!studentHistory.containsKey(student)) {
            throw new IllegalArgumentException("Student has not taken any quizzes yet.");
        }

        // Obtener preguntas que no fueron respondidas correctamente
        List<Question> revisionQuestions = getUnansweredOrIncorrectQuestions(student);

        if (revisionQuestions.size() < numberOfQuestions) {
            throw new IllegalArgumentException("Not enough questions available for revision.");
        }

        // Barajar las preguntas de revisión y seleccionar el número solicitado
        Collections.shuffle(revisionQuestions);
        return createRevisionQuizInstance(revisionQuestions.subList(0, numberOfQuestions), student);
    }

    // Método abstracto para crear una instancia de quiz regular
    protected abstract Quiz createQuizInstance(List<Question> selectedQuestions);

    // Método abstracto para crear una instancia de quiz de revisión
    protected abstract Quiz createRevisionQuizInstance(List<Question> revisionQuestions, Student student);

    // Obtener preguntas no respondidas o respondidas incorrectamente
    protected List<Question> getUnansweredOrIncorrectQuestions(Student student) {
        List<Question> history = studentHistory.get(student);
        List<Question> revisionQuestions = new ArrayList<>();
        for (Question question : questionPool) {
            if (!history.contains(question)) {
                revisionQuestions.add(question);
            }
        }
        return revisionQuestions;
    }
}
