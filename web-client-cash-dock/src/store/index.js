import Vue from 'vue'
import Vuex from 'vuex'
import axios from 'axios'

import auth from './modules/auth';
import content from './modules/content';
import message from './modules/message'
import category from './modules/category'

const API_URL = 'http://localhost:8082/content/'

import authHeader from "@/utils/header";


Vue.use(Vuex)

export default new Vuex.Store({
  modules: {
    auth, content, message, category
  },
  actions: {
    async fetchCurrency() {

      const data = (await axios.get(API_URL + 'currency')).data


      console.log(data)

      // const data = {
      //   EUR: 1,
      //   USD: 1.2,
      //   RUB: 91
      // }

        await new Promise(resolve => setTimeout(resolve, 1000));


      return data;
    }
  }
})
