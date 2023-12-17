import {request} from '@/utils/request'
const http = {
    //登录
    login(data){
        const config = {
            url:'/user/login',
            method:'post',
            data:data,
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            }
        }
        return request(config)
    },

    //修改密码
    changePassword(data){
        const config = {
            url:'/user/change',
            method:'post',
            data:data,
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            }
        }
        return request(config)
    },

    //发送验证码
    sendCode(data){
        const config = {
            url:'/user/email',
            method:'post',
            data:data,
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            }
        }
        return request(config)
    },

    //邮箱确认后
    confirmEmail(data){
        const config = {
            url:'/user/verify',
            method:'post',
            data:data,
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            }
        }
        return request(config)
    },

    //学生修改信息
    stuChangeInfo(data){
        console.log("stuChangeInfo")
        const config = {
            url:'/student/modifyInfo',
            method:'post',
            data:data,
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            }
        }
        return request(config)
    },

    //老师修改信息
    teaChangeInfo(data){
        console.log("teaChangeInfo")
        const config = {
            url:'/teacher/modifyInfo',
            method:'post',
            data:data,
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            }
        }
        return request(config)
    },

    //修改邮箱时发送验证码
    SendEmail(data){
        const config = {
            url:'/user/sendEmail',
            method:'post',
            data:data,
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            }
        }
        return request(config)
    },

    //修改邮箱
    changeEmail(data){
        const config = {
            url:'/user/modifyEmail',
            method:'post',
            data:data,
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            }
        }
        return request(config)
    },

    //修改密码
    modifyPassword(data){
        const config = {
            url:'/user/modifyPassword',
            method:'post',
            data:data,
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            }
        }
        return request(config)
    },

    //学生课程列表
    stuCourseList(){
        const config = {
            url:'/student/findCourse',
            method:'post',
        }
        return request(config)
    },

    //学生作业列表
    stuHomeworkList(data){
        const config = {
            url:'/student/findCTByCno',
            method:'post',
            data:data,
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            }
        }
        return request(config)
    },

    //下载老师布置的作业
    downloadHomework(data){
        const config = {
            url:'/homework/downloadHW',
            method:'post',
            data:data,
            responseType: 'blob',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            }
        }
        return request(config)
    },

    //学生提交申诉
    submitAppeal(data){
        const config = {
            url:'/student/sendAppeal',
            method:'post',
            data:data,
            headers: {
                'Content-Type': 'multipart/form-data'
            }
        }
        return request(config)
    },

    //获取成绩分布信息
    getScoreInfo(data){
        const config = {
            url:'/chart/getScoreDistribution',
            method:'post',
            data:data,
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            }
        }
        return request(config)
    },

    //学生提交互评分数
    submitScore(data){
        const config = {
            url:'/student/score',
            method:'post',
            data:data,
            headers: {
                'Content-Type': 'multipart/form-data'
            }
        }
        return request(config)
    },

    //下载学生作业
    downloadCT(data){
        const config = {
            url:'/content/downloadCT',
            method:'post',
            data:data,
            responseType: 'blob',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            }
        }
        return request(config)
    },

    //学生下载答案
    downloadAnswer(data){
        const config = {
            url:'/homework/downloadAns',
            method:'post',
            data:data,
            responseType: 'blob',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            }
        }
        return request(config)
    },

    //学生查看互评评分列表
    getScoreList(data){
        const config = {
            url:'/student/findSCByCID',
            method:'post',
            data:data,
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            }
        }
        return request(config)
    },

    //老师查看课程列表
    getCourseList(){
        const config = {
            url:'/teacher/findCourse',
            method:'post',
        }
        return request(config)
    },

    //老师查看课程作业列表
    getHomeworkList(data){
        const config = {
            url:'/teacher/findHWbyCno',
            method:'post',
            data:data,
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            }
        }
        return request(config)
    },

    //老师布置作业
    assignHomework(data){
        const config = {
            url:'/homework/uploadHW',
            method:'post',
            data:data,
            headers: {
                'Content-Type': 'multipart/form-data'
            }
        }
        return request(config)
    },

    //老师修改作业截止时间
    changeDeadline(data){
        const config = {
            url:'/teacher/alterDdlByHID',
            method:'post',
            data:data,
            headers: {
                'Content-Type': 'multipart/form-data'
            }
        }
        return request(config)
    },

    //老师上传答案
    uploadAnswer(data){
        const config = {
            url:'/homework/setAnswer',
            method:'post',
            data:data,
            headers: {
                'Content-Type': 'multipart/form-data'
            }
        }
        return request(config)
    },

    //老师删除作业
    deleteHomework(data){
        const config = {
            url:'/teacher/deleteHWByHId',
            method:'post',
            data:data,
            headers: {
                'Content-Type': 'multipart/form-data'
            }
        }
        return request(config)
    },

    //老师查看学生作业列表
    getStudentHomeworkList(data){
        const config = {
            url:'/teacher/findCTByHId',
            method:'post',
            data:data,
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            }
        }
        return request(config)
    },

    //教师点击作业申诉
    ClickAppeal(data){
        const config = {
            url:'/teacher/ClickAppeal',
            method:'post',
            data:data,
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            }
        }
        return request(config)
    },

    //老师查看作业申诉
    readAppeal(data){
        const config = {
            url:'/teacher/findAPByAID',
            method:'post',
            data:data,
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            }
        }
        console.log("findAppeal")
        return request(config)
    },

    //老师处理作业申诉
    handleAppeal(data){
        const config = {
            url:'/teacher/changeCTScore',
            method:'post',
            data:data,
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            }
        }
        console.log("handleAppeal")
        return request(config)
    },

    //老师删除作业申诉
    deleteAppeal(data){
        const config = {
            url:'/teacher/deleteAPByAID',
            method:'post',
            data:data,
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            }
        }
        return request(config)
    },

    //老师修改学生分数
    changeScore(data){
        const config = {
            url:'/teacher/setCTScore',
            method:'post',
            data:data,
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            }
        }
        return request(config)
    },

    //管理员获取所有学生信息
    getAllStudent(){
        const config = {
            url:'/admin/getAllStudent',
            method:'post',
        }
        return request(config)
    },
}
export default http