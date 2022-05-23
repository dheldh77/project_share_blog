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
          title="dheldh77"
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
          <v-list-item prepend-icon="mdi-upload" title="New Post" value="newPost"></v-list-item>
          <v-list-item prepend-icon="mdi-account" title="Category" value="account"></v-list-item>
          <v-list-item prepend-icon="mdi-account-group-outline" title="My Group" value="users"></v-list-item>
          <v-list-item prepend-icon="mdi-star" title="Starred" value="starred"></v-list-item>
        </v-list>
      </v-navigation-drawer>
      <v-main style="height: 0;">
      </v-main>
    </v-layout>
  </v-card>
</template>

<script>
export default {
  name: 'NavBar',

  components: {
  },

  data: () => ({
    loading: false,
    selection: 1,
    drawer: true,
        items: [
          { title: 'New Post', icon: 'mdi-upload' },
          { title: 'My Account', icon: 'mdi-account' },
          { title: 'Users', icon: 'mdi-account-group-outline' },
          { title: 'Starred', icons: 'mdi-star'}
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