package quiz;

import question.Question;
import student.Student;
import java.util.*;

/**
 * The QuizFactory is responsible for creating quizzes (both regular and revision). It holds a pool of questions
 * and tracks each student's quiz history to prevent repetition of questions in future quizzes.
 * This class is abstract, meaning specific quiz types need to extend it and provide the details for quiz creation.
 */
public abstract class QuizFactory implements Quiz {
    // A list of questions used in all quizzes
    protected final List<Question> questionPool;
    // Tracks the questions each student has already seen
    protected   Map<Student, List<Question>> studentHistory;

    /**
     * Constructor for QuizFactory. Takes a list of questions as input and initializes the quiz system.
     *
     * @param questionPool The pool of questions from which quizzes will be generated. Must not be null or empty.
     * @throws IllegalArgumentException if the question pool is null or empty.
     */
    public QuizFactory(List<Question> questionPool) {
        if (questionPool == null || questionPool.isEmpty()) {
            throw new IllegalArgumentException("The question pool cannot be empty.");
        }
        this.questionPool = new ArrayList<>(questionPool);
        this.studentHistory = new HashMap<>();
    }

    /**
     * Generates a regular quiz with a specified number of questions. It shuffles the question pool and selects
     * a subset of questions, avoiding any questions that have been seen by the student.
     *
     * @param numberOfQuestions The number of questions for the quiz.
     * @return A new quiz instance with the selected questions.
     * @throws IllegalArgumentException if the number of questions is less than 1 or more than available questions.
     */
    @Override
    public Quiz generateQuiz(int numberOfQuestions) {
        if (numberOfQuestions < 1 || numberOfQuestions > questionPool.size()) {
            throw new IllegalArgumentException("Invalid number of questions. Must be between 1 and " + questionPool.size());
        }

        // Selecciona preguntas al azar del pool de preguntas
        List<Question> selectedQuestions = new ArrayList<>(questionPool);
        Collections.shuffle(selectedQuestions); // Baraja el pool de preguntas
        return createQuizInstance(selectedQuestions.subList(0, numberOfQuestions)); // Selecciona el número de preguntas solicitado
    }


    /**
     * Generates a revision quiz that only includes questions the student hasn't seen before or has answered incorrectly.
     * If there aren't enough questions available for the revision quiz, an exception is thrown.
     *
     * @param student The student who is taking the quiz.
     * @param numberOfQuestions The number of questions in the quiz.
     * @return A new quiz instance with revision questions.
     * @throws IllegalArgumentException if there are not enough unseen or incorrectly answered questions.
     */
    @Override
    public Quiz revise(Student student, int numberOfQuestions) {
        if (student == null) {
            throw new IllegalArgumentException("Student cannot be null.");
        }

        if (!student.getStatistics().canTakeRevisionQuiz()) {
            throw new IllegalStateException("Cannot take more revision quizzes. Final verdict: " + student.getStatistics().getVerdict());
        }

        List<Question> unseenOrIncorrectQuestions = selectUnseenOrIncorrectQuestions(numberOfQuestions, student);

        if (unseenOrIncorrectQuestions.size() < numberOfQuestions) {
            throw new IllegalArgumentException("Not enough unseen or incorrectly answered questions for revision.");
        }

        return createRevisionQuizInstance(unseenOrIncorrectQuestions, student);
    }

    /**
     * Selects questions that the student has either not seen or answered incorrectly.
     * For revision quizzes, these are the questions that the student got wrong in previous attempts.
     *
     * @param numberOfQuestions The number of questions to select.
     * @param student The student for whom the revision quiz is being generated.
     * @return A list of unseen or incorrectly answered questions.
     */
    protected List<Question> selectUnseenOrIncorrectQuestions(int numberOfQuestions, Student student) {
        List<Question> unseenOrIncorrectQuestions = new ArrayList<>();

        // Obtener el historial de preguntas vistas y respondidas incorrectamente
        List<Question> history = studentHistory.getOrDefault(student, new ArrayList<>());
        for (Question question : questionPool) {
            if (!history.contains(question)) {
                unseenOrIncorrectQuestions.add(question);  // Añadir preguntas no vistas
            }
        }

        if (unseenOrIncorrectQuestions.size() < numberOfQuestions) {
            throw new IllegalArgumentException("Not enough unseen or incorrectly answered questions.");
        }

        Collections.shuffle(unseenOrIncorrectQuestions);  // Barajar las preguntas
        return unseenOrIncorrectQuestions.subList(0, numberOfQuestions);
    }

    /**
     * Abstract method for creating a regular quiz instance. Must be implemented by subclasses.
     *
     * @param selectedQuestions The list of selected questions for the quiz.
     * @return A new instance of the quiz.
     */
    protected abstract Quiz createQuizInstance(List<Question> selectedQuestions);

    /**
     * Abstract method for creating a revision quiz instance. Must be implemented by subclasses.
     *
     * @param revisionQuestions The list of selected revision questions.
     * @param student The student taking the quiz.
     * @return A new instance of the revision quiz.
     */
    protected abstract Quiz createRevisionQuizInstance(List<Question> revisionQuestions, Student student);

    // Registrar preguntas vistas en el historial del estudiante
    public void recordSeenQuestions(Student student, List<Question> seenQuestions) {
        // Si el historial del estudiante no está presente, inicialízalo
        studentHistory.putIfAbsent(student, new ArrayList<>());

        // Agregar las preguntas vistas al historial del estudiante
        List<Question> history = studentHistory.get(student);
        //System.out.println("HashCode del estudiante accedido: " + student.hashCode());
        //System.out.println("Contenido del mapa antes de añadir: " + studentHistory);

        if (history != null) {
            history.addAll(seenQuestions); // Añadir preguntas al historial
            System.out.println("Registrando preguntas: " + seenQuestions + " para el estudiante: " + student);

            //System.out.println("Historial actualizado para el estudiante: " + student + " con preguntas: " + seenQuestions);
        } else {
            throw new IllegalStateException("Failed to initialize question history for student.");
        }

        System.out.println("Contenido del mapa después de añadir: " + studentHistory);
    }


    public Map<Student, List<Question>> getStudentHistory() {
        return studentHistory;
    }
}


