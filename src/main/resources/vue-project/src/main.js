//import './assets/main.css' vue 기본 css 주석처리
import './assets/main2.css'

import { createApp } from 'vue'
import { createPinia } from 'pinia' 

import App from './App.vue'
import router from './router'
import './assets/tailwind.css';
const app = createApp(App)

app.use(createPinia())
app.use(router)

app.mount('#app')
