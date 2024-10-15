package unitTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import question.FreeResponseQuestion;
import question.MultipleChoiceQuestion;
import question.Question;
import quiz.Quiz;
import quiz.QuizFactory;
import quiz.RegularQuiz;
import quiz.RevisionQuiz;
import student.Student;
import statistics.Statistics;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class RevisionQuizTest {

    private List<Question> questionPool;
    private QuizFactory quizFactory;
    private Student student;

    @BeforeEach
    public void setup() {
        // Crear un banco de preguntas mixtas más grande
        questionPool = new ArrayList<>();
        questionPool.add(new FreeResponseQuestion("What is the capital of Spain?", "Madrid"));
        questionPool.add(new FreeResponseQuestion("What is the chemical formula for water?", "H2O"));
        questionPool.add(new FreeResponseQuestion("Who wrote '1984'?", "George Orwell"));
        questionPool.add(new FreeResponseQuestion("What is the largest planet in the Solar System?", "Jupiter"));
        questionPool.add(new FreeResponseQuestion("Who discovered penicillin?", "Alexander Fleming"));
        questionPool.add(new MultipleChoiceQuestion("Which are vegetables?", new String[]{"Carrot", "Broccoli", "Spinach"}));
        questionPool.add(new MultipleChoiceQuestion("Which are primary colors?", new String[]{"Red", "Blue", "Yellow"}));
        questionPool.add(new MultipleChoiceQuestion("Which are fruits?", new String[]{"Apple", "Banana", "Orange"}));
        questionPool.add(new MultipleChoiceQuestion("Which are chemical elements?", new String[]{"Oxygen", "Carbon", "Nitrogen"}));
        questionPool.add(new MultipleChoiceQuestion("Which are programming languages?", new String[]{"Java", "Python", "C++"}));

        // Crear una instancia de Student
        Calendar cal = Calendar.getInstance();
        cal.set(1995, Calendar.JANUARY, 1);
        Date birthDate = cal.getTime();
        student = new Student("Jane", "Doe", birthDate);

        // Inicializar el QuizFactory con el pool de preguntas
        quizFactory = new RevisionQuiz(questionPool, student);
    }

    @Test
    public void testReviseGeneratesCorrectQuiz() {
        // Simular la creación de un quiz regular con un RegularQuiz
        QuizFactory regularQuizFactory = new RegularQuiz(questionPool);  // Asegúrate de que sea un regular quiz

        // Primer quiz regular, donde el estudiante responde algunas preguntas mal
        List<String> regularQuizAnswers = List.of("Madrid", "Wrong Answer", "Wrong Answer", "Wrong Answer", "Alexander Fleming");
        regularQuizFactory.generateQuiz(5).takeQuiz(student, questionPool.subList(0, 5), regularQuizAnswers);

        // El estudiante no ha aprobado aún
        Statistics stats = student.getStatistics();
        assertEquals("TBD", stats.getVerdict());

        // Ahora se genera un quiz de revisión basado en las respuestas incorrectas
        Quiz revisionQuiz = quizFactory.revise(student, 3);

        // Obtener las preguntas del quiz de revisión generado
        List<Question> revisionQuizQuestions = revisionQuiz.getQuestions();

        // Asegurarse de que las preguntas de revisión son las que falló en el primer intento
        List<Question> expectedQuestions = questionPool.subList(1, 3);  // Preguntas incorrectas del quiz original
        assertTrue(revisionQuizQuestions.containsAll(expectedQuestions), "Las preguntas del RevisionQuiz deben ser las que el estudiante falló en el intento regular");
    }


    @Test
    public void testTakeRevisionQuiz() {
        // Generar el quiz de revisión con preguntas específicas
        Quiz revisionQuiz = quizFactory.revise(student, 4);

        // Respuestas correctas para el quiz de revisión
        List<String> revisionAnswers = List.of("Madrid", "H2O", "Carrot,Broccoli,Spinach", "Red,Blue,Yellow");
        double score = revisionQuiz.takeQuiz(student, questionPool.subList(0, 4), revisionAnswers);

        // El estudiante debe haber respondido todo correctamente
        assertEquals(1.0, score);

        Statistics stats = student.getStatistics();
        assertEquals("TBD", stats.getVerdict());  // El veredicto no cambia porque es un quiz de revisión
    }

    @Test
    public void testCannotTakeMoreThanTwoRevisionQuizzes() {
        // Primer quiz de revisión
        quizFactory.revise(student, 4).takeQuiz(student, questionPool.subList(0, 4), List.of("Madrid", "H2O", "Carrot,Broccoli,Spinach", "Red,Blue,Yellow"));

        // Segundo quiz de revisión
        quizFactory.revise(student, 4).takeQuiz(student, questionPool.subList(4, 8), List.of("Apple,Banana,Orange", "Jupiter", "Oxygen,Carbon,Nitrogen", "Java,Python,C++"));

        // Estadísticas deben indicar que no puede tomar más quizzes de revisión
        Statistics stats = student.getStatistics();
        assertFalse(stats.canTakeRevisionQuiz());

        // Intentar tomar un tercer quiz de revisión debe lanzar una excepción
        IllegalStateException exception = assertThrows(IllegalStateException.class, () ->
                quizFactory.revise(student, 4)
        );

        assertEquals("Cannot take more revision quizzes. Final verdict: TBD", exception.getMessage());
    }

    @Test
    public void testRevisionQuizDoesNotContainPreviouslySeenQuestions() {
        // Tomar un quiz regular con respuestas correctas para ver todas las preguntas
        List<String> correctAnswers = List.of("Madrid", "H2O", "Carrot,Broccoli,Spinach", "Red,Blue,Yellow");
        quizFactory.generateQuiz(4).takeQuiz(student, questionPool.subList(0, 4), correctAnswers);

        // Generar un quiz de revisión
        Quiz revisionQuiz = quizFactory.revise(student, 4);

        // Asegurarse de que no se seleccionen preguntas ya vistas
        List<Question> revisionQuestions = questionPool.subList(0, 4);
        assertTrue(revisionQuestions.isEmpty(), "El quiz de revisión no debe contener preguntas ya vistas");
    }
}


