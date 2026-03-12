package com.course.main;

import com.course.model.Course;
import com.course.model.Student;
import com.course.service.CourseService;
import com.course.exception.CourseFullException;
import com.course.exception.CourseNotFoundException;

public class Main {
    public static void main(String[] args) {
        CourseService service = new CourseService();

        Course c1 = new Course(101, "Java Programming", 2);
        Course c2 = new Course(102, "Web Development", 1);
        Course c3 = new Course(103, "Database Systems", 3);

        service.addCourse(c1);
        service.addCourse(c2);
        service.addCourse(c3);

        service.viewCourses();

        Student s1 = new Student(1, "Vansh");
        Student s2 = new Student(2, "Avik");
        Student s3 = new Student(3, "Anmol");

        System.out.println("\n--- Starting Enrollments ---");
        
        try {
            service.enrollStudent(101, s1);         
            service.enrollStudent(101, s2);         
            service.enrollStudent(101, s3); 
        } catch (CourseNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (CourseFullException e) {
            System.out.println(e.getMessage());
        }

        try {
            service.enrollStudent(999, s1);
        } catch (CourseNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (CourseFullException e) {
            System.out.println(e.getMessage());
        }

        service.viewCourses();
        service.readEnrollments();
    }
}
