import axios from 'axios';
import authHeader from './auth-header';

//const API_URL = '/api/user/';
//const API_URL = 'http://localhost:8082/api/user/';
const API_URL = process.env.VUE_APP_ROOT_API + 'user/'

    class UserService {
    getPublicContent() {
        return axios.get(API_URL + 'public');
    }

    getUserBoard() {
        return axios.get(API_URL + 'signed', { headers: authHeader() });
    }

    getModeratorBoard() {
        return axios.get(API_URL + 'mod', { headers: authHeader() });
    }

    getAdminBoard() {
        return axios.get(API_URL + 'admin', { headers: authHeader() });
    }

    getUsersList() {
        return axios.get(API_URL + 'all', { headers: authHeader() });
    }

    update({ commit }, user) {
        return axios.put(API_URL + 'update', {
            username: user.username,
            email: user.email,
            id: user.id
        }, { headers: authHeader() }).then(
            data => {
                commit('updateSuccess', user)
                return Promise.resolve(data)
            }
        )
    }

    delete(id) {
        console.log("delete")
        console.log(id)
        return axios.delete(API_URL + 'delete/'+id, { headers: authHeader() }).then(
            response => {
                response.data
                localStorage.removeItem('user')
            }
        )
    }
}

export default new UserService();