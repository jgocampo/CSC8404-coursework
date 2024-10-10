package statistics;

import student.Student;
import java.util.ArrayList;
import java.util.List;

public final class Statistics {
    private final Student student;
    private final List<Double> regularQuizScores;
    private final List<Double> revisionQuizScores;
    private int regularAttempts;
    private int revisionAttempts;
    private String verdict;

    // Constructor
    public Statistics(Student student) {
        this.student = student;
        this.regularQuizScores = new ArrayList<>();
        this.revisionQuizScores = new ArrayList<>();
        this.verdict = "TBD";  // TBD significa "To Be Determined"
    }

    // Registro de un puntaje de quiz regular
    public void recordRegularQuizScore(double score) {
        if ("PASS".equals(verdict) || "FAIL".equals(verdict)) {
            return;  // No se permiten más intentos si ya se ha emitido un veredicto
        }

        regularQuizScores.add(score);
        regularAttempts++;

        // Si el puntaje es >= 50%, el estudiante pasa
        if (score >= 0.5) {
            verdict = "PASS";
        } else if (regularAttempts >= 2) {
            verdict = "FAIL";  // Si ha fallado dos veces, el veredicto es FAIL
        }
    }

    // Registro de un puntaje de quiz de revisión
    public void recordRevisionQuizScore(double score) {
        if ("PASS".equals(verdict) || "FAIL".equals(verdict) || revisionAttempts >= 2) {
            return;  // No se permiten más revisiones si ya se ha emitido un veredicto o ha alcanzado el límite
        }

        revisionQuizScores.add(score);
        revisionAttempts++;

        // Si el puntaje es >= 50%, el estudiante pasa
        if (score >= 0.5) {
            verdict = "PASS";
        }
    }

    // Genera un informe de las estadísticas del estudiante
    public String generateStatistics() {
        StringBuilder report = new StringBuilder();
        report.append("Statistics for student: ").append(student.getName()).append("\n");
        report.append("Final verdict: ").append(verdict).append("\n");
        report.append("Number of regular quiz attempts: ").append(regularAttempts).append("\n");
        report.append("Number of revision quiz attempts: ").append(revisionAttempts).append("\n");
        report.append("Regular quiz scores: ").append(regularQuizScores).append("\n");
        report.append("Revision quiz scores: ").append(revisionQuizScores).append("\n");

        return report.toString();
    }

    // Verifica si el estudiante puede tomar más quizzes regulares
    public boolean canTakeRegularQuiz() {
        return "TBD".equals(verdict);
    }

    // Verifica si el estudiante puede tomar más quizzes de revisión
    public boolean canTakeRevisionQuiz() {
        return "TBD".equals(verdict) && revisionAttempts < 2;
    }

    // Obtiene el veredicto final
    public String getVerdict() {
        return verdict;
    }
}
