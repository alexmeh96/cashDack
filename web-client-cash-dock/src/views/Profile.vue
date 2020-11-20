<template>
  <div>
    <div class="page-title">
      <h3>{{'ProfileTitle' | localize}}</h3>
    </div>

    <form class="form" @submit.prevent="submitHandler">
      <div class="input-field">
        <input
            id="description"
            type="text"
            v-model="username"
            :class="{invalid: $v.username.$dirty && !$v.username.required}"
        >
        <label for="description">{{'Name'|localize}}</label>
        <small
            class="helper-text invalid"
            v-if="$v.username.$dirty && !$v.username.required"
        >
          {{ 'Message_EnterName'|localize }}
        </small>
      </div>

      <!-- Switch -->
      <div class="switch">
        <label>
          English
          <input type="checkbox" v-model="isRuLocale">
          <span class="lever"></span>
          Русский
        </label>
      </div>

      <button class="btn waves-effect waves-light" type="submit">
        {{'Update'|localize}}
        <i class="material-icons right">send</i>
      </button>
    </form>
  </div>
</template>

<script>
import {mapGetters} from 'vuex'
import {required} from "vuelidate/lib/validators";
export default {
  name: 'Profile',
  data: () => ({
    username: '',
    isRuLocale: true
  }),
  validations: {
    username: {required},
  },
  mounted() {
    this.username = this.getInfo.username
    this.isRuLocale = this.getInfo.locale === 'ru-RU'
    //Обновление инпутов материалайза
    setTimeout(() => {
      M.updateTextFields()
    })
  },
  computed: {
    ...mapGetters(['getInfo'])
  },
  methods: {
    async submitHandler() {
      if (this.$v.$invalid) {
        this.$v.$touch()
        return
      }

      try {
        await this.$store.dispatch("updateInfo", {
          username: this.username,
          locale: this.isRuLocale ? 'ru-RU' : 'en-US'
        })
      } catch (e) {}

    }
  }
}
</script>

<style scoped>
.switch {
  margin-bottom: 2rem;
}
</style>
