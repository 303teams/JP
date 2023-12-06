import axios from 'axios'
export function request(config) {
    // 创建axios的实例
    const instance = axios.create({
        baseURL: 'http://localhost:8081',
        timeout: 10000
    })
    // 请求拦截器配置
    instance.interceptors.request.use(config => {
        if(localStorage.getItem('token')){
            const headers = {
                'token': localStorage.getItem('token')
            }
            config.headers = Object.assign(headers, config.headers || {})
        }
            return config
        }, error => {
            console.log(error)
            return Promise.reject(error)
        }
    )
    // 响应拦截器配置
    instance.interceptors.response.use(response => {
        console.log(response)
        const res = response
        return res
    }, error => {
        return Promise.reject(error)
    })
    // 发送真正的网络请求
    return instance(config);
}
export default request