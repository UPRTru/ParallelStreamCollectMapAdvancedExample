import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

class Student {
    private String name;
    private Map<String, Integer> grades;

    public Student(String name, Map<String, Integer> grades) {
        this.name = name;
        this.grades = grades;
    }

    public Map<String, Integer> getGrades() {
        return grades;
    }

    @Override
    public String toString() {
        return name;
    }
}

public class ParallelStreamCollectMapAdvancedExample {
    public static void main(String[] args) {
        List<Student> students = Arrays.asList(
                new Student("Student1", Map.of("Math", 90, "Physics", 85)),
                new Student("Student2", Map.of("Math", 95, "Physics", 88)),
                new Student("Student3", Map.of("Math", 88, "Chemistry", 92)),
                new Student("Student4", Map.of("Physics", 78, "Chemistry", 85))
        );

        //Создайте коллекцию студентов, где каждый студент содержит информацию о предметах, которые он изучает, и его оценках по этим предметам.
        Map<Student, Map<String, Integer>> studentsMap = students.parallelStream()
                .collect(Collectors.toMap(student -> student, Student::getGrades));
        studentsMap.entrySet().forEach((System.out::println));
        System.out.println("________________________________________\n");

        //Используйте Parallel Stream для обработки данных и создания Map, где ключ - предмет, а значение - средняя оценка по всем студентам.
        Map<String, Double> gradesMap = studentsMap.values().parallelStream()
                .flatMap(grades -> grades.entrySet().stream())
                .collect(Collectors.groupingBy(Map.Entry::getKey, Collectors.averagingDouble(Map.Entry::getValue)));
        gradesMap.entrySet().forEach(System.out::println);
        System.out.println("________________________________________\n");

        //Выведите результат: общую Map с средними оценками по всем предметам.
        Map<Student, Double> overallGradesMap = studentsMap.entrySet().parallelStream()
                .collect(Collectors.toMap(Map.Entry::getKey, entry -> entry.getValue().entrySet().stream()
                        .collect(Collectors.averagingDouble(Map.Entry::getValue))));
        overallGradesMap.entrySet().forEach(System.out::println);
    }
}

