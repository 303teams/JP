import { createStore } from 'vuex'; // 使用createStore来创建Vuex store
import createPersistedState from "vuex-persistedstate"

const getDefaultState = () => {
    return {
        role: "",
        id: "",
        name: "",
        sex: "",
        email: "",
        age: "",
    }
}

const store = createStore({
    state: {
        role: "",
        id: "",
        name: "",
        sex: "",
        email: "",
        age: "",
    },

    getters: {
        getRole: state => state.role
    },
    mutations: {
        setRole(state, value) {
            state.role = value;
        },
        setId(state, value) {
            state.id = value;
        },
        setName(state, value) {
            state.name = value;
        },
        setSex(state, value) {
            state.sex = value;
        },
        setEmail(state, value) {
            state.email = value;
        },
        setAge(state, value) {
            state.age = value;
        },
        updateInfo(state, payload) {
            // 把payload的属性分别赋值给state的对应属性
            state.id = payload.id;
            state.name = payload.name;
            state.sex = payload.sex;
            state.email = payload.email;
            state.age = payload.age;
        },
        RESET_STATE: (state) => {
            Object.assign(state, getDefaultState())
        },

    },

    actions: {
        setRole(context, value) {
            context.commit('setRole', value);
        },
        setId(context, value) {
            context.commit('setId', value);
        },
        setName(context, value) {
            context.commit('setName', value);
        },
        setSex(context, value) {
            context.commit('setSex', value);
        },
        setEmail(context, value) {
            context.commit('setEmail', value);
        },
        setAge(context, value) {
            context.commit('setAge', value);
        },
        updateInfo(context, payload) {
            context.commit('updateInfo', payload);
        }
    },

    modules: {},

    plugins: [
        createPersistedState()
    ]
});

export default store;
