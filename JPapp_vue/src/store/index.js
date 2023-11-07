import { createStore } from 'vuex'; // 使用createStore来创建Vuex store

const store = createStore({
    state: {
        role: "",
        token: "",
    },

    mutations: {
        setRole(state, value) {
            state.role = value;
        },
        setToken(state, value) {
            state.token = value;
        },
    },

    actions: {
        setRole(context, value) {
            context.commit('setRole', value);
        },
        setToken(context, value) {
            context.commit('setToken', value);
        },
    },



    modules: {}
});

export default store;
