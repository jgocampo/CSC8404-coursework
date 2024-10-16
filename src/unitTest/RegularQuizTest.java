package unitTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import question.FreeResponseQuestion;
import question.MultipleChoiceQuestion;
import question.Question;
import quiz.RegularQuiz;
import student.Student;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class RegularQuizTest {

    private List<Question> questionPool;
    private RegularQuiz regularQuiz;
    private Student student;

    @BeforeEach
    public void setup() {
        // Crear un banco de preguntas mixto
        questionPool = new ArrayList<>();
        questionPool.add(new FreeResponseQuestion("What is the capital of Spain?", "Madrid"));
        questionPool.add(new FreeResponseQuestion("What is the chemical formula for water?", "H2O"));
        questionPool.add(new MultipleChoiceQuestion("Which are vegetables?", new String[]{"Carrot", "Broccoli", "Spinach"}));
        questionPool.add(new MultipleChoiceQuestion("Which are primary colors?", new String[]{"Red", "Blue", "Yellow"}));

        // Crear un estudiante
        Calendar cal = Calendar.getInstance();
        cal.set(1995, Calendar.JANUARY, 1);
        Date birthDate = cal.getTime();
        student = new Student("Jane", "Doe", birthDate);

        // Crear un quiz regular
        regularQuiz = new RegularQuiz(questionPool);
    }

    @Test
    public void testGenerateAndTakeRegularQuiz() {
        // Seleccionar manualmente 3 preguntas para el quiz regular
        List<Question> quizQuestions = questionPool.subList(0, 3);  // Seleccionar las primeras 3 preguntas
        List<String> answers = List.of("Madrid", "H2O", "Carrot,Broccoli,Spinach");  // Respuestas correctas

        double score = regularQuiz.takeQuiz(student, quizQuestions, answers);

        assertEquals(1.0, score);  // Todas las respuestas correctas
        assertEquals(1, student.getStatistics().getRegularAttempts());  // Verificar que se registró el intento

        // Verificar que las preguntas vistas se registraron correctamente usando el historial
        assertEquals(3, regularQuiz.getStudentHistory().get(student).size());
    }

    @Test
    public void testRegularQuizWithPartialCorrectAnswers() {
        // Seleccionar manualmente 3 preguntas para el quiz regular
        List<Question> quizQuestions = questionPool.subList(0, 3);  // Seleccionar las primeras 3 preguntas
        List<String> answers = List.of("Madrid", "Wrong", "Carrot,Broccoli,Spinach");  // Algunas respuestas correctas

        double score = regularQuiz.takeQuiz(student, quizQuestions, answers);

        assertEquals(2.0 / 3.0, score);  // 2 de 3 respuestas correctas

        // Verificar que las preguntas vistas se registraron correctamente
        assertEquals(3, regularQuiz.getStudentHistory().get(student).size());
    }

    @Test
    public void testRegularQuizWithIncorrectAnswers() {
        // Seleccionar manualmente 3 preguntas para el quiz regular
        List<Question> quizQuestions = questionPool.subList(0, 3);  // Seleccionar las primeras 3 preguntas
        List<String> answers = List.of("Wrong", "Wrong", "Wrong");  // Todas las respuestas incorrectas

        double score = regularQuiz.takeQuiz(student, quizQuestions, answers);

        assertEquals(0.0, score);  // Todas las respuestas incorrectas

        // Verificar que las preguntas vistas se registraron correctamente
        assertEquals(3, regularQuiz.getStudentHistory().get(student).size());
    }
}


