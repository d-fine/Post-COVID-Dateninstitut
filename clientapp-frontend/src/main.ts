import { createApp } from 'vue';
import { createPinia } from 'pinia';
import PrimeVue from 'primevue/config';
import Aura from '@primevue/themes/aura';
import { DialogService } from 'primevue';
import ToastService from 'primevue/toastservice';

import App from './App.vue';
import router from './router';

import '@/assets/main.css';

const app = createApp(App);

app.use(createPinia());
app.use(router);
app.use(DialogService);
app.use(ToastService);
app.use(PrimeVue, {
  ripple: true,
  theme: {
    preset: Aura,
  },
});

app.mount('#app');
