<template>
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
</template>

<script>
export default {
  name: 'SearchBar',

  components: {
  },
}
</script>