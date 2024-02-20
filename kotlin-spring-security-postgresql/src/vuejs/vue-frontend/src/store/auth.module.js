import AuthService from '../services/auth.service';
import UserService from '../services/user.service';

const user = JSON.parse(localStorage.getItem('user'));
const initialState = user
    ? { status: { loggedIn: true }, user }
    : { status: { loggedIn: false }, user: null };

export const auth = {
    namespaced: true,
    state: initialState,
    actions: {
        login({ commit }, user) {
            return AuthService.login(user).then(
                user => {
                    commit('loginSuccess', user);
                    return Promise.resolve(user);
                },
                error => {
                    commit('loginFailure');
                    return Promise.reject(error);
                }
            );
        },
        logout({ commit }) {
            AuthService.logout();
            commit('logout');
        },
        register({ commit }, user) {
            return AuthService.register(user).then(
                response => {
                    commit('registerSuccess');
                    return Promise.resolve(response.data);
                },
                error => {
                    commit('registerFailure');
                    return Promise.reject(error);
                }
            );
        },
        update({ commit }, user) {
            console.log("before login")
            console.log(user)
            let tmpU = {}
            tmpU.username = user.currentUsername
            tmpU.password = user.password
            console.log("before login tmp")
            console.log(tmpU)
            return AuthService.login(tmpU).then(
                response => {
                    console.log(" update auth")
                    console.log(response)
                    UserService.update({ commit }, {
                        username: user.username,
                        email: user.email,
                        id: user.id,
                        roles: user.roles
                    }).then(
                        data => {
                            console.log(" update data")
                            console.log(data)
                            AuthService.login(user)
                            return Promise.resolve(data)
                        },
                        error => {
                            console.log(" error update data")
                            console.log(error)
                            return Promise.reject(error);
                        }
                    )
                },
                error => {
                    console.log(" error update auth")
                    console.log(error)
                    return Promise.reject(error)
                }
            )

        }
    },
    mutations: {
        loginSuccess(state, user) {
            state.status.loggedIn = true;
            state.user = user;
        },
        updateSuccess(state, user) {
            state.user = user;
        },
        loginFailure(state) {
            state.status.loggedIn = false;
            state.user = null;
        },
        logout(state) {
            state.status.loggedIn = false;
            state.user = null;
        },
        registerSuccess(state) {
            state.status.loggedIn = false;
        },
        registerFailure(state) {
            state.status.loggedIn = false;
        }
    }
};