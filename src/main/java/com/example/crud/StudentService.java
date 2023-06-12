package com.example.crud;

import com.example.crud.model.StudentDTO;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.IntStream;

@Service
public class StudentService {

    //복수의 StudentDTO를 담는 멤버변수(필드)
    private final List<StudentDTO> studentList = new ArrayList<>();
    private Long nextId = 1L;

    public StudentService() {
        createStudent("alpha", "alpha@gmail.com");
        createStudent("beta", "beta@gmail.com");
        createStudent("ghama", "ghama@gmail.com");
    }

    //새로운 StudentDTO를 생성하는 메소드
    public StudentDTO createStudent(String name, String email) {
        StudentDTO newStudent = new StudentDTO(
                nextId, name, email
        );
        nextId++;
        studentList.add(newStudent);
        return newStudent;
    }

    public StudentDTO readStudent(Long id) {
        for (StudentDTO studentDTO: studentList) {
            if (studentDTO.getId().equals(id)) {
                return studentDTO;
            }
        }
        return null;
    } //forEach로 구현

    public StudentDTO readStudent2(Long id) {
        return studentList
                .stream()
                .filter(studentDTO -> studentDTO.getId().equals(id))
                .findFirst()
                .orElse(null);
    } //stream으로 구현

    public List<StudentDTO> readStudentAll() {
        return studentList;
    }

    public StudentDTO updateStudent(Long id, String name, String email) {
        // 하나의 StudentDTO를 찾아서 name과 email을 바꿔주자
        int target = 1;
        for(int i = 0; i < studentList.size(); i++) {
            // 학생의 인덱스 찾으면 기록 후 반복 종료
            if(studentList.get(i).getId().equals(id)) {
                target = i;
                break;
            }
        }

        // 찾은 대상의 name과 email 변경
        if (target != 1) {
            studentList.get(target).setName(name);
            studentList.get(target).setEmail(email);
            return studentList.get(target);
        }
        else return null;
    }

    public StudentDTO updateStudent2(Long id, String name, String email) {
        return studentList
                .stream()
                .filter(studentDTO -> studentDTO.getId().equals(id))
                .peek(studentDTO -> {
                    studentDTO.setName(name);
                    studentDTO.setEmail(email);
                })
                .findFirst()
                .orElse(null);
    }

    public boolean deleteStudent(Long id) {
        int target = -1;
        for(int i = 0; i < studentList.size(); i++) {
            if(studentList.get(i).getId().equals(id)) {
                target = i;
                break;
            }
        }
        if (target != -1) {
            studentList.remove(target);
            return true;
        }
        else return false;
    } //forEach문

    public boolean deleteStudent2(Long id) {
        OptionalInt idx = IntStream
                .range(0, studentList.size())
                .filter(i -> studentList.get(i).getId().equals(id))
                .findFirst();
        if (idx.isPresent()) {
            studentList.remove(idx.getAsInt());
            return true;
        }
        return false; //Not Found
    } //Stream
}
