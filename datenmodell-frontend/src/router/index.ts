import NewTransactionView from '@/components/pages/NewTransactionView.vue'
import { createRouter, createWebHistory } from 'vue-router'
import ResultsTransactionView from '@/components/pages/ResultsTransactionView.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/data_processing/results',
      name: 'Results',
      component: ResultsTransactionView,
    },
    {
      path: '/data_processing/new_transaction',
      name: 'New Transaction',
      component: NewTransactionView,
    }
  ]
})

export default router
