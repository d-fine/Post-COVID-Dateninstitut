import '@/assets/main.css'

import { createApp } from 'vue'
import { createPinia } from 'pinia'

import App from './App.vue'
import router from './router'
import DialogService from 'primevue/dialogservice';

import PrimeVue from 'primevue/config';
import ConfirmationService from 'primevue/confirmationservice'
import ToastService from 'primevue/toastservice';
import Aura from '@primevue/themes/aura';

const app = createApp(App)

app.use(createPinia());
app.use(router);
app.use(PrimeVue, {
    theme: {
        preset: Aura
    }
});
app.use(ConfirmationService);
app.use(ToastService);
app.use(DialogService);

app.mount('#app')
