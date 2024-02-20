<template>
  <div>
    <v-card
        flat
        title="User list"
    >
      <template v-slot:text>
        <v-text-field
            v-model="search"
            label="Search"
            prepend-inner-icon="mdi-magnify"
            single-line
            variant="outlined"
            hide-details
        ></v-text-field>
      </template>

      <v-data-table
          :headers="headers"
          :items="content"
          :search="search"
      ></v-data-table>
    </v-card>
  </div>
</template>

<script>
import UserService from "../services/user.service";

export default {
  name: "List-users",
  data() {
    return {
      content: [],
      search: '',
      headers: [
        {
          align: 'start',
          key: 'username',
          sortable: false,
          title: 'User list',
        },
        { key: 'username', title: 'username' },
        { key: 'email', title: 'email' },
      ],
    };
  },
  mounted() {
    UserService.getUsersList().then(
        (response) => {
          this.content = response.data;
        },
        (error) => {
          this.content =
              (error.response &&
                  error.response.data &&
                  error.response.data.message) ||
              error.message ||
              error.toString();
        }
    );
  },
};
</script>