import Vue from 'vue'
import Vuex from 'vuex'
import axios from 'axios'

import auth from './modules/auth'
import message from './modules/message'
import category from './modules/category'
import record from './modules/record'
import info from './modules/info'

const API_URL = 'http://localhost:8082/content/'

import authHeader from "@/utils/header";


Vue.use(Vuex)

export default new Vuex.Store({
  modules: {
    auth, message, category, record, info
  },
  actions: {
    async fetchCurrency() {

      const data = (await axios.get(API_URL + 'currency')).data

      await new Promise(resolve => setTimeout(resolve, 1000));

      return data;
    }
  }
})
