package com.course.service;

import com.course.model.Course;
import com.course.model.Student;
import com.course.exception.CourseFullException;
import com.course.exception.CourseNotFoundException;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class CourseService {
    private Course[] courses;
    private int courseCount;

    public CourseService() {
        courses = new Course[10];
        courseCount = 0;
    }

    public void addCourse(Course c) {
        if (courseCount < courses.length) {
            courses[courseCount] = c;
            courseCount++;
            System.out.println("Course added successfully: " + c.getCourseName());
        }
    }

    public void enrollStudent(int courseId, Student s) throws CourseNotFoundException, CourseFullException {
        Course foundCourse = null;
        
        for (int i = 0; i < courseCount; i++) {
            if (courses[i].getCourseId() == courseId) {
                foundCourse = courses[i];
                break;
            }
        }

        if (foundCourse == null) {
            throw new CourseNotFoundException("Error: Course with ID " + courseId + " not found!");
        }

        if (foundCourse.getEnrolledStudents() >= foundCourse.getMaxSeats()) {
            throw new CourseFullException("Error: Course " + foundCourse.getCourseName() + " is full!");
        }

        foundCourse.setEnrolledStudents(foundCourse.getEnrolledStudents() + 1);
        
        try {
            FileWriter fw = new FileWriter("courses.txt", true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(courseId + "," + s.getStudentId() + "," + s.getStudentName());
            bw.newLine();
            bw.close();
            System.out.println("Student " + s.getStudentName() + " successfully enrolled in " + foundCourse.getCourseName());
        } catch (IOException e) {
            System.out.println("Error saving enrollment: " + e.getMessage());
        }
    }

    public void viewCourses() {
        System.out.println("\n--- Available Courses ---");
        for (int i = 0; i < courseCount; i++) {
            courses[i].display();
        }
    }
    
    public void readEnrollments() {
        System.out.println("\n--- Enrollment Records (File) ---");
        try {
            FileReader fr = new FileReader("courses.txt");
            BufferedReader br = new BufferedReader(fr);
            
            String line = br.readLine();
            while (line != null) {
                System.out.println(line);
                line = br.readLine();
            }
            br.close();
        } catch (IOException e) {
            System.out.println("No enrollments found yet or file structure missing.");
        }
    }
}
