import Vue from 'vue'
import Vuex from 'vuex'

import auth from './modules/auth';
import content from './modules/content';
import message from './modules/message'

Vue.use(Vuex)

export default new Vuex.Store({
  modules: {
    auth, content, message
  }
})
