import axios from "axios"
import authHeader from "@/utils/header";

const API_URL = 'http://localhost:8082/category/';

export default {
  actions: {
    async fetchCategories(ctx) {
      try {

        await new Promise(resolve => setTimeout(resolve, 1000));

        const header = await authHeader()
        const data = (await axios.get(API_URL, {headers: header})).data
        return data
      } catch (e) {
        ctx.commit('setError', e)
        throw e
      }

    },
    async fetchCategoryById(ctx, id) {
      try {
        const header = await authHeader()
        const data = (await axios.get(API_URL + id, {headers: header})).data
        return data
      } catch (e) {
        ctx.commit('setError', e)
        throw e
      }

    },
    async editCategory(ctx, category) {
      try {
        const header = await authHeader()
        const data = (await axios.post(API_URL + "edit", category,{headers: header})).data
        await new Promise(resolve => setTimeout(resolve, 1000));
      } catch (e) {
        ctx.commit('setError', e)
        throw e
      }
    },
    async createCategory(ctx, category) {
      try {
        await new Promise(resolve => setTimeout(resolve, 1000));

        const header = await authHeader()
        const data = (await axios.post(API_URL + "add", category,{headers: header})).data
        return data
      } catch (e) {
        ctx.commit('setError', e)
        throw e
      }
    }
  }
}
