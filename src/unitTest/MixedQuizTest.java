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

public class MixedQuizTest {

    private List<Question> questionPool;
    private QuizFactory quizFactory; // Declarar el QuizFactory
    private QuizFactory quizFactory1;
    private Student student;
    private Set<Question> seenQuestions;

    @BeforeEach
    public void setup() {
        // Crear un banco de 20 preguntas: 10 FreeResponse y 10 MultipleChoice con preguntas reales
        questionPool = new ArrayList<>();

        // Añadir 10 FreeResponse Questions
        questionPool.add(new FreeResponseQuestion("What is the capital of France?", "Paris"));
        questionPool.add(new FreeResponseQuestion("Who wrote '1984'?", "George Orwell"));
        questionPool.add(new FreeResponseQuestion("What is the chemical symbol for water?", "H2O"));
        questionPool.add(new FreeResponseQuestion("Who was the first president of the United States?", "George Washington"));
        questionPool.add(new FreeResponseQuestion("What is the tallest mountain in the world?", "Mount Everest"));
        questionPool.add(new FreeResponseQuestion("Which planet is known as the Red Planet?", "Mars"));
        questionPool.add(new FreeResponseQuestion("What is the smallest prime number?", "2"));
        questionPool.add(new FreeResponseQuestion("What element does 'O' represent on the periodic table?", "Oxygen"));
        questionPool.add(new FreeResponseQuestion("Who painted the Mona Lisa?", "Leonardo da Vinci"));
        questionPool.add(new FreeResponseQuestion("What is the hardest natural substance on Earth?", "Diamond"));

        // Añadir 10 MultipleChoice Questions
        questionPool.add(new MultipleChoiceQuestion("Which of the following are fruits?", new String[]{"Apple", "Banana", "Orange"}));
        questionPool.add(new MultipleChoiceQuestion("Which of the following are colors?", new String[]{"Red", "Green", "Blue"}));
        questionPool.add(new MultipleChoiceQuestion("Which of the following are primary colors?", new String[]{"Red", "Blue", "Yellow"}));
        questionPool.add(new MultipleChoiceQuestion("Which of the following are programming languages?", new String[]{"Java", "Python", "C++"}));
        questionPool.add(new MultipleChoiceQuestion("Which of the following are mammals?", new String[]{"Dog", "Cat", "Elephant"}));
        questionPool.add(new MultipleChoiceQuestion("Which of the following are countries?", new String[]{"France", "Germany", "Brazil"}));
        questionPool.add(new MultipleChoiceQuestion("Which of the following are planets?", new String[]{"Mars", "Venus", "Jupiter"}));
        questionPool.add(new MultipleChoiceQuestion("Which of the following are vegetables?", new String[]{"Carrot", "Broccoli", "Spinach"}));
        questionPool.add(new MultipleChoiceQuestion("Which of the following are chemical elements?", new String[]{"Oxygen", "Hydrogen", "Carbon"}));
        questionPool.add(new MultipleChoiceQuestion("Which of the following are continents?", new String[]{"Africa", "Asia", "Europe"}));

        // Crear un estudiante
        Calendar cal = Calendar.getInstance();
        cal.set(1995, Calendar.JANUARY, 1);
        Date birthDate = cal.getTime();
        student = new Student("John", "Doe", birthDate);

        // Inicializar el QuizFactory con el pool de preguntas
        quizFactory = new RevisionQuiz(questionPool, student);

        // Inicializar el QuizFactory con el pool de preguntas
        quizFactory1 = new RegularQuiz(questionPool);

        // Inicializar un conjunto de preguntas vistas para rastrear
        seenQuestions = new HashSet<>();
    }


    @Test
    public void testRevisionAndRegularQuizzesWithHistoryValidation() {
        Statistics stats = student.getStatistics();

        // 1. Primer intento de quiz de revisión (no afecta el veredicto)
        List<Question> revisionQuestions1 = Arrays.asList(
                questionPool.get(0), // FreeResponse: "What is the capital of France?"
                questionPool.get(1), // FreeResponse: "Who wrote '1984'?"
                questionPool.get(10), // MultipleChoice: "Which of the following are fruits?"
                questionPool.get(11)  // MultipleChoice: "Which of the following are colors?"
        );
        List<String> answers1 = List.of("Paris", "George Orwell", "Apple,Banana,Orange", "Red,Green,Blue");

        Quiz revisionQuiz1 = quizFactory.revise(student, 4);
        // Tomar el primer quiz de revisión
        double revisionScore1 = revisionQuiz1.takeQuiz(student, revisionQuestions1, answers1);
        System.out.println("Primer quiz de revisión score: " + revisionScore1);

        // Verificar que el veredicto sigue siendo TBD
        assertEquals("TBD", stats.getVerdict());

        // Verificar que las preguntas vistas se registren en el historial del estudiante
        quizFactory.recordSeenQuestions(student, revisionQuestions1);
        List<Question> recordedHistory1 = quizFactory.getStudentHistory().get(student);
        assertNotNull(recordedHistory1, "El historial de preguntas vistas no debe ser nulo.");
        assertTrue(recordedHistory1.containsAll(revisionQuestions1), "Las preguntas del primer quiz de revisión deben estar registradas en el historial.");

        // 2. Primer intento de quiz regular (fallido)
        List<Question> regularQuestions1 = Arrays.asList(
                questionPool.get(2), // FreeResponse: "What is the chemical symbol for water?"
                questionPool.get(3), // FreeResponse: "Who was the first president of the United States?"
                questionPool.get(12), // MultipleChoice: "Which of the following are primary colors?"
                questionPool.get(13)  // MultipleChoice: "Which of the following are programming languages?"
        );
        List<String> incorrectAnswers = List.of("Wrong Answer", "Wrong Answer", "Wrong Answer", "Wrong Answer");

        Quiz regularQuiz1 = quizFactory1.generateQuiz(4);
        // Tomar el primer quiz regular
        double regularScore1 = regularQuiz1.takeQuiz(student, regularQuestions1, incorrectAnswers);
        System.out.println("Primer quiz regular score (fallido): " + regularScore1);

        // Verificar que falló el primer regular quiz
        assertEquals(0.0, regularScore1);
        assertEquals("TBD", stats.getVerdict());  // Veredicto aún no cambia

        // Verificar que las preguntas vistas en el primer quiz regular se registren
        quizFactory.recordSeenQuestions(student, regularQuestions1);
        List<Question> recordedHistory2 = quizFactory.getStudentHistory().get(student);
        assertNotNull(recordedHistory2, "El historial de preguntas vistas no debe ser nulo.");
        assertTrue(recordedHistory2.containsAll(regularQuestions1), "Las preguntas del primer quiz regular deben estar registradas en el historial.");

        // 3. Segundo intento de quiz de revisión
        List<Question> revisionQuestions2 = Arrays.asList(
                questionPool.get(4), // FreeResponse: "What is the tallest mountain in the world?"
                questionPool.get(5), // FreeResponse: "Which planet is known as the Red Planet?"
                questionPool.get(14), // MultipleChoice: "Which of the following are mammals?"
                questionPool.get(15)  // MultipleChoice: "Which of the following are countries?"
        );
        List<String> answers2 = List.of("Mount Everest", "Mars", "Dog,Cat,Elephant", "France,Germany,Brazil");

        Quiz revisionQuiz2 = quizFactory.revise(student, 4);
        // Tomar el segundo quiz de revisión
        double revisionScore2 = revisionQuiz2.takeQuiz(student, revisionQuestions2, answers2);
        System.out.println("Segundo quiz de revisión score: " + revisionScore2);

        // Verificar que el veredicto sigue siendo TBD (los quizzes de revisión no afectan el veredicto)
        assertEquals("TBD", stats.getVerdict());

        // Verificar que las preguntas vistas del segundo quiz de revisión se registren correctamente
        quizFactory.recordSeenQuestions(student, revisionQuestions2);
        List<Question> recordedHistory3 = quizFactory.getStudentHistory().get(student);
        assertNotNull(recordedHistory3, "El historial de preguntas vistas no debe ser nulo.");
        assertTrue(recordedHistory3.containsAll(revisionQuestions2), "Las preguntas del segundo quiz de revisión deben estar registradas en el historial.");

        // 4. Segundo intento de quiz regular (aprobado)
        List<Question> regularQuestions2 = Arrays.asList(
                questionPool.get(6), // FreeResponse: "What is the smallest prime number?"
                questionPool.get(7), // FreeResponse: "What element does 'O' represent on the periodic table?"
                questionPool.get(16), // MultipleChoice: "Which of the following are planets?"
                questionPool.get(17)  // MultipleChoice: "Which of the following are vegetables?"
        );
        List<String> correctAnswers = List.of("2", "Oxygen", "Mars,Venus,Jupiter", "Carrot,Broccoli,Spinach");


        Quiz regularQuiz2 = quizFactory1.generateQuiz(4);
        // Tomar el segundo quiz regular
        double regularScore2 = regularQuiz2.takeQuiz(student, regularQuestions2, correctAnswers);
        System.out.println("Segundo quiz regular score (aprobado): " + regularScore2);

        // Verificar que el estudiante pasó el segundo regular quiz
        assertEquals(1.0, regularScore2);

        // Verificar que el veredicto cambia a PASS
        assertEquals("PASS", stats.getVerdict());

        // Verificar que las preguntas vistas en el segundo quiz regular se registren correctamente
        quizFactory.recordSeenQuestions(student, regularQuestions2);
        List<Question> recordedHistory4 = quizFactory.getStudentHistory().get(student);
        assertNotNull(recordedHistory4, "El historial de preguntas vistas no debe ser nulo.");
        assertTrue(recordedHistory4.containsAll(regularQuestions2), "Las preguntas del segundo quiz regular deben estar registradas en el historial.");
    }




}
