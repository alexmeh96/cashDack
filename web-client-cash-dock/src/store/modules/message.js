export default {
  state: {
    error: null,
    message: null
  },
  getters: {
    getError: s => s.error,
    getMessage: s => s.message
  },
  mutations: {
    setMessage(state, message) {
      state.message = message
    },
    clearMessage(state) {
      state.message = null
    },
    setError(state, error) {
      state.error = error
    },
    clearError(state) {
      state.error = null
    }
  }
}
