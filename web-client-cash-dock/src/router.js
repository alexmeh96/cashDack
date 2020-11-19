import Vue from 'vue'
import Router from 'vue-router'
import store from '@/store'

Vue.use(Router)

const router =  new Router({
  mode: 'history',
  base: process.env.BASE_URL,
  routes: [
    {
      path: '/login',
      name: 'login',
      meta: {layout: 'Empty'},
      component: () => import('./views/Login.vue')
    },
    {
      path: '/register',
      name: 'register',
      meta: {layout: 'Empty'},
      component: () => import('./views/Register.vue')
    },
    {
      path: '/',
      name: 'home',
      meta: {layout: 'Main', auth: true},
      component: () => import('./views/Home.vue')
    },
    {
      path: '/categories',
      name: 'categories',
      meta: {layout: 'Main', auth: true},
      component: () => import('./views/Categories.vue')
    },
    {
      path: '/detail/:id',
      name: 'detail',
      meta: {layout: 'Main', auth: true},
      component: () => import('./views/Detail.vue')
    },
    {
      path: '/history',
      name: 'history',
      meta: {layout: 'Main', auth: true},
      component: () => import('./views/History.vue')
    },
    {
      path: '/planning',
      name: 'planning',
      meta: {layout: 'Main', auth: true},
      component: () => import('./views/Planning.vue')
    },
    {
      path: '/profile',
      name: 'profile',
      meta: {layout: 'Main', auth: true},
      component: () => import('./views/Profile.vue')
    },
    {
      path: '/record',
      name: 'record',
      meta: {layout: 'Main', auth: true},
      component: () => import('./views/Record.vue')
    }
  ]
})

router.beforeEach((to, from, next) => {
  const loggedIn = store.getters.getLoggedIn
  const auth = to.matched.some(record => record.meta.auth)


  if (auth && !loggedIn) {
    store.dispatch("refreshTokenAct").then(
      () => next(),
      () => next('/login?message=login')
    )
  }else {
    next()
  }
})

export default router
