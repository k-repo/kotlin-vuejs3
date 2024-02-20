<template>
  <div>
    <header>
      <h3>{{ content }}</h3>
    </header>
    <v-card>
      <v-timeline side="end" align="start">
        <v-timeline-item
            dot-color="pink"
            size="small"
        >
          <div class="d-flex">
            <strong class="me-4">5pm</strong>
            <div>
              <strong>New Icon</strong>
              <div class="text-caption">
                Mobile App
              </div>
            </div>
          </div>
        </v-timeline-item>

        <v-timeline-item
            dot-color="teal-lighten-3"
            size="small"
        >
          <div class="d-flex">
            <strong class="me-4">3-4pm</strong>
            <div>
              <strong>Design Stand Up</strong>
              <div class="text-caption mb-2">
                Hangouts
              </div>
            </div>
          </div>
        </v-timeline-item>

        <v-timeline-item
            dot-color="pink"
            size="small"
        >
          <div class="d-flex">
            <strong class="me-4">12pm</strong>
            <div>
              <strong>Lunch break</strong>
            </div>
          </div>
        </v-timeline-item>

        <v-timeline-item
            dot-color="teal-lighten-3"
            size="small"
        >
          <div class="d-flex">
            <strong class="me-4">9-11am</strong>
            <div>
              <strong>Finish Home Screen</strong>
              <div class="text-caption">
                Web App
              </div>
            </div>
          </div>
        </v-timeline-item>
      </v-timeline>
    </v-card>
  </div>
</template>

<script>
import UserService from "../services/user.service";

export default {
  name: "Moderator-Page",
  data() {
    return {
      content: "",
    };
  },
  mounted() {
    UserService.getModeratorBoard().then(
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