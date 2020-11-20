import axios from "axios";
import authHeader from "@/utils/header";

const API_URL = 'http://localhost:8080/auth/';

export default {
  state: {
    token: {},
    loggedIn: false
  },
  getters: {
    getLoggedIn(state) {
      return state.loggedIn
    },
    getToken: s => s.token,
  },
  actions: {
    async loginAct(ctx, {email, password}) {
      try {
        const data = (await axios.post(API_URL + 'signin', {
          email,
          password
        }, {withCredentials: true})).data

        ctx.commit('loginMut', data)

      } catch (e) {
        ctx.commit('setError', e.response.data.message)
        throw e
      }
    },
    async registerAct(ctx, formData) {
      try {
        const data = (await axios.post(API_URL + 'signup', formData)).data
        ctx.commit('setMessage', data.message)
      } catch (e) {
        ctx.commit('setError', e.response.data.message)
        throw e
      }
    },
    async refreshTokenAct(ctx) {
      try {
        const data = (await axios.get(API_URL + 'refresh', {withCredentials: true})).data
        ctx.commit('loginMut', data)
        return Promise.resolve()
      } catch (e) {
        return Promise.reject()
      }

    },
    async logoutAct(ctx) {
      const header = await authHeader()
      await axios.get(API_URL + 'logout', {headers: header})
      ctx.commit("logoutMut")
      ctx.commit("clearInfo")
    }

  },
  mutations: {
    loginMut(state, data) {
      state.token = {
        tokenValue: data.tokenValue,
        duration: data.duration
      }
      state.loggedIn = true
    },
    logoutMut(state) {
      state.loggedIn = false;
      state.token = {};
    }

  }

}
