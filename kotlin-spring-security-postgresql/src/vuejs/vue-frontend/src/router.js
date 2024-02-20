import { createWebHistory, createRouter } from "vue-router";
import Home from "./components/home-app.vue";
import Login from "./components/login-user.vue";
import Register from "./components/register-user.vue";
import UsersList from "./components/list-users.vue";
import UserUpdate from "./components/update-user.vue";
// lazy-loaded
const Profile = () => import("./components/profile-user.vue")
const BoardAdmin = () => import("./components/board-admin.vue")
const BoardModerator = () => import("./components/board-moderator.vue")
const BoardUser = () => import("./components/board-user.vue")

const routes = [
    {
        path: "/",
        name: "home",
        component: Home,
    },
    {
        path: "/home",
        component: Home,
    },
    {
        path: "/login",
        component: Login,
    },
    {
        path: "/register",
        component: Register,
    },
    {
        path: "/profile",
        name: "profile",
        // lazy-loaded
        component: Profile,
    },
    {
        path: "/admin",
        name: "admin",
        // lazy-loaded
        component: BoardAdmin,
    },
    {
        path: "/mod",
        name: "moderator",
        // lazy-loaded
        component: BoardModerator,
    },
    {
        path: "/user",
        name: "user",
        // lazy-loaded
        component: BoardUser,
    },
    {
        path: "/users",
        name: "users",
        // lazy-loaded
        component: UsersList,
    },
    {
        path: "/user/update/:id",
        name: "user update",
        // lazy-loaded
        component: UserUpdate,
    }
];

const router = createRouter({
    history: createWebHistory(),
    routes,
});

// router.beforeEach((to, from, next) => {
//   const publicPages = ['/login', '/register', '/home'];
//   const authRequired = !publicPages.includes(to.path);
//   const loggedIn = localStorage.getItem('user');

//   // trying to access a restricted page + not logged in
//   // redirect to login page
//   if (authRequired && !loggedIn) {
//     next('/login');
//   } else {
//     next();
//   }
// });

export default router;