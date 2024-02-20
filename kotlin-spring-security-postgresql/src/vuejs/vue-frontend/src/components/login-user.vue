<template>
  <v-container class="fill-height " fluid>
    <v-sheet  class="mx-auto" >
    <v-alert
        v-if="message"
        type="error"
        closable
        variant="tonal"
    >
     {{message}}
    </v-alert>
    <br>
    <br>
    <br>
    <v-form fast-fail ref="form">
      <v-avatar color="info" size="300">
        <v-img
            src="https://avataaars.io/?avatarStyle=Transparent&topType=ShortHairShortCurly&accessoriesType=Prescription02&hairColor=Black&facialHairType=Blank&clotheType=Hoodie&clotheColor=White&eyeType=Default&eyebrowType=DefaultNatural&mouthType=Default&skinColor=Light"
        />
      </v-avatar>

      <br>
      <br>
      <br>

      <v-text-field
          v-model="username"
          label="Username"
          :rules="usernameRules"
      ></v-text-field>

      <v-text-field
          v-model="password"
          type="password"
          label="Password"
          :rules="passwordRules"
      ></v-text-field>

      <v-btn color="primary" @click="validate" block class="mt-2">Submit</v-btn>


    </v-form>
  </v-sheet>
  </v-container>
</template>

<script>

export default {
  name: "Login-page",
  components: {
  },
  data: () => ({
    message: '',
    loading: false,
    username: '',
    usernameRules: [
      value => {
        if (value?.length > 1) return true
        return 'Username must be at least 2 characters.'
      },
    ],
    password: '',
    passwordRules: [
      value => {
        if (value?.length > 1) return true
        return 'Password must be at least 2 characters.'
      },
    ],
  }),
  computed: {
    loggedIn() {
      return this.$store.state.auth.status.loggedIn;
    },
  },
  created() {
    if (this.loggedIn) {
      this.$router.push("/profile");
    }
  },
  methods: {
    async validate () {
      const { valid } = await this.$refs.form.validate()
      if (valid) this.handleLogin()
    },
    handleLogin() {
      this.loading = true
      let user = {}
      user.username = this.username
      user.password = this.password
      this.$store.dispatch("auth/login", user).then(
          () => {
            this.$router.push("/profile");
          },
          (error) => {
            this.loading = false;
            this.message =
                (error.response &&
                    error.response.data &&
                    error.response.data.message) ||
                error.message ||
                error.toString();
          }
      );
    },
  },
};
</script>