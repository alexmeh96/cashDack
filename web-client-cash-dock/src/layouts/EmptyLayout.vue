<template>
  <div class="grey darken-1 empty-layout">
    <router-view />
  </div>
</template>

<script>
import messages from "@/utils/messages";
export default {
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
  }

}
</script>
