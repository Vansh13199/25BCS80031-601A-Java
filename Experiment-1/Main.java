import java.util.*;

enum Grade {
    A, B, C, PASS, FAIL
}

class Student {

    int theory;
    int lab;
    int attendance;
    int bonus;

    Student(int t, int l, int a, int b) {
        theory = t;
        lab = l;
        attendance = a;
        bonus = b;
    }
}

interface ModerationRule {
    int apply(int score, Student s);
}

class AttendanceRule implements ModerationRule {

    public int apply(int score, Student s) {
        if (s.attendance >= 90)
            return score + 5;
        return score;
    }
}

class DifficultyRule implements ModerationRule {

    public int apply(int score, Student s) {
        return score + 3;
    }
}

class ManualRule implements ModerationRule {

    public int apply(int score, Student s) {
        return score + s.bonus;
    }
}

class RuleGroup implements ModerationRule {

    List<ModerationRule> rules = new ArrayList<>();

    void addRule(ModerationRule r) {
        rules.add(r);
    }

    public int apply(int score, Student s) {

        int result = score;

        for (ModerationRule r : rules) {
            result = r.apply(result, s);
        }

        return result;
    }
}

abstract class EvaluationEngine {

    protected ModerationRule rule;

    EvaluationEngine(ModerationRule r) {
        rule = r;
    }

    public final Grade evaluate(Student s) {

        int theory = s.theory;
        int lab = s.lab;

        int score = calculateScore(theory, lab);

        score = rule.apply(score, s);

        return generateGrade(score);
    }

    protected abstract int calculateScore(int theory, int lab);

    protected abstract Grade generateGrade(int score);
}

class BTechEvaluation extends EvaluationEngine {

    BTechEvaluation(ModerationRule r) {
        super(r);
    }

    protected int calculateScore(int t, int l) {
        return (int)(t * 0.7 + l * 0.3);
    }

    protected Grade generateGrade(int s) {

        if (s >= 85) return Grade.A;
        if (s >= 70) return Grade.B;
        return Grade.C;
    }
}

class MCAEvaluation extends EvaluationEngine {

    MCAEvaluation(ModerationRule r) {
        super(r);
    }

    protected int calculateScore(int t, int l) {
        return (t + l) / 2;
    }

    protected Grade generateGrade(int s) {

        if (s >= 80) return Grade.A;
        if (s >= 65) return Grade.B;
        return Grade.C;
    }
}

class PhDEvaluation extends EvaluationEngine {

    PhDEvaluation(ModerationRule r) {
        super(r);
    }

    protected int calculateScore(int t, int l) {
        return t;
    }

    protected Grade generateGrade(int s) {

        if (s >= 60) return Grade.PASS;
        return Grade.FAIL;
    }
}

public class Main {

    public static void main(String[] args) {

        Student s = new Student(75, 80, 92, 3);

        RuleGroup btechRules = new RuleGroup();
        btechRules.addRule(new AttendanceRule());
        btechRules.addRule(new DifficultyRule());
        btechRules.addRule(new ManualRule());

        RuleGroup mcaRules = new RuleGroup();
        mcaRules.addRule(new AttendanceRule());
        mcaRules.addRule(new ManualRule());

        RuleGroup phdRules = new RuleGroup();
        phdRules.addRule(new ManualRule());

        EvaluationEngine engine;

        engine = new BTechEvaluation(btechRules);
        // engine = new MCAEvaluation(mcaRules);
        // engine = new PhDEvaluation(phdRules);

        Grade result = engine.evaluate(s);

        System.out.println("Result: " + result);
    }
}
