<template>
  <NavBar />
  <SearchBar />
  <router-view />
</template>

<script>
import NavBar from '@/components/NavBar'
import SearchBar from '@/components/SearchBar'
export default {
  name: 'App',

  components: {
    NavBar,
    SearchBar
},
}
</script>
