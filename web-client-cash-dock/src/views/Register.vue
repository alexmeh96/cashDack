<template>
  <div>
    <form class="card auth-card" @submit.prevent="submitHandler">
      <div v-if="!successful">
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
                v-model.trim="name"
                :class="{invalid: $v.name.$dirty && !$v.name.required}"
            >
            <label for="name">Имя</label>
            <small
                class="helper-text invalid"
                v-if="$v.name.$dirty && !$v.name.required"
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
      </div>
    </form>
    <div v-if="successful">
      {{message}}
    </div>


  </div>
</template>

<script>
import {email, required, minLength} from 'vuelidate/lib/validators'
import User from '@/model/user';
import {mapActions, mapGetters} from 'vuex'

export default {
  name: 'register',
  data: () => ({
    email: '',
    password: '',
    name: '',
    submitted: false,
    successful: false,
    message: '',
    agree: false
  }),
  validations: {
    email: {email, required},
    password: {required, minLength: minLength(6)},
    name: {required},
    agree: {checked: v => v}
  },
  computed: mapGetters(["getLoggedIn"]),
  mounted() {
    if (this.loggedIn) {
      this.$router.push('/profile');
    }
  },
  methods: {
    ...mapActions(["registerAct"]),
    submitHandler() {
      this.message = '';
      this.submitted = true;
      if (this.$v.$invalid) {
        this.$v.$touch()
        return
      }
      const user = new User(this.name, this.email, this.password)
      this.registerAct(user).then(
          data => {
            this.message = data.message;
            this.successful = true;
          },
          error => {
            this.message = "registration error";
            console.log((error.response && error.response.data) ||
                error.message ||
                error.toString())

            this.successful = false;
          }
      );
    }
  }
}
</script>
