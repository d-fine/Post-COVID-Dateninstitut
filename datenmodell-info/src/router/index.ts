import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../components/pages/HomeView.vue'
import FAQView from '../components/pages/FAQView.vue'
import ImpressumView from '../components/pages/ImpressumView.vue'
import PrivacyView from '../components/pages/PrivacyView.vue'
import WorkshopsView from '../components/pages/WorkshopsView.vue'
import WorkInProgressView from '@/components/pages/WorkInProgressView.vue'
import TeamView from '@/components/pages/about/TeamView.vue'
import TargetView from '@/components/pages/about/TargetView.vue'
import ChallengeView from '@/components/pages/about/ChallengeView.vue'
import GitlabView from '@/components/pages/tech/GitlabView.vue'
import EurodatView from '@/components/pages/tech/EurodatView.vue'
import DatenkatalogView from '@/components/pages/DatenkatalogView.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: HomeView,
    },
    {
      path: '/tech',
      name: 'tech',
      children: [
        {
          path: 'gitlab',
          name: 'GitLab',
          component: GitlabView,
        },
        {
          path: 'eurodat',
          name: 'EuroDat',
          component: EurodatView,
        },]
    },
    {
      path: '/about',
      name: 'Team',
      children: [
        {
          path: 'team',
          name: 'Team',
          component: TeamView,
        },
        {
          path: 'challenge',
          name: 'Challenge',
          component: ChallengeView,
        },
        {
          path: 'target',
          name: 'Target',
          component: TargetView,
        },
      ]
    },
    {
      path: '/faq',
      name: 'faq',
      component: FAQView,
    },
    {
      path: '/privacy',
      name: 'Privacy',
      component: PrivacyView,
    },
    {
      path: '/impressum',
      name: 'Impressum',
      component: ImpressumView,
    },
    {
      path: '/workshops',
      name: 'Workshops',
      component: WorkshopsView,
    },
    {
      path: '/datenkatalog',
      name: 'Datenkatalog',
      component: DatenkatalogView
    },
    {
      path: '/wip',
      name: 'WIP',
      component: WorkInProgressView,
    },
    {
      path: '/.well-known/security.txt',
      redirect: (to) => {
        window.location.href = 'https://www.d-fine.com/.well-known/security.txt'
        return '//redirected'
      }
    }
  ],
  scrollBehavior(to, from, savedPosition) {
    if (savedPosition) {
      return savedPosition;
    } else {
      return { top: 0 };
    }
  }
})

export default router
