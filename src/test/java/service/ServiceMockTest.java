package service;

import domain.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import repository.GradeXMLRepository;
import repository.HomeworkXMLRepository;
import repository.StudentXMLRepository;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.when;

class ServiceMockTest {
    private static Service service;
    @Mock
    private static StudentXMLRepository studentXmlRepoMock;
    @Mock
    private static HomeworkXMLRepository homeworkXmlRepoMock;
    @Mock
    private static GradeXMLRepository gradeXmlRepoMock;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
        service = new Service(studentXmlRepoMock, homeworkXmlRepoMock, gradeXmlRepoMock);
    }

    @Test
    void saveStudent() {
        String id = "5";
        String name = "Test Student";
        int group = 500;

        when(studentXmlRepoMock.save(anyObject())).thenReturn(null);
        int result = service.saveStudent(id, name, group);
        assertEquals(1, result);
    }

    @Test
    void findAllStudents() {
        ArrayList<Student> students = new ArrayList<>();
        students.add(new Student("7", "FStudent1", 500));
        students.add(new Student("8", "FStudent2", 500));

        when(studentXmlRepoMock.findAll()).thenReturn(students);
        assertNotNull(service.findAllStudents());
    }

    @Test
    void updateStudent() {
        String id = "5";
        String newName = "Updated Student";
        int newGroup = 111;

        service.updateStudent(id, newName, newGroup);
        Mockito.verify(studentXmlRepoMock).update(new Student(id, newName, newGroup));
    }
}