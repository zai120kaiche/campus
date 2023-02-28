import { createStore } from 'vuex'

export default createStore({
  state: {
    index: 3,
  },
  getters: {
    getIndex: state => {
      return state.index
    }
  },
  mutations: {
    SET_INDEX: (state, index) => {
      state.index = index
    }
  },
  actions: {
  },
  modules: {
  }
})
