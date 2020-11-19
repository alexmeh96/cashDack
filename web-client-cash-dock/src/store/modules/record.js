import axios from "axios"
import authHeader from "@/utils/header";

const API_URL = 'http://localhost:8082/record/';
export default {
  actions: {
    async createRecord(ctx, record) {
      try {
        const header = await authHeader()
        await axios.post(API_URL + "add", record, {headers: header})
      } catch (e) {
        ctx.commit('setError', e)
        throw e
      }
    },
    async fetchRecords(ctx) {
      try {
        const header = await authHeader()
        const data = (await axios.get(API_URL, {headers: header})).data
        console.log(data)
        return data
      } catch (e) {
        ctx.commit('setError', e)
        throw e
      }
    },
    async fetchRecordById(ctx, id) {
      try {
        const header = await authHeader()
        const data = (await axios.get(API_URL + id, {headers: header})).data
        console.log(data)
        return data
      } catch (e) {
        ctx.commit('setError', e)
        throw e
      }
    }
  }
}
