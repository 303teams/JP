import { createStore } from 'vuex'; // 使用createStore来创建Vuex store

const store = createStore({
    state: {
        role: "",
        sex: "",
    },

    mutations: {
        setRole(state, value) {
            state.role = value;
        },
        setSex(state, value) {
            state.sex = value;
        },
    },

    actions: {
        setRole(context, value) {
            context.commit('setRole', value);
        },
        setSex(context, value) {
            context.commit('setSex', value);
        },
    },



    modules: {}
});

export default store;
