package service;

import domain.Grade;
import domain.Homework;
import domain.Student;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import repository.GradeXMLRepository;
import repository.HomeworkXMLRepository;
import repository.StudentXMLRepository;
import validation.GradeValidator;
import validation.HomeworkValidator;
import validation.StudentValidator;
import validation.Validator;

import static org.junit.jupiter.api.Assertions.*;


class ServiceTest {

    private static Service service;

    @BeforeAll
    static void setUp() {
        Validator<Student> studentValidator = new StudentValidator();
        Validator<Homework> homeworkValidator = new HomeworkValidator();
        Validator<Grade> gradeValidator = new GradeValidator();

        StudentXMLRepository fileRepository1 = new StudentXMLRepository(studentValidator, "studentstest.xml");
        HomeworkXMLRepository fileRepository2 = new HomeworkXMLRepository(homeworkValidator, "homeworktest.xml");
        GradeXMLRepository fileRepository3 = new GradeXMLRepository(gradeValidator, "gradestest.xml");

        service = new Service(fileRepository1, fileRepository2, fileRepository3);
    }

    @Test
    void findAllStudents() {
        assertNotNull(service.findAllStudents());
    }

    @Test
    void saveStudent() {
        String id = "5";
        String name = "Test Student";
        int group = 500;

        if (service.saveStudent(id, name, group) != 0) {
            System.out.println("Student added successfully! \n");
        }
        else {
            System.out.println("Student exists or is invalid! \n");
        }

        boolean found = false;
        for (Student student : service.findAllStudents()) {
            if (student.getID().equals(id) && student.getName().equals(name) && student.getGroup() == group) {
                found = true;
                break;
            }
        }
        assertTrue(found);
    }

    @Test
    void updateStudent() {
        String id = "5";
        String newName = "Updated Student";
        int newGroup = 111;

        if (service.updateStudent(id, newName, newGroup) != 0) {
            System.out.println("Student updated successfully! \n");
        }
        else {
            System.out.println("Student doesn't exist! \n");
        }

        boolean changed = false;
        for (Student student : service.findAllStudents()) {
            if (student.getID().equals(id)) {
                if (student.getName().equals(newName) && student.getGroup() == newGroup) {
                    changed = true;
                }
                break;
            }
        }
        assertTrue(changed);
    }

    @ParameterizedTest
    @ValueSource(strings = "5")
    void deleteStudent(String id) {
        if (service.deleteStudent(id) != 0) {
            System.out.println("Student deleted successfully! \n");
        }
        else {
            System.out.println("Student doesn't exist! \n");
        }

        boolean found = false;
        for (Student student : service.findAllStudents()) {
            if (student.getID().equals(id)) {
                found = true;
                break;
            }
        }
        assertFalse(found);
    }

    @Test
    void extendDeadline() {
        String id = "1";
        int nrWeeks = 1;

        int deadline = 0;
        for (Homework homework : service.findAllHomework()) {
            if (homework.getID().equals(id)) {
                deadline = homework.getDeadline();
                break;
            }
        }

        if (service.extendDeadline(id, nrWeeks) != 0) {
            System.out.println("Deadline extended successfully! \n");
        }
        else {
            System.out.println("Homework doesn't exist! \n");
        }

        for (Homework homework : service.findAllHomework()) {
            if (homework.getID().equals(id)) {
                assertEquals(deadline + nrWeeks, homework.getDeadline());
                break;
            }
        }
    }

}