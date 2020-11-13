import store from '@/store'

async function authHeader() {

  const token = store.getters.getToken;
  if (token) {
    const now = new Date().getTime()
    if (token.duration - now < 5000) {

      try {
        await store.dispatch("refreshTokenAct")
        return {Authorization: 'Bearer ' + store.getters.getToken};
      } catch (e) {
        return {}
      }

    }
    return {Authorization: 'Bearer ' + token.tokenValue};
  }

  return {};

}

export default authHeader
