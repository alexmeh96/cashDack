<template>
  <div>
    <Loader v-if="loading" />
    <div class="app-main-layout" v-else>
      <Navbar @click="isOpen = !isOpen"/>

      <Sidebar v-model="isOpen"/>

      <main class="app-content" :class="{full: !isOpen}">
        <div class="app-page">
          <router-view/>
        </div>
      </main>

      <div class="fixed-action-btn">
        <router-link class="btn-floating btn-large blue" to="/record" v-tooltip="'Создать новую запись'">
          <i class="large material-icons">add</i>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script>
import Navbar from '@/components/app/Navbar'
import Sidebar from '@/components/app/Sidebar'
import messages from "@/utils/messages";

export default {
  name: 'main-layout',
  data: () => ({
    isOpen: true,
    loading: true
  }),
  components: {
    Navbar, Sidebar
  },
  computed: {
    error() {
      return this.$store.getters.error
    }
  },
  watch: {
    error(backError) {
      console.log(backError)
      this.$error(messages[backError] || '.что-то пошло не так')
    }
  },
  async mounted() {
    await new Promise(resolve => setTimeout(resolve, 1000));

    if (!Object.keys(this.$store.getters.getInfo).length) {
      console.log("!!!!!!!!!!!!!!!!!!1")
      await this.$store.dispatch("fetchInfo")
    }
    this.loading = false
  }
}
</script>
