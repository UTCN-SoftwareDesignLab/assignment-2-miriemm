<template>
  <v-card>
    <v-bottom-navigation
        :value="value"
        color="primary"
        horizontal
    >
    <v-btn @click="bookPage">
      <span>Books</span>
      <v-icon>mdi-book-open-page-variant</v-icon>
    </v-btn>

    <v-btn @click="userPage">
      <span>Users</span>
      <v-icon>mdi-account-tie</v-icon>
    </v-btn>

    <v-btn @click="reportPage">
      <span>Reports</span>
      <v-icon>mdi-file-chart</v-icon>
    </v-btn>
    </v-bottom-navigation>
  </v-card>
</template>

<script>

import router from "../router";

export default {
  data: () => ({ value: 0 }),
  methods:{
    bookPage() {
      router.push("/books");
    },
    userPage() {
      router.push("/users");
    },
    reportPage(){
      router.push("/reports")
    },
  },
};
</script>

<style scoped></style>
