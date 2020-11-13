import axios from "axios";
import authHeader from "@/utils/header";

const API_URL = 'http://localhost:8080/auth/';

export default {
  state: {
    user: null,
    token: null,
    loggedIn: false
  },
  getters: {
    getUsername(state) {
      return state.user ? state.user.username : ""
    },
    getLoggedIn(state) {
      return state.loggedIn
    },
    getUser: s => s.user,
    getToken: s => s.token,
  },
  actions: {
    async loginAct(ctx, {email, password}) {
      try {
        const data = (await axios.post(API_URL + 'signin',  {
          email,
          password
        }, { withCredentials: true })).data

        ctx.commit('loginMut', data)

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
    async refreshTokenAct(ctx) {
      try {
        const data = (await axios.get(API_URL + 'refresh', { withCredentials: true })).data
        ctx.commit('loginMut', data)
        return Promise.resolve()
      } catch (e) {
        return Promise.reject()
      }

    },
    async logoutAct(ctx) {
      const header = await authHeader()
      console.log(header)
      const data = await axios.get(API_URL + 'logout', { headers: header })
      console.log(data)
      ctx.commit("logoutMut")
    }

  },
  mutations: {
    loginMut(state, data) {
      state.user = data.user
      state.token = data.token
      state.loggedIn = true
      console.log(data)
    },
    logoutMut(state) {
      state.loggedIn = false;
      state.user = null;
      state.token = null;
    }

  }

}
