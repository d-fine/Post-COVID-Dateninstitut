import { createRouter, createWebHistory } from 'vue-router';
import HomeView from '../components/pages/HomeView.vue';
import FAQView from '../components/pages/FAQView.vue';
import ImpressumView from '../components/pages/ImpressumView.vue';
import PrivacyView from '../components/pages/PrivacyView.vue';
import WorkshopsView from '../components/pages/WorkshopsView.vue';
import WorkInProgressView from '@/components/pages/WorkInProgressView.vue';
import TeamView from '@/components/pages/about/TeamView.vue';
import TargetView from '@/components/pages/about/TargetView.vue';
import ChallengeView from '@/components/pages/about/ChallengeView.vue';
import GithubView from '@/components/pages/tech/GithubView.vue';
import EurodatView from '@/components/pages/tech/EurodatView.vue';
import DatacatalogInfoView from '@/components/pages/datenkatalog/DatenkatalogInfo.vue';
import DatacatalogSearchView from '@/components/pages/datenkatalog/DatenkatalogSuche.vue';
import InformationDHView from '@/components/pages/researchhub/InformationDHView.vue';
import DataScienceVue from '@/components/pages/researchhub/DataScienceView.vue';
import DataVue from '@/components/pages/researchhub/DataView.vue';

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
          path: 'github',
          name: 'GitHub',
          component: GithubView,
        },
        {
          path: 'eurodat',
          name: 'EuroDat',
          component: EurodatView,
        },
      ],
    },
    {
      path: '/about',
      name: 'About',
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
      ],
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
      path: '/datacatalog',
      name: 'Datenkatalog',
      children: [
        {
          path: 'info',
          name: 'DatacatalogInfo',
          component: DatacatalogInfoView,
        },
        {
          path: 'data',
          name: 'DatacatalogSearch',
          component: DatacatalogSearchView,
        },
      ],
    },
    {
      path: '/researchhub',
      name: 'ResearchHub',
      children: [
        {
          path: 'info',
          name: 'Info',
          component: InformationDHView,
        },
        {
          path: 'datascience',
          name: 'DataScience',
          component: DataScienceVue,
        },
        {
          path: 'markt',
          name: 'Markt',
          component: DataVue,
        },
      ],
    },
    {
      path: '/wip',
      name: 'WIP',
      component: WorkInProgressView,
    },
    {
      path: '/.well-known/security.txt',
      redirect: () => {
        window.location.href = 'https://www.d-fine.com/.well-known/security.txt';
        return '//redirected';
      },
    },
  ],
  scrollBehavior(to, from, savedPosition) {
    if (savedPosition) {
      return savedPosition;
    } else {
      return { top: 0 };
    }
  },
});

export default router;
