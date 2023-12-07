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
}
export default http