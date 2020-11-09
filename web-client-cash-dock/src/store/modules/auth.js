import axios from "axios";

const API_URL = 'http://localhost:8080/auth/';
const user = JSON.parse(localStorage.getItem('user'));

export default {
  state: {
    user,
    loggedIn: false
  },
  getters: {
    getUsername(state) {
      return state.user ? state.user.username : ""
    },
    getLoggedIn(state) {
      return state.loggedIn
    },
    getRole(state) {
      return state.user.roles
    }
  },
  actions: {
    async loginAct(ctx, {email, password}) {
      try {
        const user = (await axios.post(API_URL + 'signin', {
          email,
          password
        })).data
        if (user.token) {
          localStorage.setItem('user', JSON.stringify(user));
        }
        ctx.commit('loginMut', user)

      } catch (e) {
        console.log(e.response.data)
        ctx.commit('setError', e.response.data.message)
        throw e
      }
    },
    async registerAct(ctx, formData) {
      try {
        const data = (await axios.post(API_URL + 'signup', formData)).data
        console.log(data.message)
        ctx.commit('setMessage', data.message)
      } catch (e) {
        console.log(e.response.data.message)
        ctx.commit('setError', e.response.data.message)
        throw e
      }

    },
    logoutAct(ctx) {
      localStorage.removeItem('user')
      ctx.commit("logoutMut")
    }

  },
  mutations: {
    loginMut(state, user) {
      state.user = user
      state.loggedIn = true
    },
    logoutMut(state) {
      state.loggedIn = false;
      state.user = null;
    }

  }

}
