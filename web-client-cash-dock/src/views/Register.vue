<template>
  <div>
    <form class="card auth-card" @submit.prevent="submitHandler">

      <div class="card-content">
        <span class="card-title">Домашняя бухгалтерия</span>
        <div class="input-field">
          <input
              id="email"
              type="text"
              v-model.trim="email"
              :class="{invalid: ($v.email.$dirty && !$v.email.required) || ($v.email.$dirty && !$v.email.email)}"
          >
          <label for="email">Email</label>
          <small
              class="helper-text invalid"
              v-if="$v.email.$dirty && !$v.email.required"
          >Поле Email не должно быть пустым</small>
          <small
              class="helper-text invalid"
              v-else-if="$v.email.$dirty && !$v.email.email"
          >Введите корретный Email</small>
        </div>
        <div class="input-field">
          <input
              id="password"
              type="password"
              v-model.trim="password"
              :class="{invalid: ($v.password.$dirty && !$v.password.required) || ($v.password.$dirty && !$v.password.minLength)}"
          >
          <label for="password">Пароль</label>
          <small
              class="helper-text invalid"
              v-if="$v.password.$dirty && !$v.password.required"
          >
            Введите пароль
          </small>
          <small
              class="helper-text invalid"
              v-else-if="$v.password.$dirty && !$v.password.minLength"
          >
            Пароль должен быть {{ $v.password.$params.minLength.min }} символов. Сейчас он {{ password.length }}
          </small>
        </div>
        <div class="input-field">
          <input
              id="name"
              type="text"
              v-model.trim="username"
              :class="{invalid: $v.username.$dirty && !$v.username.required}"
          >
          <label for="name">Имя</label>
          <small
              class="helper-text invalid"
              v-if="$v.username.$dirty && !$v.username.required"
          >
            Введите ваше имя
          </small>
        </div>
        <p>
          <label>
            <input type="checkbox" v-model="agree"/>
            <span>С правилами согласен</span>
          </label>
        </p>
      </div>
      <div class="card-action">
        <div>
          <button
              class="btn waves-effect waves-light auth-submit"
              type="submit"
          >
            Зарегистрироваться
            <i class="material-icons right">send</i>
          </button>
        </div>

        <p class="center">
          Уже есть аккаунт?
          <router-link to="/login">Войти!</router-link>
        </p>
      </div>
      <div v-if="message">
        {{ message }}
      </div>
      <div v-else-if="messageError">
        {{ messageError }}
      </div>

    </form>




  </div>
</template>

<script>
import {email, required, minLength} from 'vuelidate/lib/validators'
import {mapActions, mapGetters} from 'vuex'
import messages from "@/utils/messages";

export default {
  name: 'register',
  data: () => ({
    email: '',
    password: '',
    username: '',
    messageError: '',
    message: '',
    agree: false
  }),
  validations: {
    email: {email, required},
    password: {required, minLength: minLength(6)},
    username: {required},
    agree: {checked: v => v}
  },
  computed: mapGetters(["getLoggedIn", "getError", "getMessage"]),
  mounted() {
    if (this.loggedIn) {
      this.$router.push('/profile');
    }
  },
  methods: {
    ...mapActions(["registerAct"]),
    async submitHandler() {

      if (this.$v.$invalid) {
        this.$v.$touch()
        return
      }
      const formData = {
        email: this.email,
        password: this.password,
        username: this.username

      }
      try {
        await this.registerAct(formData)
        this.message = messages[this.getMessage]

      } catch (e) {
        this.messageError = messages[this.getError]
      }

    }
  }
}
</script>
