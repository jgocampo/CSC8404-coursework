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

import java.util.*;

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
        QuizFactory regularQuizFactory = new RegularQuiz(questionPool);  // Generar un regular quiz

        // Primer quiz regular, donde el estudiante responde algunas preguntas incorrectamente
        // Preguntas seleccionadas del questionPool: 0, 1, 2, 3, 4
        List<String> regularQuizAnswers = List.of("Madrid", "Wrong Answer", "Wrong Answer", "Wrong Answer", "Alexander Fleming");
        regularQuizFactory.generateQuiz(5).takeQuiz(student, questionPool.subList(0, 5), regularQuizAnswers);

        // El estudiante no ha aprobado aún
        Statistics stats = student.getStatistics();
        assertEquals("TBD", stats.getVerdict());

        // Ahora se genera un quiz de revisión basado en las respuestas incorrectas
        // Preguntas incorrectas seleccionadas: 1, 2, 3
        Quiz revisionQuiz = quizFactory.revise(student, 3);
        List<Question> revisionQuestions = questionPool.subList(1, 4);  // Preguntas incorrectas del quiz original
        quizFactory.recordSeenQuestions(student, revisionQuestions); // Registrar las preguntas vistas

        assertTrue(quizFactory.getStudentHistory().get(student).containsAll(revisionQuestions),
                "Las preguntas del RevisionQuiz deben ser las que el estudiante falló en el intento regular");
    }

    @Test
    public void testTakeRevisionQuiz() {
        // 1. Primer intento de quiz de revisión
        List<Question> revisionQuestions1 = Arrays.asList(
                questionPool.get(0), // FreeResponse: "What is the capital of Spain?"
                questionPool.get(1), // FreeResponse: "What is the chemical formula for water?"
                questionPool.get(5), // MultipleChoice: "Which are vegetables?"
                questionPool.get(6)  // MultipleChoice: "Which are primary colors?"
        );

        // Respuestas correctas para estas preguntas
        List<String> revisionAnswers1 = List.of("Madrid", "H2O", "Carrot,Broccoli,Spinach", "Red,Blue,Yellow");

        // Tomar el quiz de revisión
        Quiz revisionQuiz = quizFactory.revise(student, 4);
        double score = revisionQuiz.takeQuiz(student, revisionQuestions1, revisionAnswers1);
        System.out.println("Estado del studentHistory después del quiz: " + quizFactory.getStudentHistory());

        quizFactory.recordSeenQuestions(student, revisionQuestions1);


        // Verificar el puntaje obtenido
        assertEquals(1.0, score, "El puntaje del quiz de revisión debería ser 1.0");

        // Verificar que el veredicto no cambie después de un quiz de revisión
        Statistics stats = student.getStatistics();
        assertEquals("TBD", stats.getVerdict());

        // Verificar que las preguntas vistas se registren correctamente
        Map<Student, List<Question>> studentHistory = quizFactory.getStudentHistory();

        // Validar que el mapa no esté vacío
        assertFalse(studentHistory.isEmpty(), "El mapa de studentHistory no debe estar vacío.");

        // Obtener el historial del estudiante
        List<Question> recordedHistory = studentHistory.get(student);
        assertNotNull(recordedHistory, "El historial de preguntas debe existir.");

        // Asegurarse de que las preguntas registradas sean las correctas
        assertTrue(recordedHistory.containsAll(revisionQuestions1), "Las preguntas del quiz de revisión deben estar registradas como vistas.");
    }





    @Test
    public void testCannotTakeMoreThanTwoRevisionQuizzes() {
        // Primer quiz de revisión
        // Preguntas seleccionadas del questionPool: 0, 1, 5, 6
        quizFactory.revise(student, 4).takeQuiz(student, questionPool.subList(0, 4), List.of("Madrid", "H2O", "Carrot,Broccoli,Spinach", "Red,Blue,Yellow"));

        // Segundo quiz de revisión
        // Preguntas seleccionadas del questionPool: 4, 5, 8, 9
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
        // Primer intento de revisión: preguntas seleccionadas explícitamente del questionPool
        List<Question> firstRevisionQuestions = Arrays.asList(
                questionPool.get(0),  // "What is the capital of Spain?"
                questionPool.get(1),  // "What is the chemical formula for water?"
                questionPool.get(5),  // "Which are vegetables?"
                questionPool.get(6)   // "Which are primary colors?"
        );

        // Respuestas correctas para estas preguntas
        List<String> firstRevisionAnswers = List.of("Madrid", "H2O", "Carrot,Broccoli,Spinach", "Red,Blue,Yellow");

        // Tomar el primer quiz de revisión
        Quiz firstRevisionQuiz = quizFactory.revise(student, 4);
        double score = firstRevisionQuiz.takeQuiz(student, firstRevisionQuestions, firstRevisionAnswers);

        quizFactory.recordSeenQuestions(student, firstRevisionQuestions);

        // Verificar que las preguntas del primer quiz de revisión se registraron correctamente
        List<Question> recordedHistory = quizFactory.getStudentHistory().get(student);
        assertNotNull(recordedHistory, "El historial de preguntas vistas no debe ser nulo.");
        assertTrue(recordedHistory.containsAll(firstRevisionQuestions),
                "El historial de preguntas vistas debe contener todas las preguntas del primer quiz de revisión.");

        // Segundo intento de revisión: nuevas preguntas seleccionadas del questionPool
        List<Question> secondRevisionQuestions = Arrays.asList(
                questionPool.get(2),  // "Who wrote '1984'?"
                questionPool.get(3),  // "What is the largest planet in the Solar System?"
                questionPool.get(7),  // "Which are fruits?"
                questionPool.get(8)   // "Which are chemical elements?"
        );

        // Verificar que las preguntas seleccionadas para el segundo quiz de revisión no están en el historial del estudiante
        for (Question question : secondRevisionQuestions) {
            assertFalse(recordedHistory.contains(question),
                    "El segundo quiz de revisión no debe contener preguntas ya vistas.");
        }

        // Tomar el segundo quiz de revisión
        Quiz secondRevisionQuiz = quizFactory.revise(student, 4);
        score = secondRevisionQuiz.takeQuiz(student, secondRevisionQuestions, List.of("George Orwell", "Jupiter", "Apple,Banana,Orange", "Oxygen,Carbon,Nitrogen"));
        quizFactory.recordSeenQuestions(student, secondRevisionQuestions);

        // Verificar que las preguntas del segundo quiz de revisión también se registran correctamente
        recordedHistory = quizFactory.getStudentHistory().get(student);  // Actualizar el historial
        assertTrue(recordedHistory.containsAll(secondRevisionQuestions),
                "El historial de preguntas vistas debe contener todas las preguntas del segundo quiz de revisión.");
    }


}



