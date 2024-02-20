<template>
  <div>
    <header>
      <v-parallax
          src="https://cdn.vuetifyjs.com/images/backgrounds/vbanner.jpg"
      >
        <div class="d-flex flex-column fill-height justify-center align-center text-white">
          <h1 class="text-h4 font-weight-thin mb-4">
            Vuetify Skeletton app
          </h1>
          <h3>{{ content }}</h3>
          <h4 class="subheading">
            Build your application today!
          </h4>
        </div>
      </v-parallax>
    </header>
  </div>
</template>

<script>
import UserService from "../services/user.service";

export default {
  name: "Home-Page",
  data() {
    return {
      content: "",
    };
  },
  mounted() {
    UserService.getPublicContent().then(
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