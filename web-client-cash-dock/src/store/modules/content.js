import axios from "axios";
import authHeader from "@/utils/header";

const API_URL = 'http://localhost:8080/content/';

export default {
  state: {
    content: ''
  },
  getters: {
    getContent(state) {
      return state.content
    }
  },
  actions: {
    userContentAct(ctx) {

      axios.get(API_URL + 'user', {withCredentials: true}).then(
        response => {
          ctx.commit("contentMut", response.data)
        }
      )
    },
    allContentAct(ctx) {
      axios.get(API_URL + 'all').then(
        response => {
          ctx.commit("contentMut", response.data)
        }
      )
    },
    adminContentAct(ctx) {
      axios.get(API_URL + 'admin', {headers: authHeader()}).then(
        response => {
          ctx.commit("contentMut", response.data)
        }
      )
    },
  },
  mutations: {
    contentMut(state, content) {
      state.content = content
    }
  }
}
