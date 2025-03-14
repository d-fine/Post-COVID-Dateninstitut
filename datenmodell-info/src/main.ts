import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import Aura from '@primevue/themes/aura';
import PrimeVue from 'primevue/config';



import '@/assets/main.css'

const app = createApp(App)

app.use(PrimeVue, {
    theme: {
        preset: Aura, 
        options: {
            darkModeSelector: false 
        }
    }
})
app.use(router)

app.mount('#app')
