import axios from "axios"
import authHeader from "@/utils/header";

const API_URL = 'http://localhost:8082/info/';

export default {
  state: {
    info: {}
  },
  getters: {
    getInfo: s => s.info
  },
  actions: {
    async updateInfo(ctx, toUpdate) {
      try {
        const header = await authHeader()
        await axios.post(API_URL + "updateInfo", toUpdate, {headers: header})
        const updateData = {...ctx.getters.getInfo, ...toUpdate}
        ctx.commit("setInfo", updateData)
      } catch (e) {
        ctx.commit('setError', e)
        throw e
      }
    },

    async fetchInfo(ctx) {
      try {
        const header = await authHeader()
        const data = (await axios.get(API_URL, {headers: header})).data
        ctx.commit("setInfo", data)
      } catch (e) {
        ctx.commit('setError', e)
        throw e
      }
    }
  },

  mutations: {
    setInfo(state, info) {
      state.info = info
    },
    clearInfo(state) {
      state.info = {}
    }
  }

}
