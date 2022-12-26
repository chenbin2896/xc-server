package com.xuecheng.learning.dao;

import com.xuecheng.framework.domain.learning.XcLearningCourse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LearningCourseJPA extends JpaRepository<XcLearningCourse, String> {
}
