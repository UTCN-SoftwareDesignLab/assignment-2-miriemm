<template>
  <v-app>
    <v-main>
      <v-container fluid>
        <TopBar v-if="isLoggedIn"></TopBar>
        <NavBarAdminPage v-if="isAdminLogged"></NavBarAdminPage>
        <router-view></router-view>
      </v-container>
    </v-main>
  </v-app>
</template>

<script>
import TopBar from "./components/TopBar";
import NavBarAdminPage from "./components/NavBarAdminPage";
export default {
  name: "App",
  components: { TopBar, NavBarAdminPage },
  data: () => ({
  }),
  computed: {
    isLoggedIn: function () {
      return this.$store.state.auth.status.loggedIn;
    },
    isAdminLogged: function() {
      return this.$store.getters["auth/isAdmin"];
    }
  },
};
</script>
