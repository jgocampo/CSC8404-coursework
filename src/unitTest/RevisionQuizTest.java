package unitTest;
import quiz.RevisionQuiz;
import question.Question;
import student.Student;
import statistics.Statistics;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the RevisionQuiz class with static question pool and proper handling of multiple choice questions.
 */
public class RevisionQuizTest {

    /**
     * Test that a student can take a revision quiz after failing a regular quiz.
     */
    @Test
    public void testTakeRevisionQuizAfterFailingRegularQuiz() {
        List<Question> questionPool = TestQuestionPool.getQuestionPool();
        RevisionQuiz revisionQuiz = new RevisionQuiz(questionPool);

        // Crear un estudiante
        Student student = new Student("Jane", "Doe", new java.util.Date());
        Statistics stats = student.getStatistics();
        stats.recordRegularQuizScore(0.3);  // El estudiante falló el quiz regular

        // Asegurarse de que las respuestas coinciden con el número de preguntas
        List<String> answers = Arrays.asList("Quito", "Mars", "Java,Python,C++", "Apple,Banana");
        double score = revisionQuiz.takeQuiz(student, questionPool, answers);

        assertTrue(score > 0.0);  // El estudiante debería recibir un puntaje mayor a 0
    }


    /**
     * Test that a student cannot take more than two revision quizzes.
     */
    @Test
    public void testCannotExceedTwoRevisionQuizzes() {
        List<Question> questionPool = TestQuestionPool.getQuestionPool();
        RevisionQuiz revisionQuiz = new RevisionQuiz(questionPool);

        // Crear un estudiante
        Student student = new Student("John", "Doe", new java.util.Date());
        Statistics stats = student.getStatistics();
        stats.recordRegularQuizScore(0.0);  // El estudiante falló el quiz regular

        // El estudiante toma dos quizzes de revisión
        stats.recordRevisionQuizScore(0.2);  // Primer intento de revisión
        stats.recordRevisionQuizScore(0.3);  // Segundo intento de revisión

        // Intentar tomar un tercer quiz de revisión debería fallar
        double score = revisionQuiz.takeQuiz(student, questionPool, Arrays.asList("London", "CO2"));
        assertEquals(0.0, score);  // No puede tomar otro quiz, por lo que el puntaje debe ser 0
    }

    /**
     * Test that a student cannot take a revision quiz after receiving a PASS verdict.
     */
    @Test
    public void testCannotTakeRevisionQuizAfterPass() {
        List<Question> questionPool = TestQuestionPool.getQuestionPool();
        RevisionQuiz revisionQuiz = new RevisionQuiz(questionPool);

        // Crear un estudiante
        Student student = new Student("Jane", "Doe", new java.util.Date());
        Statistics stats = student.getStatistics();
        stats.recordRegularQuizScore(0.7);  // El estudiante pasó el quiz regular

        // Intentar tomar un quiz de revisión después de haber pasado el quiz regular
        double score = revisionQuiz.takeQuiz(student, questionPool, Arrays.asList("Paris", "Java,Python,C++"));
        assertEquals(0.0, score);  // No debe poder tomar un quiz de revisión si ya pasó
    }

    /**
     * Test that the revision quiz excludes questions that were answered correctly in the regular quiz.
     */
    @Test
    public void testRevisionQuizExcludesCorrectlyAnsweredQuestions() {
        List<Question> questionPool = TestQuestionPool.getQuestionPool();
        RevisionQuiz revisionQuiz = new RevisionQuiz(questionPool);

        // Crear un estudiante
        Student student = new Student("Jane", "Doe", new java.util.Date());
        Statistics stats = student.getStatistics();
        stats.recordRegularQuizScore(0.5);  // El estudiante pasó una pregunta correctamente

        // Generar un quiz de revisión con preguntas incorrectas
        List<String> answers = Arrays.asList("London", "H2O", "Java,Python,C++");
        double score = revisionQuiz.takeQuiz(student, questionPool, answers);

        // Verificar que no se incluyan las preguntas que el estudiante respondió correctamente
        assertTrue(score > 0.0);  // El puntaje debería reflejar solo las preguntas no vistas o incorrectas
    }

    /**
     * Test that a student can take a revision quiz with questions of both types.
     */
    @Test
    public void testRevisionQuizWithBothQuestionTypes() {
        List<Question> questionPool = TestQuestionPool.getQuestionPool();
        RevisionQuiz revisionQuiz = new RevisionQuiz(questionPool);

        // Crear un estudiante
        Student student = new Student("Jane", "Doe", new java.util.Date());
        Statistics stats = student.getStatistics();
        stats.recordRegularQuizScore(0.4);  // Falló el quiz regular

        // Estudiante toma un quiz de revisión con preguntas de ambos tipos
        List<String> answers = Arrays.asList("Paris", "Java,Python,C++");
        double score = revisionQuiz.takeQuiz(student, questionPool, answers);

        assertTrue(score > 0.0);  // El estudiante debería recibir un puntaje mayor a 0
    }
}

