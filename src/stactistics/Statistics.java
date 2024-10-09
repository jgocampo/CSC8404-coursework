package statistics;

import student.Student;
import java.util.*;

public class Statistics {
    private final Student student;
    private final List<Double> regularQuizScores;  // Puntajes de quizzes regulares
    private final List<Double> revisionQuizScores;  // Puntajes de quizzes de revisión
    private String verdict;  // Puede ser "PASS", "FAIL" o null si no ha recibido un veredicto
    private int failedRegularAttempts;  // Número de fallos en quizzes regulares
    private int revisionAttempts;  // Número de intentos en quizzes de revisión

    public Statistics(Student student) {
        this.student = student;
        this.regularQuizScores = new ArrayList<>();
        this.revisionQuizScores = new ArrayList<>();
        this.verdict = null;  // No ha recibido un veredicto inicialmente
        this.failedRegularAttempts = 0;
        this.revisionAttempts = 0;
    }

    // Agrega un puntaje de un quiz regular
    public void addRegularQuizScore(double score) {
        if (verdict != null) {
            throw new IllegalStateException("Student already has a verdict: " + verdict);
        }
        if (score < 0 || score > 1) {
            throw new IllegalArgumentException("Score must be between 0 and 1.");
        }

        regularQuizScores.add(score);

        if (score >= 0.5) {
            verdict = "PASS";  // Si el estudiante saca 50% o más, pasa el quiz
        } else {
            failedRegularAttempts++;
            if (failedRegularAttempts >= 2) {
                verdict = "FAIL";  // Si falla dos veces, recibe un veredicto de "FAIL"
            }
        }
    }

    // Agrega un puntaje de un quiz de revisión
    public void addRevisionQuizScore(double score) {
        if (verdict != null) {
            throw new IllegalStateException("Student already has a verdict: " + verdict);
        }
        if (revisionAttempts >= 2) {
            throw new IllegalStateException("Student cannot take more than two revision quizzes.");
        }
        if (score < 0 || score > 1) {
            throw new IllegalArgumentException("Score must be between 0 and 1.");
        }

        revisionQuizScores.add(score);
        revisionAttempts++;

        // Si el puntaje es igual o mayor a 50% y no ha fallado dos veces, pasa
        if (score >= 0.5) {
            verdict = "PASS";
        }
    }

    // Devuelve el promedio de puntajes de quizzes regulares
    public double getAverageRegularQuizScore() {
        return regularQuizScores.stream()
                .mapToDouble(Double::doubleValue)
                .average()
                .orElse(0.0);
    }

    // Devuelve el promedio de puntajes de quizzes de revisión
    public double getAverageRevisionQuizScore() {
        return revisionQuizScores.stream()
                .mapToDouble(Double::doubleValue)
                .average()
                .orElse(0.0);
    }

    // Devuelve todos los puntajes de quizzes regulares
    public List<Double> getAllRegularQuizScores() {
        return new ArrayList<>(regularQuizScores);
    }

    // Devuelve todos los puntajes de quizzes de revisión
    public List<Double> getAllRevisionQuizScores() {
        return new ArrayList<>(revisionQuizScores);
    }

    // Determina si el estudiante ha pasado o fallado basado en los quizzes regulares
    public String getRegularQuizResult() {
        return verdict != null ? verdict : "No Verdict";
    }

    // Determina si el estudiante ha pasado o fallado basado en los quizzes de revisión
    public String getRevisionQuizResult() {
        return verdict != null ? verdict : "No Verdict";
    }

    // Verifica si el estudiante puede tomar un nuevo quiz regular
    public boolean canTakeRegularQuiz() {
        return verdict == null;
    }

    // Verifica si el estudiante puede tomar un nuevo quiz de revisión
    public boolean canTakeRevisionQuiz() {
        return verdict == null && revisionAttempts < 2;
    }

    // Devuelve el estudiante
    public Student getStudent() {
        return student;
    }

    // Devuelve el veredicto actual del estudiante (PASS o FAIL)
    public String getVerdict() {
        return verdict != null ? verdict : "No Verdict";
    }
}
