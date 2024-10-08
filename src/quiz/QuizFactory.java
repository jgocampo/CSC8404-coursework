package quiz;
import question.Question;
import student.Student;
import java.util.*;


public abstract class QuizFactory implements Quiz {

    protected final List<Question> questionPool;
    protected final Map<Student, List<Question>> studentHistory;

    public QuizFactory(List<Question> questionPool) {
        if (questionPool == null || questionPool.isEmpty()) {
            throw new IllegalArgumentException("Question pool cannot be empty.");
        }
        this.questionPool = new ArrayList<>(questionPool);
        this.studentHistory = new HashMap<>();
    }

    @Override
    public Quiz generateQuiz(int numberOfQuestions) {
        if (numberOfQuestions < 1 || numberOfQuestions > questionPool.size()) {
            throw new IllegalArgumentException("Invalid number of questions.");
        }
        Collections.shuffle(questionPool);
        List<Question> selectedQuestions = new ArrayList<>(questionPool.subList(0, numberOfQuestions));
        return createQuizInstance(selectedQuestions);  // Método abstracto que será implementado en las subclases
    }

    @Override
    public Quiz revise(Student student, int numberOfQuestions) {
        if (!studentHistory.containsKey(student)) {
            throw new IllegalArgumentException("Student has not taken any quizzes yet.");
        }
        List<Question> revisionQuestions = getUnansweredOrIncorrectQuestions(student);
        if (revisionQuestions.size() < numberOfQuestions) {
            throw new IllegalArgumentException("Not enough questions available for revision.");
        }
        Collections.shuffle(revisionQuestions);
        return createRevisionQuizInstance(revisionQuestions.subList(0, numberOfQuestions), student);
    }

    // Método abstracto para crear una instancia de quiz regular
    protected abstract Quiz createQuizInstance(List<Question> selectedQuestions);

    // Método abstracto para crear una instancia de quiz de revisión
    protected abstract Quiz createRevisionQuizInstance(List<Question> revisionQuestions, Student student);

    // Método para obtener las preguntas no respondidas o incorrectas de un estudiante
    private List<Question> getUnansweredOrIncorrectQuestions(Student student) {
        List<Question> history = studentHistory.get(student);
        List<Question> revisionQuestions = new ArrayList<>();
        for (Question question : questionPool) {
            if (!history.contains(question)) {
                revisionQuestions.add(question);
            }
        }
        return revisionQuestions;
    }

    // Método para registrar los intentos del quiz en el historial
    protected void recordQuizAttempt(Student student, List<Question> questions) {
        studentHistory.computeIfAbsent(student, k -> new ArrayList<>()).addAll(questions);
    }
}

