<template>
  <v-card>
    <v-layout>
      <v-navigation-drawer
        v-model="drawer"
        :rail="rail"
        permanent
        @click="rail = false"
      >
        <v-list-item
          prepend-avatar="https://randomuser.me/api/portraits/men/85.jpg"
          title="John Leider"
        >
          <template v-slot:append>
            <v-btn
              variant="text"
              icon="mdi-chevron-left"
              @click.stop="rail = !rail"
            ></v-btn>
          </template>
        </v-list-item>

        <v-divider></v-divider>

        <v-list density="compact" nav>
          <v-list-item prepend-icon="mdi-home-city" title="Home" value="home"></v-list-item>
          <v-list-item prepend-icon="mdi-account" title="My Account" value="account"></v-list-item>
          <v-list-item prepend-icon="mdi-account-group-outline" title="Users" value="users"></v-list-item>
        </v-list>
      </v-navigation-drawer>
      <v-main style="height: 100vh; margin-bottom: 30px">
        <v-toolbar color="blue-grey-darken-3">
          <v-spacer></v-spacer>

          <v-btn class="text-none" stacked>
            <v-badge dot color="success">
              <v-icon>mdi-home-outline</v-icon>
            </v-badge>
          </v-btn>

          <v-btn class="text-none" stacked>
            <v-icon>mdi-account-multiple-outline</v-icon>
          </v-btn>

          <v-btn class="text-none" stacked>
            <v-icon>mdi-magnify</v-icon>
          </v-btn>

          <v-btn class="text-none" stacked>
            <v-badge content="2" color="error">
              <v-icon>mdi-bell-outline</v-icon>
            </v-badge>
          </v-btn>
          <v-spacer></v-spacer>
        </v-toolbar>
        <router-view />
      </v-main>
    </v-layout>
  </v-card>
</template>

<script>
export default {
  name: 'App',

  components: {
  },

  data: () => ({
    loading: false,
    selection: 1,
    drawer: true,
        items: [
          { title: 'Home', icon: 'mdi-home-city' },
          { title: 'My Account', icon: 'mdi-account' },
          { title: 'Users', icon: 'mdi-account-group-outline' },
        ],
        rail: true,
  }),

  methods: {
    reserve () {
      this.loading = true

      setTimeout(() => (this.loading = false), 2000)
    },
  },
}
</script>
