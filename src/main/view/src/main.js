import { createApp } from 'vue'
import App from './App.vue'
import vuetify from './plugins/vuetify'
import { loadFonts } from './plugins/webfontloader'
import axios from 'axios'
import { router } from "./router/router"

loadFonts()

const app = createApp(App);

app.use(vuetify)
app.use(router)
app.mount('#app')
app.config.globalProperties.$axios = axios;