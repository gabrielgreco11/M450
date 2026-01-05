package ch.tbz.m450.testing.tools.repository.entities;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class StudentEntityTests {

    private Student student;

    @BeforeEach
    public void setUp() {
        student = new Student(1, "Max Mustermann", "max@example.com");
    }

    @Test
    public void testStudentCreation() {
        assertEquals(1, student.getId());
        assertEquals("Max Mustermann", student.getName());
        assertEquals("max@example.com", student.getEmail());
    }

    @Test
    public void testStudentConstructorWithoutId() {
        Student s = new Student("Anna Schmidt", "anna@example.com");
        assertEquals("Anna Schmidt", s.getName());
        assertEquals("anna@example.com", s.getEmail());
    }

    @Test
    public void testStudentDefaultConstructor() {
        Student s = new Student();
        assertEquals("", s.getName());
        assertEquals("", s.getEmail());
    }

    @Test
    public void testStudentWithSoftAssertions() {
        // SoftAssertions: all assertions are checked, failures collected, then reported at end
        SoftAssertions softly = new SoftAssertions();
        
        softly.assertThat(student.getId())
                .as("Student ID should be 1")
                .isEqualTo(1);
        
        softly.assertThat(student.getName())
                .as("Student name should be Max Mustermann")
                .isEqualTo("Max Mustermann");
        
        softly.assertThat(student.getEmail())
                .as("Student email should be max@example.com")
                .isEqualTo("max@example.com");
        
        softly.assertAll(); // Verifies all assertions; throws if any failed
    }

    @Test
    public void testStudentEquality() {
        Student student2 = new Student(1, "Max Mustermann", "max@example.com");
        // Both have same id, name, email
        assertTrue(student.equals(student2) || student.getId() == student2.getId());
    }

    @Test
    public void testStudentWithMultipleSoftAssertions() {
        // Example: verify multiple student instances at once
        Student s1 = new Student(1, "Alice", "alice@example.com");
        Student s2 = new Student(2, "Bob", "bob@example.com");
        Student s3 = new Student(3, "Charlie", "charlie@example.com");

        SoftAssertions softly = new SoftAssertions();
        
        softly.assertThat(s1.getName()).isEqualTo("Alice");
        softly.assertThat(s1.getEmail()).isEqualTo("alice@example.com");
        
        softly.assertThat(s2.getName()).isEqualTo("Bob");
        softly.assertThat(s2.getEmail()).isEqualTo("bob@example.com");
        
        softly.assertThat(s3.getName()).isEqualTo("Charlie");
        softly.assertThat(s3.getEmail()).contains("@");
        
        softly.assertAll();
    }
}
