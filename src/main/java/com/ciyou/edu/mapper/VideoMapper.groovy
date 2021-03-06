package com.ciyou.edu.mapper

import com.ciyou.edu.entity.Video
import com.github.pagehelper.Page
import org.apache.ibatis.annotations.Insert
import org.apache.ibatis.annotations.One
import org.apache.ibatis.annotations.Param
import org.apache.ibatis.annotations.Result
import org.apache.ibatis.annotations.Results
import org.apache.ibatis.annotations.Select
import org.apache.ibatis.annotations.Update

/**
 * @Author C.
 * @Date 2018-02-20 18:31
 */
interface VideoMapper {

    @Select("select * from Video where videoType <> 0 order by videoId ")
    @Results([
            //查询关联对象
            @Result(property = "grade",
                    column = "gradeId",
                    one = @One(select = "com.ciyou.edu.mapper.GradeMapper.getGrade")),
            @Result(property = "teacher",
                    column = "teacherId",
                    one = @One(select = "com.ciyou.edu.mapper.TeacherMapper.getTeacherByTid")),
            @Result(property = "subject",
                    column = "subjectId",
                    one = @One(select = "com.ciyou.edu.mapper.SubjectMapper.getSubject"))
    ])
    Page<Video> findAllVideo()

    @Insert("insert into Video(videoName,videoUrl,imgUrl,teacherId,subjectId,gradeId,createTime) values(#{videoName},#{videoUrl},#{imgUrl},#{teacher.tid},#{subject.subjectId},#{grade.gradeId},#{createTime})")
    int addVideo(Video video)

    @Select("select * from Video where videoId = #{videoId} and videoType <> 0")
    @Results([
            //查询关联对象
            @Result(property = "grade",
                    column = "gradeId",
                    one = @One(select = "com.ciyou.edu.mapper.GradeMapper.getGrade")),
            @Result(property = "teacher",
                    column = "teacherId",
                    one = @One(select = "com.ciyou.edu.mapper.TeacherMapper.getTeacherByTid")),
            @Result(property = "subject",
                    column = "subjectId",
                    one = @One(select = "com.ciyou.edu.mapper.SubjectMapper.getSubject"))
    ])
    Video getVideoById(@Param("videoId")Integer videoId)

    @Update("update Video set videoName = #{videoName}, videoUrl = #{videoUrl}, imgUrl = #{imgUrl}, subjectId = #{subject.subjectId}, gradeId = #{grade.gradeId} where videoId = #{videoId}")
    int updateVideo(Video video)

    @Update("update Video set videoType = 0 where videoId = #{videoId}")
    int deleteVideo(@Param("videoId")Integer videoId)

    @Select("select Video.* from Video,Grade,Subject,Teacher where (Video.videoId like '%\${value}%' or Video.videoName like '%\${value}%' or Teacher.name like '%\${value}%' or Grade.gradeName like '%\${value}%' or Subject.subjectName like '%\${value}%') and videoType <> 0 and Video.gradeId = Grade.gradeId and Video.subjectId = Subject.subjectId and Video.teacherId = Teacher.tid order by Video.videoId")
    @Results([
            //查询关联对象
            @Result(property = "grade",
                    column = "gradeId",
                    one = @One(select = "com.ciyou.edu.mapper.GradeMapper.getGrade")),
            @Result(property = "teacher",
                    column = "teacherId",
                    one = @One(select = "com.ciyou.edu.mapper.TeacherMapper.getTeacherByTid")),
            @Result(property = "subject",
                    column = "subjectId",
                    one = @One(select = "com.ciyou.edu.mapper.SubjectMapper.getSubject"))
    ])
    Page<Video> queryVideoByPage(@Param("value")String value)

    @Update("update Video set videoType = #{videoType} where videoId = #{videoId}")
    int updateVideoType(@Param("videoId")Integer videoId, @Param("videoType")Integer videoType)


    @Select("select * from Video where gradeId = #{gradeId} and videoType <> 0 and videoType <> 1")
    @Results([
            //查询关联对象
            @Result(property = "grade",
                    column = "gradeId",
                    one = @One(select = "com.ciyou.edu.mapper.GradeMapper.getGrade")),
            @Result(property = "teacher",
                    column = "teacherId",
                    one = @One(select = "com.ciyou.edu.mapper.TeacherMapper.getTeacherByTid")),
            @Result(property = "subject",
                    column = "subjectId",
                    one = @One(select = "com.ciyou.edu.mapper.SubjectMapper.getSubject"))
    ])
    Page<Video> findByGradeAndPage(@Param("gradeId")Integer gradeId)

    @Select("select * from Video where videoType <> 0 and videoType <> 1 order by videoId ")
    @Results([
            //查询关联对象
            @Result(property = "grade",
                    column = "gradeId",
                    one = @One(select = "com.ciyou.edu.mapper.GradeMapper.getGrade")),
            @Result(property = "teacher",
                    column = "teacherId",
                    one = @One(select = "com.ciyou.edu.mapper.TeacherMapper.getTeacherByTid")),
            @Result(property = "subject",
                    column = "subjectId",
                    one = @One(select = "com.ciyou.edu.mapper.SubjectMapper.getSubject"))
    ])
    Page<Video> findVideoByPage()

    @Select("select * from Video where subjectId = #{subjectId} and videoType <> 0 and videoType <> 1")
    @Results([
            //查询关联对象
            @Result(property = "grade",
                    column = "gradeId",
                    one = @One(select = "com.ciyou.edu.mapper.GradeMapper.getGrade")),
            @Result(property = "teacher",
                    column = "teacherId",
                    one = @One(select = "com.ciyou.edu.mapper.TeacherMapper.getTeacherByTid")),
            @Result(property = "subject",
                    column = "subjectId",
                    one = @One(select = "com.ciyou.edu.mapper.SubjectMapper.getSubject"))
    ])
    Page<Video> findBySubjectAndPage(@Param("subjectId")Integer subjectId)

    @Select("select * from Video where subjectId = #{subjectId} and gradeId = #{gradeId} and videoType <> 0 and videoType <> 1")
    @Results([
            //查询关联对象
            @Result(property = "grade",
                    column = "gradeId",
                    one = @One(select = "com.ciyou.edu.mapper.GradeMapper.getGrade")),
            @Result(property = "teacher",
                    column = "teacherId",
                    one = @One(select = "com.ciyou.edu.mapper.TeacherMapper.getTeacherByTid")),
            @Result(property = "subject",
                    column = "subjectId",
                    one = @One(select = "com.ciyou.edu.mapper.SubjectMapper.getSubject"))
    ])
    Page<Video> findBySubjectGradePage(@Param("gradeId")Integer gradeId,@Param("subjectId")Integer subjectId)
}
