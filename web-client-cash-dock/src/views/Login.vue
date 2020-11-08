<template>
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
          Пароль должен быть {{$v.password.$params.minLength.min}} символов. Сейчас он {{password.length}}
        </small>
      </div>
    </div>
    <div class="card-action">
      <div>
        <button
            class="btn waves-effect waves-light auth-submit"
            type="submit"
        >
          Войти
          <i class="material-icons right">send</i>
        </button>
        <div>
          <div v-if="message" class="alert alert-danger" role="alert">{{message}}</div>
        </div>
      </div>

      <p class="center">
        Нет аккаунта?
        <router-link to="/register">Зарегистрироваться</router-link>
      </p>
    </div>
  </form>
</template>

<script>
import {email, required, minLength} from 'vuelidate/lib/validators'
import messages from '@/utils/messages'
import User from '@/model/user';
import { mapActions, mapGetters } from 'vuex'

export default {
  name: 'login',
  data() {
    return {
      email: '',
      password: '',
      loading: false,
      message: ''
    };
  },
  computed: mapGetters(["getLoggedIn"]),
  created() {
    if (this.getLoggedIn) {
      this.$router.push('/profile');
    }
  },
  validations: {
    email: {email, required},
    password: {required, minLength: minLength(6)}
  },
  mounted() {
    if (messages[this.$route.query.message]) {
      this.$message(messages[this.$route.query.message])
    }
  },
  methods: {
    ... mapActions(["loginAct"]),
    submitHandler() {
      this.loading = true;
      if (this.$v.$invalid) {
        this.$v.$touch()
        this.loading = false;
        return
      }

      if (this.email && this.password) {
        const user = new User('', this.email, this.password)
        this.loginAct(user).then(
            () => {
              this.$router.push('/profile');
            },
            error => {
              this.loading = false;
              console.log( (error.response && error.response.data) ||
                  error.message ||
                  error.toString())
              this.message = "no correct email or password"

            }
        );
      }
    }
  }
}
</script>
