package unitTest;
import question.MultipleChoiceQuestion;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the MultipleChoiceQuestion class.
 */
public class MultipleChoiceQuestionTest {

    /**
     * Test correct answer with different orders.
     */
    @Test
    public void testCorrectAnswer() {
        MultipleChoiceQuestion question = new MultipleChoiceQuestion(
                "Which of these are programming languages?", new String[]{"Java", "Python", "C++"}
        );
        assertTrue(question.checkAnswer("java, python, c++"));
        assertTrue(question.checkAnswer("C++, Python, Java")); // Different order
    }

    /**
     * Test incorrect answer.
     */
    @Test
    public void testIncorrectAnswer() {
        MultipleChoiceQuestion question = new MultipleChoiceQuestion(
                "Which of these are programming languages?", new String[]{"Java", "Python", "C++"}
        );
        assertFalse(question.checkAnswer("Java, Python"));
    }

    /**
     * Test null answer.
     */
    @Test
    public void testNullAnswer() {
        MultipleChoiceQuestion question = new MultipleChoiceQuestion(
                "Which of these are programming languages?", new String[]{"Java", "Python", "C++"}
        );
        assertFalse(question.checkAnswer(null));
    }

    /**
     * Test invalid format answer.
     */
    @Test
    public void testInvalidFormatAnswer() {
        MultipleChoiceQuestion question = new MultipleChoiceQuestion(
                "Which of these are programming languages?", new String[]{"Java", "Python", "C++"}
        );
        assertFalse(question.checkAnswer("Java-Python-C++"));
    }
}

