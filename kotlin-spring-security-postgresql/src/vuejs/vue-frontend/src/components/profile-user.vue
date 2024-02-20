<template>
  <v-container class="fill-height " fluid>
    <v-sheet  class="mx-auto" >
  <div v-if="currentUser">
    <v-card>
      <v-card-text>
        <v-list-item class="w-100">
          <template v-slot:prepend>
            <v-avatar size="200" class="mr-10"
                color="info"
                image="https://avataaars.io/?avatarStyle=Transparent&topType=ShortHairShortCurly&accessoriesType=Prescription02&hairColor=Black&facialHairType=Blank&clotheType=Hoodie&clotheColor=White&eyeType=Default&eyebrowType=DefaultNatural&mouthType=Default&skinColor=Light"
            ></v-avatar>
          </template>

          <v-list-item-title>Username</v-list-item-title>
          <v-list-item-subtitle>{{currentUser.username}}</v-list-item-subtitle>

          <br>

          <v-list-item-title>Email</v-list-item-title>
          <v-list-item-subtitle>{{currentUser.email}}</v-list-item-subtitle>

          <br>

          <v-list-item-title>Roles</v-list-item-title>
          <div v-for="role in currentUser.roles" :key="role">
            <v-list-item-subtitle>- {{role}}</v-list-item-subtitle>
          </div>

          <v-spacer></v-spacer>

        </v-list-item>
      </v-card-text>
      <v-card-actions>
        <v-btn color="primary" :to="'/user/update/'+currentUser.id" >
          Update Account
        </v-btn>

        <v-dialog
        v-model="dialogDelete"
        persistent
        width="auto"
        >
        <template v-slot:activator="{ props }">
          <v-btn
              color="primary"
              v-bind="props"
          >
            Delete Account
          </v-btn>
        </template>
        <v-card>
          <v-card-title class="text-h5">
            Delete {{currentUser.username}} account
          </v-card-title>
          <v-card-text>
            Are you sure ?
          </v-card-text>
          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn
                variant="text"
                @click="dialogDelete = false"
            >
              Cancel
            </v-btn>
            <v-btn
                variant="text"
                @click="deleteAccount"
            >
              Delete
            </v-btn>
          </v-card-actions>
        </v-card>
        </v-dialog>

      </v-card-actions>
    </v-card>

  </div>
    </v-sheet>
  </v-container>
</template>

<script>
import UserService from "../services/user.service";
export default {
  name: 'Profile-Page',
  data () {
    return {
      dialogDelete: false
    }
  },
  computed: {
    currentUser() {
      return this.$store.state.auth.user;
    }
  },
  mounted() {
    if (!this.currentUser) {
      this.$router.push('/login');
    }
  },
  methods: {
    deleteAccount() {
      UserService.delete(this.currentUser.id)
      this.$store.dispatch('auth/logout');
      this.$router.push('/login');
    }
  }
};
</script>