const user = JSON.parse(localStorage.getItem('user'));
import AuthService from "@/service/auth/auth"

export default {
    state: {
        user,
        loggedIn: false
    },
    getters: {
        getUsername(state) {
            return state.user ? state.user.username : ""
        },
        getLoggedIn(state) {
            return state.loggedIn
        },
        getRole(state) {
            return state.user.roles
        }
    },
    actions: {
        loginAct(ctx, user) {
            if (user.username && user.password) {
                return AuthService.login(user).then(
                    user => {
                        ctx.commit('loginMut', user)
                        return Promise.resolve(user)
                    },
                    error => {
                        return Promise.reject(error)
                    }
                )
            }
            return Promise.reject()
        },
        registerAct(ctx, user) {
            if (user.username && user.password && user.email) {
                return AuthService.register(user).then(
                    () => Promise.resolve(),
                    error => {
                        return Promise.reject(error)
                    }
                )
            }
            return Promise.reject()
        },
        logoutAct(ctx) {
            AuthService.logout()
            ctx.commit("logoutMut")
        }

    },
    mutations: {
        loginMut(state, user) {
            state.user = user
            state.loggedIn = true
        },
        logoutMut(state) {
            state.loggedIn = false;
            state.user = null;
        }

    }

}
