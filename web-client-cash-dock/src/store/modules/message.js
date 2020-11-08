export default {
  state: {
    error: null
  },
  getters: {
    getError: s => s.error
  },
  mutations: {
    setError(state, error) {
      state.error = error
    },
    clearError(state) {
      state.error = null
    }
  }
}
