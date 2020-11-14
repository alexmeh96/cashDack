import Vue from 'vue'
import Vuex from 'vuex'

import auth from './modules/auth';
import content from './modules/content';
import message from './modules/message'

Vue.use(Vuex)

export default new Vuex.Store({
  modules: {
    auth, content, message
  },
  actions: {
    async currencyAct() {
      const data = {
        EUR: 1,
        USD: 1.2,
        RUB: 91
      }

        await new Promise(resolve => setTimeout(resolve, 2000));


      return data;
    }
  }
})
