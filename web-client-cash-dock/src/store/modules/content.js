import axios from "axios";
import authHeader from "@/utils/header";

const API_URL = 'http://localhost:8082/content/';

export default {
  state: {
    content: '',
    bill: 10000
  },
  getters: {
    getContent(state) {
      return state.content
    },
    getBill(state) {
      return state.bill
    }
  },
  actions: {
    async allContentAct(ctx) {
      try {
        const data = await axios.get(API_URL + 'all');
        ctx.commit("contentMut", data)
      } catch (e) {

      }
    },
    async userContentAct(ctx) {
      try {
        const header = await authHeader()
        console.log(header)
        const data = await axios.get(API_URL + 'user', {headers: header});
        ctx.commit("contentMut", data)
      } catch (e) {
        console.log('no content!')
      }

    },
    async adminContentAct(ctx) {
      try {
        const data = await axios.get(API_URL + 'admin', {headers: authHeader()});
        ctx.commit("contentMut", data)
      } catch (e) {

      }
    }
  },
  mutations: {
    contentMut(state, content) {
      state.content = content
      console.log(content)
    }
  }
}
