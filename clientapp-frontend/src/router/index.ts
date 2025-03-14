import { createRouter, createWebHistory } from 'vue-router'

import HomeView from '@/components/pages/HomeView.vue'
import ExplorerView from '@/components/pages/data/ExplorerView.vue'
import NewUploadView from '@/components/pages/data/NewUploadView.vue'
import DatasetView from '@/components/pages/data/datasets/DatasetView.vue'
import AdminView from '@/components/pages/admin/AdminView.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/admin',
      name: 'admin',
      component: AdminView
    },
    {
      path: '/data',
      name: 'data',
      children: [
        {
          path: 'datasets/id',
          component: DatasetView,
        },
        {
          path: 'explorer',
          name: 'explorer',
          component: ExplorerView
        },
        {
          path: 'new-upload',
          name: 'new-upload',
          component: NewUploadView
        }
      ]
    },
    {
      path: '/',
      name: 'home',
      component: HomeView,
    },
  ],
})

export default router
