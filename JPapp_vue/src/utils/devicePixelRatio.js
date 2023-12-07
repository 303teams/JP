class DevicePixelRatio {
    constructor() {
        // 尝试从 sessionStorage 中获取 initialDevicePixelRatio，如果不存在则设置为 1
        this.initialDevicePixelRatio = parseFloat(window.sessionStorage.getItem('initialDevicePixelRatio')) || 1;
        // this.initialDevicePixelRatio = window.devicePixelRatio;
        console.log('initialDevicePixelRatio', this.initialDevicePixelRatio)
    }

    // 获取系统类型
    _getSystem() {
        let agent = navigator.userAgent.toLowerCase();
        // 只针对 windows 处理，其他系统暂无该情况，如有，继续在此添加
        if (agent.indexOf("windows") >= 0) {
            return true;
        }
    }

    // 监听方法兼容写法
    _addHandler(element, type, handler) {
        if (element.addEventListener) {
            element.addEventListener(type, handler, false);
        } else if (element.attachEvent) {
            element.attachEvent("on" + type, handler);
        } else {
            element["on" + type] = handler;
        }
    }

    // 校正浏览器缩放比例
    _correct() {
        // 获取初始缩放率
        const initialDPR = this.initialDevicePixelRatio;
        console.log('initialDPR', initialDPR)
        console.log('devicePixelRatio', window.devicePixelRatio)
        // 计算页面body标签zoom修改其大小，来抵消devicePixelRatio带来的变化。
        document.body.style.zoom = 1 / window.devicePixelRatio * initialDPR;
    }

    // 监听页面缩放
    _watch() {
        let t = this;
        t._addHandler(window, 'resize', function () {
            // 注意这个方法是解决全局有两个 window.resize
            // 重新校正
            t._correct()
        });
    }

    // 初始化页面比例
    init() {
        let t = this;
        if (t._getSystem()) { // 判断设备，目前只在 Windows 系统下校正浏览器缩放比例
            // 获取页面加载时的 devicePixelRatio 并赋值给 initialDevicePixelRatio
            // 将 initialDevicePixelRatio 存储到 sessionStorage 中
            if(window.sessionStorage.getItem('initialDevicePixelRatio') === null) {
                t.initialDevicePixelRatio = window.devicePixelRatio;
                sessionStorage.setItem('initialDevicePixelRatio', t.initialDevicePixelRatio);
            }
            // 初始化页面校正浏览器缩放比例
            t._correct();
            // 开启监听页面缩放
            t._watch();
        }
    }
}

export default DevicePixelRatio;
