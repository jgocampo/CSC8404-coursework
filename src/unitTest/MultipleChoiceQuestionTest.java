package unitTest;
import question.MultipleChoiceQuestion;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the MultipleChoiceQuestion class, which validates answers consisting of literals.
 */
public class MultipleChoiceQuestionTest {

    /**
     * Test correct answer with literals in different orders.
     */
    @Test
    public void testCorrectAnswers() {
        MultipleChoiceQuestion question = new MultipleChoiceQuestion(
                "Which of the following are prime numbers?", new String[]{"a", "b", "c"}
        );
        // Respuesta correcta con diferentes órdenes
        assertTrue(question.checkAnswer("a,b,c"));
        assertTrue(question.checkAnswer("b,c,a"));
        assertTrue(question.checkAnswer("c,a,b"));
    }

    /**
     * Test incorrect answer with missing or extra literals.
     */
    @Test
    public void testIncorrectAnswers() {
        MultipleChoiceQuestion question = new MultipleChoiceQuestion(
                "Which of the following are prime numbers?", new String[]{"a", "b", "c"}
        );

        // Faltan literales
        assertFalse(question.checkAnswer("a,b"));  // Faltan respuestas
        assertFalse(question.checkAnswer("b"));    // Solo una respuesta
        // Literales de más
        assertFalse(question.checkAnswer("a,b,c,d"));  // Respuesta con un literal extra
    }

    /**
     * Test null answer is considered incorrect.
     */
    @Test
    public void testNullAnswer() {
        MultipleChoiceQuestion question = new MultipleChoiceQuestion(
                "Which of the following are prime numbers?", new String[]{"a", "b", "c"}
        );
        assertFalse(question.checkAnswer(null));  // Respuesta nula es incorrecta
    }

    /**
     * Test answer with wrong format is considered incorrect.
     */
    @Test
    public void testWrongFormatAnswer() {
        MultipleChoiceQuestion question = new MultipleChoiceQuestion(
                "Which of the following are prime numbers?", new String[]{"a", "b", "c"}
        );
        // Respuesta mal formateada
        assertFalse(question.checkAnswer("a-b-c"));  // Respuesta mal separada
    }

    /**
     * Test answer with extra spaces is still considered correct.
     */
    @Test
    public void testCorrectAnswerWithSpaces() {
        MultipleChoiceQuestion question = new MultipleChoiceQuestion(
                "Which of the following are prime numbers?", new String[]{"a", "b", "c"}
        );
        // Respuesta correcta con espacios adicionales
        assertTrue(question.checkAnswer("  a,  b ,c   "));
    }
}


