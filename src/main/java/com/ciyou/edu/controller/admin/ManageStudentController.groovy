package com.ciyou.edu.controller.admin

import com.ciyou.edu.entity.Classes
import com.ciyou.edu.entity.PageInfo
import com.ciyou.edu.entity.Student
import com.ciyou.edu.service.StudentService
import com.ciyou.edu.utils.JSONUtil
import com.github.pagehelper.Page
import org.apache.shiro.crypto.hash.Md5Hash
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.servlet.ModelAndView

import java.util.regex.Pattern

/**
 * @Author C.
 * @Date 2018-02-14 23:37
 */
@Controller
class ManageStudentController {

    private static final Logger logger = LoggerFactory.getLogger(ManageStudentController.class)

    @Autowired
    private StudentService studentService

    @RequestMapping("/admin/manageStudent")
    ModelAndView findStudentByPage(Integer page){
        if(page == null){
            page = 1
        }
        ModelAndView mv = new ModelAndView("admin/manageStudent")
        logger.info("findStudentByPage : 查询第${page}页")
        //不赋值pageSize，默认为10
        Page<Student> students = studentService?.findByPage(page)
        // 需要把Page包装成PageInfo对象才能序列化。该插件也默认实现了一个PageInfo
        PageInfo<Student> pageInfo = new PageInfo<Student>(students)
        logger.info("查询结果：" + pageInfo )
        pageInfo?.setUrl("/admin/manageStudent?")
        mv?.addObject("pageInfo",pageInfo)
        return mv
    }

    @RequestMapping(value="/admin/addStudent", method=RequestMethod.POST, produces="application/json;charset=UTF-8")
    @ResponseBody
    String addStudent(Student student,Integer classesId){
        logger.info("添加Student信息：" + student + ",班级ID：" + classesId)
        //校验提交的student
        if(!student?.getStudentId() || student?.getStudentId()?.trim() == ""){
            return JSONUtil.returnFailReuslt("学生学号不能为空")
        }else if(!student?.getName() || student?.getName()?.trim() == ""){
            return JSONUtil.returnFailReuslt("学生姓名不能为空")
        }else if(!Pattern.compile("[\\u4E00-\\u9FFF]+")?.matcher(student?.getName())?.matches()){
            return JSONUtil.returnFailReuslt("学生姓名必须为中文")
        }else if(classesId == null || classesId == 0){
            return JSONUtil.returnFailReuslt("班级不能为空")
        }else if(student?.getAge() && !Pattern.compile( '^[1-9]+\\d*$')?.matcher(student?.getAge()?.toString())?.matches()){
            return JSONUtil.returnFailReuslt("年龄必须为正整数")
        }else if(student?.getMobile() && !Pattern.compile('^1[34578]\\d{9}$')?.matcher(student?.getMobile())?.matches()){
            return JSONUtil.returnFailReuslt("电话号码有误")
        }else if(student?.getEmail() && !Pattern.compile('^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+')?.matcher(student?.getEmail())?.matches()){
            return JSONUtil.returnFailReuslt("邮箱有误")
        }else if(student?.getParentMobile() && !Pattern.compile('^1[34578]\\d{9}$')?.matcher(student?.getParentMobile())?.matches()){
            return JSONUtil.returnFailReuslt("家长电话号码有误")
        }else if(student?.getParentEmail() && !Pattern.compile('^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+')?.matcher(student?.getParentEmail())?.matches()){
            return JSONUtil.returnFailReuslt("家长邮箱有误")
        }
        //查找该学生学号的学生是否存在
        if(studentService?.findByStudentId(student?.getStudentId())){
            //如果已经存在
            return JSONUtil.returnFailReuslt("该学生已存在")
        }else{
            try{
                //密码默认:123456
                String passwordMd5= new Md5Hash("123456",student?.getStudentId(),2).toHex()
                student.setPassword(passwordMd5)
                Classes classes = new Classes()
                classes?.setClassesId(classesId)
                student?.setClasses(classes)
                student?.setCreateTime(new Date(System.currentTimeMillis()))
                //如果是男生
                if(student?.getSex() == 1){
                    student?.setPicImg("/static/img/boy.png")
                }else{
                    student?.setPicImg("/static/img/girl.png")
                }
                if(studentService?.addStudent(student)){
                    return JSONUtil.returnSuccessResult("添加成功")
                }else{
                    return JSONUtil.returnFailReuslt("添加失败")
                }
            }catch (Exception e){
                logger.info("添加Student错误：" + e.getMessage())
                return JSONUtil.returnFailReuslt("添加失败，请重试")
            }
        }

    }

}
