//package com.hirekarma.repository;
//
//import java.util.List;
//
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.stereotype.Repository;
//
//import com.hirekarma.model.StudentCGPA;
//
//@Repository("studentCGPARepository")
//public interface StudentCGPARepository extends JpaRepository<StudentCGPA, Long> {
//
//	@Query("select id , cgpaName from StudentCGPA")
//	List<Object[]> getCgpaList();
//
//}
