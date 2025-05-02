import NewTransactionView from '@/components/pages/NewTransactionView.vue';
import { createRouter, createWebHistory } from 'vue-router';
import OverviewTransactionView from '@/components/pages/OverviewTransactionView.vue';
import HomeView from '@/components/HomeView.vue';
import DataCatalogView from '@/components/pages/DataCatalogView.vue';

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/data_processing/overview',
      name: 'Overview',
      component: OverviewTransactionView,
    },
    {
      path: '/data_processing/new_transaction',
      name: 'New Transaction',
      component: NewTransactionView,
    },
    {
      path: '/',
      name: 'Home',
      component: HomeView,
    },
    {
      path: '/datacatalog',
      name: 'Data Catalog',
      component: DataCatalogView,
    },
  ],
});

export default router;
