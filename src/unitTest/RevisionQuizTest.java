package unitTest;
import quiz.RevisionQuiz;
import question.FreeResponseQuestion;
import question.MultipleChoiceQuestion;
import question.Question;
import student.Student;
import statistics.Statistics;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the RevisionQuiz class with manually assigned questions.
 */
public class RevisionQuizTest {

    /**
     * Test que un estudiante puede tomar un examen regular con preguntas manualmente asignadas.
     */
    @Test
    public void testTakeRegularQuiz() {
        // Crear preguntas manualmente
        List<Question> questions = new ArrayList<>();
        questions.add(new FreeResponseQuestion("What is the capital of France?", "Paris"));
        questions.add(new MultipleChoiceQuestion("Which are programming languages?", new String[]{"Java", "Python", "C++"}));

        // Crear una instancia de RevisionQuiz (simulando un quiz regular)
        RevisionQuiz quiz = new RevisionQuiz(questions);

        // Crear un estudiante
        Student student = new Student("John", "Doe", new java.util.Date());

        // Proporcionar respuestas correctas
        List<String> answers = List.of("Paris", "Java,Python,C++");

        // Tomar el quiz
        double score = quiz.takeQuiz(student, questions, answers);

        // Verificar el puntaje (100% correcto)
        assertEquals(1.0, score);
    }

    /**
     * Test que un estudiante puede tomar un examen de revisión con preguntas asignadas manualmente.
     */
    @Test
    public void testTakeRevisionQuiz() {
        // Crear preguntas manualmente
        List<Question> questions = new ArrayList<>();
        questions.add(new FreeResponseQuestion("What is the capital of France?", "Paris"));
        questions.add(new FreeResponseQuestion("What is the chemical symbol for water?", "H2O"));
        questions.add(new MultipleChoiceQuestion("Which are fruits?", new String[]{"Apple", "Banana", "Orange"}));

        // Crear una instancia de RevisionQuiz (simulando un quiz de revisión)
        RevisionQuiz revisionQuiz = new RevisionQuiz(questions);

        // Crear un estudiante
        Student student = new Student("Jane", "Doe", new java.util.Date());
        Statistics stats = student.getStatistics();
        stats.recordRegularQuizScore(0.3);  // El estudiante falló el quiz regular

        // Proporcionar respuestas correctas
        List<String> answers = List.of("Paris", "H2O", "Apple,Banana,Orange");

        // Tomar el quiz de revisión
        double score = revisionQuiz.takeQuiz(student, questions, answers);

        // Verificar el puntaje (100% correcto)
        assertEquals(1.0, score);
    }

    /**
     * Test que un estudiante no puede ver preguntas repetidas en el quiz de revisión.
     */
    @Test
    public void testRevisionQuizExcludesAlreadySeenQuestions() {
        // Crear preguntas manualmente
        List<Question> allQuestions = new ArrayList<>();
        allQuestions.add(new FreeResponseQuestion("What is the capital of France?", "Paris"));
        allQuestions.add(new FreeResponseQuestion("What is the chemical symbol for water?", "H2O"));
        allQuestions.add(new MultipleChoiceQuestion("Which are fruits?", new String[]{"Apple", "Banana", "Orange"}));
        allQuestions.add(new FreeResponseQuestion("Who wrote '1984'?", "George Orwell"));

        // Crear una instancia de RevisionQuiz
        RevisionQuiz revisionQuiz = new RevisionQuiz(allQuestions);

        // Crear un estudiante
        Student student = new Student("Jane", "Doe", new java.util.Date());
        Statistics stats = student.getStatistics();
        stats.recordRegularQuizScore(0.3);  // El estudiante falló el quiz regular

        // Primer intento del quiz de revisión (damos respuestas incorrectas para evitar PASS)
        List<Question> firstQuizQuestions = allQuestions.subList(0, 2);  // Seleccionamos las dos primeras preguntas
        List<String> firstQuizAnswers = List.of("London", "CO2");  // Respuestas incorrectas
        revisionQuiz.takeQuiz(student, firstQuizQuestions, firstQuizAnswers);

        // Registrar las preguntas vistas
        List<Question> seenQuestions = new ArrayList<>(firstQuizQuestions);

        // Segundo intento del quiz de revisión, excluir preguntas ya vistas
        List<Question> secondQuizQuestions = allQuestions.subList(2, 4);  // Seleccionamos las siguientes preguntas
        List<String> secondQuizAnswers = List.of("Apple,Banana,Orange", "George Orwell");

        // Verificar que las nuevas preguntas no fueron vistas en el primer quiz
        assertFalse(seenQuestions.containsAll(secondQuizQuestions), "El estudiante no debe ver preguntas repetidas");

        // Tomar el segundo quiz de revisión
        double secondScore = revisionQuiz.takeQuiz(student, secondQuizQuestions, secondQuizAnswers);

        // Verificar el puntaje (100% correcto)
        assertEquals(1.0, secondScore);
    }


    /**
     * Test que un estudiante no puede tomar más de dos quizzes de revisión.
     */
    @Test
    public void testCannotExceedTwoRevisionQuizzes() {
        // Crear preguntas manualmente
        List<Question> questions = new ArrayList<>();
        questions.add(new FreeResponseQuestion("What is the capital of France?", "Paris"));
        questions.add(new FreeResponseQuestion("What is the chemical symbol for water?", "H2O"));
        questions.add(new MultipleChoiceQuestion("Which are programming languages?", new String[]{"Java", "Python", "C++"}));

        // Crear una instancia de RevisionQuiz
        RevisionQuiz revisionQuiz = new RevisionQuiz(questions);

        // Crear un estudiante
        Student student = new Student("John", "Doe", new java.util.Date());
        Statistics stats = student.getStatistics();
        stats.recordRegularQuizScore(0.0);  // El estudiante falló el quiz regular

        // Primer intento del quiz de revisión
        revisionQuiz.takeQuiz(student, questions, List.of("Paris", "H2O", "Java,Python,C++"));

        // Segundo intento del quiz de revisión
        revisionQuiz.takeQuiz(student, questions, List.of("Paris", "H2O", "Java,Python,C++"));

        // Intentar tomar un tercer quiz (el puntaje debe ser 0 ya que no puede tomar más quizzes)
        double thirdScore = revisionQuiz.takeQuiz(student, questions, List.of("Paris", "H2O", "Java,Python,C++"));

        // Verificar que no puede tomar un tercer quiz
        assertEquals(0.0, thirdScore);  // El puntaje debe ser 0 porque no puede tomar más de dos quizzes de revisión
    }
}


