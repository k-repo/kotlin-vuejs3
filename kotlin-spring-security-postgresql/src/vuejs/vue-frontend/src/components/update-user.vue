<template>
  <v-container class="fill-height " fluid>
    <v-sheet  class="mx-auto" >
  <div v-if="currentUser" class="container">

    <br><br>
    <v-alert
        v-if="message"
        :type="handleMessageAlert"
        closable
        variant="tonal"
    >
      {{message}}
    </v-alert>

    <br>
    <br>
    <br>
    <v-form fast-fail ref="form">
    <v-card>
      <v-card-text>
        <v-sheet class="mb-4">
          <v-avatar color="info" class="mr-15" size="100">
            <v-img
                src="https://avataaars.io/?avatarStyle=Transparent&topType=ShortHairShortCurly&accessoriesType=Prescription02&hairColor=Black&facialHairType=Blank&clotheType=Hoodie&clotheColor=White&eyeType=Default&eyebrowType=DefaultNatural&mouthType=Default&skinColor=Light"
            />
          </v-avatar>
          <v-btn color="secondary">Change Avatar</v-btn>
          <v-spacer></v-spacer>
          <v-btn class="mt-4" color="primary" to="/profile"  >
            Profile
          </v-btn>
        </v-sheet>
        <v-text-field
            label="Username" v-model="username" :rules="usernameRules"></v-text-field>
        <v-text-field
            label="Email" v-model="email" :rules="emailRules"></v-text-field>
        <v-select
                  label="Roles"
                  :items="roles"
                  v-model="roles"
                  disabled
                  multiple
                  chips
                  persistent-hint
        ></v-select>
      </v-card-text>
      <v-card-actions>


        <v-dialog
            v-model="dialogUpdate"
            persistent
            width="auto"
        >
          <template v-slot:activator="{ props }">
            <v-btn
                color="primary"
                v-bind="props"
            >
              Update Account
            </v-btn>
          </template>
          <v-card>
            <v-card-title class="text-h5">
              Update {{currentUser.username}} account
            </v-card-title>
            <v-card-text>
              <v-text-field type="password"
                  label="Confirm password" v-model="password" :rules="passwordRules"></v-text-field>
            </v-card-text>
            <v-card-actions>
              <v-spacer></v-spacer>
              <v-btn
                  variant="text"
                  @click="dialogUpdate = false"
              >
                Cancel
              </v-btn>
              <v-btn
                  variant="text"
                  @click="validate"
              >
                Update
              </v-btn>
            </v-card-actions>
          </v-card>
        </v-dialog>




      </v-card-actions>
    </v-card>
    </v-form>
  </div>
    </v-sheet>
  </v-container>
</template>

<script>
export default {
  name: 'Update-user',
  data: () => ({
    message: '',
    loading: false,
    dialogUpdate: false,
    accessToken: '',
    roles: ['ROLE_ADMIN', 'ROLE_MODERATOR', 'ROLE_USER'],
    values: ['ROLE_ADMIN', 'ROLE_MODERATOR', 'ROLE_USER'],
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
    email: '',
    emailRules: [
      value => {
        if (/^[A-Za-z0-9_.]+@[a-z.-]+\.[a-z]+$/i.test(value)) return true
        return 'Must be a valid e-mail.'
      },
    ],
  }),
  computed: {
    currentUser() {
      return this.$store.state.auth.user;
    },
    handleMessageAlert() {
      if (this.message && this.success) {
        return "success"
      } else {
        return "error"
      }
    }
  },
  mounted() {
    if (!this.currentUser) {
      this.$router.push('/login');
    } else {
      this.username = this.$store.state.auth.user.username
      this.email = this.$store.state.auth.user.email
      this.roles = this.$store.state.auth.user.roles
      this.accessToken = this.$store.state.auth.user.accessToken
    }
  },
  methods: {
    async validate () {
      const { valid } = await this.$refs.form.validate()
      if (valid) this.handleUpdate()
    },
    handleUpdate() {
      this.successful = false;
      this.loading = true;
      let user = {}
      user.username = this.username
      user.email = this.email
      user.roles = this.roles
      user.id = this.currentUser.id
      user.currentUsername = this.currentUser.username
      user.password = this.password

      this.$store.dispatch("auth/update", user).then(
          (response) => {
            console.log("update reponse")
            console.log(response)
            //this.message = response.data;
            this.successful = true;
            this.loading = false;
            this.dialogUpdate = false
            this.$router.push('/profile');
          },
          (error) => {
            this.message =
                (error.response &&
                    error.response.data &&
                    error.response.data.message) ||
                error.message ||
                error.toString()
            this.successful = false
            this.dialogUpdate = false
            this.loading = false
          }
      );
    },
  }
};
</script>