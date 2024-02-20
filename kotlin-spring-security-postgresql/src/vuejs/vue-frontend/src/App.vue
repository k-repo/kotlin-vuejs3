<template>
  <div id="app">
      <v-layout>
        <v-navigation-drawer
            expand-on-hover
            rail
        >
          <v-list v-if="currentUser">
            <v-list-item
                         prepend-avatar="https://avataaars.io/?avatarStyle=Transparent&topType=ShortHairShortCurly&accessoriesType=Prescription02&hairColor=Black&facialHairType=Blank&clotheType=Hoodie&clotheColor=White&eyeType=Default&eyebrowType=DefaultNatural&mouthType=Default&skinColor=Light"
                :title="currentUser.username"
                :subtitle="currentUser.email"
            ></v-list-item>
          </v-list>

          <v-divider></v-divider>

          <v-list density="compact" nav>
            <v-list-item title="Home" to='/home' link>
            </v-list-item>
            <v-list-item v-if="showAdminBoard" title="Admin Board" to="/admin" link>
            </v-list-item>
            <v-list-item v-if="showModeratorBoard" title="Moderator Board" to="/mod" link>
            </v-list-item>
            <v-list-item v-if="currentUser" title="User Board" to="/user" link>
            </v-list-item>
          </v-list>

          <v-spacer></v-spacer>
          <v-divider></v-divider>

          <v-list density="compact" nav>
            <v-list-item v-if="showAdminBoard" title="Users" to="/users" link>
            </v-list-item>
          </v-list>


        </v-navigation-drawer>
        <v-app-bar
            color="primary"
            prominent
        >
          <v-toolbar-title>My new app</v-toolbar-title>

          <v-spacer></v-spacer>

          <v-btn variant="text" icon="mdi-magnify"></v-btn>

          <v-menu
              v-model="menu"
              :close-on-content-click="false"
              location="end"
          >
          <template v-slot:activator="{ props }">
          <v-btn variant="text" icon="mdi-dots-vertical" v-bind="props"></v-btn>
          </template>



            <v-card min-width="200" class="mt-15" >
              <v-list v-if="currentUser">
                <v-list-item
                    prepend-avatar="https://avataaars.io/?avatarStyle=Transparent&topType=ShortHairShortCurly&accessoriesType=Prescription02&hairColor=Black&facialHairType=Blank&clotheType=Hoodie&clotheColor=White&eyeType=Default&eyebrowType=DefaultNatural&mouthType=Default&skinColor=Light"
                    :title="currentUser.username"
                    :subtitle="currentUser.email"
                >
                </v-list-item>
              </v-list>

              <v-divider></v-divider>

              <v-list>

                <v-list-item prepend-icon="mdi-face-man-profile" v-if="currentUser" title="Profile" to="/profile" link>
                </v-list-item>

                <v-list-item prepend-icon="mdi-logout" v-if="currentUser" title="Logout" @click.prevent="logOut" link>
                </v-list-item>

                <v-list-item prepend-icon="mdi-account-plus" v-if="!currentUser" title="Sign Up" to="/register" link>
                </v-list-item>

                <v-list-item prepend-icon="mdi-login"  v-if="!currentUser" title="Login" to="/login" link>
                </v-list-item>

              </v-list>

            </v-card>
          </v-menu>
        </v-app-bar>

        <v-main>
              <router-view />
        </v-main>
      </v-layout>
  </div>
</template>

<script>
export default {
  data: () => ({
    fav: true,
    menu: false,
    message: false,
    hints: true,
  }),
  computed: {
    currentUser() {
      return this.$store.state.auth.user;
    },
    showAdminBoard() {
      if (this.currentUser && this.currentUser['roles']) {
        return this.currentUser['roles'].includes('ROLE_ADMIN');
      }

      return false;
    },
    showModeratorBoard() {
      if (this.currentUser && this.currentUser['roles']) {
        return this.currentUser['roles'].includes('ROLE_MODERATOR');
      }

      return false;
    }
  },
  methods: {
    logOut() {
      this.$store.dispatch('auth/logout');
      this.$router.push('/login');
    }
  }
};
</script>